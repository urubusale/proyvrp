package util;

import java.util.Collection;
import java.util.Iterator;

import datatypes.DTAsignacion;
import datatypes.DTNodo;

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
	
	/** 
	 * Constructor por defecto.
	 * 
	 */
	private Penalization()
	{
		inicio=0;
	}
	
	/**
	 * Realiza el cálculo de la penalidad para el algoritmo.
	 * 
	 * @param		ListAsignacion Colección de <code>DTAsignacion</code>.
	 * @return  	Devuelve el valor del cálculo de la penalidad para el algoritmo.
	 * 
	 */
	public double getCalculoPenalidad (Collection<DTAsignacion> ListAsignacion){
		
		int sumatoria=0;
		int captotal=0;
		
		Iterator<DTAsignacion> itd=ListAsignacion.iterator();
		while(itd.hasNext())
		{
			DTAsignacion dep=itd.next();
		    
		    Iterator<DTNodo> itc=dep.getClientes().iterator();
		    int CapClientes = 0; 
		    captotal += dep.getDeposito().getDemanda();
			while(itc.hasNext())
			{
				DTNodo cliente=itc.next();
				CapClientes += cliente.getDemanda();
			}
			if (CapClientes > dep.getDeposito().getDemanda())
				sumatoria = sumatoria + (CapClientes - dep.getDeposito().getDemanda());	    
		}
		
		System.out.println("El cálculo de la penalidad para el algoritmo es: "+ sumatoria*100/captotal +"%");
		inicio = sumatoria;
		return (sumatoria*100/captotal);
	}
	
	/**
	 * Retorna el valor de la penalidad para el algoritmo.
	 * 
	 * @param		ListAsignacion Colección de <code>DTAsignacion</code>.
	 * @return  	Devuelve el valor del cálculo de la penalidad para el algoritmo.
	 * 
	 */
	public double getPenalidad (Collection<DTAsignacion> ListAsignacion){
		return inicio;
	}
}
