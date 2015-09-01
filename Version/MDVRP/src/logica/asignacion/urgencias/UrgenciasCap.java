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
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresión con una lista de argumentos vacía. 
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
	
	private Collection<Cliente> clientes;
	private Collection<Deposito> depositos;
	
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
