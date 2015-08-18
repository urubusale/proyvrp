package logica.asignacion;
import java.util.Collection;

import logica.IAsignacion;
import logica.asignacion.urgencias.Urgencias;
import logica.asignacion.urgencias.UrgenciasCap;
import logica.asignacion.urgencias.UrgenciasCap2;
import logica.asignacion.urgencias.UrgenciasCap22;
import datatypes.*;

public class Asignacion implements IAsignacion
{
	static private Asignacion instancia=null;
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresi�n con una lista de argumentos vac�a. 
	 * La clase se inicializa si ya no se ha inicializado. 
	 * 
	 * @return      Una nueva instancia de la clase
	 */
	static public Asignacion getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Asignacion();
			return instancia;
		}else return instancia;
	}
	
	/** 
	 * Constructor por defecto.
	 * 
	 */
	private Asignacion()
	{
	}
	
	/**
	 * El metodo se encarga de realizar la asignaci�n sin capacidades. No concidera las capacidades en los Depositos.
	 * <p>
	 * Realiza la llamada al m�todo de asignar a trav�s de urgencias. La urgencia o prioridad que tienen los clientes determina la forma de asignarlos. Un cliente con m�s urgencia se asigna primero.
	 * <p>
	 * Recibe por par�metro <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * <p>
	 * Retorna una colecci�n de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colecci�n de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * @return      Devuelve una colecci�n de <code>DTAsignacion</code>.. 
	 * 
	 */
	public Collection<DTAsignacion> asignar(DTDepositoVRP d)
	{
		return Urgencias.getInstancia().asignar(d);		
	}

	/**
	 * El metodo se encarga de realizar la asignaci�n con capacidades. Concidera las capacidades en los Depositos.
	 * <p>
	 * Realiza la llamada al m�todo de asignar a trav�s de urgencias. La urgencia o prioridad que tienen los clientes determina la forma de asignarlos. Un cliente con m�s urgencia se asigna primero.
	 * <p>
	 * Recibe por par�metro <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * <p>
	 * Retorna una colecci�n de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colecci�n de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * @return      Devuelve una colecci�n de <code>DTAsignacion</code>.. 
	 * 
	 */
	public Collection<DTAsignacion> asignarCap(DTDepositoVRP d)
	{
		return UrgenciasCap.getInstancia().asignar(d);		
	}
	
	/**
	 * El metodo se encarga de realizar la asignaci�n Hibrida para dep�sitos con capacidad limitada (Algoritmo 1).
	 * <p>
	 * Realiza la llamada al m�todo de la asignaci�n Hibrida para dep�sitos con capacidad limitada (Algoritmo 1).
	 * <p>
	 * Recibe por par�metro <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * <p>
	 * Retorna una colecci�n de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colecci�n de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * @return      Devuelve una colecci�n de <code>DTAsignacion</code>.. 
	 * 
	 */
	public Collection<DTAsignacion> asignarCap2(DTDepositoVRP d) //SEGUIR ACA
	{
		return UrgenciasCap2.getInstancia().asignar(d);		
	}

	/**
	 * El metodo se encarga de realizar la asignaci�n Hibrida para dep�sitos con capacidad limitada (Algoritmo 2).
	 * <p>
	 * Realiza la llamada al m�todo de la asignaci�n Hibrida para dep�sitos con capacidad limitada (Algoritmo 2).
	 * <p>
	 * Recibe por par�metro <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * <p>
	 * Retorna una colecci�n de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colecci�n de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * @return      Devuelve una colecci�n de <code>DTAsignacion</code>.. 
	 * 
	 */
	public Collection<DTAsignacion> asignarCap22(DTDepositoVRP d)
	{
		return UrgenciasCap22.getInstancia().asignar(d);		
	}
}


