package logica.asignacion.urgencias;

import java.util.ArrayList;
import java.util.Collection;

import util.Config;
import datatypes.DTNodo;

public class Deposito 
{
	private DTNodo nodo;
	private Collection<Cliente> asignados;
	private int capacidadLibre;
	private int capacidadLibrePonderada;
	
	/**
	 * Constructor por parametro. Se crea <code>Deposito</code> con el DTNodo <code>d</code>, una colección vacia de clientes asignados, una capacidad libre y una capacidad libre ponderada.
	 * <p>
	 * La capacidad libre ponderada se calcula como la demanda multiplicado por la hgogura de los depósitos.
	 * <p>
	 * <code>d</code> contiene la información de las coordenadas <code>x</code>, <code>y</code> y el <code>id</code>.
	 * 
	 * @param	d DTNodo.
	 * 
	 */
	public Deposito(DTNodo d)
	{
		this.nodo=d;
		asignados=new ArrayList<Cliente>();
		capacidadLibre=d.getDemanda();
		//double dep1 = Config.getInstancia().getHolguraDep1();
		//System.out.println("getHolguraDep1 " +dep1);
		capacidadLibrePonderada= (int)(d.getDemanda()*(Config.getInstancia().getHolguraDep1()));
	}
	
	/**
	 * Constructor por copia.
	 * 
	 * @param	dep Deposito.
	 * 
	 */
	public Deposito(Deposito dep)
	{
		this.nodo=dep.getNodo();
		this.asignados=new ArrayList<Cliente>(dep.getAsignados());
		this.capacidadLibre=dep.getCapacidadLibre();
		this.capacidadLibrePonderada=dep.getCapacidadLibrePonderada();
	}
	
	/**
	 * Devuelve la información del Deposito: coordenadas (x, y), el id, la capacidad.
	 * 
	 * @return	Información del Deposito.
	 * 
	 */
	public DTNodo getNodo(){return nodo;}
	
	/**
	 * Devuelve la colección de clientes asignados al depósito.
	 * 
	 * @return	Retorna la colección de clientes asignados al depósito.
	 * 
	 */
	public Collection<Cliente> getAsignados(){return asignados;}
	
	/**
	 * Agrega el cliente <code>c</code> a la colección de clientes asignados al depósito. Calcula la capacidad libre y la capacidad libre ponderada del depósito.
	 * 
	 * @param	c Cliente a agregar al depósito.
	 * 
	 */
	public void agregarCliente(Cliente c)
	{
		this.asignados.add(c);
		this.capacidadLibre=capacidadLibre-c.getNodo().getDemanda();
		this.capacidadLibrePonderada=this.capacidadLibrePonderada -c.getNodo().getDemanda();
	}
	
	/**
	 * Remueve el cliente <code>c</code> de la colección de clientes asignados al depósito. Calcula la capacidad libre y la capacidad libre ponderada del depósito
	 * 
	 * @param	c Cliente a remover del depósito.
	 * 
	 */
	public void sacarCliente(Cliente c)
	{
		this.asignados.remove(c);
		this.capacidadLibre=capacidadLibre + c.getNodo().getDemanda();
		this.capacidadLibrePonderada=this.capacidadLibrePonderada + c.getNodo().getDemanda();
	}
	
	/**
	 * Setea a <code>c</code> como la capacidad libre del depósito.
	 * 
	 * @param	c Capacidad libre a setear en el depósito.
	 * 
	 */
	public void setCapacidadLibre(int c){this.capacidadLibre=c;}
	
	/**
	 * Devuelve la capacidad libre del depósito.
	 * 
	 * @param	Retorna el valor de la capacidad libre del depósito.
	 * 
	 */
	public int getCapacidadLibre(){return this.capacidadLibre;}
	
	/**
	 * Setea a <code>c</code> como la capacidad libre ponderada del depósito.
	 * 
	 * @param	c Capacidad libre ponderada a setear en el depósito.
	 * 
	 */
	public void setCapacidadLibrePonderada(double c){this.capacidadLibrePonderada = (int)(c*this.nodo.getDemanda());}
	
	/**
	 * Devuelve la capacidad libre ponderada del depósito.
	 * 
	 * @param	Retorna el valor de la capacidad libre ponderada del depósito.
	 * 
	 */
	public int getCapacidadLibrePonderada(){return this.capacidadLibrePonderada;}
	
}