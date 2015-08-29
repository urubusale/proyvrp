package logica.asignacion.urgencias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;
import util.Distancia;
import datatypes.DTAsignacion;
import datatypes.DTDepositoVRP;
import datatypes.DTNodo;

public class Urgencias
{
	static private Urgencias instancia=null;
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresi�n con una lista de argumentos vac�a. 
	 * La clase se inicializa si ya no se ha inicializado. 
	 * 
	 * @return      Una nueva instancia de la clase
	 */
	static public Urgencias getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Urgencias();
			return instancia;
		}else return instancia;
	}
	
	/** 
	 * Constructor por defecto.
	 * 
	 */
	private Urgencias()
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
		TreeSet<Cliente> tr=new TreeSet<Cliente>(clientes);
		Iterator<Cliente> itc=tr.iterator();
		while(itc.hasNext())
		{
			Cliente proximo=itc.next();
			proximo.getMasCercano().agregarCliente(proximo);
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
			if(Distancia.getInstancia().getDistancia(n.getNodo(), c.getNodo())<distanciaMasCercano)
			{
				masCercano=n;
				distanciaMasCercano=Distancia.getInstancia().getDistancia(n.getNodo(), c.getNodo());
			}
		}
		
		int sumatoriaDistancias=0;
		Iterator<Deposito> it2=dep.iterator();
		while(it2.hasNext())
		{
			Deposito n=it2.next();
			if(n.getNodo().getId()!=masCercano.getNodo().getId())
			{
				sumatoriaDistancias=sumatoriaDistancias+Distancia.getInstancia().getDistancia(n.getNodo(),c.getNodo());
			}
		}
		c.setMasCercano(masCercano);
		return sumatoriaDistancias-distanciaMasCercano;
		
	}
}
