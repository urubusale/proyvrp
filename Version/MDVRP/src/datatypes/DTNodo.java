package datatypes;
public class DTNodo 
{
	private int x;
	private int y;
	private int demanda;
	private boolean esDeposito;
	private int id;
	
	/**
	 * Constructor por parametro. Se crea <code>DTNodo</code> con el id <code>id</code>.
	 * 
 	 * @param	id Id.
 	 *  
	 */
	public DTNodo(int id)
	{
		this.id=id;
		esDeposito=false;
		demanda=0;
	}
	
	/**
	 * Constructor por copia. 
	 * Recibe por parámetro un objeto del tipo <code>DTNodo</code> y asigna los valores de <code>DTNodo</code> pasado por parámetros a la nueva instancia de <code>DTNodo</code>.
	 * 
 	 * @param	n DTNodo.
 	 *  
	 */
	public DTNodo(DTNodo n)
	{
		this.id=n.getId();
		esDeposito=n.getEsDesposito();
		demanda=n.getDemanda();
		x=n.getX();
		y=n.getY();
	}
	
	/**
	 * Retorna el id del nodo <code>DTNodo</code>. 
	 *
	 * @return      Devuelve el valor del id del nodo <code>DTNodo</code>. 
	 * 
	 */
	public int getId(){return this.id;}
	
	/**
	 * Setea la coordenada X al nodo <code>DTNodo</code>.
	 *
	 * @param	x Coordenada X.
	 *  
	 */
	public void setX(int x){this.x=x;}
	
	/**
	 * Setea la coordenada Y al nodo <code>DTNodo</code>.
	 *
	 * @param	y Coordenada Y.
	 *  
	 */
	public void setY(int y){this.y=y;}
	
	/**
	 * Setea la demanda al nodo <code>DTNodo</code>.
	 *
	 * @param	x Demanda.
	 *  
	 */
	public void setDemanda(int x){this.demanda=x;}
	
	/**
	 * Retorna la coordenada X del nodo <code>DTNodo</code>.
	 *
	 * @return      Devuelve el valor de la coordenada X del nodo <code>DTNodo</code>.
	 *  
	 */
	public int getX(){return x;}
	
	/**
	 * Retorna la coordenada Y del nodo <code>DTNodo</code>.
	 *
	 * @return      Devuelve el valor de la coordenada Y del nodo <code>DTNodo</code>.
	 *  
	 */
	public int getY(){return y;}
	
	/**
	 * Retorna la demanda del nodo <code>DTNodo</code>.
	 *
	 * @return      Devuelve el valor de la demanda del nodo <code>DTNodo</code>.
	 *  
	 */
	public int getDemanda(){return demanda;}
	
	/**
	 * Setea <code>true</code> si el nodo <code>DTNodo</code> es un deposito. 
	 *
	 * @param	d <code>true</code> si es deposito. 
	 * 
	 */
	public void setEsDeposito(boolean d){this.esDeposito=d;}
	
	/**
	 * Retorna <code>true</code> si el nodo <code>DTNodo</code> es un deposito. 
	 *
	 * @return      <code>true</code> si el nodo <code>DTNodo</code> es deposito. 
	 * 
	 */
	public boolean getEsDesposito(){return this.esDeposito;}
}
