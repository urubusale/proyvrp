package main;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import datatypes.DTDepositoVRP;
import datatypes.DTNodo;

public class JTableNodos extends JTable 
{
	private JPanelMapa mapa;
	
	/**
	 * Constructor por defecto. Invoca al constructor de <code>JTable</code>
	 * 
	 */
	public JTableNodos()
	{
		super();
		this.setAutoCreateColumnsFromModel(true);
		this.setAutoCreateRowSorter(false);
		TableModel dataModel=new ModeloTablaNodos();
		this.setModel(dataModel);
		this.getColumnModel().getColumn(0).setPreferredWidth(80);
		this.getColumnModel().getColumn(0).setHeaderValue(new String("ID"));
		this.getColumnModel().getColumn(1).setHeaderValue(new String("X"));
		this.getColumnModel().getColumn(2).setHeaderValue(new String("Y"));
		this.getColumnModel().getColumn(3).setHeaderValue(new String("DEM./CAP."));
		this.getColumnModel().getColumn(4).setHeaderValue(new String("TIPO"));

		this.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.getSelectionModel().addListSelectionListener(this);
		this.getTableHeader().setReorderingAllowed(false);
	}
	
	/**
	 * Setea todos los atributos del VRP a partir de <code>d</code>.
	 * 
	 * @param	d <code>DTDepositoVRP</code>.	
	 * 
	 */
	public void setVRP(DTDepositoVRP d)
	{
		TableModel dataModel=new ModeloTablaNodos(d);
		this.setModel(dataModel);
		this.getColumnModel().getColumn(0).setPreferredWidth(80);
		this.getColumnModel().getColumn(0).setHeaderValue(new String("ID"));
		this.getColumnModel().getColumn(1).setHeaderValue(new String("X"));
		this.getColumnModel().getColumn(2).setHeaderValue(new String("Y"));
		this.getColumnModel().getColumn(3).setHeaderValue(new String("DEM./CAP."));
		this.getColumnModel().getColumn(4).setHeaderValue(new String("TIPO"));
		
		javax.swing.table.TableRowSorter trsr=new javax.swing.table.TableRowSorter< ModeloTablaNodos>(( ModeloTablaNodos)this.getModel());
		trsr.setComparator(0, new Comparador());
		trsr.setComparator(1, new Comparador());
		trsr.setComparator(2, new Comparador());
		trsr.setComparator(3, new Comparador());
		this.setRowSorter(trsr);
		trsr.toggleSortOrder(0);
	}
	
	/**
	 * Setea el mapa <code>m</code> a <code>JTableNodos</code>
	 * 
	 * @param	m Mapa.	
	 * 
	 */
	public void setMapa(JPanelMapa m){this.mapa=m;}
	
	/**
	 * Se invoca cada vez que cambie el valor de la selección. Invoca a <code>valueChanged</code> de <code>JTable</code>
	 * 
	 * @param	e ListSelectionEvent.
	 * 
	 */
	public void valueChanged(ListSelectionEvent e)
	{
		super.valueChanged(e);
		int[] rs=this.getSelectedRows();
		ArrayList<DTNodo> ar=new ArrayList<DTNodo>();
		for(int i=0;i<rs.length;i++)
		{
			int s=rs[i];
			DTNodo n=((ModeloTablaNodos)this.getModel()).getNodoAt(this.convertRowIndexToModel(s));
			ar.add(n);
		}
	//	System.out.println("algunso resaltados "+ar.size());
		this.mapa.setResaltados(ar);
	}

	private class ModeloTablaNodos extends AbstractTableModel
	{
		private Collection<DTNodo> nodos;
		public ModeloTablaNodos(DTDepositoVRP dt)
		{
			nodos=dt.getNodos();

		}
		public ModeloTablaNodos()
		{
			nodos=new ArrayList<DTNodo>();

		}
		
		 public int getColumnCount() 
	     //Returns the number of columns in the model.
		 {
			 return 5;
		 }
		 public int getRowCount() 
		 {
			 return nodos.size();
		 }
		 public Object getValueAt(int rowIndex, int columnIndex)
		 {

			 if (columnIndex==0)
			 {
				 DTNodo dt=(DTNodo)this.nodos.toArray()[rowIndex];
				 return dt.getId();
				 //return s;
			 }
			 if (columnIndex==1)
			 {
				 DTNodo dt=(DTNodo)this.nodos.toArray()[rowIndex];
				 return dt.getX();
				 //return s;
			 }
			 if (columnIndex==2)
			 {
				 DTNodo dt=(DTNodo)this.nodos.toArray()[rowIndex];
				 return dt.getY();
				 //return s;
			 }
			 if (columnIndex==3)
			 {
				 DTNodo dt=(DTNodo)this.nodos.toArray()[rowIndex];
				 return dt.getDemanda();
				 //return s;
			 }
			 if (columnIndex==4)
			 {
				 DTNodo dt=(DTNodo)this.nodos.toArray()[rowIndex];
				 if(dt.getEsDesposito()) return "DEPÓSITO";
				 else return "CLIENTE";
				 //return s;
			 }
			
		 return new String("---");
	     //Returns the value for the cell at columnIndex and rowIndex. 
		 }
		 
		 public DTNodo getNodoAt(int i)
		 {
			 DTNodo dt=(DTNodo)this.nodos.toArray()[i];
			 return dt;
		 }
	}


	private class Comparador implements Comparator 
	{
		public void Comparador()
		{}
		public int compare(Object o1, Object o2)
		{
			try
			{
				return Integer.valueOf((int)o1).compareTo(Integer.valueOf((int)o2));
			}
			catch(Exception ex)
			{
				return 1;
			}
			
		}
		public boolean equals(Object obj)
		{
			return false;
		}
	}
}