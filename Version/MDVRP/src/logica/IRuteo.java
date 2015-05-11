package logica;
import java.util.Collection;
import datatypes.DTAsignacion;
import datatypes.DTRuteo;

public interface IRuteo 
{
	public Collection<DTRuteo> rutear(DTAsignacion dt,int capacidad);

}
