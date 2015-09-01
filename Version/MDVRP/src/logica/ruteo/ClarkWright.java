package logica.ruteo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import util.Config;
import util.Distancia;
import util.Ruta;
import datatypes.DTAsignacion;
import datatypes.DTDistancia;
import datatypes.DTNodo;
import datatypes.DTRuteo;
import logica.Fabrica;
import logica.IRuteo;
import logica.ruteo.opt.Landainterchange;
//import logica.ruteo.opt.Landainterchange;

public class ClarkWright implements IRuteo
{
	static private List<DTDistancia> listDistance;
	static private ClarkWright instancia=null;
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresión con una lista de argumentos vacía. 
	 * La clase se inicializa si ya no se ha inicializado. 
	 * 
	 * @return      Una nueva instancia de la clase
	 */
	static public ClarkWright getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new ClarkWright();
			return instancia;
		}else return instancia;
	}
	
	/** 
	 * Constructor por defecto.
	 * 
	 */
	private ClarkWright()
	{
	}
	
	/**
	 * Realiza el Cálculo de ahorros. 
	 * <p>
	 * Calcula <code>sij</code> para cada par de clientes <code>i</code> y <code>j</code>.
	 * <p>
	 * <code>sij = ci0 + c0j - cij</code>
	 *
	 * @param	DTAsignacion con el deposito <code>d</code> y la colección de clientes <code>cs</code> 
	 * 
	 */
	// Cálculo de ahorros - Calcular sij para cada par de clientes i y j.
	//sij = ci0 + c0j - cij
	private void calcularAhorros (DTAsignacion dt){
		listDistance = new ArrayList<DTDistancia>();
		DTNodo deposito=dt.getDeposito();
		Iterator<DTNodo>it=dt.getClientes().iterator();
		while(it.hasNext())
		{
			DTNodo nodo1=it.next();
			Iterator<DTNodo> it2=dt.getClientes().iterator();
			while(it2.hasNext())
			{
				DTNodo nodo2=it2.next();
				if (nodo1.getId()!= nodo2.getId())
				{					
					int ci0 = Distancia.getInstancia().getDistancia(nodo1, deposito);
					int c0j = Distancia.getInstancia().getDistancia(deposito, nodo2);
					int cij = Distancia.getInstancia().getDistancia(nodo1, nodo2);
					DTDistancia distancias = new DTDistancia(nodo1, nodo2, ci0 + c0j - cij);
					listDistance.add(distancias);
					Collections.sort(listDistance);
					Collections.reverse(listDistance);
				}
			}
		}
				
	}
	
	@Override
	public Collection<DTRuteo> rutear(DTAsignacion dt, int capacidad) 
	{
		Collection<DTRuteo> solucion = new ArrayList<DTRuteo>();
		DTNodo deposito=dt.getDeposito();
		Iterator<DTNodo> it=dt.getClientes().iterator();
		DTRuteo constr;
		
		//Paso 1 (Inicialización). Para cada cliente i construir la ruta (0, i, 0).
		while(it.hasNext())
		{
			
			DTNodo prox=it.next();
			constr=new DTRuteo(deposito);
			constr.agregarCliente(prox);
			constr.setCosto(util.Distancia.getInstancia().getDistancia(deposito, prox)*2);
			solucion.add(constr);
		}

		// Paso 2 Cálculo de ahorros
		calcularAhorros (dt);
		
		for (DTDistancia distancia : listDistance) {
	        if (distancia.getDistancia() > 0) { 

	        	DTNodo nodo_i = distancia.getNodo1();
	        	DTNodo nodo_j = distancia.getNodo2();
	        	DTRuteo ruta1 = Ruta.getInstancia().getRutaDondeClienteEsUltimo(solucion, nodo_i);
	        	DTRuteo ruta2 = Ruta.getInstancia().getRutaDondeClienteEsPrimero(solucion, nodo_j);
	            if ((ruta1!=null) & (ruta2!=null)) 
	            {
	            	if(!Ruta.getInstancia().sonMismaRuta(ruta1,ruta2)) 
	                if (Ruta.getInstancia().getCarga(ruta1) + Ruta.getInstancia().getCarga(ruta2) <= capacidad) 
	                {
	                //	Si el merge es Factible
	                	solucion = Ruta.getInstancia().mergeRutas(solucion, ruta1, ruta2);
	                }
	            }
	        }
	    }
		return solucion;
	}
	
	/**
	 * El metodo se encarga de realizar el ruteo aplicando el algoritmo de Clark & Wright y optimiza el conjunto de rutas solución. 
	 * 
	 * @param	dt <code>DTAsignacion</code> donde contiene el deposito y la colección de clientes que estan asigandos a ese depositos.
	 * @param	capacidad Capacidad de los vehiculos.
	 * @return      Devuelve una colección de <code>DTRuteo</code>.
	 * 
	 */
	public Collection<DTRuteo> rutear4opt(DTAsignacion dt,int capacidad)
	{
		ArrayList<DTRuteo> cw=new ArrayList<DTRuteo>(this.rutear(dt, capacidad));
		ArrayList<DTRuteo> opt=new ArrayList<DTRuteo>();
		Iterator<DTRuteo> it=cw.iterator();
		while(it.hasNext())
		{
			DTRuteo dta=it.next();
			DTRuteo nuevo=logica.ruteo.opt.Route14opt.getInstancia().opt4(dta,Config.getInstancia().getLambdaOpt());
			opt.add(nuevo);
		}
		return opt;
	}
	
	/**
	 * El metodo se encarga de realizar el ruteo aplicando el algoritmo de Clark & Wright y optimiza el conjunto de rutas solución aplicando Lambda-Intercambio. 
	 * 
	 * @param	dt <code>DTAsignacion</code> donde contiene el deposito y la colección de clientes que estan asigandos a ese depositos.
	 * @param	capacidad Capacidad de los vehiculos.
	 * @return      Devuelve una colección de <code>DTRuteo</code>.
	 * 
	 */
	public Collection<DTRuteo> post2intraroute(DTAsignacion dt,int capacidad)
	{
		Collection<DTRuteo> asig=Fabrica.getInstancia().getRuteo().rutear4opt(dt, capacidad);
		return Landainterchange.getInstancia().route2(asig,capacidad);
	}
}
