package logica;
import logica.asignacion.Asignacion;
import logica.ruteo.ClarkWright;

public class Fabrica 
{
	static private Fabrica instancia=null;
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresión con una lista de argumentos vacía. 
	 * La clase se inicializa si ya no se ha inicializado. 
	 * 
	 * @return      Una nueva instancia de la clase
	 */
	static public Fabrica getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Fabrica();
			return instancia;
		}else return instancia;
	}
	
	/**
	 * Constructor por defecto.
	 * 
	 */
	private Fabrica()
	{
	}
	
	/**
	 * Retorna una nueva instancia de la interfece <code>ISistema</code>. 
	 *
	 * @return      Devuelve una nueva instancia de la interfece <code>ISistema</code>. 
	 * 
	 */
	public ISistema getSistema()
	{
		return Sistema.getInstancia();
	}
	
	/**
	 * Retorna una nueva instancia de la interfece <code>IAsignacion</code>. 
	 *
	 * @return      Devuelve una nueva instancia de la interfece <code>IAsignacion</code>. 
	 * 
	 */
	public IAsignacion getAsignacion()
	{
		return Asignacion.getInstancia();
	}
	
	/**
	 * Retorna una nueva instancia de la interfece <code>IRuteo</code>. 
	 *
	 * @return      Devuelve una nueva instancia de la interfece <code>IRuteo</code>. 
	 * 
	 */
	public IRuteo getRuteo()
	{
		return ClarkWright.getInstancia();
	}
}
