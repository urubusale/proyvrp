package datatypes;
import java.util.ArrayList;
import java.util.Collection;

public class DTAsignacion 
{
	private DTNodo deposito;
	private Collection<DTNodo> clientes;
	public DTAsignacion(DTNodo d)
	{
		this.deposito=d;
		this.clientes=new ArrayList<DTNodo>();
	}
	public DTAsignacion(DTNodo d,Collection<DTNodo> cs)
	{
		this.deposito=d;
		this.clientes=cs;
	}
	public DTNodo getDeposito(){return this.deposito;}
	public Collection<DTNodo> getClientes(){return this.clientes;}
	public void setClientes(Collection<DTNodo> col){this.clientes=col;}
	public void agregarCliente(DTNodo c){this.clientes.add(c);}
}
