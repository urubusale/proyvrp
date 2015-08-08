package util;

public class Config {
	static private Config instancia=null;
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresión con una lista de argumentos vacía. 
	 * La clase se inicializa si ya no se ha inicializado. 
	 * 
	 * @return      Una nueva instancia de la clase
	 */
	static public Config getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Config();
			return instancia;
		}else return instancia;
	}
	
	/** 
	 * Constructor por defecto.
	 * 
	 */
	private Config()
	{		
		bTiempo=false;
		bIteracciones=false;
		bMejora=false;
		tiempo=0;
		iteracciones=0;
		mejora=0;		
		lambdaOpt=3;
		
		// UrgenciasCap2 - algoritmo=0
		// UrgenciasCap22 - algoritmo=1
		holguraDep=1;
		holguraAlgoritmo[2]=false;
		holguraAlgoritmo[1]=false;
	}

	/** 
	 * Variables estaticas.
	 */
	// Condiciones de parada
	static private boolean bTiempo;
	static private boolean bIteracciones;
	static private boolean bMejora;
	static private long tiempo;
	static private int iteracciones;
	static private double mejora;

	// Holgura en los depositos
	static private int algoritmo;
	static private double holguraDep;
	static private boolean[] holguraAlgoritmo = new boolean[3];
	static public int urgenciasCapRapido = 1;
	static public int urgenciasCapLento = 2;

	// Optimización en Rutas
	static private int lambdaOpt;
		
	// Variables de tiempo inicial y costo inicial
	static private long timeStart;
	static private int costoInicial;
	
	/**
	 * Retorna true si NO tiene seleccionado condición de parada 
	 * por Tiempo, cant. Interacciones y % Mejora. 
	 *
	 * @return      <code>true</code>: Si NO tiene seleccionado condición de parada
	 * <p>
	 * 				<code>false</code>: Si tiene seleccionado alguna condición de parada
	 */
	public boolean isNinguno() {
		return !bTiempo && !bIteracciones && !bMejora;
	}
	
	/**
	 * Retorna True si tiene seleccionado condición de parada por Tiempo. 
	 *
	 * @return      <code>true</code> si tiene seleccionado condición de parada por tiempo. 
	 * 
	 */
	public boolean isTiempo() {
		return bTiempo;
	}
	
	/**
	 * Setea si tiene condición de parada por Tiempo. 
	 *
	 * @param	bTiempo <code>true</code> si tiene condición de parada por Tiempo 
	 * 
	 */
	public void setTiempo(boolean bTiempo) {
		Config.bTiempo = bTiempo;
	}
	
	/**
	 * Retorna True si tiene seleccionado condición de parada por cantidad de Iteracciones. 
	 *
	 * @return      <code>true</code> si tiene seleccionado condición de parada por cantidad de Iteracciones. 
	 * 
	 */
	public boolean isIteracciones() {
		return bIteracciones;
	}
	
	/**
	 * Setea si tiene condición de parada por cantidad de Iteracciones. 
	 *
	 * @param	bIteracciones <code>true</code> si tiene condición de parada por cantidad de Iteracciones 
	 * 
	 */
	public void setIteracciones(boolean bIteracciones) {
		Config.bIteracciones = bIteracciones;
	}
	
	/**
	 * Retorna True si tiene seleccionado condición de parada por porcentaje de mejora. 
	 *
	 * @return      <code>true</code> si tiene seleccionado condición de parada por porcentaje de mejora. 
	 * 
	 */
	public boolean isMejora() {
		return bMejora;
	}
	
	/**
	 * Setea si tiene condición de parada por porcentaje de mejora. 
	 *
	 * @param	bMejora <code>true</code> si tiene condición de parada por porcentaje de mejora 
	 * 
	 */
	public void setMejora(boolean bMejora) {
		Config.bMejora = bMejora;
	}
	
	/**
	 * Retorna el tiempo de la condición de parada por tiempo. 
	 <p>
	 * Nota: Tiempo en segundos.
 	 * </p>
	 * @return      Devuelve el valor del tiempo de la condición de parada por tiempo. 
	 * 
	 */
	public int getTiempo() {
		return (int) tiempo / 1000;
	}
	
	/**
	 * Setea el tiempo de la condición de parada por tiempo. 
	 *
	 * @param	tiempo Valor del tiempo de la condición de parada por tiempo.
	 * <p>
	 * Nota: Tiempo en segundos.
 	 * </p>
 	 *  
	 */
	public void setTiempo(int tiempo) {
		Config.tiempo = tiempo * 1000;
	}
	
	/**
	 * Retorna la cantidad de iteracciones de la condición de parada cantidad de Iteracciones. 
	 * 
	 * @return      Devuelve el valor de la cantidad de iteracciones de la condición de parada cantidad de Iteracciones 
	 * 
	 */
	public int getIteracciones() {
		return iteracciones;
	}
	
	/**
	 * Setea la cantidad de iteracciones de la condición de parada cantidad de Iteracciones. 
	 *
	 * @param	iteracciones Valor de la cantodad de iteracciones de la condición de parada cantidad de Iteracciones.
	 *  
	 */
	public void setIteracciones(int iteracciones) {
		Config.iteracciones = iteracciones;
	}
	
	/**
	 * Retorna el porcentaje de mejora de la condición de parada por porcentaje de mejora. 
	 * 
	 * @return      Devuelve el valor del porcentaje de mejora de la condición de parada por porcentaje de mejora.
	 * 
	 */
	public double getMejora() {
		return mejora;
	}
	
	/**
	 * Setea el porcentaje de mejora de la condición de parada por porcentaje de mejora. 
	 *
	 * @param	mejora Valor del porcentaje de mejora de la condición de parada por porcentaje de mejora.
	 *  
	 */
	public void setMejora(double mejora) {
		Config.mejora = mejora;
	}
	
	/**
	 * Retorna la holgura del deposito en escala [1..2]. 
	 * 
	 * @return      Devuelve el valor de la holgura del deposito si tiene seteado holgura para el algortimo previamente seteado con setAlgoritmo(int algoritmo).
	 * Devuelve 1 en otro caso.
	 * 
	 */
	public double getHolguraDep1() {
		//System.out.println("holguraDep " + holguraDep);
		if (Config.holguraAlgoritmo[Config.algoritmo] = true)
			return holguraDep;
		else
			return 1;
	}
	
	/**
	 * Retorna la holgura del deposito en escala [100..200]. 
	 * 
	 * @return      Devuelve el valor de la holgura del deposito si tiene seteado holgura para el algortimo previamente seteado con setAlgoritmo(int algoritmo).
	 * Devuelve 100 en otro caso.
	 * 
	 */
	public int getHolguraDep() {
		if (Config.holguraAlgoritmo[Config.algoritmo] = true)
			return (int) (holguraDep * 100);
		else
			return 100;
	}
	
	/**
	 * Setea la holgura del deposito. 
	 *
	 * @param	holgura Valor de la holgura del deposito.
	 *  
	 */
	public void setHolguraDep(int holgura) {
		Config.holguraDep = (double) (holgura / 100.0);
	}
	
	/**
	 * Retorna el algoritmo que se esta ejecutando actualmente. 
	 * 
	 * @return      Devuelve el valor del algoritmo que se esta ejecutando actualmente.
	 * 
	 */
	public int getAlgoritmo() {
		return algoritmo;
	}
	
	/**
	 * Setea el algoritmo que se esta ejecutando actualmente. 
	 *
	 * @param	algoritmo Valor del algoritmo que se esta ejecutando actualmente.
	 *  
	 */
	public void setAlgoritmo(int algoritmo) {
		Config.algoritmo = algoritmo;
	}
	
	/**
	 * Retorna el valor de optimización de rutas. 
	 * 
	 * @return      Devuelve el valor del LambdaOpt.
	 * 
	 */
	public int getLambdaOpt() {
		return lambdaOpt;
	}
	
	/**
	 * Setea el valor de optimización de rutas. 
	 *
	 * @param	lambdaOpt Valor del LambdaOpt.
	 *  
	 */
	public void setLambdaOpt(int lambdaOpt) {
		Config.lambdaOpt = lambdaOpt;
	}
	
	/**
	 * Setea el tiempo de iniciado el algoritmo. 
	 *
	 * @param	timeStart Valor del tiempo en milisegundos de iniciado el algoritmo.
	 *  
	 */
	private static void setTimeStart(long timeStart) {
		Config.timeStart = timeStart;
	}
	
	/**
	 * Setea el costo inicial del algoritmo. 
	 *
	 * @param	costoInicial Valor del costo inicial del algoritmo.
	 *  
	 */
	private static void setCostoInicial(int costoInicial) {
		Config.costoInicial = costoInicial;
	}
	
	/**
	 * Retorna true si tiene seteado holgura el algoritmo dado. 
	 * 
	 * @param	algoritmo Valor del algoritmo.
	 * @return      Devuelve true si tiene seteado holgura en el algoritmo.
	 * 
	 */
	public boolean getHolguraAlgoritmo(int algoritmo) {
		return Config.holguraAlgoritmo[algoritmo];
	}
	
	/**
	 * Setea para el algoritmo dado si utiliza holgura. 
	 *
	 * @param	algoritmo Valor del algoritmo para setearle si utiliza holgura.
	 * @param	bHolgura true si utiliza holgura en el algoritmo.
	 *  
	 */
	public void setHolguraAlgoritmo(int algoritmo, boolean bHolgura) {
		Config.holguraAlgoritmo[algoritmo] = bHolgura;
	}
	
	/**
	 * Inicializa el costo inicial y el tiempo al empezar el Algoritmo
	 * 
	 * @param	costoInicial costo inicial del algoritmo
	 * 
	 */
	public void empezarAlgoritmo(int costoInicial) {		
		setTimeStart(System.currentTimeMillis());
		setCostoInicial(costoInicial);
	}
	
	/**
	 * Retorna true si debe terminar el algoritmo por configuración.
	 *  		
	 * 
	 * @param	cantidadIteraciones Cantidad de iteracciones
	 * @param	costoMenor Cantidad de iteracciones
	 * @return      true si debe terminar el algoritmo en caso:
	 * <p>
	 * Si tiene seleccionado condición de parada por cantidad de Iteracciones y el valor de la cantidad de iteracciones es menor o igual a la cantidad de iteracciones actuales.
	 * <p>
	 * Si tiene seleccionado condición de parada por tiempo y el tiempo de la condición de parada sumado al timepo de inicio del algoritmo es menor o igual al timepo actual.
	 * <p>
	 * Si tiene seleccionado condición de parada por porcentaje de mejora y el porcentaje de mejora (costoMenor) es menor o igual al valor del porcentaje de mejora.
	 * 
	 */
	public boolean terminarPorConfig(int cantidadIteraciones, double costoMenor) {
		long timeActual = System.currentTimeMillis();
		
		if (Config.bIteracciones)
			if (Config.iteracciones <= cantidadIteraciones)
				return true;
		
		if (Config.bTiempo)			
			if (timeStart + Config.tiempo <= timeActual)
				return true;
		
		if (Config.bMejora)			
			if ((100-(costoMenor*100)/Config.costoInicial) <= Config.mejora)
				return true;
		
		return false;	
	}
}
