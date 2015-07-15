package logica;
import java.util.Collection;

import datatypes.DTAsignacion;
import datatypes.DTDepositoVRP;
import datatypes.DTRuteo;

public interface IRuteo 
{
	public Collection<DTRuteo> rutear(DTAsignacion dt,int capacidad);
	public Collection<DTRuteo> rutear4opt(DTAsignacion dt,int capacidad);
	public Collection<DTRuteo> post2intraroute(DTAsignacion dt,int capacidad);

}
