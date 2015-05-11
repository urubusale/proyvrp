package main;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JScrollPane;
import datatypes.DTDepositoVRP;
import datatypes.DTRuteo;

public class JPanelRutas extends JScrollPane 
{
	private JTableRutas tabla;
	private JPanelMapa mapa;
	private DTDepositoVRP vrp;

	public JPanelRutas(JTableRutas n)
	{
		super(n);
		this.tabla=n;
		this.setPreferredSize(new Dimension(280,100));
	}
	
	public void setRutas(Collection<DTRuteo> col)
	{
		tabla.setRutas(col);
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
	
}
