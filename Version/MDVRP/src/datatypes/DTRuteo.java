package datatypes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
public class DTRuteo 
{
	private DTNodo deposito;
	private ArrayList<DTNodo> ruta;
	private int costo;
	
	/**
	 * Constructor por parametro. Se crea <code>DTRuteo</code> con el deposito <code>d</code>, ruta de clientes vacia y con costo cero.
	 * 
 	 * @param	d Deposito.
 	 *  
	 */
	public DTRuteo(DTNodo d)
	{
		this.deposito=d;
		this.ruta=new ArrayList<DTNodo>();
		costo=0;
	}
	
	/**
	 * Constructor por parametro. Se crea <code>DTRuteo</code> con el deposito <code>d</code> y ruta de cliente <code>cs</code>.
	 * 
 	 * @param	d Deposito.
 	 * @param	cs Lista de clientes.
 	 *  
	 */
	public DTRuteo(DTNodo d,ArrayList<DTNodo> cs)
	{
		this.deposito=d;
		this.ruta=cs;
	}
	
	/**
	 * Retorna el deposito. 
	 *
	 * @return      Devuelve el deposito. 
	 * 
	 */
	public DTNodo getDeposito(){return this.deposito;}
	
	/**
	 * Retorna la colección de clientes de la ruta de <code>DTRuteo</code>. 
	 *
	 * @return      Devuelve la colección de clientes de la ruta. 
	 * 
	 */
	public Collection<DTNodo> getRuta(){return this.ruta;}
	
	/**
	 * Setea la colección de clientes a <code>DTRuteo</code>.
	 *
	 * @param	col Colección de clientes.
	 *  
	 */
	public void setRuta(ArrayList<DTNodo> col){this.ruta=col;}
	
	/**
	 * Agrega el cliente <code>c</code> a la colección de clientes de <code>DTRuteo</code>.
	 *
	 * @param	c Cliente a agregar a la colección de clientes.
	 *  
	 */
	public void agregarCliente(DTNodo c){this.ruta.add(c);}
	
	/**
	 * Setea el costo a <code>DTRuteo</code>.
	 *
	 * @param	c Costo.
	 *  
	 */
	public void setCosto(int c){this.costo=c;}
	
	/**
	 * Retorna el costo de <code>DTRuteo</code>. 
	 *
	 * @return      Devuelve el valor del costo de <code>DTRuteo</code>. 
	 * 
	 */
	public int getCosto(){return this.costo;}
	
	
	public void println()
	{
		System.out.print("dep: "+deposito.getId());
		Iterator<DTNodo> it=ruta.iterator();
		while(it.hasNext())
		{
			DTNodo n=it.next();
			System.out.print("-"+n.getId());
		}
		System.out.println("");
	}
}
