package logica.ruteo;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

import logica.IRuteo;
import datatypes.*;

public class Ruteo implements IRuteo
{
	static private Ruteo instancia=null;
	static public Ruteo getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Ruteo();
			return instancia;
		}else return instancia;
	}
	private Ruteo()
	{
	}
	
	

	public Collection<DTRuteo> rutear(DTAsignacion dt,int capacidad)
	{
		ArrayList<DTRuteo> ar=new ArrayList<DTRuteo>();
		DTNodo deposito=dt.getDeposito();
		int conteocap=0;
		Iterator<DTNodo> it=dt.getClientes().iterator();
		DTRuteo constr=new DTRuteo(deposito);
		while(it.hasNext())
		{
			DTNodo prox=it.next();
			if((prox.getDemanda()+conteocap)>capacidad)
			{
				constr.setCosto(1);
				ar.add(constr);
				conteocap=prox.getDemanda();
				constr=new DTRuteo(deposito);
				constr.agregarCliente(prox);
			}
			else
			{
				constr.agregarCliente(prox);
				conteocap=conteocap+prox.getDemanda();
			}
		}
		constr.setCosto(1);
		ar.add(constr);
		return ar;
	}
	
	public Collection<DTRuteo> rutear4opt(DTAsignacion dt,int capacidad)
	{
		return new ArrayList<DTRuteo>();
	}

	public Collection<DTRuteo> post2intraroute(DTAsignacion dt,int capacidad)
	{
		return new ArrayList<DTRuteo>();
	}

}


