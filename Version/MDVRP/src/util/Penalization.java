package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.soap.Detail;

import logica.asignacion.urgencias.Cliente;
import logica.asignacion.urgencias.Deposito;
import datatypes.DTAsignacion;
import datatypes.DTNodo;
import datatypes.DTRuteo;

public class Penalization {
	static private double inicio;
	static private Penalization instancia=null;
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresión con una lista de argumentos vacía. 
	 * La clase se inicializa si ya no se ha inicializado. 
	 * 
	 * @return      Una nueva instancia de la clase
	 */
	static public Penalization getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Penalization();
			return instancia;
		}else return instancia;
	}
	
	private Penalization()
	{
		inicio=0;
	}
	
	/**
	 * En . 
	 * 
	 * @param		ListRutas Colección de rutas.
	 * @param		nodo_i Cliente que deseamos encontrar como primer cliente en una ruta.
	 * @return  	Devuelve el valor de la ruta donde el cliente <code>nodo_i</code> es el primer cliente en la ruta.
	 * <p>
	 * En caso de encontrar ninguna ruta donde <code>nodo_i</code> no es el primer cliente en la ruta devuelve <code>null</code>.
	 * 
	 */
	public double getCalculoPenalidad (Collection<DTAsignacion> ListAsignacion){
		
		int sumatoria=0;

		Iterator<DTAsignacion> itd=ListAsignacion.iterator();
		while(itd.hasNext())
		{
			DTAsignacion dep=itd.next();
		    
		    Iterator<DTNodo> itc=dep.getClientes().iterator();
		    int CapClientes = 0; 
			while(itc.hasNext())
			{
				DTNodo cliente=itc.next();
				CapClientes += cliente.getDemanda();
			}
			if (CapClientes > dep.getDeposito().getDemanda())
				sumatoria = sumatoria + (CapClientes - dep.getDeposito().getDemanda());	    
		}
		
		System.out.println("El calculo de la penalidad para el algoritmo es "+ sumatoria);
		inicio = sumatoria;
		return sumatoria;
	}
	
	public double getPenalidad (Collection<DTAsignacion> ListAsignacion){
		return inicio;
	}
	
	
}
