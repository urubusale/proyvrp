package main;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JPanel;

import datatypes.DTAsignacion;
import datatypes.DTDepositoVRP;
import datatypes.DTNodo;
import datatypes.DTRuteo;

public class JPanelMapa extends JPanel 
{
	
	private boolean contornos;
	private boolean sombreado;

	private DTDepositoVRP vrp;
	private int ancho;
	private int alto;
	
	private int xminimo;
	private int xmaximo;
	private int yminimo;
	private int ymaximo;
	private Font fuente;
	private Font fuenteneg;
	private Hashtable<Integer,DTNodo> resaltados;
	private Hashtable<Integer,DTNodo> resaltadosdeasginacion;

	private Hashtable<Integer,DTNodo> asignaciones; //la clave es el deposito .... el deposito tambien esta como nodo
	private Collection<DTRuteo> resaltadosrutas;

	private Collection<DTAsignacion> asignacionescrudas;
	private Collection<DTRuteo> rutas;

	private boolean asignado;
	private boolean ruteado;

	int ancho2=xmaximo-xminimo;
	int alto2=ymaximo-yminimo;
	private int borde;
	
	private int escalaBD;
	
	/**
	 * Constructor por defecto.
	 * 
	 */
	public JPanelMapa()
	{
		super();
		vrp=new DTDepositoVRP();
		resaltados=new Hashtable<Integer,DTNodo>();
		resaltadosdeasginacion=new Hashtable<Integer,DTNodo>();	
		resaltadosrutas=new ArrayList<DTRuteo>();
		this.fuente = new Font("Verdana", Font.PLAIN, 8);
		this.fuenteneg=new Font("Helvetica-Bold",Font.BOLD,20);
		asignado=false;
		ruteado=false;
		borde=10;
		contornos=false;
		escalaBD=8;
	}
	
	/**
	 * Setea VRP a partir de <code>d</code>.
	 * 
	 * @param	d <code>DTDepositoVRP</code>.	
	 * 
	 */
	public void setVRP(DTDepositoVRP d)
	{
		this.vrp=d;
		asignado=false;
		ruteado=false;
		this.repaint();
	}
	
	private class Acumulador
	{
		public ArrayList<int[]> contornosX;
		public ArrayList<int[]> contornosY;
		public ArrayList<Integer> contadoresContornos;
		public ArrayList<int[]> sombreadosX;
		public ArrayList<int[]> sombreadosY;
		public ArrayList<Integer> contadoresSombreados;
		public Acumulador()
		{
			contornosX=new ArrayList<int[]>();
			sombreadosX=new ArrayList<int[]>();
			contornosY=new ArrayList<int[]>();
			sombreadosY=new ArrayList<int[]>();
			contadoresContornos=new ArrayList<Integer>();
			contadoresSombreados=new ArrayList<Integer>();
		}
		
	}

