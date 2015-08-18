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
	 * @param		<code>datos</code> Colección de String que contiene los datos para generar un <code>DTDepositoVRP</code>.	 * 
	 * @return      Retorna un nuevo<code>DTDepositoVRP</code> con los datos <code>datos</code> pasados por parametro. 
	 * @throws		<code>Exception</code> si hay error al leer el archivo.
	 * 
	 */
	public DTDepositoVRP ImportarDepositoVRP(Collection<String> datos) throws Exception;
	
	/**
	 * El método se encarga de realizar la asignación sin capacidades. No concidera las capacidades en los Depositos.
	 * <p>
	 * Realiza la llamada al método <code>asignar</code> de la interfase <code>IAsignacion</code>.
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
	 * El método se encarga de realizar la asignación con capacidades. Concidera las capacidades en los Depositos.
	 * <p>
	 * Realiza la llamada al método <code>asignarCap</code> de la interfase <code>IAsignacion</code>.
	 * <p>
	 * Recibe por parámetro <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * <p>
	 * Retorna una colección de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colección de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * @return      Devuelve una colección de <code>DTAsignacion</code>.
	 * 
	 */
	public Collection<DTAsignacion> asignarCap(DTDepositoVRP d);
	
	/**
	 * El metodo se encarga de realizar la asignación Hibrida para depósitos con capacidad limitada (Algoritmo 1).
	 * <p>
	 * Realiza la llamada al método <code>asignarCap2</code> de la interfase <code>IAsignacion</code>.
	 * <p>
	 * Recibe por parámetro <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * <p>
	 * Retorna una colección de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colección de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * @return      Devuelve una colección de <code>DTAsignacion</code>.
	 * 
	 */
	public Collection<DTAsignacion> asignarCap2(DTDepositoVRP d);
	
	/**
	 * El metodo se encarga de realizar la asignación Hibrida para depósitos con capacidad limitada (Algoritmo 2).
	 * <p>
	 * Realiza la llamada al método <code>asignarCap22</code> de la interfase <code>IAsignacion</code>.
	 * <p>
	 * Recibe por parámetro <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * <p>
	 * Retorna una colección de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colección de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * @return      Devuelve una colección de <code>DTAsignacion</code>.
	 * 
	 */
	public Collection<DTAsignacion> asignarCap22(DTDepositoVRP d);

	/**
	 * El método se encarga de realizar el ruteo. Se realiza una exploración limitada del espacio de b´usqueda y dan soluciones de calidad aceptable en tiempos de cálculo generalmente moderados.
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
	
	public Collection<DTRuteo> rutearopt(DTAsignacion dt,int capacidad);
	
	public Collection<DTRuteo> post2intraroute(DTAsignacion dt,int capacidad);
	
	public int getEstadoConsulta();
	
	public void setAbortoEstadoConsulta();
	
	public void setFinalizadoOkEstadoConsulta();
	
	public void setInicioEstadoConsulta();
	
	public int getPorgresoDeAvance();

}
