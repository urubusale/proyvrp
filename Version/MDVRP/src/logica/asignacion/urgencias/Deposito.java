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
	 * Constructor por parametro. Se crea <code>Deposito</code> con el DTNodo <code>d</code>, una colecci�n vacia de clientes asignados, una capacidad libre y una capacidad libre ponderada.
	 * <p>
	 * La capacidad libre ponderada se calcula como la demanda multiplicado por la hgogura de los dep�sitos.
	 * <p>
	 * <code>d</code> contiene la informaci�n de las coordenadas <code>x</code>, <code>y</code> y el <code>id</code>.
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
	 * Devuelve la informaci�n del Deposito: coordenadas (x, y), el id, la capacidad.
	 * 
	 * @return	Informaci�n del Deposito.
	 * 
	 */
	public DTNodo getNodo(){return nodo;}
	
	/**
	 * Devuelve la colecci�n de clientes asignados al dep�sito.
	 * 
	 * @return	Retorna la colecci�n de clientes asignados al dep�sito.
	 * 
	 */
	public Collection<Cliente> getAsignados(){return asignados;}
	
	/**
	 * Agrega el cliente <code>c</code> a la colecci�n de clientes asignados al dep�sito. Calcula la capacidad libre y la capacidad libre ponderada del dep�sito.
	 * 
	 * @param	c Cliente a agregar al dep�sito.
	 * 
	 */
	public void agregarCliente(Cliente c)
	{
		this.asignados.add(c);
		this.capacidadLibre=capacidadLibre-c.getNodo().getDemanda();
		this.capacidadLibrePonderada=this.capacidadLibrePonderada -c.getNodo().getDemanda();
	}
	
	/**
	 * Remueve el cliente <code>c</code> de la colecci�n de clientes asignados al dep�sito. Calcula la capacidad libre y la capacidad libre ponderada del dep�sito
	 * 
	 * @param	c Cliente a remover del dep�sito.
	 * 
	 */
	public void sacarCliente(Cliente c)
	{
		this.asignados.remove(c);
		this.capacidadLibre=capacidadLibre + c.getNodo().getDemanda();
		this.capacidadLibrePonderada=this.capacidadLibrePonderada + c.getNodo().getDemanda();
	}
	
	/**
	 * Setea a <code>c</code> como la capacidad libre del dep�sito.
	 * 
	 * @param	c Capacidad libre a setear en el dep�sito.
	 * 
	 */
	public void setCapacidadLibre(int c){this.capacidadLibre=c;}
	
	/**
	 * Devuelve la capacidad libre del dep�sito.
	 * 
	 * @param	Retorna el valor de la capacidad libre del dep�sito.
	 * 
	 */
	public int getCapacidadLibre(){return this.capacidadLibre;}
	
	/**
	 * Setea a <code>c</code> como la capacidad libre ponderada del dep�sito.
	 * 
	 * @param	c Capacidad libre ponderada a setear en el dep�sito.
	 * 
	 */
	public void setCapacidadLibrePonderada(double c){this.capacidadLibrePonderada = (int)(c*this.nodo.getDemanda());}
	
	/**
	 * Devuelve la capacidad libre ponderada del dep�sito.
	 * 
	 * @param	Retorna el valor de la capacidad libre ponderada del dep�sito.
	 * 
	 */
	public int getCapacidadLibrePonderada(){return this.capacidadLibrePonderada;}
	
}