	/**
	 * Invocado para dibujar los distintos componenete.
	 * 
	 * @param	g Gr�fico a dibujar.	
	 * 
	 */
	public void paint (Graphics g) 
	{
		super.paint (g);
		ancho=this.getWidth()-borde*2;
		alto=this.getHeight()-borde*2;
		
		xminimo=Integer.MAX_VALUE;
		xmaximo=Integer.MIN_VALUE;
		yminimo=Integer.MAX_VALUE;
		ymaximo=Integer.MIN_VALUE;
		
		Iterator<DTNodo> it=this.vrp.getNodos().iterator();
		while(it.hasNext())
		{
			DTNodo d=it.next();
			if(d.getX()<xminimo) xminimo=d.getX();
			if(d.getX()>xmaximo) xmaximo=d.getX();
			if(d.getY()<yminimo) yminimo=d.getY();
			if(d.getY()>ymaximo) ymaximo=d.getY();
		}
		ancho2=xmaximo-xminimo;
		alto2=ymaximo-yminimo;

				
		//******************************
		Acumulador acum=new Acumulador();
		if((asignado&&this.contornos)||(asignado&&this.sombreado))
		{
			int margenx=ancho2/60;
			int margeny=ancho2/60;
			if(margenx==0)margenx=1;
			if(margeny==0)margeny=1;
			Iterator<DTAsignacion> ita= asignacionescrudas.iterator();
			while(ita.hasNext())
			{
				DTAsignacion es=ita.next();
				Collection<DTNodo> ntd=es.getClientes();
				int norte=Integer.MAX_VALUE;
				int sur=Integer.MIN_VALUE;
				int este=Integer.MAX_VALUE;
				int oeste=Integer.MIN_VALUE;
				Iterator<DTNodo> itno=ntd.iterator();
				ArrayList<DTNodo> borde=new ArrayList<DTNodo>();
				while(itno.hasNext())
				{
					DTNodo n=itno.next();
					borde.add(new DTNodo(n));
					if(n.getY()>sur)sur=n.getY();
					if(n.getY()<norte)norte=n.getY();
					if(n.getX()>oeste)oeste=n.getX();
					if(n.getY()<este)este=n.getY();
				}
				borde.add(es.getDeposito());
				DTNodo extremoizq=null;
				int extrizq=Integer.MIN_VALUE;
				int extrder=Integer.MAX_VALUE;
				DTNodo extremoder=null;
				DTNodo extremosup=null;
				DTNodo extremoinf=null;
				int extrsup=Integer.MIN_VALUE;
				int extrinf=Integer.MAX_VALUE;
				Iterator<DTNodo> extr=borde.iterator();
				while(extr.hasNext())
				{
					DTNodo pr=extr.next();
					if(pr.getX()<extrder)
					{
						extrder=pr.getX();
						extremoder=pr;
					}
					if(pr.getX()>extrizq)
					{
						extrizq=pr.getX();
						extremoizq=pr;
					}
					if(pr.getY()>extrsup)
					{
						extrsup=pr.getY();
						extremosup=pr;
					}
					if(pr.getY()<extrinf)
					{
						extrinf=pr.getY();
						extremoinf=pr;
					}
				}
				ArrayList<DTNodo> nborde;
				nborde=new ArrayList<DTNodo>();
				DTNodo extd1=new DTNodo(extremoder);
				extd1.setX(extd1.getX()-margenx);
				nborde.add(extd1);
				
				//System.out.println("derecho:"+extremoder.getId()+" izquierdo:"+extremoizq.getId()+" arriba:"+extremoinf.getId()+" abajo:"+extremosup.getId());
				
				//primer cuadrante
				DTNodo tortuga=extremoder;
				boolean haymas=true;
				while(haymas)
				{
					haymas=false;
					if((util.Distancia.getInstancia().getDistanciaGrafica(extremoinf,tortuga).intValue()!=0))
					{
						BigDecimal num=new BigDecimal(tortuga.getY()-extremoinf.getY());
						BigDecimal den=util.Distancia.getInstancia().getDistanciaGrafica(extremoinf,tortuga);
						BigDecimal sen=num.divide(den, escalaBD, RoundingMode.CEILING);
						Iterator<DTNodo> izqader=borde.iterator();
						DTNodo mayor=tortuga;
						while(izqader.hasNext())
						{
							DTNodo nt=izqader.next();
							if((util.Distancia.getInstancia().getDistanciaGrafica(tortuga,nt).intValue())!=0)
							if(nt.getX()<extremoinf.getX())
							{
								BigDecimal num2=new BigDecimal(tortuga.getY()-nt.getY());
								BigDecimal den2=util.Distancia.getInstancia().getDistanciaGrafica(tortuga,nt);
								BigDecimal sen2=num2.divide(den2, escalaBD, RoundingMode.CEILING);
							
								if(sen2.compareTo(sen)==1)
								{
									BigDecimal num3=new BigDecimal(tortuga.getY()-nt.getY());
									BigDecimal den3=util.Distancia.getInstancia().getDistanciaGrafica(tortuga,nt);
									sen=num3.divide(den3, escalaBD, RoundingMode.CEILING);
									mayor=nt;
									haymas=true;
								}
							}
						}
						if(haymas)
						{
							DTNodo m1=new DTNodo(mayor);
							m1.setX(m1.getX()-margenx);
							m1.setY(m1.getY()-margeny);
							nborde.add(m1);
							tortuga=mayor;
						}
					}
				}
				
				DTNodo eim1=new DTNodo(extremoinf);
				eim1.setY(eim1.getY()-margeny);
				nborde.add(eim1);

				//segundo cuadrante
				tortuga=extremoizq;
				haymas=true;
				java.util.Stack<DTNodo> saca=new java.util.Stack<DTNodo>();
				while(haymas)
				{
					haymas=false;
					if((util.Distancia.getInstancia().getDistanciaGrafica(extremoinf,tortuga).intValue()!=0))
					{
						BigDecimal num=new BigDecimal(tortuga.getY()-extremoinf.getY());
						BigDecimal den=util.Distancia.getInstancia().getDistanciaGrafica(extremoinf,tortuga);
						BigDecimal sen=num.divide(den, escalaBD, RoundingMode.CEILING);
						Iterator<DTNodo> izqader=borde.iterator();
						DTNodo mayor=tortuga;
						while(izqader.hasNext())
						{
							DTNodo nt=izqader.next();
							if((util.Distancia.getInstancia().getDistanciaGrafica(tortuga,nt).intValue())!=0)
							if((nt.getX()>extremoinf.getX()))
							{
								BigDecimal num2=new BigDecimal(tortuga.getY()-nt.getY());
								BigDecimal den2=util.Distancia.getInstancia().getDistanciaGrafica(tortuga,nt);
								BigDecimal sen2=num2.divide(den2, escalaBD, RoundingMode.CEILING);
								if(sen2.compareTo(sen)==1)
								{
									BigDecimal num3=new BigDecimal(tortuga.getY()-nt.getY());
									BigDecimal den3=util.Distancia.getInstancia().getDistanciaGrafica(tortuga,nt);
									sen=num3.divide(den3, escalaBD, RoundingMode.CEILING);
									mayor=nt;
									haymas=true;
								}
							}
						}
						if(haymas)
						{
							saca.push(mayor);
							tortuga=mayor;
						}
					}
				}
				while(!saca.isEmpty())
				{
					DTNodo m1=new DTNodo(saca.pop());
					m1.setX(m1.getX()+margenx);
					m1.setY(m1.getY()-margeny);
					nborde.add(m1);
				}
				DTNodo eizm1=new DTNodo(extremoizq);
				eizm1.setX(eizm1.getX()+margenx);
				nborde.add(eizm1);
				//tercer cuadrante
				tortuga=extremoizq;
				haymas=true;
				while(haymas)
				{
					haymas=false;
					if((util.Distancia.getInstancia().getDistanciaGrafica(extremosup,tortuga).intValue()!=0))
					{
						BigDecimal num=new BigDecimal(tortuga.getY()-extremosup.getY());
						BigDecimal den=util.Distancia.getInstancia().getDistanciaGrafica(extremosup,tortuga);
						BigDecimal sen=num.divide(den, escalaBD, RoundingMode.CEILING);
						Iterator<DTNodo> izqader=borde.iterator();
						DTNodo mayor=tortuga;
						while(izqader.hasNext())
						{
							DTNodo nt=izqader.next();
							if((util.Distancia.getInstancia().getDistanciaGrafica(tortuga,nt).intValue())!=0)
							if(nt.getX()>extremosup.getX())
							{
								BigDecimal num2=new BigDecimal(tortuga.getY()-nt.getY());
								BigDecimal den2=util.Distancia.getInstancia().getDistanciaGrafica(tortuga,nt);
								BigDecimal sen2=num2.divide(den2, escalaBD, RoundingMode.CEILING);
							
								if(sen2.compareTo(sen)==-1)
								{
									BigDecimal num3=new BigDecimal(tortuga.getY()-nt.getY());
									BigDecimal den3=util.Distancia.getInstancia().getDistanciaGrafica(tortuga,nt);
									sen=num3.divide(den3, escalaBD, RoundingMode.CEILING);
									mayor=nt;
									haymas=true;
								}
							}
						}
						if(haymas)
						{
							DTNodo m1=new DTNodo(mayor);
							m1.setY(m1.getY()+margeny);
							m1.setX(m1.getX()+margenx);
							nborde.add(m1);
							tortuga=mayor;
						}
					}
				}
				
				DTNodo eim1s=new DTNodo(extremosup);
				eim1s.setY(eim1s.getY()+margeny);
				nborde.add(eim1s);
				//cuarto cuadrante
				tortuga=extremoder;
				haymas=true;
				java.util.Stack<DTNodo> saca2=new java.util.Stack<DTNodo>();
				while(haymas)
				{
					haymas=false;
					if((util.Distancia.getInstancia().getDistanciaGrafica(extremosup,tortuga).intValue()!=0))
					{
						BigDecimal num=new BigDecimal(tortuga.getY()-extremosup.getY());
						BigDecimal den=util.Distancia.getInstancia().getDistanciaGrafica(extremosup,tortuga);
						BigDecimal sen=num.divide(den, escalaBD, RoundingMode.CEILING);
						Iterator<DTNodo> izqader=borde.iterator();
						DTNodo mayor=tortuga;
						while(izqader.hasNext())
						{
							DTNodo nt=izqader.next();
							if((util.Distancia.getInstancia().getDistanciaGrafica(tortuga,nt).intValue())!=0)
							if(nt.getX()<extremosup.getX())
							{
								BigDecimal num2=new BigDecimal(tortuga.getY()-nt.getY());
								BigDecimal den2=util.Distancia.getInstancia().getDistanciaGrafica(tortuga,nt);
								BigDecimal sen2=num2.divide(den2, escalaBD, RoundingMode.CEILING);
							
								if(sen2.compareTo(sen)==-1)
								{
									BigDecimal num3=new BigDecimal(tortuga.getY()-nt.getY());
									BigDecimal den3=util.Distancia.getInstancia().getDistanciaGrafica(tortuga,nt);
									sen=num3.divide(den3, escalaBD, RoundingMode.CEILING);
									mayor=nt;
									haymas=true;
								}
							}
						}
						if(haymas)
						{
							saca2.push(mayor);
							tortuga=mayor;
						}
					}
				}
				while(!saca2.isEmpty())
				{
					DTNodo eim1q=new DTNodo(saca2.pop());
					eim1q.setY(eim1q.getY()+margeny);
					eim1q.setX(eim1q.getX()-margenx);
					nborde.add(eim1q);
				}
				Iterator<DTNodo> itb=nborde.iterator();
				int contador=0;
				int equis[];
				equis=new int[nborde.size()];
				int yes[];
				yes=new int[nborde.size()];
				while(itb.hasNext())
				{
					DTNodo pr=itb.next();
					equis[contador]=this.transformarX(pr.getX());
					yes[contador]=this.transformarY(pr.getY());
					contador++;
				}
				
				if(this.sombreado)
				{
					acum.sombreadosX.add(equis);
					acum.sombreadosY.add(yes);
					acum.contadoresSombreados.add(new Integer(contador));
				}
				if(this.contornos)
				{
					acum.contornosX.add(equis);
					acum.contornosY.add(yes);
					acum.contadoresContornos.add(new Integer(contador));
				}
			}
			
		}	
		//**************************************
		int cantsombr=acum.sombreadosX.size();
		for(int i=0;i<cantsombr;i++)
		{
			g.setColor(Color.LIGHT_GRAY);
			((Graphics2D)g).fillPolygon((int[])acum.sombreadosX.toArray()[i],(int[]) acum.sombreadosY.toArray()[i],(int)acum.contadoresSombreados.toArray()[i]);

		}
		
		int cantcont=acum.contornosX.size();
		for(int i=0;i<cantcont;i++)
		{
			g.setColor(Color.BLACK);
			((Graphics2D)g).drawPolygon((int[])acum.contornosX.toArray()[i],(int[]) acum.contornosY.toArray()[i],(int)acum.contadoresContornos.toArray()[i]);
		}
		
		Iterator<DTNodo> it2=this.vrp.getNodos().iterator();
		while(it2.hasNext())
		{
			DTNodo d=it2.next();
			if(d.getEsDesposito())	
			{
				if(!asignado) g.setColor(Color.RED);
				else
				{
					int c=asignaciones.get(d.getId()).getId();
					g.setColor(this.getColor(c));
				}
				if(resaltados.containsKey(d.getId())||resaltadosdeasginacion.containsKey(d.getId())) g.setFont(fuenteneg);
				else g.setFont(fuente);
				g.drawString("D_"+d.getId(), transformarX(d.getX()),transformarY(d.getY()) );
			}
			else 
			{
				if(!asignado) g.setColor(Color.BLUE);
				else
				{
					int c=asignaciones.get(d.getId()).getId();
					g.setColor(this.getColor(c));
				}
				if(resaltados.containsKey(d.getId())||resaltadosdeasginacion.containsKey(d.getId()))g.setFont(fuenteneg);
				else g.setFont(fuente);
				g.drawString("C_"+d.getId(), transformarX(d.getX()),transformarY(d.getY()) );
			}
		}
		if(ruteado)
		{
			Iterator<DTRuteo> itr=rutas.iterator();
			while(itr.hasNext())
			{
				DTRuteo dt=itr.next();
				Color deruta=this.getColorDeRuta(dt);
				if(estaResaltadaRuta(dt))
				{
					((Graphics2D)g).setStroke(new BasicStroke(3));
					 g.setColor(deruta);
				}
				else
				{
					((Graphics2D)g).setStroke(new BasicStroke(1));
					g.setColor(deruta);
				}
				DTNodo dep=dt.getDeposito();
				Iterator<DTNodo> itclientes=dt.getRuta().iterator();
				int xprevio=dep.getX();
				int yprevio=dep.getY();
				while(itclientes.hasNext())
				{
					DTNodo cli=itclientes.next();
					g.drawLine(transformarX(xprevio), transformarY(yprevio),transformarX(cli.getX()),transformarY(cli.getY()));
					xprevio=cli.getX();
					yprevio=cli.getY();
				}
				g.drawLine(transformarX(xprevio), transformarY(yprevio),transformarX(dep.getX())+5,transformarY(dep.getY())+5);
			}
		}
		
	}
	
