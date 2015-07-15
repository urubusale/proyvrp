package datatypes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
public class DTRuteo 
{
	private DTNodo deposito;
	private ArrayList<DTNodo> ruta;
	private int costo;
	public DTRuteo(DTNodo d)
	{
		this.deposito=d;
		this.ruta=new ArrayList<DTNodo>();
		costo=0;
	}
	public DTRuteo(DTNodo d,ArrayList<DTNodo> cs)
	{
		this.deposito=d;
		this.ruta=cs;
	}
	public DTNodo getDeposito(){return this.deposito;}
	public Collection<DTNodo> getRuta(){return this.ruta;}
	public void setRuta(ArrayList<DTNodo> col){this.ruta=col;}
	public void agregarCliente(DTNodo c){this.ruta.add(c);}
	public void setCosto(int c){this.costo=c;}
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
