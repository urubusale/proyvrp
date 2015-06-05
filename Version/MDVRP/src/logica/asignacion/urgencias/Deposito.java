package logica.asignacion.urgencias;

import java.util.ArrayList;
import java.util.Collection;

import datatypes.DTNodo;


public class Deposito 
{
	private DTNodo nodo;
	private Collection<Cliente> asignados;
	private int capacidadLibre;
	public Deposito(DTNodo d)
	{
		this.nodo=d;
		asignados=new ArrayList<Cliente>();
		capacidadLibre=d.getDemanda();
	}
	public Deposito(Deposito dep)
	{
		this.nodo=dep.getNodo();
		this.asignados=new ArrayList<Cliente>(dep.getAsignados());
		this.capacidadLibre=dep.getCapacidadLibre();
	}
	public DTNodo getNodo(){return nodo;}
	public Collection<Cliente> getAsignados(){return asignados;}
	public void agregarCliente(Cliente c)
	{
		this.asignados.add(c);
		this.capacidadLibre=capacidadLibre-c.getNodo().getDemanda();
	}
	
	public void sacarCliente(Cliente c)
	{
		this.asignados.remove(c);
		this.capacidadLibre=capacidadLibre + c.getNodo().getDemanda();
	}
	
	public void setCapacidadLibre(int c){this.capacidadLibre=c;}
	public int getCapacidadLibre(){return this.capacidadLibre;}
}