package logica;
import java.util.Collection;

import datatypes.DTAsignacion;
import datatypes.DTRuteo;

public interface IRuteo 
{
	/**
	 * El m�todo se encarga de realizar el ruteo. Se realiza una exploraci�n limitada del espacio de b�usqueda y dan soluciones de calidad aceptable en tiempos de c�lculo generalmente moderados.
	 * <p>
	 * Realiza la llamada al m�todo <code>rutear</code> de la interfase <code>IRuteo</code>.
	 * <p>
	 * Recibe por par�metro el deposito y la colecci�n de clientes que estan asigandos a ese depositos (<code>DTAsignacion</code>), y la <code>capacidad</code> de los vehiculos.
	 * <p>
	 * Retorna una colecci�n de <code>DTRuteo</code>. Cada <code>DTRuteo</code> contiene el deposito, una lista de clientes que representa la ruta y el costo de la misma.
	 *
	 * @param	dt <code>DTAsignacion</code> donde contiene el deposito y la colecci�n de clientes que estan asigandos a ese depositos.
	 * @param	capacidad Capacidad de los vehiculos.
	 * @return      Devuelve una colecci�n de <code>DTRuteo</code>.
	 * 
	 */
	public Collection<DTRuteo> rutear(DTAsignacion dt,int capacidad);
	
	public Collection<DTRuteo> rutear4opt(DTAsignacion dt,int capacidad);
	
	public Collection<DTRuteo> post2intraroute(DTAsignacion dt,int capacidad);

}
