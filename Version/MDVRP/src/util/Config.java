package util;

public class Config {
	static private Config instancia=null;
	static public Config getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Config();
			return instancia;
		}else return instancia;
	}
	private Config()
	{
		bTiempo=false;
		bIteracciones=false;
		bMejora=false;
		tiempo=0;
		iteracciones=0;
		mejora=0;
		holguraDep=1;
		lambdaOpt=3;
	}

	static private boolean bTiempo;
	static private boolean bIteracciones;
	static private boolean bMejora;
	static private long tiempo;
	static private int iteracciones;
	static private double mejora;
	
	static private double holguraDep;
	
	static private int lambdaOpt;
		
	static private long timeStart;
	static private int costoInicial;
	
	public boolean isNinguno() {
		return !bTiempo && !bIteracciones && !bMejora;
	}
	public boolean isTiempo() {
		return bTiempo;
	}
	public void setTiempo(boolean bTiempo) {
		Config.bTiempo = bTiempo;
	}
	public boolean isIteracciones() {
		return bIteracciones;
	}
	public void setIteracciones(boolean bIteracciones) {
		Config.bIteracciones = bIteracciones;
	}
	public boolean isMejora() {
		return bMejora;
	}
	public void setMejora(boolean bMejora) {
		Config.bMejora = bMejora;
	}
	public int getTiempo() {
		return (int) tiempo / 1000;
	}
	public void setTiempo(int tiempo) {
		Config.tiempo = tiempo * 1000;
	}
	public int getIteracciones() {
		return iteracciones;
	}
	public void setIteracciones(int iteracciones) {
		Config.iteracciones = iteracciones;
	}
	public double getMejora() {
		return mejora;
	}
	public void setMejora(double mejora) {
		Config.mejora = mejora;
	}
	public double getHolguraDep1() {
		return holguraDep;
	}
	public int getHolguraDep() {
		return (int) (holguraDep * 100);
	}
	public void setHolguraDep(int holgura) {
		Config.holguraDep = (double) (holgura / 100);
	}
	public int getLambdaOpt() {
		return lambdaOpt;
	}
	public void setLambdaOpt(int lambdaOpt) {
		Config.lambdaOpt = lambdaOpt;
	}
	private static void setTimeStart(long timeStart) {
		Config.timeStart = timeStart;
	}
	private static void setCostoInicial(int costoInicial) {
		Config.costoInicial = costoInicial;
	}	
	public void empezarAlgoritmo(int costoInicial) {		
		setTimeStart(System.currentTimeMillis());
		setCostoInicial(costoInicial);
	}
	
	public boolean terminarPorConfig(int cantidadIteraciones, double costoMenor) {
		long timeActual = System.currentTimeMillis();
		
		/*System.out.println("tiempo actual "+timeActual);
		System.out.println("tiempo start  "+timeStart);
		System.out.println("tiempo Config "+Config.tiempo);
		long timesum = timeStart + Config.tiempo;
		System.out.println("tiempo sumado "+timesum);
		System.out.println("costoMenor "+costoMenor);
		System.out.println("costoAnterior "+costoAnterior);
		double costoprint = 100-((costoMenor*100)/costoAnterior);
		System.out.println("% costo "+costoprint);
		System.out.println("%mejora "+Config.mejora);*/
		
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