	/**
	 * Retorna True si la ruta <code>r</code> esta resaltada. 
	 *
	 * @return      <code>true</code> si la ruta <code>r</code> esta resaltada. 
	 * 
	 */
	public boolean estaResaltadaRuta(DTRuteo r)
	{
		Iterator<DTRuteo> it=this.resaltadosrutas.iterator();
		while(it.hasNext())
		{
			DTRuteo d=it.next();
			if(d.getDeposito().getId()!=r.getDeposito().getId())
			{}
			else
			{
				if(d.getRuta().size()==0){}
				else
				{
					if(r.getRuta().size()==0){}
					else
					{
						if(r.getRuta().iterator().next().getId()==d.getRuta().iterator().next().getId()) return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Transforma <code>X</code> para poder ser dibujado en el mapa.
	 * 
	 * @param	x Punto a dibujar.
	 * @return      Punto transformado para dibujar.
	 * 
	 */
	public int transformarX(int x)
	{
		int posrelativa=x-xminimo;
		return ancho*posrelativa/ancho2+borde;
	}
	
	/**
	 * Transforma <code>Y</code> para poder ser dibujado en el mapa.
	 * 
	 * @param	y Punto a dibujar.
	 * @return      Punto transformado para dibujar.
	 * 
	 */
	public int transformarY(int y)
	{
		int posrelativa=y-yminimo;
		return alto*posrelativa/alto2+borde;
	}
	
	/**
	 * Setea a la colecci�n <code>c</code> como colecci�n de nodos resaltados en el mapa y los dibuja. 
	 * 
	 * @param	c Colecci�n de nodos resaltados.
	 * 
	 */
	public void setResaltados(Collection<DTNodo> c)
	{
		this.resaltados=new Hashtable<Integer,DTNodo>();
		Iterator<DTNodo> it2=c.iterator();
		while(it2.hasNext())
		{
			DTNodo n=it2.next();
			resaltados.put(n.getId(), n);
		}
		this.repaint();
	}

	/**
	 * Setea a la colecci�n <code>c</code> como colecci�n de rutas resaltadas en el mapa y las dibuja. 
	 * 
	 * @param	c Colecci�n de rutas resaltadas.
	 * 
	 */
	public void setRutasResaltadas(Collection<DTRuteo> c)
	{
		this.resaltadosrutas=c;
		//System.out.println("rutas resalteadas "+this.resaltadosrutas.size());
		this.repaint();
	}

	/**
	 * Setea la colecci�n de asignaciones <code>c</code> y las dibuja.
	 * 
	 * @param	c Colecci�n de <code>DTAsignacion</code>.	
	 * 
	 */
	public void setAsignaciones(Collection<DTAsignacion> c)
	{
		this.asignaciones=new Hashtable<Integer,DTNodo>();
		Iterator<DTAsignacion> it2=c.iterator();
		while(it2.hasNext())
		{
			DTAsignacion n=it2.next();
			Iterator<DTNodo> itn=n.getClientes().iterator();
			DTNodo dep=n.getDeposito();
			asignaciones.put(dep.getId(), dep);
			while(itn.hasNext())
			{
				DTNodo dn=itn.next();
				asignaciones.put(dn.getId(), dep);				
			}
			//System.out.println(n.getClientes().size()+"--------------------------");

		}
		//System.out.println("se asigno al mapa"+asignaciones.size());
		asignado=true;
		asignacionescrudas=c;
		ruteado=false;
		this.repaint();
	}

	/**
	 * Setea la colecci�n de rutas <code>c</code> y las dibuja.
	 * 
	 * @param	c Colecci�n de Rutas.	
	 * 
	 */
	public void setRutas(Collection<DTRuteo> c)
	{
		this.rutas=c;	
		ruteado=true;
		this.repaint();
	}

	/**
	 * Retorna un color para el dep�sito<code>dep</code> de una paleta de colores. 
	 *
	 * @param	dep Dep�sito.	
	 * @return      Color. 
	 * 
	 */
	public Color getColor(int dep)
	{
		int numerodeposito=-1;
		int contador=0;
		Iterator<DTAsignacion> it=asignacionescrudas.iterator();
		while(it.hasNext())
		{
			DTAsignacion dt=it.next();
			if(dt.getDeposito().getId()==dep)numerodeposito=contador;
			contador++;
		}
		int col=numerodeposito % 6;
		if(col==0) return Color.BLACK;
		if(col==1) return Color.BLUE;
		if(col==2) return Color.RED;
		if(col==3) return Color.GREEN;
		if(col==4) return Color.ORANGE;
		if(col==5) return Color.MAGENTA;
		return Color.BLACK;

	}
	
	/**
	 * Retorna un color para la ruta <code>d</code> de una paleta de colores. 
	 *
	 * @param	d DTRuteo.	
	 * @return      Color. 
	 * 
	 */
	public Color getColorDeRuta(DTRuteo d)
	{
		int numeroderuta=-1;
		int contador=0;
		Iterator<DTRuteo> it=this.rutas.iterator();
		while(it.hasNext())
		{
			DTRuteo dt=it.next();
			if(dt.getDeposito().getId()==d.getDeposito().getId())
			{
				if((dt.getRuta().size()>0)&&(d.getRuta().size()>0))
				{
					if(dt.getRuta().iterator().next().getId()==d.getRuta().iterator().next().getId())
					{
						numeroderuta=contador;
					}
				}
			}
			contador++;
		}
		int col=numeroderuta % 7;
		if(col==0) return Color.BLUE;
		if(col==1) return Color.RED;
		if(col==2) return Color.GREEN;
		if(col==3) return Color.ORANGE;
		if(col==4) return Color.MAGENTA;
		if(col==5) return Color.BLACK;
		if(col==6) return Color.YELLOW;
		return Color.BLACK;
	}
	
	/**
	 * Setea a la colecci�n <code>vs</code> como colecci�n de nodos resaltados asignaciones en el mapa y los dibuja. 
	 * 
	 * @param	vs Colecci�n de nodos resaltados asignaciones.
	 * 
	 */
	public void setResaltadosAsignaciones(Collection<Integer> vs)
	{
		this.resaltadosdeasginacion=new Hashtable<Integer,DTNodo>();
		Iterator<Integer> it=vs.iterator();
		while(it.hasNext())
		{
			Integer n=it.next();
			Iterator<DTAsignacion> its=this.asignacionescrudas.iterator();
			while(its.hasNext())
			{
				DTAsignacion an=its.next();
				if(an.getDeposito().getId()==n)
				{
					Iterator<DTNodo> no=an.getClientes().iterator();
					while(no.hasNext())
					{
						DTNodo noa=no.next();
						resaltadosdeasginacion.put(noa.getId(), noa);
					}
					resaltadosdeasginacion.put(an.getDeposito().getId(), an.getDeposito());
					
				}
				
				
			}
		}
		this.repaint();

	}

	/**
	 * Setea si tiene contorno y los dibuja. 
	 *
	 * @param	c <code>true</code> si tiene contorno.
	 * 
	 */
	public void setContorno(boolean c)
	{
		this.contornos=c;
		this.repaint();
	}
	
	/**
	 * Setea si tiene sombreado y los dibuja. 
	 *
	 * @param	c <code>true</code> si tiene sombreado. 
	 * 
	 */
	public void setSombreado(boolean c)
	{
		this.sombreado=c;
		this.repaint();
	}
}
