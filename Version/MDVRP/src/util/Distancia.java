package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import logica.Sistema;
import datatypes.DTNodo;


public class Distancia 
{
	static private Distancia instancia=null;
	static public Distancia getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Distancia();
			return instancia;
		}else return instancia;
	}
	private Distancia()
	{
		euclidia=true;
		lowercol=false;
		lowerrow=false;
		ran=new Random(1234);
	}

	static private boolean euclidia;// si no es euclidia se asuma EDGE_WEIGHT_FORMAT: LOWER_ROW 
	static private boolean lowercol;
	static private boolean lowerrow;
	static private int dimension;
	static private Collection<Integer> valores;
	static private Random ran;

	public int getDistancia(DTNodo n1,DTNodo n2)
	{
		if(euclidia)
		{
			int difx=n1.getX()-n2.getX();
			int dify=n1.getY()-n2.getY();
			long distancia=Math.round(Math.sqrt((difx*difx+dify*dify)));
			return (int)distancia;
		}else
		{
			if(this.lowercol)
			return this.obtenerDistExplicitaLowerCol(n1, n2);
			else
			{
				if (this.lowerrow)
				{
					return obtenerDistExplicitaLowerRow(n1,n2);
				}
				else return 1;
			}
					
		}
	}
	
	private int obtenerDistExplicitaLowerRow(DTNodo n1,DTNodo n2)
	{
		DTNodo menor;
		DTNodo mayor;
		if(n1.getId()<n2.getId()) 
		{
			menor=n1;
			mayor=n2;
		}
		else 
		{
			menor=n2;
			mayor=n1;
		}
		if(n1.getId()==n2.getId())return 0;
		int contador=0;
		for(int i=2;i<=dimension;i++)//recorro filas
		{
			for(int j=1;j<i;j++)//recorr columnas
			{
				if(i==mayor.getId()&&j==menor.getId())
				{
					Integer valor=(Integer)valores.toArray()[contador];
	//				System.out.println("retorno de matriz lower row"+i+" "+j+" "+valor);
					return valor;
				}
				contador++;
			}
		}
		
		return 1;
	}

	private int obtenerDistExplicitaLowerCol(DTNodo n1,DTNodo n2)
	{
		DTNodo menor;
		DTNodo mayor;
		if(n1.getId()<n2.getId()) 
		{
			menor=n1;
			mayor=n2;
		}
		else 
		{
			menor=n2;
			mayor=n1;
		}
		if(n1.getId()==n2.getId())return 0;
		int contador=0;
		for(int i=1;i<dimension;i++)//recorro columnas
		{
			for(int j=(1+i);j<=dimension;j++)//recorr filas
			{
				if(i==menor.getId()&&j==mayor.getId())
				{
					Integer valor=(Integer)valores.toArray()[contador];
			//		System.out.println("retorno de matriz lowercol"+j+" "+i+" "+valor);
					return valor;
				}
				contador++;
			}
		}
		
		return 1;
	}

	public void setearMatrizExplicitaLowerCol(int d,Collection<Integer> valores)
	{
		this.dimension=d;
		this.valores=valores;
		this.euclidia=false;
		this.lowercol=true;
		this.lowerrow=false;
	}
	
	public void setearMatrizExplicitaLowerRow(int d,Collection<Integer> valores)
	{
		this.dimension=d;
		this.valores=valores;
		this.euclidia=false;
		this.lowercol=false;
		this.lowerrow=true;
	}
	public void setearEuclidia()
	{
		this.euclidia=true;
		this.lowercol=false;
		this.lowerrow=false;
	}
	public void mapearMatriz(Collection<DTNodo> col)
	{
		int mayorDistancia=0;
		Iterator<Integer> it=valores.iterator();
		while(it.hasNext())
		{
			Integer inta=it.next();
			if(inta>mayorDistancia)mayorDistancia=inta.intValue();
		}
		
		int rango=mayorDistancia*col.size()/10;
		System.out.println("rango "+rango+" "+col.size());
		DTNodo n1=null;
		DTNodo n2=null;
		System.out.println("mayor distancia "+mayorDistancia);
		
		int menorDistancia=Integer.MAX_VALUE;
		Iterator<DTNodo> it2=col.iterator();
		while(it2.hasNext())
		{
			DTNodo n=it2.next();
			Iterator<DTNodo> it3=col.iterator();
			while(it3.hasNext())
			{
				DTNodo nn=it3.next();
				int dist=this.getDistancia(n, nn);
				if((dist>0)&&(dist<menorDistancia))
				{
					menorDistancia=dist;
					n1=n;
					n2=nn;
				}
			}
		}
		System.out.println("asigno "+n1.getId());
		System.out.println("asigno "+n2.getId());
		
		//en n1 y n2 están los iniciales n1 es el centro
		ArrayList<DTNodo> mapeados=new ArrayList<DTNodo>();
		n1.setX(rango/2);
		n1.setY(rango/2);
		mapeados.add(n1);
		n2.setX(rango/2+this.getDistancia(n1, n2));
		n2.setY(rango/2);
		mapeados.add(n2);
	//	System.out.println(n1.getId()+"*******************"+n2.getId());
		while(mapeados.size()<col.size())
		{
			
			DTNodo proximocercano=null;
			int distanciaproximo=Integer.MAX_VALUE;
			Iterator<DTNodo> itm=mapeados.iterator();
			while(itm.hasNext())
			{
				DTNodo map=itm.next();
				Iterator<DTNodo> itmmm=col.iterator();
				while(itmmm.hasNext())
				{
					DTNodo ma=itmmm.next();
					if(mapeados.contains(ma)){}
					else
					{
						if(this.getDistancia(ma, map)<distanciaproximo)
						{
							distanciaproximo=this.getDistancia(ma, map);
							proximocercano=ma;
						}
					}
				}
			}
			// aca se busca la intersección de los ciruclos.....
			System.out.println("asigno "+proximocercano.getId());
			
			insertarNodo(mapeados,proximocercano,rango);
			//
		}
			
	
	}
	
	public void insertarNodo(ArrayList<DTNodo> maps,DTNodo p,int rango)
	{
		Sistema.getInstancia().adelantarPorgresoDeAvance((float)100/this.dimension);
		try{Thread.sleep(1000);}catch(Exception ex){ex.printStackTrace();}
		Sistema.getInstancia().setMapeados(new ArrayList<DTNodo>(maps));
		Sistema.getInstancia().setRadio(p);
		
		ArrayList<DTPunto> interseccion=new ArrayList<DTPunto>();
		DTPunto mejor=null;
		int mejorpunto=0;

		for (int i=0;i<10000;i++)
		{
			int x=ran.nextInt(rango);//provisorio
			int y=ran.nextInt(rango);
			DTPunto punto=new DTPunto(x,y);
			Iterator<DTNodo> it=maps.iterator();
			boolean adentro=true;
			int contador=0;

			while(it.hasNext())
			{
				DTNodo prox=it.next();
				if(Math.sqrt((x-prox.getX())*(x-prox.getX())+(y-prox.getY())*(y-prox.getY()))<this.getDistancia(p, prox))
				{
					contador++;
					if(contador>mejorpunto)
					{
						mejorpunto=contador;
						mejor=punto;
					}
					
				}else{adentro=false;}
			}
			if(adentro)interseccion.add(punto);
		}
		
		
		if(interseccion.size()==0)
		{
			
			if(mejor!=null)
			{
				System.out.println("agrego el mejor punto para "+p.getId()+" "+mejorpunto);
				interseccion.add(mejor);
			}else 
			{
				System.out.println("no se encontro punto para para "+p.getId()+" se agrega uno al azar");
				interseccion.add(new DTPunto(ran.nextInt(rango),ran.nextInt(rango)));
			}
		}
		
		DTPunto pu=null;
		int mayor=0;
		
		Iterator<DTPunto> ita=interseccion.iterator();
		while(ita.hasNext())
		{
			DTPunto pun=ita.next();
			Iterator<DTNodo> iu=maps.iterator();
			int cont=0;
			while(iu.hasNext())
			{
				DTNodo nop=iu.next();
				cont=cont+(int)(Math.sqrt((pun.getX()-nop.getX())*(pun.getX()-nop.getX())+(pun.getY()-nop.getY())*(pun.getY()-nop.getY())));
			}
			if(cont>mayor)
			{
				mayor=cont;
				pu=pun;
			}
					
			
		}
		
		if(pu!=null)
		{
			p.setX(pu.getX());
			p.setY(pu.getY());
		}
		else
		{
			System.out.println("no entró ningún numero-....."+p.getId());
			p.setX(rango/2);
			p.setY(rango/2);
		}
		maps.add(p);
		

	}
	
	private class DTPunto
	{
		private int x;
		private int y;
		public DTPunto(int x,int y)
		{
			this.x=x;
			this.y=y;
		}
		public int getX(){return x;}
		public int getY(){return y;}
	}
}