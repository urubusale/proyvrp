package logica.asignacion;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

import logica.IAsignacion;
import logica.asignacion.urgencias.Urgencias;
import logica.asignacion.urgencias.UrgenciasCap;
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
		return UrgenciasCap.getInstancia().asignar(d);		
	}


}


