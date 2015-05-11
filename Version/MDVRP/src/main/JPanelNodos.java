package main;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import datatypes.DTDepositoVRP;

public class JPanelNodos extends JScrollPane 
{
	private JTableNodos tabla;
	private JPanelMapa mapa;
	public JPanelNodos(JTableNodos n)
	{
		super(n);
		this.tabla=n;
		this.setPreferredSize(new Dimension(280,10));

		//JTableNodos tabla;
		//this.add(tabla);
	}
	
	public void setVRP(DTDepositoVRP dt)
	{
		tabla.setVRP(dt);
		this.setPreferredSize(new Dimension(280,10));

	}
	public void setMapa(JPanelMapa m)
	{
		this.mapa=m;
		tabla.setMapa(m);
	}
	
}
