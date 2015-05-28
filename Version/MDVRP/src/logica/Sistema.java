package logica;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;

import datatypes.*;

public class Sistema implements ISistema
{
	static private Sistema instancia=null;
	static public Sistema getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Sistema();
			return instancia;
		}else return instancia;
	}
	private Sistema()
	{
	}
	
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
	
	
	public Collection<DTAsignacion> asignar(DTDepositoVRP d)
	{
		return Fabrica.getInstancia().getAsignacion().asignar(d);
	}

	public Collection<DTRuteo> rutear(DTAsignacion dt,int capacidad)
	{
		return Fabrica.getInstancia().getRuteo().rutear(dt,capacidad);
	}
	
	public Collection<DTAsignacion> asignarCap(DTDepositoVRP d)
	{
		return Fabrica.getInstancia().getAsignacion().asignarCap(d);
	}
	
	public Collection<DTAsignacion> asignarCap2(DTDepositoVRP d)
	{
		return Fabrica.getInstancia().getAsignacion().asignarCap2(d);
	}

	
	
	private int estadoConsulta;
	private double progreso;

	synchronized public int getEstadoConsulta()
	{
		return estadoConsulta;
	}
	synchronized public void setAbortoEstadoConsulta()
	{
		estadoConsulta=-1;
	}
	synchronized public void setFinalizadoOkEstadoConsulta()
	{
		estadoConsulta=1;
	}
	synchronized public void setInicioEstadoConsulta()
	{
		estadoConsulta=0;
		progreso=0;
	}
	
	public int getPorgresoDeAvance()
	{
		return (int) Math.round(progreso);
	
	}
	synchronized public void adelantarPorgresoDeAvance(float p)
	{
//		System.out.println("adelantado .... "+p);
		this.progreso=this.progreso+p;
	}

	private Collection<DTNodo> mapeados;
	private DTNodo radio;
	public DTNodo getRadio(){return radio;}
	public void setRadio(DTNodo s){this.radio=s;}
	public void setMapeados(Collection<DTNodo> m){this.mapeados=m;}
	public Collection<DTNodo> getMapeados(){return this.mapeados;}
}


