package datatypes;

public class DTDistancia implements Comparable<DTDistancia> {
	private DTNodo nodo1;
	private DTNodo nodo2;
	private int distancia;
	
	public DTDistancia(DTNodo nodo1, DTNodo nodo2, int distancia) {
		super();
		this.nodo1 = nodo1;
		this.nodo2 = nodo2;
		this.distancia = distancia;
	}
	public DTNodo getNodo1() {return nodo1;}
	public void setNodo1(DTNodo nodo1) {this.nodo1 = nodo1;}
	public DTNodo getNodo2() {return nodo2;}
	public void setNodo2(DTNodo nodo2) {this.nodo2 = nodo2;}
	public int getDistancia() {return distancia;}
	public void setDistancia(int distancia) {this.distancia = distancia;}
	
	@Override
	public int compareTo(DTDistancia other) {
		if (distancia > other.distancia) {
			return 1;
		}
		else if (distancia < other.distancia) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	
}
