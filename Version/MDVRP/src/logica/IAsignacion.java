package logica;
import java.util.Collection;
import datatypes.DTAsignacion;
import datatypes.DTDepositoVRP;

public interface IAsignacion 
{
	/**
	 * La interfase del metodo se encarga de realizar la asignaci�n sin capacidades. No concidera las capacidades en los Depositos.
	 * <p>
	 * Realiza la llamada al m�todo de asignar a trav�s de urgencias. La urgencia o prioridad que tienen los clientes determina la forma de asignarlos. Un cliente con m�s urgencia se asigna primero.
	 * <p>
	 * Recibe por par�metro <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * <p>
	 * Retorna una colecci�n de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colecci�n de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * @return      Devuelve una colecci�n de <code>DTAsignacion</code>.
	 * 
	 */
	public Collection<DTAsignacion> asignar(DTDepositoVRP d);
	
	/**
	 * La interfase del metodo se encarga de realizar la asignaci�n con capacidades. Concidera las capacidades en los Depositos.
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
	public Collection<DTAsignacion> asignarCap(DTDepositoVRP d);
	
	/**
	 * La interfase del metodo se encarga de realizar la asignaci�n Hibrida para dep�sitos con capacidad limitada (Algoritmo 1).
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
	public Collection<DTAsignacion> asignarCap2(DTDepositoVRP d);
	
	/**
	 * La interfase del metodo se encarga de realizar la asignaci�n Hibrida para dep�sitos con capacidad limitada (Algoritmo 2).
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
	public Collection<DTAsignacion> asignarCap22(DTDepositoVRP d);

}
