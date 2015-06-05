package logica.ruteo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import util.Distancia;
import util.Ruta;
import datatypes.DTAsignacion;
import datatypes.DTDistancia;
import datatypes.DTNodo;
import datatypes.DTRuteo;
import logica.IRuteo;

public class ClarkWright implements IRuteo
{
	static private List<DTDistancia> listDistance;
	static private ClarkWright instancia=null;
	static public ClarkWright getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new ClarkWright();
			return instancia;
		}else return instancia;
	}
	private ClarkWright()
	{
	}
	
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
//		System.out.println("ruteo "+dt.getDeposito().getId());
		Collection<DTRuteo> solucion = new ArrayList<DTRuteo>();
		DTNodo deposito=dt.getDeposito();
		Iterator<DTNodo> it=dt.getClientes().iterator();
		DTRuteo constr;
		
		//Paso 1 (inicialización). Para cada cliente i construir la ruta (0, i, 0).
		while(it.hasNext())
		{
			
			DTNodo prox=it.next();
		//	System.out.println("inicio "+prox.getId());
			constr=new DTRuteo(deposito);
			constr.agregarCliente(prox);
			constr.setCosto(util.Distancia.getInstancia().getDistancia(deposito, prox)*2);
			solucion.add(constr);
		}

		// Paso 2 Cálculo de ahorros
		calcularAhorros (dt);
		
	//	System.out.println("Solucion Inicial: \n"+solucion.toString());
		
		for (DTDistancia distancia : listDistance) {
	        if (distancia.getDistancia() > 0) { 

	        	DTNodo nodo_i = distancia.getNodo1();
	        	DTNodo nodo_j = distancia.getNodo2();
	  //      	System.out.println("nodoi nodoj "+nodo_i.getId()+" "+nodo_j.getId());
	        	DTRuteo ruta1 = Ruta.getInstancia().getRutaDondeClienteEsUltimo(solucion, nodo_i);
	        	DTRuteo ruta2 = Ruta.getInstancia().getRutaDondeClienteEsPrimero(solucion, nodo_j);
	            if ((ruta1!=null) & (ruta2!=null)) 
	            {
	            	if(!Ruta.getInstancia().sonMismaRuta(ruta1,ruta2)) 
	                if (Ruta.getInstancia().getCarga(ruta1) + Ruta.getInstancia().getCarga(ruta2) <= capacidad) 
	                { 
	                //	 Si el merge es Factible
	                //	if(Ruta.getInstancia().sonMismaRuta(ruta1,ruta2)) System.out.println("son la misma ruta!!!! en el merge");
	                	solucion = Ruta.getInstancia().mergeRutas(solucion, ruta1, ruta2);
	                }
	            }
	        }
	    }		
		return solucion;
	}
	

}
