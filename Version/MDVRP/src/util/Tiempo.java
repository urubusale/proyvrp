package util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Tiempo {
	static private Tiempo instancia=null;
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresión con una lista de argumentos vacía. 
	 * La clase se inicializa si ya no se ha inicializado. 
	 * 
	 * @return      Una nueva instancia de la clase
	 */
	static public Tiempo getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Tiempo();
			return instancia;
		}else return instancia;
	}
	
	/** 
	 * Constructor por defecto.
	 * 
	 */
	private Tiempo()
	{
		inicio=0;
	}

	/** 
	 * Variables estaticas.
	 */
	static private double inicio;
		
	public void inicioAlgoritmo() {
		inicio = System.currentTimeMillis();
	}
	
	public BigDecimal finAlgoritmo() {
		long fin = System.currentTimeMillis();
		double tiempoTotal = fin - inicio;
		double tiempoAlgoritmo = (double) (tiempoTotal/ 1000.0);
		BigDecimal value = new BigDecimal(tiempoAlgoritmo);
		value = value.setScale(3, RoundingMode.CEILING);
		return value;
	}
}
