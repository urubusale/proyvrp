package main;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JScrollPane;

import datatypes.DTDepositoVRP;
import datatypes.DTRuteo;

public class JPanelRutas extends JScrollPane 
{
	private JTableRutas tabla;
	private JPanelMapa mapa;
	private DTDepositoVRP vrp;
	private JPanelEtiquetas etiquetas;
	public JPanelRutas(JTableRutas n)
	{
		super(n);
		this.tabla=n;
		this.setPreferredSize(new Dimension(280,100));
	}
	
	public void setRutas(Collection<DTRuteo> col)
	{
		tabla.setRutas(col);
		Iterator<DTRuteo> r=col.iterator();
		int suma=0;
		while(r.hasNext())
		{
			suma=suma+r.next().getCosto();
		}
		this.etiquetas.setCosto(suma);
		this.setPreferredSize(new Dimension(280,100));

	}
	public void setMapa(JPanelMapa m)
	{
		this.mapa=m;
		tabla.setMapa(m);
	}
	
	public void setVRP(DTDepositoVRP dt)
	{
		this.vrp=dt;
		tabla.setRutas(new ArrayList<DTRuteo>());
	}
	
	public void setEtiquetas(JPanelEtiquetas et)
	{
		this.etiquetas=et;
	}
}
