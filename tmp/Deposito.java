package logica.asignacion.urgencias;

import java.util.ArrayList;
import java.util.Collection;

import datatypes.DTNodo;

///////////////// OJO 1.2 HARCODEADO /////////////////
public class Deposito 
{
	private DTNodo nodo;
	private Collection<Cliente> asignados;
	private int capacidadLibre;
	private int capacidadLibrePonderada;
	public Deposito(DTNodo d)
	{
		this.nodo=d;
		asignados=new ArrayList<Cliente>();
		capacidadLibre=d.getDemanda();
		capacidadLibrePonderada= (int)(d.getDemanda()*1.2); 
	}
	
	public Deposito(Deposito dep)
	{
		this.nodo=dep.getNodo();
		this.asignados=new ArrayList<Cliente>(dep.getAsignados());
		this.capacidadLibre=dep.getCapacidadLibre();
		this.capacidadLibrePonderada=dep.getCapacidadLibrePonderada();
	}
	public DTNodo getNodo(){return nodo;}
	public Collection<Cliente> getAsignados(){return asignados;}
	public void agregarCliente(Cliente c)
	{
		this.asignados.add(c);
		this.capacidadLibre=capacidadLibre-c.getNodo().getDemanda();
		this.capacidadLibrePonderada=this.capacidadLibrePonderada -c.getNodo().getDemanda();
	}
	
	public void sacarCliente(Cliente c)
	{
		this.asignados.remove(c);
		this.capacidadLibre=capacidadLibre + c.getNodo().getDemanda();
		this.capacidadLibrePonderada=this.capacidadLibrePonderada + c.getNodo().getDemanda();
	}
	
	public void setCapacidadLibre(int c){this.capacidadLibre=c;}
	public int getCapacidadLibre(){return this.capacidadLibre;}
	public void setCapacidadLibrePonderada(double c){this.capacidadLibrePonderada = (int)(c*this.nodo.getDemanda());}
	public int getCapacidadLibrePonderada(){return this.capacidadLibrePonderada;}
}