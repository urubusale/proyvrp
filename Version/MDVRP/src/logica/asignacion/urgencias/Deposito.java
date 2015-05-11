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
	public DTNodo getNodo(){return nodo;}
	public Collection<Cliente> getAsignados(){return asignados;}
	public void agregarCliente(Cliente c)
	{
		this.asignados.add(c);
		this.capacidadLibre=capacidadLibre-c.getNodo().getDemanda();
	}
	public void setCapacidadLibre(int c){this.capacidadLibre=c;}
	public int getCapacidadLibre(){return this.capacidadLibre;}
}