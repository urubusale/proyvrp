package logica;
import java.util.Collection;

import datatypes.DTAsignacion;
import datatypes.DTRuteo;

public interface IRuteo 
{
	/**
	 * El método se encarga de realizar el ruteo. Se realiza una exploración limitada del espacio de búsqueda y dan soluciones de calidad aceptable en tiempos de cálculo generalmente moderados.
	 * <p>
	 * Realiza la llamada al método <code>rutear</code> de la interfase <code>IRuteo</code>.
	 * <p>
	 * Recibe por parámetro el deposito y la colección de clientes que estan asigandos a ese depositos (<code>DTAsignacion</code>), y la <code>capacidad</code> de los vehiculos.
	 * <p>
	 * Retorna una colección de <code>DTRuteo</code>. Cada <code>DTRuteo</code> contiene el deposito, una lista de clientes que representa la ruta y el costo de la misma.
	 *
	 * @param	dt <code>DTAsignacion</code> donde contiene el deposito y la colección de clientes que estan asigandos a ese depositos.
	 * @param	capacidad Capacidad de los vehiculos.
	 * @return      Devuelve una colección de <code>DTRuteo</code>.
	 * 
	 */
	public Collection<DTRuteo> rutear(DTAsignacion dt,int capacidad);
	
	/**
	 * El método se encarga de realizar el ruteo aplicando el algoritmo de ruteo y optimiza el conjunto de rutas solución. 
	 * 
	 * @param	dt <code>DTAsignacion</code> donde contiene el deposito y la colección de clientes que estan asigandos a ese depositos.
	 * @param	capacidad Capacidad de los vehiculos.
	 * @return      Devuelve una colección de <code>DTRuteo</code>.
	 * 
	 */
	public Collection<DTRuteo> rutear4opt(DTAsignacion dt,int capacidad);
	
	/**
	 * El método se encarga de realizar el ruteo aplicando el algoritmo de ruteo y optimiza el conjunto de rutas solución (Intraroute). 
	 * 
	 * @param	dt <code>DTAsignacion</code> donde contiene el deposito y la colección de clientes que estan asigandos a ese depositos.
	 * @param	capacidad Capacidad de los vehiculos.
	 * @return      Devuelve una colección de <code>DTRuteo</code>.
	 * 
	 */
	public Collection<DTRuteo> post2intraroute(DTAsignacion dt,int capacidad);

}
