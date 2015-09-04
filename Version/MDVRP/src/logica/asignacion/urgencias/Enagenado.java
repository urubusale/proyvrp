package logica.asignacion.urgencias;

public class Enagenado implements Comparable{
	private ClienteCap2 cliente;
	private Deposito deposito;
	private Deposito depositoDestino;
	private int mu;
	
	/**
	 * Constructor por parametro. Se crea <code>Enagenado</code> con los dos clientes mas cercanos <code>c</code>, el depósito origen <code>d</code> y el depósito destino <code>depositoDestino</code>.
	 * 
	 * @param	c Los dos clientes mas cercanos.
	 * @param	d Depósito Origen.
	 * @param	d Depósito Destino.
	 * 
	 */
	public Enagenado (ClienteCap2 c, Deposito d, Deposito depositoDestino) {
		this.setCliente(c) ;
		this.setDeposito(d) ;
		this.setDepositoDestino(depositoDestino) ;
	}
	
	/**
	 * Devuelve los dos clientes mas cercanos.
	 * 
	 * @return	c Los dos clientes mas cercanos.
	 * 
	 */
	public ClienteCap2 getCliente() {
		return cliente;
	}

	/**
	 * Setea a <code>cliente</code> como los dos clientes mas cercanos.
	 * 
	 * @param	cliente Los dos clientes mas cercanos.
	 * 
	 */
	public void setCliente(ClienteCap2 cliente) {
		this.cliente = cliente;
	}

	/**
	 * Devuelve el depósito destino.
	 * 
	 * @return	Retorna el <code>id</code> del depósito destino.
	 * 
	 */
	public Deposito getDepositoDestino() {
		return depositoDestino;
	}

	/**
	 * Setea a <code>depositoDestino</code> como depósito destino.
	 * 
	 * @param	depositoDestino Depósito destino.
	 * 
	 */
	public void setDepositoDestino(Deposito depositoDestino) {
		this.depositoDestino = depositoDestino;
	}

	/**
	 * Devuelve el depósito origen.
	 * 
	 * @return	Retorna el <code>id</code> del depósito origen.
	 * 
	 */
	public Deposito getDeposito() {
		return deposito;
	}

	/**
	 * Setea a <code>deposito</code> como depósito origen.
	 * 
	 * @param	deposito Depósito origen.
	 * 
	 */
	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}
	
	/**
	 * Setea a <code>m</code> como valor de <code>Mu</code>.
	 * 
	 * @param	m Valor de <code>Mu</code>.
	 * 
	 */
	public void setMu(int m) {
		this.mu=m;
	}
	
	/**
	 * Devuelve el valor de <code>Mu</code>.
	 * 
	 * @return	Retorna el valor de <code>Mu</code>.
	 * 
	 */
	public int getMu() {
		return mu;
	}
	
	/**
	 * Compara este objeto con el objeto especificado por el parametro. 
	 * Devuelve un entero negativo, cero o un número entero positivo cuando este objeto es mayor que, igual a, o mayor que el objeto especificado.
	 *
	 * @param	o Objeto a comparar.
	 * 
	 */
	public int compareTo(Object o)
	{
		return Integer.valueOf(mu).compareTo(((Enagenado)o).mu);
	}
	
}