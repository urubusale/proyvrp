package main;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JScrollPane;
import datatypes.DTAsignacion;

public class JPanelAsignaciones extends JScrollPane 
{
	private JTableAsignaciones tabla;
	private JPanelMapa mapa;
	
	/**
	 * Constructor por parametro. Se crea <code>JPanelAsignaciones</code> con la tabla de asignaciones <code>n</code>. Invoca al constructor de <code>JScrollPane</code>
	 * 
	 * @param	n Tabla de Asignaciones.	
	 * 
	 */
	public JPanelAsignaciones(JTableAsignaciones n)
	{
		super(n);
		this.tabla=n;
		this.setPreferredSize(new Dimension(280,10));
	}
	
	/**
	 * Setea la tabla de asignaciones <code>cl</code> a <code>JTableAsignaciones</code>
	 * 
	 * @param	cl Colección de <code>DTAsignacion</code>.	
	 * 
	 */
	public void setAsignaciones(Collection<DTAsignacion> cl)
	{
		tabla.setAsignaciones(cl);
		this.setPreferredSize(new Dimension(280,100));
	}
	
	/**
	 * Limpia el JPanelAsignaciones. Setea la tabla de asignaciones vacia.
	 * 
	 * 
	 */
	public void limpiar()
	{
		tabla.setAsignaciones(new ArrayList<DTAsignacion>());
		this.setPreferredSize(new Dimension(280,100));
	}
	
	/**
	 * Setea el mapa <code>m</code> a <code>JTableAsignaciones</code>
	 * 
	 * @param	m Mapa.	
	 * 
	 */
	public void setMapa(JPanelMapa m)
	{
		this.mapa=m;
		tabla.setMapa(m);
	}
	
}
