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
	
	/**
	 * Constructor por parametro. Se crea <code>JTableRutas</code> con la tabla de Rutas <code>n</code>. Invoca al constructor de <code>JScrollPane</code>
	 * 
	 * @param	n Tabla de Nodos.	
	 * 
	 */
	public JPanelRutas(JTableRutas n)
	{
		super(n);
		this.tabla=n;
		this.setPreferredSize(new Dimension(280,100));
	}
	
	/**
	 * Setea la colección de rutas <code>col</code> a <code>JPanelRutas</code>
	 * 
	 * @param	col Colección de Rutas.	
	 * 
	 */
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
	
	/**
	 * Setea el mapa <code>m</code> a <code>JPanelRutas</code>
	 * 
	 * @param	m Mapa.	
	 * 
	 */
	public void setMapa(JPanelMapa m)
	{
		this.mapa=m;
		tabla.setMapa(m);
	}
	
	/**
	 * Setea todos los atributos del VRP a partir de <code>dt</code>.
	 * 
	 * @param	dt <code>DTDepositoVRP</code>.	
	 * 
	 */
	public void setVRP(DTDepositoVRP dt)
	{
		this.vrp=dt;
		tabla.setRutas(new ArrayList<DTRuteo>());
	}
	
	/**
	 * Setea el panel de etiquetas <code>et</code> a <code>JPanelRutas</code>
	 * 
	 * @param	et Panel de Etiquetas.	
	 * 
	 */
	public void setEtiquetas(JPanelEtiquetas et)
	{
		this.etiquetas=et;
	}
}