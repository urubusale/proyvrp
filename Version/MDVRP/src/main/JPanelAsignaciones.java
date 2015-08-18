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
	
	
	public JPanelAsignaciones(JTableAsignaciones n)
	{
		super(n);
		this.tabla=n;
		this.setPreferredSize(new Dimension(280,10));

		//JTableNodos tabla;
		//this.add(tabla);
	}
	
	public void setAsignaciones(Collection<DTAsignacion> cl)
	{
		tabla.setAsignaciones(cl);
		this.setPreferredSize(new Dimension(280,100));

	}
	public void limpiar()
	{
		tabla.setAsignaciones(new ArrayList<DTAsignacion>());
		this.setPreferredSize(new Dimension(280,100));
	}
	public void setMapa(JPanelMapa m)
	{
		this.mapa=m;
		tabla.setMapa(m);
	}
	
}
