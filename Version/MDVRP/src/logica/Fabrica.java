package logica;
import logica.asignacion.Asignacion;
import logica.ruteo.Ruteo;

public class Fabrica 
{
	static private Fabrica instancia=null;
	static public Fabrica getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Fabrica();
			return instancia;
		}else return instancia;
	}
	private Fabrica()
	{
	}
	
	public ISistema getSistema()
	{
		return Sistema.getInstancia();
	}
	public IAsignacion getAsignacion()
	{
		return Asignacion.getInstancia();
	}
	public IRuteo getRuteo()
	{
		return Ruteo.getInstancia();
	}
}
