package logica.asignacion;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

import logica.IAsignacion;
import logica.asignacion.urgencias.Urgencias;
import logica.asignacion.urgencias.UrgenciasCap;
import logica.asignacion.urgencias.UrgenciasCap2;
import logica.asignacion.urgencias.UrgenciasCap21;
import logica.asignacion.urgencias.UrgenciasCap22;
import datatypes.*;

public class Asignacion implements IAsignacion
{
	static private Asignacion instancia=null;
	static public Asignacion getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Asignacion();
			return instancia;
		}else return instancia;
	}
	private Asignacion()
	{
	}
	
	
	
	public Collection<DTAsignacion> asignar(DTDepositoVRP d)
	{
		return Urgencias.getInstancia().asignar(d);		
	}

	public Collection<DTAsignacion> asignarCap(DTDepositoVRP d)
	{
		return UrgenciasCap.getInstancia().asignar(d);		
	}
	
	public Collection<DTAsignacion> asignarCap2(DTDepositoVRP d) //SEGUIR ACA
	{
		return UrgenciasCap2.getInstancia().asignar(d);		
	}


}

