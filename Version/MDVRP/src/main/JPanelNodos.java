package main;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import datatypes.DTDepositoVRP;

public class JPanelNodos extends JScrollPane 
{
	private JTableNodos tabla;
	private JPanelMapa mapa;
	
	/**
	 * Constructor por parametro. Se crea <code>JPanelNodos</code> con la tabla de nodos <code>n</code>. Invoca al constructor de <code>JScrollPane</code>
	 * 
	 * @param	n Tabla de Nodos.	
	 * 
	 */
	public JPanelNodos(JTableNodos n)
	{
		super(n);
		this.tabla=n;
		this.setPreferredSize(new Dimension(280,10));

		//JTableNodos tabla;
		//this.add(tabla);
	}
	
	/**
	 * Setea todos los atributos del VRP a partir de <code>dt</code>.
	 * 
	 * @param	dt <code>DTDepositoVRP</code>.	
	 * 
	 */
	public void setVRP(DTDepositoVRP dt)
	{
		tabla.setVRP(dt);
		this.setPreferredSize(new Dimension(280,10));

	}
	
	/**
	 * Setea el mapa <code>m</code> a <code>JPanelNodos</code>
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