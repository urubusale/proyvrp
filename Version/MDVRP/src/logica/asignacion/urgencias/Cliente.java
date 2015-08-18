package logica.asignacion.urgencias;

import datatypes.DTNodo;

public class Cliente implements Comparable
{
	private DTNodo nodo;
	private int mu;
	private Deposito masCercano;
	
	public Cliente(DTNodo d)
	{
		this.nodo=d;
		mu=0;
	}
	
	public DTNodo getNodo(){return nodo;}
	
	public void setMu(int m){this.mu=m;}
	
	public int getMu(){return this.mu;}
	
	public void setMasCercano(Deposito d){this.masCercano=d;}
	
	public Deposito getMasCercano(){return this.masCercano;}
	
	public int compareTo(Object o)
	{
		if(((Cliente)o).getMu()>this.mu)return 1;
		else if(((Cliente)o).getMu()==this.mu) return 1;
			else return -1;
	}
	
}
