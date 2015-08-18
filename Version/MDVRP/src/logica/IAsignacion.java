package logica;
import java.util.Collection;
import datatypes.DTAsignacion;
import datatypes.DTDepositoVRP;

public interface IAsignacion 
{
	/**
	 * La interfase del metodo se encarga de realizar la asignación sin capacidades. No concidera las capacidades en los Depositos.
	 * <p>
	 * Realiza la llamada al método de asignar a través de urgencias. La urgencia o prioridad que tienen los clientes determina la forma de asignarlos. Un cliente con más urgencia se asigna primero.
	 * <p>
	 * Recibe por parámetro <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * <p>
	 * Retorna una colección de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colección de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * @return      Devuelve una colección de <code>DTAsignacion</code>.
	 * 
	 */
	public Collection<DTAsignacion> asignar(DTDepositoVRP d);
	
	/**
	 * La interfase del metodo se encarga de realizar la asignación con capacidades. Concidera las capacidades en los Depositos.
	 * <p>
	 * Realiza la llamada al método de asignar a través de urgencias. La urgencia o prioridad que tienen los clientes determina la forma de asignarlos. Un cliente con más urgencia se asigna primero.
	 * <p>
	 * Recibe por parámetro <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * <p>
	 * Retorna una colección de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colección de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * @return      Devuelve una colección de <code>DTAsignacion</code>.. 
	 * 
	 */
	public Collection<DTAsignacion> asignarCap(DTDepositoVRP d);
	
	/**
	 * La interfase del metodo se encarga de realizar la asignación Hibrida para depósitos con capacidad limitada (Algoritmo 1).
	 * <p>
	 * Realiza la llamada al método de la asignación Hibrida para depósitos con capacidad limitada (Algoritmo 1).
	 * <p>
	 * Recibe por parámetro <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * <p>
	 * Retorna una colección de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colección de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * @return      Devuelve una colección de <code>DTAsignacion</code>.. 
	 * 
	 */
	public Collection<DTAsignacion> asignarCap2(DTDepositoVRP d);
	
	/**
	 * La interfase del metodo se encarga de realizar la asignación Hibrida para depósitos con capacidad limitada (Algoritmo 2).
	 * <p>
	 * Realiza la llamada al método de la asignación Hibrida para depósitos con capacidad limitada (Algoritmo 2).
	 * <p>
	 * Recibe por parámetro <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * <p>
	 * Retorna una colección de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colección de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * @return      Devuelve una colección de <code>DTAsignacion</code>.. 
	 * 
	 */
	public Collection<DTAsignacion> asignarCap22(DTDepositoVRP d);

}
