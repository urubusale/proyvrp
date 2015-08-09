package datatypes;

public class DTDistancia implements Comparable<DTDistancia> {
	private DTNodo nodo1;
	private DTNodo nodo2;
	private int distancia;
	
	/**
	 * Constructor por parametro. Se crea <code>DTDistancia</code> con el nodo <code>nodo1</code>, nodo <code>nodo2</code> y la distancia <code>distancia</code> entre ellos.
	 * 
 	 * @param	nodo1 Nodo 1.	 
 	 * @param	nodo2 Nodo 2.	 
 	 * @param	distancia Distancia entre el nodo <code>nodo1</code> y el nodo <code>nodo2</code> .	 
	 * 
	 */
	public DTDistancia(DTNodo nodo1, DTNodo nodo2, int distancia) {
		super();
		this.nodo1 = nodo1;
		this.nodo2 = nodo2;
		this.distancia = distancia;
	}
	
	/**
	 * Retorna el nodo <code>nodo1</code> de <code>DTDistancia</code>. 
	 *
	 * @return      Devuelve el nodo <code>nodo1</code>. 
	 * 
	 */
	public DTNodo getNodo1() {return nodo1;}
	
	/**
	 * Setea el nodo <code>nodo1</code> de <code>DTDistancia</code>.
	 *
	 * @param	nodo1 Nodo.
	 *  
	 */
	public void setNodo1(DTNodo nodo1) {this.nodo1 = nodo1;}
	
	/**
	 * Retorna el nodo <code>nodo2</code> de <code>DTDistancia</code>. 
	 *
	 * @return      Devuelve el nodo <code>nodo2</code>. 
	 * 
	 */
	public DTNodo getNodo2() {return nodo2;}
	
	/**
	 * Setea el nodo <code>nodo2</code> de <code>DTDistancia</code>.
	 *
	 * @param	nodo2 Nodo.
	 *  
	 */
	public void setNodo2(DTNodo nodo2) {this.nodo2 = nodo2;}
	
	/**
	 * Retorna la distancia <code>distancia</code> de <code>DTDistancia</code>. 
	 *
	 * @return      Devuelve la distancia <code>distancia</code>. 
	 * 
	 */
	public int getDistancia() {return distancia;}
	
	/**
	 * Setea la distancia <code>distancia</code> de <code>DTDistancia</code>.
	 *
	 * @param	distancia Distancia.
	 *  
	 */
	public void setDistancia(int distancia) {this.distancia = distancia;}
	
	/**
	 * Compara este objeto con el objeto especificado por el parametro. 
	 * Devuelve un entero negativo, cero o un número entero positivo cuando este objeto es mayor que, igual a, o mayor que el objeto especificado.
	 *
	 * @param	other DTDistancia a comparar.
	 * 
	 */
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
