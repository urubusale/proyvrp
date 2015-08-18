package main;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import datatypes.DTAsignacion;
import datatypes.DTNodo;

public class JTableAsignaciones extends JTable 
{
	private JPanelMapa mapa;
	public JTableAsignaciones()
	{
		super();
		this.setAutoCreateColumnsFromModel(true);
		this.setAutoCreateRowSorter(false);
		TableModel dataModel=new ModeloTablaAsignaciones();
		this.setModel(dataModel);
		this.getColumnModel().getColumn(0).setHeaderValue(new String("DEPOSITO"));
		this.getColumnModel().getColumn(1).setHeaderValue(new String("CLIENTE"));

		this.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	//	this.getSelectionModel().addListSelectionListener(this);
		this.getTableHeader().setReorderingAllowed(false);
		
	
	}
	
	public void setAsignaciones(Collection<DTAsignacion> col)
	{
		TableModel dataModel=new ModeloTablaAsignaciones(col);
		this.setModel(dataModel);
		this.getColumnModel().getColumn(0).setPreferredWidth(80);
		this.getColumnModel().getColumn(0).setHeaderValue(new String("DEPOSITO"));
		this.getColumnModel().getColumn(1).setHeaderValue(new String("CLIENTE"));
		this.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		this.getSelectionModel().addListSelectionListener(this);
		
		javax.swing.table.TableRowSorter trsr=new javax.swing.table.TableRowSorter< ModeloTablaAsignaciones>(( ModeloTablaAsignaciones)this.getModel());
		trsr.setComparator(0, new Comparador());
		trsr.setComparator(1, new Comparador());
		this.setRowSorter(trsr);
		trsr.toggleSortOrder(0);

			
	}
	
	public void setMapa(JPanelMapa m){this.mapa=m;}
	public void valueChanged(ListSelectionEvent e)
	{
		super.valueChanged(e);
	/*	int[] rs=this.getSelectedRows();
		ArrayList<DTLineaTabla> ar=new ArrayList<DTLineaTabla>();
		for(int i=0;i<rs.length;i++)
		{
			int s=rs[i];
			DTLineaTabla n=((ModeloTablaAsignaciones)this.getModel()).getNodoAt(this.convertRowIndexToModel(s));
			ar.add(n);
		}
		System.out.println("algunso resaltados "+ar.size());
		Hashtable<Integer,Integer> depoasig=new Hashtable<Integer,Integer>();
		Iterator<DTLineaTabla> itnnn=ar.iterator();
		while(itnnn.hasNext())
		{
			DTLineaTabla dtn=itnnn.next();
			depoasig.put(Integer.valueOf(dtn.deposito), Integer.valueOf(dtn.deposito));
		}
		this.mapa.setResaltadosAsignaciones(depoasig.values());
	*/}

	private class ModeloTablaAsignaciones extends AbstractTableModel
	{
		private Collection<DTAsignacion> asignaciones;
		private Collection<DTLineaTabla> lineas;
		public ModeloTablaAsignaciones(Collection<DTAsignacion> col)
		{
			asignaciones=col;
			lineas=new ArrayList<DTLineaTabla>();
			Iterator<DTAsignacion> it=asignaciones.iterator();
			while(it.hasNext())
			{
				DTAsignacion as=it.next();
				int dep=as.getDeposito().getId();
				Iterator<DTNodo> itn=as.getClientes().iterator();
				while(itn.hasNext())
				{
					DTNodo no=itn.next();
					DTLineaTabla nuevo=new DTLineaTabla(dep,no.getId());
					lineas.add(nuevo);
				}
			}
			//System.out.println(lineas.size()+" tamlines");

		}
		public ModeloTablaAsignaciones()
		{
			asignaciones=new ArrayList<DTAsignacion>();
			lineas=new ArrayList<DTLineaTabla>();

		}
		
		 public int getColumnCount() 
	     //Returns the number of columns in the model.
		 {
			 return 2;
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
				 return dt.getCliente();
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
		private int cliente;
		public DTLineaTabla(int d,int c)
		{
			deposito=d;
			cliente=c;
		}
		public int getDeposito(){return deposito;}
		public int getCliente(){return cliente;}
	}
}