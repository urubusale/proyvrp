package logica;
import java.util.Collection;
import datatypes.DTAsignacion;
import datatypes.DTDepositoVRP;

public interface IAsignacion 
{
	public Collection<DTAsignacion> asignar(DTDepositoVRP d);
	public Collection<DTAsignacion> asignarCap(DTDepositoVRP d);
	public Collection<DTAsignacion> asignarCap2(DTDepositoVRP d);

}
