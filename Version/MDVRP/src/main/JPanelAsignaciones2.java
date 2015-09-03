package main;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JScrollPane;
import datatypes.DTAsignacion;

public class JPanelAsignaciones2 extends JScrollPane 
{
	private JTableAsignaciones2 tabla;
	private JPanelMapa mapa;
	
	/**
	 * Constructor por parametro. Se crea <code>JPanelAsignaciones2</code> con la tabla de asignaciones <code>n</code>. Invoca al constructor de <code>JScrollPane</code>
	 * 
	 * @param	n Tabla de Asignaciones.	
	 * 
	 */
	public JPanelAsignaciones2(JTableAsignaciones2 n)
	{
		super(n);
		this.tabla=n;
		this.setPreferredSize(new Dimension(280,10));

		//JTableNodos tabla;
		//this.add(tabla);
	}
	
	/**
	 * Setea la tabla de asignaciones <code>cl</code> a <code>JTableAsignaciones2</code>
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
	 * Limpia el JPanelAsignaciones2. Setea la tabla de asignaciones vacia.
	 * 
	 * 
	 */
	public void limpiar()
	{
		tabla.setAsignaciones(new ArrayList<DTAsignacion>());
		this.setPreferredSize(new Dimension(280,100));
	}
	
	/**
	 * Setea el mapa <code>m</code> a <code>JTableAsignaciones2</code>
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
