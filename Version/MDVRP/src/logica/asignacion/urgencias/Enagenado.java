package logica.asignacion.urgencias;

public class Enagenado {
	private ClienteCap2 cliente;
	private Deposito deposito;
	private Deposito depositoDestino;
	
	public Enagenado (ClienteCap2 c, Deposito d, Deposito depositoDestino) {
		this.setCliente(c) ;
		this.setDeposito(d) ;
		this.setDepositoDestino(depositoDestino) ;
	}

	public ClienteCap2 getCliente() {
		return cliente;
	}

	public void setCliente(ClienteCap2 cliente) {
		this.cliente = cliente;
	}

	public Deposito getDepositoDestino() {
		return depositoDestino;
	}

	public void setDepositoDestino(Deposito depositoDestino) {
		this.depositoDestino = depositoDestino;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}
}
