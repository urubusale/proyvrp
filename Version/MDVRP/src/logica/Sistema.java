package logica;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import datatypes.*;

public class Sistema implements ISistema
{
	static private Sistema instancia=null;
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresión con una lista de argumentos vacía. 
	 * La clase se inicializa si ya no se ha inicializado. 
	 * 
	 * @return      Una nueva instancia de la clase
	 */
	static public Sistema getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Sistema();
			return instancia;
		}else return instancia;
	}
	
	/** 
	 * Constructor por defecto.
	 * 
	 */
	private Sistema()
	{
	}
	
	/** 
	 * Genera un nuevo<code>DTDepositoVRP</code> con los datos <code>datos</code>.
	 * <p>
	 * Realiza la llamada a 
	 * <code>setNAME</code>, 
	 * <code>setCAPACITY</code>, 
	 * <code>setCOMMENT</code>, 
	 * <code>setDIMENSION</code>, 
	 * <code>setDISPLAY_DATA_TYPE</code>, 
	 * <code>setEDGE_WEIGHT_FORMAT</code>, 
	 * <code>setEDGE_WEIGHT_TYPE</code>, 
	 * <code>setTYPE</code>, 
	 * <code>setNodos</code> 
	 * de <code>DTDepositoVRP</code>
	 * 
	 * @param		datos Colección de String que contiene los datos para generar un <code>DTDepositoVRP</code>.	 * 
	 * @return      Retorna un nuevo<code>DTDepositoVRP</code> con los datos <code>datos</code> pasados por parametro. 
	 * @throws		<code>Exception</code> si hay error al leer el archivo.
	 * 
	 */
	public DTDepositoVRP ImportarDepositoVRP(Collection<String> datos) throws Exception
	{
		
			Hashtable<Integer,DTNodo> nodos=new Hashtable<Integer,DTNodo>();
			String estado="";
			String NAME="";
			String COMMENT="";
			String DIMENSION="";
			String TYPE="";
			String EDGE_WEIGHT_TYPE="";
			String EDGE_WEIGHT_FORMAT="";
			String DISPLAY_DATA_TYPE="";
			String CAPACITY="";
			Iterator<String> it=datos.iterator();
			ArrayList<Integer> explicita=new ArrayList<Integer>();
			while(it.hasNext())
			{
				
				String n=it.next();
			//	System.out.println(n);
				if((estado.compareTo("")==0)&&(n.contains("NAME")))
				{
					StringTokenizer stk=new StringTokenizer(n,":");
					String izq=stk.nextToken();
					String der=stk.nextToken();
					NAME=der.trim();
					continue;
				}
				if((estado.compareTo("")==0)&&(n.contains("COMMENT")))
				{
					StringTokenizer stk=new StringTokenizer(n,":");
					String izq=stk.nextToken();
					String der=stk.nextToken();
					COMMENT=der.trim();
					continue;

				}
				if((estado.compareTo("")==0)&&(n.contains("DIMENSION")))
				{
					StringTokenizer stk=new StringTokenizer(n,":");
					String izq=stk.nextToken();
					String der=stk.nextToken();
					DIMENSION=der.trim();
					continue;
				}
				if((estado.compareTo("")==0)&&(n.contains("EDGE_WEIGHT_TYPE")))
				{
					StringTokenizer stk=new StringTokenizer(n,":");
					String izq=stk.nextToken();
					String der=stk.nextToken();
					EDGE_WEIGHT_TYPE=der.trim();
					if(EDGE_WEIGHT_TYPE.contains("EXPLICIT"))
					{
						for(int i=1;i<=Integer.valueOf(DIMENSION);i++)
						{
							DTNodo an=new DTNodo(i);
						
							nodos.put(i, an);
						}
					}
					continue;
				}
				if((estado.compareTo("")==0)&&(n.contains("TYPE")))
				{
					StringTokenizer stk=new StringTokenizer(n,":");
					String izq=stk.nextToken();
					String der=stk.nextToken();
					TYPE=der.trim();
					continue;
				}
				
				if((estado.compareTo("")==0)&&(n.contains("EDGE_WEIGHT_FORMAT")))
				{
					StringTokenizer stk=new StringTokenizer(n,":");
					String izq=stk.nextToken();
					String der=stk.nextToken();
					EDGE_WEIGHT_FORMAT=der.trim();
					continue;
				}
				if((estado.compareTo("")==0)&&(n.contains("DISPLAY_DATA_TYPE")))
				{
					StringTokenizer stk=new StringTokenizer(n,":");
					String izq=stk.nextToken();
					String der=stk.nextToken();
					DISPLAY_DATA_TYPE=der.trim();
					continue;
				}
				if((estado.compareTo("")==0)&&(n.contains("CAPACITY")))
				{
					StringTokenizer stk=new StringTokenizer(n,":");
					String izq=stk.nextToken();
					String der=stk.nextToken();
					CAPACITY=der.trim();
					continue;
				}
				
				if((n.contains("NODE_COORD_SECTION")))
				{
					estado="NODE_COORD_SECTION";
					continue;
				}
				if((n.contains("DEMAND_SECTION")))
				{
					estado="DEMAND_SECTION";
					continue;
				}
				if((n.contains("DEPOT_SECTION")))
				{
					estado="DEPOT_SECTION";
					continue;
				}
				if((n.contains("EDGE_WEIGHT_SECTION")))
				{
					estado="EDGE_WEIGHT_SECTION";
					continue;
				}
	//----------------------
				if((estado.compareTo("NODE_COORD_SECTION")==0))
				{
					StringTokenizer stk=new StringTokenizer(n);
					String nodo=stk.nextToken();
					String x=stk.nextToken();
					String y=stk.nextToken();
					int id,X,Y;
					id=Integer.valueOf(nodo);
					X=Integer.valueOf(x);
					Y=Integer.valueOf(y);
					try
					{}
					catch(Exception ex)
					{
						throw new Exception("Error al leer el archivo");
					}
					DTNodo nod=new DTNodo(id);
					nod.setX(X);
					nod.setY(Y);
					nodos.put(id,nod);
					continue;
				}
				if((estado.compareTo("DEMAND_SECTION")==0))
				{
					StringTokenizer stk=new StringTokenizer(n);
					String nodo=stk.nextToken();
					String x=stk.nextToken();
					int id,X;
					id=Integer.valueOf(nodo);
					X=Integer.valueOf(x);
					try
					{}
					catch(Exception ex)
					{
						throw new Exception("Error al leer el archivo");
					}
					
					DTNodo nod=nodos.get(id);
					nod.setDemanda(X);
					continue;
				}
				if((estado.compareTo("DEPOT_SECTION")==0))
				{
					StringTokenizer stk=new StringTokenizer(n);
					String nodo=stk.nextToken();
					int id;
					id=Integer.valueOf(nodo);
					try
					{}
					catch(Exception ex)
					{
						throw new Exception("Error al leer el archivo");
					}
					if(Integer.valueOf(id).compareTo(Integer.valueOf("-1"))!=0)
					{
						DTNodo nod=nodos.get(id);
						nod.setEsDeposito(true);
					}
					continue;
				}
				if((estado.compareTo("EDGE_WEIGHT_SECTION")==0))
				{
					StringTokenizer stk=new StringTokenizer(n);
					while(stk.hasMoreTokens())
					{
						String nodo=stk.nextToken();
						System.out.println(nodo+"    nodo!!!");
						Integer inte=Integer.valueOf(nodo);
						explicita.add(inte);
					}
				//	System.out.println("tam de matriz "+explicita.size());
					continue;
				}
				
			}
			if(EDGE_WEIGHT_TYPE.contains("EXPLICIT"))
			{
				if(EDGE_WEIGHT_FORMAT.compareTo("LOWER_COL")==0) 
				{
					//throw new Exception("PARA TIPO EXPLICITO SOLO SE SOPORTA  EDGE_WEIGHT_FORMAT=LOWER_COL");
					util.Distancia.getInstancia().setearMatrizExplicitaLowerCol(Integer.valueOf(DIMENSION), explicita);
				}else
					if(EDGE_WEIGHT_FORMAT.compareTo("LOWER_ROW")==0) 
					{
						//throw new Exception("PARA TIPO EXPLICITO SOLO SE SOPORTA  EDGE_WEIGHT_FORMAT=LOWER_COL");
						util.Distancia.getInstancia().setearMatrizExplicitaLowerRow(Integer.valueOf(DIMENSION), explicita);
					}else throw new Exception("NO SE SOPORTA EDGE_WEIGHT_FORMAT="+EDGE_WEIGHT_FORMAT);
				
				/*Iterator<DTNodo> itn=nodos.values().iterator();
				Random ran=new Random(23);
				while(itn.hasNext())
				{
					DTNodo nodoa=itn.next();
					nodoa.setX(ran.nextInt(100));//provisorio
					nodoa.setY(ran.nextInt(100));
				}*/
				util.Distancia.getInstancia().mapearMatriz(nodos.values());
				
			}else util.Distancia.getInstancia().setearEuclidia();
			
			DTDepositoVRP vrp=new DTDepositoVRP();
			vrp.setNAME(NAME);
			vrp.setCAPACITY(CAPACITY);
			vrp.setCOMMENT(COMMENT);
			vrp.setDIMENSION(DIMENSION);
			vrp.setDISPLAY_DATA_TYPE(DISPLAY_DATA_TYPE);
			vrp.setEDGE_WEIGHT_FORMAT(EDGE_WEIGHT_FORMAT);
			vrp.setEDGE_WEIGHT_TYPE(EDGE_WEIGHT_TYPE);
			vrp.setTYPE(TYPE);
			vrp.setNodos(nodos.values());
			return vrp;
	}
	
	/**
	 * El método se encarga de realizar la asignación sin capacidades. No concidera las capacidades en los Depositos.
	 * <p>
	 * Realiza la llamada al método <code>asignar</code> de la interfase <code>IAsignacion</code>.
	 * <p>
	 * Recibe por parámetro <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * <p>
	 * Retorna una colección de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colección de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * @return      Devuelve una colección de <code>DTAsignacion</code>.
	 * 
	 */
	public Collection<DTAsignacion> asignar(DTDepositoVRP d)
	{
		return Fabrica.getInstancia().getAsignacion().asignar(d);
	}

	/**
	 * El método se encarga de realizar la asignación con capacidades. Concidera las capacidades en los Depositos.
	 * <p>
	 * Realiza la llamada al método <code>asignarCap</code> de la interfase <code>IAsignacion</code>.
	 * <p>
	 * Recibe por parámetro <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * <p>
	 * Retorna una colección de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colección de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * @return      Devuelve una colección de <code>DTAsignacion</code>.
	 * 
	 */
	public Collection<DTAsignacion> asignarCap(DTDepositoVRP d)
	{
		return Fabrica.getInstancia().getAsignacion().asignarCap(d);
	}
	
	/**
	 * El metodo se encarga de realizar la asignación Hibrida para depósitos con capacidad limitada (Algoritmo 1).
	 * <p>
	 * Realiza la llamada al método <code>asignarCap2</code> de la interfase <code>IAsignacion</code>.
	 * <p>
	 * Recibe por parámetro <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * <p>
	 * Retorna una colección de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colección de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * @return      Devuelve una colección de <code>DTAsignacion</code>.
	 * 
	 */
	public Collection<DTAsignacion> asignarCap2(DTDepositoVRP d)
	{
		return Fabrica.getInstancia().getAsignacion().asignarCap2(d);
	}

	/**
	 * El metodo se encarga de realizar la asignación Hibrida para depósitos con capacidad limitada (Algoritmo 2).
	 * <p>
	 * Realiza la llamada al método <code>asignarCap22</code> de la interfase <code>IAsignacion</code>.
	 * <p>
	 * Recibe por parámetro <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * <p>
	 * Retorna una colección de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colección de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * @return      Devuelve una colección de <code>DTAsignacion</code>.
	 * 
	 */
	public Collection<DTAsignacion> asignarCap22(DTDepositoVRP d)
	{
		return Fabrica.getInstancia().getAsignacion().asignarCap22(d);
	}

	/**
	 * El método se encarga de realizar el ruteo. Se realiza una exploración limitada del espacio de b´usqueda y dan soluciones de calidad aceptable en tiempos de cálculo generalmente moderados.
	 * <p>
	 * Realiza la llamada al método <code>rutear</code> de la interfase <code>IRuteo</code>.
	 * <p>
	 * Recibe por parámetro el deposito y la colección de clientes que estan asigandos a ese depositos (<code>DTAsignacion</code>), y la <code>capacidad</code> de los vehiculos.
	 * <p>
	 * Retorna una colección de <code>DTRuteo</code>. Cada <code>DTRuteo</code> contiene el deposito, una lista de clientes que representa la ruta y el costo de la misma.
	 *
	 * @param	dt <code>DTAsignacion</code> donde contiene el deposito y la colección de clientes que estan asigandos a ese depositos.
	 * @param	capacidad Capacidad de los vehiculos.
	 * @return      Devuelve una colección de <code>DTRuteo</code>.
	 * 
	 */
	public Collection<DTRuteo> rutear(DTAsignacion dt,int capacidad)
	{
		return Fabrica.getInstancia().getRuteo().rutear(dt,capacidad);
	}
	
	/**
	 * El metodo se encarga de realizar el ruteo aplicando el algoritmo de ruteo y optimiza el conjunto de rutas solución. 
	 * 
	 * @param	dt <code>DTAsignacion</code> donde contiene el deposito y la colección de clientes que estan asigandos a ese depositos.
	 * @param	capacidad Capacidad de los vehiculos.
	 * @return      Devuelve una colección de <code>DTRuteo</code>.
	 * 
	 */
	public Collection<DTRuteo> post2intraroute(DTAsignacion dt,int capacidad)
	{
		return Fabrica.getInstancia().getRuteo().post2intraroute(dt, capacidad);
	}
	
	/**
	 * El metodo se encarga de realizar el ruteo aplicando el algoritmo de ruteo y optimiza el conjunto de rutas solución. 
	 * 
	 * @param	dt <code>DTAsignacion</code> donde contiene el deposito y la colección de clientes que estan asigandos a ese depositos.
	 * @param	capacidad Capacidad de los vehiculos.
	 * @return      Devuelve una colección de <code>DTRuteo</code>.
	 * 
	 */
	public Collection<DTRuteo> rutearopt(DTAsignacion dt,int capacidad)
	{
		return Fabrica.getInstancia().getRuteo().rutear4opt(dt,capacidad);
	}
	
	private int estadoConsulta;
	private double progreso;

	/**
	 * Retorna el estado consulta del algoritmo. 
	 * 
	 * @return      Devuelve el estado consulta del algoritmo.
	 * 
	 */
	synchronized public int getEstadoConsulta()
	{
		return estadoConsulta;
	}
	
	/**
	 * Setea el estado consulta del algoritmo como <code>ABORTADO</code>. 
	 * 
	 */
	synchronized public void setAbortoEstadoConsulta()
	{
		estadoConsulta=-1;
	}
	
	/**
	 * Setea el estado consulta del algoritmo como <code>FINALIZADO OK</code>. 
	 * 
	 */
	synchronized public void setFinalizadoOkEstadoConsulta()
	{
		estadoConsulta=1;
	}
	
	/**
	 * Inicializa el estado consulta del algoritmo. 
	 * <p>
	 * Setea el progreso de avance igual 0, los mensajes vacios y arreglo de resaltados vacia.
	 * 
	 */
	synchronized public void setInicioEstadoConsulta()
	{
		estadoConsulta=0;
		progreso=0;
		mensaje="";
		resaltados=new ArrayList<DTNodo>();
		parciales=null;
	}
	
	/**
	 * Retorna el progreso de avance del algoritmo. 
	 * 
	 * @return      Devuelve el progreso de avance del algoritmo.
	 * 
	 */
	public int getPorgresoDeAvance()
	{
		return (int) Math.round(progreso);	
	}
	
	/**
	 * Adelanta el progreso de avance en <code>p</code> unidades
	 * 
	 * @param	p unidades a adelantar el progreso de avance.
	 * 
	 */
	synchronized public void adelantarPorgresoDeAvance(float p)
	{
		this.progreso=this.progreso+p;
	}

	private Collection<DTNodo> mapeados;
	
	private Collection<DTNodo> resaltados;
	private Collection<DTAsignacion> parciales;
	private String mensaje;
	private DTNodo radio;

	/**
	 * Retorna el radio. 
	 * 
	 * @return      Devuelve el valor del radio.
	 * 
	 */
	public DTNodo getRadio(){return radio;}
	
	/**
	 * Setea el radio con el valor <code>s</code>.
	 * 
	 * @param	s Radio.
	 * 
	 */
	public void setRadio(DTNodo s){this.radio=s;}
		
	/**
	 * Setea a la colección <code>m</code> como colección de nodos mapeados. 
	 * 
	 * @param	m Colección de nodos mapeados a setear.
	 * 
	 */
	public void setMapeados(Collection<DTNodo> m){this.mapeados=m;}
	
	/**
	 * Retorna la colección de nodos mapeados. 
	 * 
	 * @return      Devuelve la colección de nodos mapeados.
	 * 
	 */
	public Collection<DTNodo> getMapeados(){return this.mapeados;}
	
	/**
	 * Setea a la colección <code>m</code> como colección de asignaciones parciales. 
	 * 
	 * @param	m Colección parciales de asiganciones a setear.
	 * 
	 */
	public void setParciales(Collection<DTAsignacion> m){this.parciales=m;}
	
	/**
	 * Retorna la colección parciales de asiganciones. 
	 * 
	 * @return      Devuelve la colección parciales de asiganciones.
	 * 
	 */
	public Collection<DTAsignacion> getParciales(){return this.parciales;}
	
	/**
	 * Setea a la colección <code>m</code> como colección de nodos resaltados en el mapa. 
	 * 
	 * @param	m Colección de nodos resaltados.
	 * 
	 */
	public void setResaltados(Collection<DTNodo> m){
		if(this.resaltados==null) resaltados=new ArrayList<DTNodo>();
		this.resaltados.addAll(m);
	}
	
	/**
	 * Retorna la colección de nodos mapeados. 
	 * 
	 * @return      Devuelve la colección de nodos resaltados en el mapa.
	 * 
	 */
	public Collection<DTNodo> getResaltados(){return this.resaltados;}
	
	/**
	 * Agrega el String <code>m</code> a los mensajes a mostrar. 
	 * 
	 * @param	m String a agregar a los mensajes a mostrar.
	 * 
	 */
	public void setMensaje(String m)
	{mensaje=mensaje+m+"\n";}
	
	/**
	 * Retorna los mensajes a mostrar en pantalla. 
	 * 
	 * @return      los mensajes a mostrar en pantalla.
	 * 
	 */
	public String getMensaje(){return this.mensaje;}

}
