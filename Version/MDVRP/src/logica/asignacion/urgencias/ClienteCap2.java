package logica.asignacion.urgencias;

import datatypes.DTNodo;

public class ClienteCap2 extends Cliente implements Comparable {

	private ClienteCap2 clieteMasCercano1;
	private ClienteCap2 clieteMasCercano2;		
	
	/**
	 * Constructor por parametro. Se crea <code>ClienteCap2</code>. Invoca al constructor por parametro de <code>Cliente</code>
	 * 
	 * @param	d DTNodo.
	 * 
	 */
	public ClienteCap2(DTNodo d)
	{
		 super(d);
	}
	
	/**
	 * Devuelve el primer cliente mas cercano. 
	 *
	 * @return      Devuelve el valor del primer cliente mas cercano. 
	 * 
	 */
	public ClienteCap2 getClieteMasCercano1() {
		return clieteMasCercano1;
	}
	
	/**
	 * Setea a <code>clieteMasCercano1</code> como el primer cliente mas cercano. 
	 *
	 * @param	clieteMasCercano1 Primer cliente mas cercano.
	 * 
	 */
	public void setClieteMasCercano1(ClienteCap2 clieteMasCercano1) {
		this.clieteMasCercano1 = clieteMasCercano1;
	}
	
	/**
	 * Devuelve el segundo cliente mas cercano. 
	 *
	 * @return      Devuelve el valor del segundo cliente mas cercano. 
	 * 
	 */
	public ClienteCap2 getClieteMasCercano2() {
		return clieteMasCercano2;
	}
	
	/**
	 * Setea a <code>clieteMasCercano2</code> como el segundo cliente mas cercano. 
	 *
	 * @param	clieteMasCercano2 Segundo cliente mas cercano.
	 * 
	 */
	public void setClieteMasCercano2(ClienteCap2 clieteMasCercano2) {
		this.clieteMasCercano2 = clieteMasCercano2;
	}
	
}
