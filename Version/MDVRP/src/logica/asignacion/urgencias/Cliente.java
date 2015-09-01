package logica.asignacion.urgencias;

import datatypes.DTNodo;

public class Cliente implements Comparable
{
	private DTNodo nodo;
	private int mu;
	private Deposito masCercano;
	
	/**
	 * Constructor por parametro. Se crea <code>Cliente</code> con el DTNodo <code>d</code> y mu igual a <code>0</code>.
	 * <p>
	 * <code>d</code> contiene la información de las coordenadas <code>x</code>, <code>y</code>, la <code>demanda</code> y el <code>id</code>.
	 * 
	 * @param	d DTNodo.
	 * 
	 */
	public Cliente(DTNodo d)
	{
		this.nodo=d;
		mu=0;
	}
	
	/**
	 * Retorna <code>DTNodo</code> con la informacion de las coordenadas <code>x</code>, <code>y</code>, la <code>demanda</code> y el <code>id</code>. 
	 *
	 * @return      <code>DTNodo</code> con la información del nodo. 
	 * 
	 */
	public DTNodo getNodo(){return nodo;}
	
	/**
	 * Setea el valor de <code>mu</code>. 
	 *
	 * @param	m valor de <code>mu</code>..
	 * 
	 */
	public void setMu(int m){this.mu=m;}
	
	/**
	 * Retorna el valor de <code>mu</code>. 
	 *
	 * @return      Devuelve el valor de <code>mu</code>. 
	 * 
	 */
	public int getMu(){return this.mu;}
	
	/**
	 * Setea el depósito <code>d</code> como el mas cercano. 
	 *
	 * @param	d Depósito.
	 * 
	 */
	public void setMasCercano(Deposito d){this.masCercano=d;}
	
	/**
	 * Devuelve el depósito mas cercano. 
	 *
	 * @return      Devuelve el valor del depósito mas cercano. 
	 * 
	 */
	public Deposito getMasCercano(){return this.masCercano;}
	
	/**
	 * Compara este Cliente con el Objeto <code>o</code> pasado por parametro. 
	 *
	 * @param	o Objeto a comparar.
	 * @return      Devuelve <code>1</code> si el <code>mu</code> del Objeto <code>o</code> es mayor o igual al <code>mu</code> del Cliente. Devuelve <code>-1</code> en otro caso.
	 * 
	 */
	public int compareTo(Object o)
	{
		if(((Cliente)o).getMu()>this.mu)return 1;
		else if(((Cliente)o).getMu()==this.mu) return 1;
			else return -1;
	}
	
}
