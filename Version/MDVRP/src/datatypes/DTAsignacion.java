package datatypes;
import java.util.ArrayList;
import java.util.Collection;

public class DTAsignacion 
{
	private DTNodo deposito;
	private Collection<DTNodo> clientes;
	
	/**
	 * Constructor por parametro. Se crea <code>DTAsignacion</code> con el deposito <code>d</code>
	 * 
 	 * @param	d Deposito.	 
	 * 
	 */
	public DTAsignacion(DTNodo d)
	{
		this.deposito=d;
		this.clientes=new ArrayList<DTNodo>();
	}
	
	/**
	 * Constructor por parametro. Se crea <code>DTAsignacion</code> con el deposito <code>d</code> y la colección de clientes <code>cs</code>
	 * 
	 * @param	d Deposito.	 
	 * @param	cs Colección de clientes.	 
	 * 
	 */
	public DTAsignacion(DTNodo d,Collection<DTNodo> cs)
	{
		this.deposito=d;
		this.clientes=cs;
	}
	
	/**
	 * Retorna el deposito que pertenece a <code>DTAsignacion</code>. 
	 *
	 * @return      Devuelve el deposito que pertenece a <code>DTAsignacion</code>. 
	 * 
	 */
	public DTNodo getDeposito(){return this.deposito;}
	
	/**
	 * Retorna la colección de clientes que pertenecen a <code>DTAsignacion</code>. 
	 *
	 * @return      Devuelve la colección de clientes que pertenecen a <code>DTAsignacion</code>. 
	 * 
	 */
	public Collection<DTNodo> getClientes(){return this.clientes;}
	
	/**
	 * Setea la coleccion de cliente <code>col</code>. a la coleccion de clientes de <code>DTAsignacion</code>. 
	 *
	 * @param	col Coleccion de cliente.
	 *  
	 */
	public void setClientes(Collection<DTNodo> col){this.clientes=col;}
	
	/**
	 * Agrega el cliente <code>c</code>. a la coleccion de clientes de <code>DTAsignacion</code>. 
	 *
	 * @param	c Cliente.
	 *  
	 */
	public void agregarCliente(DTNodo c){this.clientes.add(c);}
}
