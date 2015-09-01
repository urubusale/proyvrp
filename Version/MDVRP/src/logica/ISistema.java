package logica;
import java.util.Collection;

import datatypes.DTAsignacion;
import datatypes.DTDepositoVRP;
import datatypes.DTRuteo;

public interface ISistema 
{
	/** 
	 * Genera un nuevo<code>DTDepositoVRP</code> con los datos <code>datos</code>.
	 * <p>
	 * Realiza la llamada a 
	 * <code>setNAME</code>, 
	 * <code>setCAPACITY</code>, 
	 * <code>setCOMMENT</code>, 
	 * <code>setDIMENSION</code>, 
	 * <code>setDISPLAY_DATA_TYPE</code>, 
	 * <code>setEDGE_WEIGHT_FORMAT</code>, 
	 * <code>setEDGE_WEIGHT_TYPE</code>, 
	 * <code>setTYPE</code>, 
	 * <code>setNodos</code> 
	 * de <code>DTDepositoVRP</code>
	 * 
	 * @param		datos Colecci�n de String que contiene los datos para generar un <code>DTDepositoVRP</code>.	 * 
	 * @return      Retorna un nuevo<code>DTDepositoVRP</code> con los datos <code>datos</code> pasados por parametro. 
	 * @throws		<code>Exception</code> si hay error al leer el archivo.
	 * 
	 */
	public DTDepositoVRP ImportarDepositoVRP(Collection<String> datos) throws Exception;
	
	/**
	 * El m�todo se encarga de realizar la asignaci�n sin capacidades. No concidera las capacidades en los Depositos.
	 * <p>
	 * Realiza la llamada al m�todo <code>asignar</code> de la interfase <code>IAsignacion</code>.
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
	 * El m�todo se encarga de realizar la asignaci�n con capacidades. Concidera las capacidades en los Depositos.
	 * <p>
	 * Realiza la llamada al m�todo <code>asignarCap</code> de la interfase <code>IAsignacion</code>.
	 * <p>
	 * Recibe por par�metro <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * <p>
	 * Retorna una colecci�n de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colecci�n de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * @return      Devuelve una colecci�n de <code>DTAsignacion</code>.
	 * 
	 */
	public Collection<DTAsignacion> asignarCap(DTDepositoVRP d);
	
	/**
	 * El metodo se encarga de realizar la asignaci�n Hibrida para dep�sitos con capacidad limitada (Algoritmo 1).
	 * <p>
	 * Realiza la llamada al m�todo <code>asignarCap2</code> de la interfase <code>IAsignacion</code>.
	 * <p>
	 * Recibe por par�metro <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * <p>
	 * Retorna una colecci�n de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colecci�n de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * @return      Devuelve una colecci�n de <code>DTAsignacion</code>.
	 * 
	 */
	public Collection<DTAsignacion> asignarCap2(DTDepositoVRP d);
	
	/**
	 * El metodo se encarga de realizar la asignaci�n Hibrida para dep�sitos con capacidad limitada (Algoritmo 2).
	 * <p>
	 * Realiza la llamada al m�todo <code>asignarCap22</code> de la interfase <code>IAsignacion</code>.
	 * <p>
	 * Recibe por par�metro <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * <p>
	 * Retorna una colecci�n de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colecci�n de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * @return      Devuelve una colecci�n de <code>DTAsignacion</code>.
	 * 
	 */
	public Collection<DTAsignacion> asignarCap22(DTDepositoVRP d);

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
	
	/**
	 * El metodo se encarga de realizar el ruteo aplicando el algoritmo de ruteo y optimiza el conjunto de rutas soluci�n. 
	 * 
	 * @param	dt <code>DTAsignacion</code> donde contiene el deposito y la colecci�n de clientes que estan asigandos a ese depositos.
	 * @param	capacidad Capacidad de los vehiculos.
	 * @return      Devuelve una colecci�n de <code>DTRuteo</code>.
	 * 
	 */
	public Collection<DTRuteo> rutearopt(DTAsignacion dt,int capacidad);
	
	public Collection<DTRuteo> post2intraroute(DTAsignacion dt,int capacidad);
	
	/**
	 * Retorna el estado consulta. 
	 * 
	 * @return      Devuelve el estado consulta.
	 * 
	 */
	public int getEstadoConsulta();
	
	/**
	 * Setea el estado consulta como Abortado. 
	 * 
	 */
	public void setAbortoEstadoConsulta();
	
	/**
	 * Setea el estado consulta como Finalizado Ok. 
	 * 
	 */
	public void setFinalizadoOkEstadoConsulta();
	
	/**
	 * Inicializa el estado consulta del algoritmo. 
	 * <p>
	 * Setea el progreso de avance igual 0, los mensajes vacios y arreglo de resaltados vacia.
	 * 
	 */
	public void setInicioEstadoConsulta();
	
	/**
	 * Retorna el progreso de avance del algoritmo. 
	 * 
	 * @return      Devuelve el progreso de avance del algoritmo.
	 * 
	 */
	public int getPorgresoDeAvance();
	
}
