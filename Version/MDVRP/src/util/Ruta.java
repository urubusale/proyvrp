package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import datatypes.DTNodo;
import datatypes.DTRuteo;

public class Ruta {
	static private Ruta instancia=null;
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresión con una lista de argumentos vacía. 
	 * La clase se inicializa si ya no se ha inicializado. 
	 * 
	 * @return      Una nueva instancia de la clase
	 */
	static public Ruta getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Ruta();
			return instancia;
		}else return instancia;
	}
	
	/**
	 * Retorna la ruta donde el cliente <code>nodo_i</code> es el ultimo cliente en la ruta. 
	 * 
	 * @param		ListRutas Colección de rutas.
	 * @param		nodo_i Cliente que deseamos encontrar como último cliente en una ruta.
	 * @return  	Devuelve el valor de la ruta donde el cliente <code>nodo_i</code> es el ultimo cliente en la ruta.
	 * <p>
	 * En caso de encontrar ninguna ruta donde <code>nodo_i</code> no es el ultimo cliente en la ruta devuelve <code>null</code>.
	 * 
	 */
	public DTRuteo getRutaDondeClienteEsUltimo(Collection<DTRuteo> ListRutas, DTNodo nodo_i){
		DTRuteo rutaEncontrada = null;
		for (DTRuteo rutaActual : ListRutas) {
			ArrayList<DTNodo> rutaNodo = (ArrayList<DTNodo>) rutaActual.getRuta();
			if (!rutaNodo.isEmpty()) {
				if (rutaNodo.get(rutaNodo.size()-1).getId()==nodo_i.getId()) {
					rutaEncontrada = rutaActual;
				}
			}
		}
		return rutaEncontrada;
	}
	
	/**
	 * Retorna la ruta donde el cliente <code>nodo_i</code> es el primer cliente en la ruta. 
	 * 
	 * @param		ListRutas Colección de rutas.
	 * @param		nodo_i Cliente que deseamos encontrar como primer cliente en una ruta.
	 * @return  	Devuelve el valor de la ruta donde el cliente <code>nodo_i</code> es el primer cliente en la ruta.
	 * <p>
	 * En caso de encontrar ninguna ruta donde <code>nodo_i</code> no es el primer cliente en la ruta devuelve <code>null</code>.
	 * 
	 */
	public DTRuteo getRutaDondeClienteEsPrimero(Collection<DTRuteo> ListRutas, DTNodo nodo_i){
		DTRuteo rutaEncontrada = null;
		for (DTRuteo rutaActual : ListRutas) {
			ArrayList<DTNodo> rutaNodo = (ArrayList<DTNodo>) rutaActual.getRuta();
			if (!rutaNodo.isEmpty()) {
				if (rutaNodo.get(0).getId()==nodo_i.getId()) {
					rutaEncontrada = rutaActual;
				}
			}
		}
		return rutaEncontrada;
	}

	/**
	 * Remueve las rutas <code>ruta1</code> y <code>ruta2</code> la coleccion de rutas <code>solucion</code>.
	 * <p>
	 * Reliza el merge entre las rutas <code>ruta1</code> y <code>ruta2</code> y la ruta unida se agrega a la colección de rutas <code>solucion</code>. Se actualiza el costo de la nueva ruta.
	 * <p>
	 * Retorna la nueva ruta <code>solucion</code>.
	 * 
	 * @param		solucion Colección de rutas de la solución.
	 * @param		ruta1 Ruta a realizar el merge.
	 * @param		ruta2 Ruta a realizar el merge.
	 * @return  	Devuelve la colección de rutas producto de realizar el merge entre la <code>ruta1</code> y la <code>ruta2</code>.
	 * 
	 */
	public Collection<DTRuteo> mergeRutas(Collection<DTRuteo> solucion, DTRuteo ruta1, DTRuteo ruta2) {
		
		// Remuevo las rutas de la solucion
		solucion.remove(ruta1);
		solucion.remove(ruta2);
		
		DTNodo deposito = ruta1.getDeposito();
		DTRuteo RutaUnida = new DTRuteo(deposito);
		// Ruta 1
		for (DTNodo nodoRuta : ruta1.getRuta()) {
			RutaUnida.agregarCliente(nodoRuta);
		}
		// Ruta 2
		for (DTNodo nodoRuta : ruta2.getRuta()) {
			RutaUnida.agregarCliente(nodoRuta);
		}
		
		setCosto(RutaUnida);
		solucion.add(RutaUnida);
		return solucion;
	}
	
	/**
	 * Calcula y setea el costo de una ruta.
	 *  
	 * @param		dt Ruta a calcular y setar el costo.
	 *  
	 */
	public void setCosto(DTRuteo dt)
	{
		DTNodo dep=dt.getDeposito();
		Iterator<DTNodo> it=dt.getRuta().iterator();
		DTNodo previo=dep;
		int costo=0;
		while(it.hasNext())
		{
			DTNodo n=it.next();
			costo=costo+util.Distancia.getInstancia().getDistancia(previo, n);
			previo=n;
		}
		costo=costo+util.Distancia.getInstancia().getDistancia(previo, dep);
		dt.setCosto(costo);
		
	}
	
	/**
	 * Retorna la demanda de todos los cliente que estan en la ruta <code>dt</code>. 
	 * 
	 * @return      Devuelve el valor de la demanda de todos los cliente que estan en la ruta <code>dt</code>.
	 * 
	 */
	public int getCarga(DTRuteo dt)
	{
		DTNodo dep=dt.getDeposito();
		Iterator<DTNodo> it=dt.getRuta().iterator();
		int carga=0;
		while(it.hasNext())
		{
			DTNodo n=it.next();
			carga=carga+n.getDemanda();
		}
		return carga;
	}
	
	/**
	 * Retorna <code>true</code> si las ruta <code>uno</code> y ruta <code>dos</code> son las mismas. 
	 * 
	 * @param	uno Ruta a comparar.
	 * @param	dos Ruta a comparar.
	 * @return      Devuelve true <code>true</code> si las ruta <code>uno</code> y ruta <code>dos</code> son las mismas.
	 * 
	 */
	public boolean sonMismaRuta(DTRuteo uno,DTRuteo dos)
	{
		if(uno.getDeposito().getId()==dos.getDeposito().getId())
		{
			if(uno.getRuta().size()==00&&dos.getRuta().size()==0)
			{
				return true;
				
			}else
			{
				if(uno.getRuta().size()>0&&dos.getRuta().size()>0)
				{
					if(uno.getRuta().iterator().next().getId()==dos.getRuta().iterator().next().getId())return true;
					else return false;
				}else return false;
			}
		}else return false;
	}
	
	/**
	 * Retorna el costo de la colección de rutas. 
	 * 
	 * @param	col Colección de rutas a calcular el costo. 
	 * @return      Devuelve el costo de la colección de rutas.
	 * 
	 */
	public int getCostoCW(Collection<DTRuteo> col,int cap)
	{
		int costo=0;
		Iterator<DTRuteo> it=col.iterator();
		while(it.hasNext())
		{
			DTRuteo dtr=it.next();
			this.setCosto(dtr);
			costo=costo+dtr.getCosto();
		}
		return costo;
	}
}
