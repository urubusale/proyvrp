package logica;
import java.util.Collection;
import datatypes.DTAsignacion;
import datatypes.DTDepositoVRP;
import datatypes.DTRuteo;
public interface ISistema 
{
	public DTDepositoVRP ImportarDepositoVRP(Collection<String> datos) throws Exception;
	public Collection<DTAsignacion> asignar(DTDepositoVRP d);
	public Collection<DTAsignacion> asignarCap(DTDepositoVRP d);
	public Collection<DTRuteo> rutear(DTAsignacion dt,int capacidad);
	public Collection<DTAsignacion> asignarCap2(DTDepositoVRP d);
	public Collection<DTAsignacion> asignarCap22(DTDepositoVRP d);

	public Collection<DTRuteo> rutearopt(DTAsignacion dt,int capacidad);
	public Collection<DTRuteo> post2intraroute(DTAsignacion dt,int capacidad);
	
	public int getEstadoConsulta();
	public void setAbortoEstadoConsulta();
	public void setFinalizadoOkEstadoConsulta();
	public void setInicioEstadoConsulta();
	public int getPorgresoDeAvance();



}
