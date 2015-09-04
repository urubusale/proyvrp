package main;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import datatypes.DTNodo;
import datatypes.DTRuteo;

public class JTableRutas extends JTable 
{
	private JPanelMapa mapa;
	private Collection<DTRuteo> rutas;

	/**
	 * Constructor por defecto. Invoca al constructor de <code>JTable</code>
	 * 
	 */
	public JTableRutas()
	{
		super();
		this.setAutoCreateColumnsFromModel(true);
		this.setAutoCreateRowSorter(false);
		TableModel dataModel=new ModeloTablaRutas();
		this.setModel(dataModel);
		this.getColumnModel().getColumn(0).setPreferredWidth(20);
		this.getColumnModel().getColumn(0).setHeaderValue(new String("DEPOSITO"));
		this.getColumnModel().getColumn(1).setPreferredWidth(500);

		this.getColumnModel().getColumn(1).setHeaderValue(new String("RUTA"));

		this.getColumnModel().getColumn(2).setHeaderValue(new String("COSTO"));
		this.getColumnModel().getColumn(2).setPreferredWidth(20);
		
		this.getColumnModel().getColumn(3).setHeaderValue(new String("CARGA"));
		this.getColumnModel().getColumn(3).setPreferredWidth(20);


		this.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.getSelectionModel().addListSelectionListener(this);
		this.getTableHeader().setReorderingAllowed(false);
	}
	
	/**
	 * Setea la colección de rutas <code>col</code> a <code>JTableRutas</code>. Invoca a <code>setRutas</code> de <code>JPanelMapa</code>.
	 * 
	 * @param	col Colección de Rutas.	
	 * 
	 */
	public void setRutas(Collection<DTRuteo> col)
	{
		TableModel dataModel=new ModeloTablaRutas(col);
		this.setModel(dataModel);
		this.rutas=col;
		this.getColumnModel().getColumn(0).setPreferredWidth(20);
		this.getColumnModel().getColumn(0).setHeaderValue(new String("DEPOSITO"));
		this.getColumnModel().getColumn(1).setPreferredWidth(500);

		this.getColumnModel().getColumn(1).setHeaderValue(new String("RUTA"));

		this.getColumnModel().getColumn(2).setHeaderValue(new String("COSTO"));
		this.getColumnModel().getColumn(2).setPreferredWidth(20);
		this.getColumnModel().getColumn(3).setHeaderValue(new String("CARGA"));
		this.getColumnModel().getColumn(3).setPreferredWidth(20);

		javax.swing.table.TableRowSorter trsr=new javax.swing.table.TableRowSorter< ModeloTablaRutas>(( ModeloTablaRutas)this.getModel());
		trsr.setComparator(0, new Comparador());
		trsr.setComparator(2, new Comparador());
		trsr.setComparator(3, new Comparador());

		this.setRowSorter(trsr);
		trsr.toggleSortOrder(0);

		mapa.setRutas(col);
			
	}
	
	/**
	 * Setea el mapa <code>m</code> a <code>JTableRutas</code>
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
			DTLineaTabla n=((ModeloTablaRutas)this.getModel()).getNodoAt(this.convertRowIndexToModel(s));
			ar.add(n);
		}
		ArrayList<DTRuteo> sels=new ArrayList<DTRuteo>();
		Iterator<DTLineaTabla> it=ar.iterator();
		while(it.hasNext())
		{
			DTLineaTabla dt=it.next();
			Iterator<DTRuteo> itr=rutas.iterator();
			while(itr.hasNext())
			{
				DTRuteo o=itr.next();
				DTNodo dep=o.getDeposito();
				if(dep.getId()==dt.getDeposito())
				{
					Collection<DTNodo> nodos=o.getRuta();
					if(nodos.size()==0)
					{}
					else
					{
						int primero=nodos.iterator().next().getId();
						StringTokenizer stk=new StringTokenizer(dt.ruta);
						String primer=stk.nextToken();
						if (Integer.valueOf(primer).compareTo(Integer.valueOf(primero))==0)
						{
							sels.add(o);
						}
					}
				}
			}
		}
		
		this.mapa.setRutasResaltadas(sels);
	}

	private class ModeloTablaRutas extends AbstractTableModel
	{
		private Collection<DTRuteo> rutas;
		private Collection<DTLineaTabla> lineas;
		public ModeloTablaRutas(Collection<DTRuteo> col)
		{
			rutas=col;
			lineas=new ArrayList<DTLineaTabla>();
			Iterator<DTRuteo> it=col.iterator();
			while(it.hasNext())
			{
				DTRuteo dt=it.next();
				Iterator<DTNodo> no=dt.getRuta().iterator();
				String ruta="";
				int carga=0;
				while(no.hasNext())
				{
					DTNodo nodo=no.next();
					ruta=ruta+" "+nodo.getId();
					carga=carga+nodo.getDemanda();
				}
				lineas.add(new DTLineaTabla(dt.getDeposito().getId(),ruta,dt.getCosto(),carga));
			}

		}
		public ModeloTablaRutas()
		{
			rutas=new ArrayList<DTRuteo>();
			lineas=new ArrayList<DTLineaTabla>();
		}
		
		 public int getColumnCount() 
	     //Returns the number of columns in the model.
		 {
			 return 4;
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
				 return dt.getRuta();
				 //return s;
			 }

			 if (columnIndex==2)
			 {
				 DTLineaTabla dt=(DTLineaTabla)this.lineas.toArray()[rowIndex];
				 return dt.getCosto();
				 //return s;
			 }
			 if (columnIndex==3)
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
		private String ruta;
		private int costo;
		private int carga;

		public DTLineaTabla(int d,String c,int co,int car)
		{
			deposito=d;
			ruta=c;
			this.costo=co;
			this.carga=car;
		}
		public int getDeposito(){return deposito;}
		public String getRuta(){return ruta;}
		public int getCosto(){return this.costo;}
		public int getCarga(){return this.carga;}
	}
}