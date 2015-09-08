package logica.asignacion.urgencias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import util.*;
import datatypes.DTAsignacion;
import datatypes.DTDepositoVRP;
import datatypes.DTNodo;

public class UrgenciasCap
{
	static private UrgenciasCap instancia=null;

	private Collection<Cliente> clientes;
	private Collection<Deposito> depositos;
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresi�n con una lista de argumentos vac�a. 
	 * La clase se inicializa si ya no se ha inicializado. 
	 * 
	 * @return      Una nueva instancia de la clase
	 */
	static public UrgenciasCap getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new UrgenciasCap();
			return instancia;
		}else return instancia;
	}
	
	/** 
	 * Constructor por defecto.
	 * 
	 */
	private UrgenciasCap()
	{
	}
		
	/**
	 * El metodo se encarga de realizar la asignaci�n con capacidades. Concidera las capacidades en los Depositos.
	 * <p>
	 * La urgencia o prioridad que tienen los clientes determina la forma de asignarlos. Un cliente con m�s urgencia se asigna primero.
	 * <p>
	 * Recibe por par�metro <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * <p>
	 * Retorna una colecci�n de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colecci�n de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la informaci�n del problema a resolver.
	 * @return      Devuelve una colecci�n de <code>DTAsignacion</code>.. 
	 * 
	 */
	public Collection<DTAsignacion> asignar(DTDepositoVRP d)
	{
		clientes=new ArrayList<Cliente>();
		depositos=new ArrayList<Deposito>();
		
		Iterator<DTNodo> it=d.getNodos().iterator();
		while(it.hasNext())
		{
			DTNodo dt=it.next();
			if(dt.getEsDesposito())
			{
				Deposito dep=new Deposito(dt);
				depositos.add(dep);
			}
		}
		
		Iterator<DTNodo> it2=d.getNodos().iterator();
		while(it2.hasNext())
		{
			DTNodo dt=it2.next();
			if(!dt.getEsDesposito())
			{
				Cliente cli=new Cliente(dt);
				cli.setMu(calcularMu(cli,depositos));
				clientes.add(cli);
			}
		}
		while(clientes.size()>0)
		{
			TreeSet<Cliente> tr=new TreeSet<Cliente>(clientes);
			Iterator<Cliente> itc=tr.iterator();
			Cliente proximo=itc.next();
			proximo.getMasCercano().agregarCliente(proximo);
			clientes.remove(proximo);
			Iterator<Cliente> itcli=clientes.iterator();
			while(itcli.hasNext())
			{
				Cliente n=itcli.next();
				n.setMu(calcularMu(n,depositos));
			}
		}
		
		
		
		
		ArrayList<DTAsignacion> ar=new ArrayList<DTAsignacion>();
		
		Iterator<Deposito> itd=depositos.iterator();
		while(itd.hasNext())
		{
			Deposito dep=itd.next();
			DTAsignacion dta=new DTAsignacion(dep.getNodo());
			Iterator<Cliente> itcli=dep.getAsignados().iterator();
			while(itcli.hasNext())
			{
				Cliente cli=itcli.next();
				dta.agregarCliente(cli.getNodo());
			}
			ar.add(dta);
		}
		
		Penalization.getInstancia().getCalculoPenalidad(ar);
		
		return ar;
	}

	/**
	 * Calcula el valor de <code>mu</code>. 
	 *
	 * @param	c Cliente.
	 * @param	dep Colecci�n de dep�sitos.
	 * 
	 */
	private int calcularMu(Cliente c,Collection<Deposito> dep)
	{
		Deposito  masCercano=null;
		if(dep.size()>0)
		{
			masCercano=dep.iterator().next();
		}
		else
		{
			return 0;
		}
		int distanciaMasCercano=Integer.MAX_VALUE;
		Iterator<Deposito> it=dep.iterator();
		while(it.hasNext())
		{
			Deposito n=it.next();
			if(n.getCapacidadLibre()>=c.getNodo().getDemanda())
			{
				if(Distancia.getInstancia().getDistancia(n.getNodo(), c.getNodo())<distanciaMasCercano)
				{
					masCercano=n;
					distanciaMasCercano=Distancia.getInstancia().getDistancia(n.getNodo(), c.getNodo());
				}
			}
		}
		
		int sumatoriaDistancias=0;
		Iterator<Deposito> it2=dep.iterator();
		while(it2.hasNext())
		{
			Deposito n=it2.next();
			if(n.getCapacidadLibre()>=c.getNodo().getDemanda())
			{
				if(n.getNodo().getId()!=masCercano.getNodo().getId())
				{
					sumatoriaDistancias=sumatoriaDistancias+Distancia.getInstancia().getDistancia(n.getNodo(),c.getNodo());
				}
			}
		}
		c.setMasCercano(masCercano);
		return sumatoriaDistancias-distanciaMasCercano;
		
	}
}