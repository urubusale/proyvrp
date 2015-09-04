package main;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import datatypes.DTAsignacion;
import datatypes.DTNodo;

public class JTableAsignaciones2 extends JTable 
{
	private JPanelMapa mapa;
	
	/**
	 * Constructor por defecto. Invoca al constructor de <code>JTable</code>
	 * 
	 */
	public JTableAsignaciones2()
	{
		super();
		this.setAutoCreateColumnsFromModel(true);
		this.setAutoCreateRowSorter(false);
		TableModel dataModel=new ModeloTablaAsignaciones2();
		this.setModel(dataModel);
		this.getColumnModel().getColumn(0).setHeaderValue(new String("DEPOSITO"));
		this.getColumnModel().getColumn(1).setHeaderValue(new String("CAPACIDAD"));
		this.getColumnModel().getColumn(2).setHeaderValue(new String("CARGA"));
		this.getColumnModel().getColumn(2).setCellRenderer(new TableCellRender());

		this.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.getSelectionModel().addListSelectionListener(this);
		this.getTableHeader().setReorderingAllowed(false);
		
	
	}
	
	/**
	 * Setea la colección de Asignaciones <code>col</code> a <code>JTableAsignaciones2</code>
	 * 
	 * @param	col Colección de Rutas.	
	 * 
	 */
	public void setAsignaciones(Collection<DTAsignacion> col)
	{
		TableModel dataModel=new ModeloTablaAsignaciones2(col);
		this.setModel(dataModel);
		this.getColumnModel().getColumn(0).setHeaderValue(new String("DEPOSITO"));
		this.getColumnModel().getColumn(1).setHeaderValue(new String("CAPACIDAD"));
		this.getColumnModel().getColumn(2).setHeaderValue(new String("CARGA"));
		this.getColumnModel().getColumn(2).setCellRenderer(new TableCellRender());

		this.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.getSelectionModel().addListSelectionListener(this);
		
		javax.swing.table.TableRowSorter trsr=new javax.swing.table.TableRowSorter< ModeloTablaAsignaciones2>(( ModeloTablaAsignaciones2)this.getModel());
		trsr.setComparator(0, new Comparador());
		trsr.setComparator(1, new Comparador());
		trsr.setComparator(2, new Comparador());

		this.setRowSorter(trsr);
		trsr.toggleSortOrder(0);

			
	}
	
	/**
	 * Setea el mapa <code>m</code> a <code>JTableAsignaciones2</code>
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
		ArrayList<DTLineaTabla> ar=new ArrayList<DTLineaTabla>();
		for(int i=0;i<rs.length;i++)
		{
			int s=rs[i];
			DTLineaTabla n=((ModeloTablaAsignaciones2)this.getModel()).getNodoAt(this.convertRowIndexToModel(s));
			ar.add(n);
		}
		//System.out.println("algunso resaltados "+ar.size());
		Hashtable<Integer,Integer> depoasig=new Hashtable<Integer,Integer>();
		Iterator<DTLineaTabla> itnnn=ar.iterator();
		while(itnnn.hasNext())
		{
			DTLineaTabla dtn=itnnn.next();
			depoasig.put(Integer.valueOf(dtn.deposito), Integer.valueOf(dtn.deposito));
		}
		this.mapa.setResaltadosAsignaciones(depoasig.values());
	}

	private class ModeloTablaAsignaciones2 extends AbstractTableModel
	{
		private Collection<DTAsignacion> asignaciones;
		private Collection<DTLineaTabla> lineas;
		public ModeloTablaAsignaciones2(Collection<DTAsignacion> col)
		{
			asignaciones=col;
			lineas=new ArrayList<DTLineaTabla>();
			Iterator<DTAsignacion> it=asignaciones.iterator();
			while(it.hasNext())
			{
				DTAsignacion as=it.next();
				int dep=as.getDeposito().getId();
				int carga=0;
				Iterator<DTNodo> itn=as.getClientes().iterator();
				while(itn.hasNext())
				{
					DTNodo no=itn.next();
					carga=carga+no.getDemanda();
				}
				DTLineaTabla nuevo=new DTLineaTabla(dep,as.getDeposito().getDemanda(),carga);
				lineas.add(nuevo);

			}
		//	System.out.println(lineas.size()+" tamlines");

		}
		public ModeloTablaAsignaciones2()
		{
			asignaciones=new ArrayList<DTAsignacion>();
			lineas=new ArrayList<DTLineaTabla>();

		}
		
		 public int getColumnCount() 
	     //Returns the number of columns in the model.
		 {
			 return 3;
		 }
		 public int getRowCount() 
		 {
			 return lineas.size();
		 }
		 public Object getValueAt(int rowIndex, int columnIndex)
		 {

			 if (columnIndex==0)
			 {
				 DTLineaTabla dt=(DTLineaTabla)this.lineas.toArray()[rowIndex];
				 return dt.getDeposito();
				 //return s;
			 }
			 if (columnIndex==1)
			 {
				 DTLineaTabla dt=(DTLineaTabla)this.lineas.toArray()[rowIndex];
				 return dt.getCapacidad();
				 //return s;
			 }

			 if (columnIndex==2)
			 {
				 DTLineaTabla dt=(DTLineaTabla)this.lineas.toArray()[rowIndex];
				 return dt.getCarga();
				 //return s;
			 }

		 return new String("---");
	     //Returns the value for the cell at columnIndex and rowIndex. 
		 }
		 
		 public DTLineaTabla getNodoAt(int i)
		 {
			 DTLineaTabla dt=(DTLineaTabla)this.lineas.toArray()[i];
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


	private class DTLineaTabla
	{
		private int deposito;
		private int capacidad;
		private int carga;
		
		public DTLineaTabla(int d,int c,int car)
		{
			deposito=d;
			capacidad=c;
			carga=car;
		}
		public int getDeposito(){return deposito;}
		public int getCapacidad(){return capacidad;}
		public int getCarga(){return carga;}
	}
	
	private class TableCellRender extends DefaultTableCellRenderer 
	{
		public TableCellRender()
		{
			super();
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		 {
			 Component t=super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
			 int cap=(Integer)table.getValueAt(row, 1);
			 int carga=(Integer)table.getValueAt(row, 2);
			 if(carga>cap)
			 {
				JLabel l=new JLabel(((Integer)value).toString());
				l.setOpaque(true);
	     		l.setBackground(Color.getHSBColor(100,100,100));
	    		if(isSelected) l.setBackground(Color.BLUE); 
				return l;
			 }else return t;//else System.out.println("no es ponderado row column "+row+" "+column);
			 
			 //return t;
		 }
		 
	}
}