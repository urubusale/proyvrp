package main;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	}
	
	public void setVRP(DTDepositoVRP d)
	{
		this.vrp=d;
		asignado=false;
		ruteado=false;
		this.repaint();
	}

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

		
		//System.out.println("ancho alto "+ancho+" "+alto );
		
		
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
	//	if(vrp.getNodos().size()>0)g.drawLine(transformarX(xminimo), transformarY(yminimo),transformarX(xmaximo),transformarY(ymaximo));
	}
	
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
	
	public int transformarX(int x)
	{
		int posrelativa=x-xminimo;
		return ancho*posrelativa/ancho2+borde;

	}
	
	public int transformarY(int y)
	{
		int posrelativa=y-yminimo;
		return alto*posrelativa/alto2+borde;
	}
	
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

	public void setRutasResaltadas(Collection<DTRuteo> c)
	{
		this.resaltadosrutas=c;
		//System.out.println("rutas resalteadas "+this.resaltadosrutas.size());
		this.repaint();
	}

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

		}
		//System.out.println("se asigno al mapa"+asignaciones.size());
		asignado=true;
		asignacionescrudas=c;
		this.repaint();
	}


	public void setRutas(Collection<DTRuteo> c)
	{
		this.rutas=c;

		
	
		ruteado=true;
		this.repaint();
	}

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
}
