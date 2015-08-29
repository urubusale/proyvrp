package main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.Tiempo;
import logica.Fabrica;
import logica.Sistema;
import datatypes.DTAsignacion;
import datatypes.DTDepositoVRP;
import datatypes.DTNodo;
import datatypes.DTRuteo;

public class JPanelEtiquetas extends JPanel
{
	private DTDepositoVRP vrp;
	private JLabel NAME;
	private JLabel COMMENT;
	private JLabel DIMENSION;
	private JLabel TYPE;
	private JLabel EDGE_WEIGHT_TYPE;
	private JLabel EDGE_WEIGHT_FORMAT;
	private JLabel DISPLAY_DATA_TYPE;
	private JLabel CAPACITY;
	
	private JTextField tNAME;
	private JTextField tCOMMENT;
	private JTextField tDIMENSION;
	private JTextField tTYPE;
	private JTextField tEDGE_WEIGHT_TYPE;
	private JTextField tEDGE_WEIGHT_FORMAT;
	private JTextField tDISPLAY_DATA_TYPE;
	private JTextField tCAPACITY;
	private int capacidad;
	private JButton asignar;
	private JButton asignarCap;
	private JButton asignarCap2;
	private JButton asignarCap22;


	private JButton rutear;
	private JButton rutearopt;
	private JButton rutearoptintra;

	private boolean asignado;
	private Collection<DTAsignacion> asignaciones;
	private Collection<DTRuteo> rutas;

	private JPanel superior;
	private JPanelAsignaciones inferior;
	private JPanelAsignaciones2 inferior2;
	
	private JPanelMapa mapa;
	private JTableAsignaciones tabla;
	private JTableAsignaciones2 tabla2;

	
	private JPanelRutas panrutas;
	
	private JTextField capacidadt;
	private JTextField cargat;
	private JTextField costototalt;
	private JLabel contornos;
	private JCheckBox chcont;
	
	private JLabel sombreados;
	private JCheckBox chsomb;
	private JFramePrincipalVRP padre;	
	public JPanelEtiquetas(JFramePrincipalVRP a)
	{
		super();
		padre=a;
		this.capacidadt=new JTextField(20);
		this.cargat=new JTextField(20);
		this.costototalt=new JTextField(20);
		this.capacidadt.setEditable(false);
		this.cargat.setEditable(false);
		this.costototalt.setEditable(false);
		this.superior=new JPanel();
		this.superior.setLayout(new GridLayout(18,1));
		this.setLayout(new GridLayout(2,1));
		this.add(superior);
		tabla=new JTableAsignaciones();
		this.inferior=new JPanelAsignaciones(tabla);
		
		tabla2=new JTableAsignaciones2();
		this.inferior2=new JPanelAsignaciones2(tabla2);
		
		
		JPanel auxw=new JPanel();
		auxw.setLayout(new GridLayout(2,1));
		auxw.add(inferior);
		auxw.add(inferior2);
		
		this.add(auxw);
		
		
		this.asignado=false;
		this.NAME=new JLabel("NAME:");	
		this.COMMENT=new JLabel("COMMENT:");
		this.DIMENSION=new JLabel("DIMENSION:");
		this.TYPE=new JLabel("TYPE:");
		this.EDGE_WEIGHT_TYPE=new JLabel("EDGE_WEIGHT_TYPE:");
		this.EDGE_WEIGHT_FORMAT=new JLabel("EDGE_WEIGHT_FORMAT:");
		this.DISPLAY_DATA_TYPE=new JLabel("DISPLAY_DATA_TYPE:");
		this.CAPACITY=new JLabel("CAPACITY:");
		
		
		this.tNAME=new JTextField(20);
		this.tNAME.setEditable(false);
		this.tCOMMENT=new JTextField(20);
		this.tCOMMENT.setEditable(false);
		this.tDIMENSION=new JTextField(20);
		this.tDIMENSION.setEditable(false);
		this.tTYPE=new JTextField(20);
		this.tTYPE.setEditable(false);
		this.tEDGE_WEIGHT_TYPE=new JTextField(20);
		this.tEDGE_WEIGHT_TYPE.setEditable(false);
		this.tEDGE_WEIGHT_FORMAT=new JTextField(20);
		this.tEDGE_WEIGHT_FORMAT.setEditable(false);
		this.tDISPLAY_DATA_TYPE=new JTextField(20);
		this.tDISPLAY_DATA_TYPE.setEditable(false);
		this.tCAPACITY=new JTextField(20);
		this.tCAPACITY.setEditable(false);
		capacidad=0;
		
		JPanel p1=new JPanel();
		p1.setLayout(new GridLayout(1,2));
		p1.add(NAME);
		p1.add(tNAME);
		JPanel p2=new JPanel();
		p2.setLayout(new GridLayout(1,2));
		p2.add(COMMENT);
		p2.add(tCOMMENT);
		JPanel p3=new JPanel();
		p3.setLayout(new GridLayout(1,2));
		p3.add(DIMENSION);
		p3.add(tDIMENSION);
		JPanel p4=new JPanel();
		p4.setLayout(new GridLayout(1,2));
		p4.add(TYPE);
		p4.add(tTYPE);
		JPanel p5=new JPanel();
		p5.setLayout(new GridLayout(1,2));
		p5.add(EDGE_WEIGHT_TYPE);
		p5.add(tEDGE_WEIGHT_TYPE);
		JPanel p6=new JPanel();
		p6.setLayout(new GridLayout(1,2));
		p6.add(EDGE_WEIGHT_FORMAT);
		p6.add(tEDGE_WEIGHT_FORMAT);
		JPanel p7=new JPanel();
		p7.setLayout(new GridLayout(1,2));
		p7.add(DISPLAY_DATA_TYPE);
		p7.add(tDISPLAY_DATA_TYPE);
		JPanel p8=new JPanel();
		p8.setLayout(new GridLayout(1,2));
		p8.add(CAPACITY);
		p8.add(tCAPACITY);
		
		JPanel p9=new JPanel();
		p9.setLayout(new GridLayout(1,4));
		this.rutear=new JButton("RUTEAR");
		this.rutear.setEnabled(false);
		this.rutearopt=new JButton("RUT-opt");
		this.rutearopt.setEnabled(false);
		this.rutearoptintra=new JButton("R-iopt");
		this.rutearoptintra.setEnabled(false);
		
		p9.add(rutear);
		p9.add(rutearopt);
		p9.add(rutearoptintra);

		
		rutear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				// Inicializo el tiempo del algoritmo
				Tiempo.getInstancia().inicioAlgoritmo();
				
				Iterator<DTAsignacion> it=asignaciones.iterator();
				rutas=new ArrayList<DTRuteo>();
				while(it.hasNext())
				{
					DTAsignacion d=it.next();
					rutas.addAll(Fabrica.getInstancia().getSistema().rutear(d,capacidad));
				}
				panrutas.setRutas(rutas);
//				inferior.setAsignaciones(asignaciones);
//				mapa.setAsignaciones(asignaciones);
			//	rutear.setEnabled(false);
				
				// Establezco el Fin del tiempo del algoritmo
				BigDecimal tiempoAlgoritmo = Tiempo.getInstancia().finAlgoritmo();
				JOptionPane optionPane = new JOptionPane("Tiempo del algoritmo: " + tiempoAlgoritmo + " segundos", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = optionPane.createDialog(null, "Tiempo del algoritmo");
				dialog.setLocation(545, 305);
				dialog.setVisible(true);
			}
		});
		rutearopt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				// Inicializo el tiempo del algoritmo
				Tiempo.getInstancia().inicioAlgoritmo();
				
				Iterator<DTAsignacion> it=asignaciones.iterator();
				rutas=new ArrayList<DTRuteo>();
				while(it.hasNext())
				{
					DTAsignacion d=it.next();
					rutas.addAll(Fabrica.getInstancia().getSistema().rutearopt(d,capacidad));
				}
				panrutas.setRutas(rutas);
//				inferior.setAsignaciones(asignaciones);
//				mapa.setAsignaciones(asignaciones);
			//	rutearopt.setEnabled(false);
				
				// Establezco el Fin del tiempo del algoritmo
				BigDecimal tiempoAlgoritmo = Tiempo.getInstancia().finAlgoritmo();
				JOptionPane optionPane = new JOptionPane("Tiempo del algoritmo: " + tiempoAlgoritmo + " segundos", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = optionPane.createDialog(null, "Tiempo del algoritmo");
				dialog.setLocation(545, 305);
				dialog.setVisible(true);
			}
		});

		rutearoptintra.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				// Inicializo el tiempo del algoritmo
				Tiempo.getInstancia().inicioAlgoritmo();
				
				Iterator<DTAsignacion> it=asignaciones.iterator();
				rutas=new ArrayList<DTRuteo>();
				while(it.hasNext())
				{
					DTAsignacion d=it.next();
					rutas.addAll(Fabrica.getInstancia().getSistema().post2intraroute(d,capacidad));
				}
				panrutas.setRutas(rutas);
//				inferior.setAsignaciones(asignaciones);
//				mapa.setAsignaciones(asignaciones);
			//	rutearopt.setEnabled(false);
				
				// Establezco el Fin del tiempo del algoritmo
				BigDecimal tiempoAlgoritmo = Tiempo.getInstancia().finAlgoritmo();
				JOptionPane optionPane = new JOptionPane("Tiempo del algoritmo: " + tiempoAlgoritmo + " segundos", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = optionPane.createDialog(null, "Tiempo del algoritmo");
				dialog.setLocation(545, 305);
				dialog.setVisible(true);
			}
		});


		JPanel p17=new JPanel();
		p17.setLayout(new GridLayout(1,4));
		this.asignar=new JButton("ASIGNAR SIN CAPACIDADES");
		this.asignar.setEnabled(false);
		
		p17.add(asignar);

		asignar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				// Inicializo el tiempo del algoritmo
				Tiempo.getInstancia().inicioAlgoritmo();
				 
				asignaciones=Fabrica.getInstancia().getSistema().asignar(vrp);
				inferior.setAsignaciones(asignaciones);
				inferior2.setAsignaciones(asignaciones);

				mapa.setAsignaciones(asignaciones);
				asignado=true;
				rutear.setEnabled(true);
				rutearopt.setEnabled(true);
				rutearoptintra.setEnabled(true);

				panrutas.setRutas(new ArrayList<DTRuteo>());
				
				// Establezco el Fin del tiempo del algoritmo
				BigDecimal tiempoAlgoritmo = Tiempo.getInstancia().finAlgoritmo();
				JOptionPane optionPane = new JOptionPane("Tiempo del algoritmo: " + tiempoAlgoritmo + " segundos", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = optionPane.createDialog(null, "Tiempo del algoritmo");
				dialog.setLocation(545, 305);
				dialog.setVisible(true);
			}
		});

		JPanel p10=new JPanel();
		p10.setLayout(new GridLayout(1,2));
		
		this.asignarCap=new JButton("ASIGNAR con CAPACIDADES FASE 1");
		this.asignarCap.setEnabled(false);
		p10.add(asignarCap);
		asignarCap.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				// Inicializo el tiempo
				Tiempo.getInstancia().inicioAlgoritmo();
				
				asignaciones=Fabrica.getInstancia().getSistema().asignarCap(vrp);
				inferior.setAsignaciones(asignaciones);
				inferior2.setAsignaciones(asignaciones);
				mapa.setAsignaciones(asignaciones);
				asignado=true;
				rutear.setEnabled(true);
				rutearopt.setEnabled(true);
				rutearoptintra.setEnabled(true);
				panrutas.setRutas(new ArrayList<DTRuteo>());
				
				// Establezco el Fin del tiempo del algoritmo
				BigDecimal tiempoAlgoritmo = Tiempo.getInstancia().finAlgoritmo();
				JOptionPane optionPane = new JOptionPane("Tiempo del algoritmo: " + tiempoAlgoritmo + " segundos", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = optionPane.createDialog(null, "Tiempo del algoritmo");
				dialog.setLocation(545, 305);
				dialog.setVisible(true);
			}
		});
		
		JPanel p11=new JPanel();
		p11.setLayout(new GridLayout(1,2));
		
		this.asignarCap2=new JButton("ASIGNAR con CAPACIDADES FASE 1y2-RAPIDO");
		this.asignarCap2.setEnabled(false);
		p11.add(asignarCap2);
		asignarCap2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				// Inicializo el tiempo
				Tiempo.getInstancia().inicioAlgoritmo();
				
				asignaciones=Fabrica.getInstancia().getSistema().asignarCap2(vrp);
				inferior.setAsignaciones(asignaciones);
				inferior2.setAsignaciones(asignaciones);
				mapa.setAsignaciones(asignaciones);
				asignado=true;
				rutear.setEnabled(true);
				rutearopt.setEnabled(true);
				rutearoptintra.setEnabled(true);
				panrutas.setRutas(new ArrayList<DTRuteo>());
	//			Cargador car=new Cargador(padre);
				//car.setVisible(true);
				
				// Establezco el Fin del tiempo del algoritmo
				BigDecimal tiempoAlgoritmo = Tiempo.getInstancia().finAlgoritmo();
				JOptionPane optionPane = new JOptionPane("Tiempo del algoritmo: " + tiempoAlgoritmo + " segundos", JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = optionPane.createDialog(null, "Tiempo del algoritmo");
				dialog.setLocation(545, 305);
				dialog.setVisible(true);
			}
		});
		
		JPanel p18=new JPanel();
		p18.setLayout(new GridLayout(1,2));
		
		this.asignarCap22=new JButton("ASIGNAR con CAPACIDADES FASE 1y2-LENTO");
		this.asignarCap22.setEnabled(false);
		p18.add(asignarCap22);
		asignarCap22.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				// Inicializo el tiempo del algoritmo
				Tiempo.getInstancia().inicioAlgoritmo();
				
			/*	asignaciones=Fabrica.getInstancia().getSistema().asignarCap2(vrp);
				inferior.setAsignaciones(asignaciones);
				inferior2.setAsignaciones(asignaciones);
				mapa.setAsignaciones(asignaciones);
				asignado=true;
				rutear.setEnabled(true);
				panrutas.setRutas(new ArrayList<DTRuteo>());*/
				Cargador car=new Cargador(padre);
				//car.setVisible(true);
			}
		});
		
		
		
		
		
		
		JPanel p12=new JPanel();
		p12.setLayout(new GridLayout(1,2));
		p12.add(new JLabel("CAPACIDAD DEPOSITOS"));
		p12.add(this.capacidadt);
		JPanel p13=new JPanel();
		p13.setLayout(new GridLayout(1,2));
		p13.add(new JLabel("CARGA TOTAL"));
		p13.add(this.cargat);
		JPanel p14=new JPanel();
		p14.setLayout(new GridLayout(1,2));
		p14.add(new JLabel("COSTO TOTAL"));
		p14.add(this.costototalt);
		JPanel p15=new JPanel();
		p15.setLayout(new GridLayout(1,2));
		this.contornos=new JLabel("CONTORNOS");
		this.chcont=new JCheckBox();
		p15.add(this.contornos);
		p15.add(this.chcont);
		this.chcont.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent t)
					{
						mapa.setContorno(chcont.isSelected());
						chsomb.setEnabled(chcont.isSelected());
						if(!chcont.isSelected()) {
							chsomb.setSelected(false);
							mapa.setSombreado(false);
						}
					}
				});
		JPanel p16=new JPanel();
		this.sombreados=new JLabel("SOMBREADOS");
		this.chsomb=new JCheckBox();
		this.chsomb.setEnabled(false);
		this.chsomb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent t)
			{
				mapa.setSombreado(chsomb.isSelected());
			}
		});

		p16.setLayout(new GridLayout(1,2));
		p16.add(this.sombreados);
		p16.add(this.chsomb);
	
		this.superior.add(p1);
		this.superior.add(p2);
		this.superior.add(p3);
		this.superior.add(p4);
		this.superior.add(p5);
		this.superior.add(p6);
		this.superior.add(p7);
		this.superior.add(p8);
		this.superior.add(p9);
		this.superior.add(p17);
		this.superior.add(p18);


		this.superior.add(p10);
		this.superior.add(p11);
		this.superior.add(p12);
		this.superior.add(p13);
		this.superior.add(p14);
		this.superior.add(p15);
		this.superior.add(p16);


		this.setPreferredSize(new Dimension(280,10));
	}
	
	public void setVRP(DTDepositoVRP dt)
	{
		this.tNAME.setText(dt.getNAME());
		this.tCOMMENT.setText(dt.getCOMMENT());
		this.tDIMENSION.setText(dt.getDIMENSION());
		this.tTYPE.setText(dt.getTYPE());
		this.tEDGE_WEIGHT_TYPE.setText(dt.getEDGE_WEIGHT_TYPE());
		this.tEDGE_WEIGHT_FORMAT.setText(dt.getEDGE_WEIGHT_FORMAT());
		this.tDISPLAY_DATA_TYPE.setText(dt.getDISPLAY_DATA_TYPE());
		this.tCAPACITY.setText(dt.getCAPACITY());
		this.vrp=dt;
		this.asignar.setEnabled(true);
		this.asignarCap.setEnabled(true);
		this.asignarCap2.setEnabled(true);
		this.asignarCap22.setEnabled(true);


		this.rutear.setEnabled(false);
		this.rutearopt.setEnabled(false);
		this.rutearoptintra.setEnabled(false);
		inferior.limpiar();
		inferior2.limpiar();

		capacidad=Integer.valueOf(dt.getCAPACITY());
		int captotal=0;
		int carga=0;
		Iterator<DTNodo> it=dt.getNodos().iterator();
		while(it.hasNext())
		{
			DTNodo n=it.next();
			if(n.getEsDesposito()) captotal=captotal+n.getDemanda();
			else carga=carga+n.getDemanda();
		}
		this.capacidadt.setText(String.valueOf(captotal));
		this.cargat.setText(String.valueOf(carga));
		this.costototalt.setText("-");
	}
	
	public void setMapa(JPanelMapa m)
	{
		this.mapa=m;
		tabla.setMapa(m);
		tabla2.setMapa(m);
	}
	
	public void setPanRutas(JPanelRutas m)
	{
		this.panrutas=m;
		//tabla.setMapa(m);
	}
	
	public void setCosto(int c)
	{
		this.costototalt.setText(String.valueOf(c));
	}
	
	//********************
	private class Cargador extends JDialog implements java.beans.PropertyChangeListener
	{
		private TaskWorker task;
		private boolean done;
		private JProgressBar progressBar;
		private JTextArea mensaje;
		public Cargador(JFramePrincipalVRP pad) 
		{
			super(pad,true);
			init();
		}
		public void init()
		{
			mensaje=new JTextArea();
			mensaje.setEditable(false);
			JPanel general=new JPanel(new BorderLayout());
			JPanel centro=new JPanel();
			general.add(centro,BorderLayout.SOUTH);
			JScrollPane centrob=new JScrollPane(mensaje);
			general.add(centrob,BorderLayout.CENTER);
		//	centrob.add(mensaje);
			Fabrica.getInstancia().getSistema().setInicioEstadoConsulta();
			setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			Toolkit kit = getToolkit();
		    Dimension screenSize = kit.getScreenSize();
		    int screenWidth = screenSize.width;
			setSize(screenWidth,150);

		    int screenHeight = screenSize.height;
		    Dimension windowSize = getSize();
		    int windowWidth = windowSize.width;
		    int windowHeight = windowSize.height;
		    int upperLeftX = (screenWidth - windowWidth)/2;
		    int upperLeftY = (screenHeight - windowHeight)/2;
		    setLocation(upperLeftX,  screenSize.height-200);
			progressBar = new JProgressBar(0, 200);
			
			centro.add(progressBar);
			this.setContentPane(general);
			progressBar.setValue(0);
			//progressBar.setStringPainted(true);
			//progressBar.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			progressBar.setIndeterminate(true);
			done = false;
			task = new TaskWorker(this);
			task.addPropertyChangeListener(this);
			task.execute();
			setVisible(true);	
		}
		public void termino()
		{			
			JPanel norte=new JPanel();
			JButton fin=new JButton("ACEPTAR");
			norte.add(fin);
			JPanel p=(JPanel)this.getContentPane();
			p.add(norte,BorderLayout.NORTH);
			
			// Establezco el Fin del tiempo del algoritmo
			BigDecimal tiempoAlgoritmo = Tiempo.getInstancia().finAlgoritmo();
			String mensajeTiempo = "*** Tiempo del algoritmo: " + tiempoAlgoritmo + " segundos ***";
			String mensaje = Sistema.getInstancia().getMensaje();
	        if(mensaje!=null)
	        {
	        	this.mensaje.setText(mensaje + mensajeTiempo);
	        }else this.mensaje.setText(mensajeTiempo);
			
			fin.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e)
			{
				inferior.setAsignaciones(asignaciones);
				inferior2.setAsignaciones(asignaciones);
				mapa.setAsignaciones(asignaciones);
				asignado=true;
				rutear.setEnabled(true);
				rutearopt.setEnabled(true);
				rutearoptintra.setEnabled(true);

				panrutas.setRutas(new ArrayList<DTRuteo>());
				
				System.out.println("terminó");

				dispose();}});
			progressBar.setVisible(false);
			
			
			this.doLayout();
		}
		 public void propertyChange(java.beans.PropertyChangeEvent evt) 
		 {
			    if (!done) 
			    {
			        int progress = task.getProgress();
			        progressBar.setValue(progress);
			        Collection<DTAsignacion> parciales=Sistema.getInstancia().getParciales();
			        if(parciales!=null)
			        {
			        	inferior.setAsignaciones(parciales);
						inferior2.setAsignaciones(parciales);
						mapa.setAsignaciones(parciales);
			        }
			        Collection<DTNodo> resaltados=Sistema.getInstancia().getResaltados();
			        if(resaltados!=null)
			        {
						mapa.setResaltados(resaltados);
			        }
			       String mensaje=Sistema.getInstancia().getMensaje();
			          if(mensaje!=null)
			        {
			        	this.mensaje.setText(mensaje);
			        }else this.mensaje.setText("");
			    //    taskOutput.append(String.format(		                "Completed %d%% of task.\n", progress));
			    }
		 }
			
		private class TaskWorker extends javax.swing.SwingWorker<Void, Void>
		{
			private Cargador diw;
			public TaskWorker(Cargador dia) 
			{
				super();
			 	this.diw=dia;
			 }
			 
			 @Override
		     public Void doInBackground() 
			 {
		         int progress = 0;
		         setProgress(0);
		         Hilo h=new Hilo();
				 h.start();
		         while (Fabrica.getInstancia().getSistema().getEstadoConsulta()==0) 
		         {
		             try 
		             {
		                 Thread.sleep(80);
		               //  System.out.println("sigo vivo");
		            	 
		             } 
		             catch (InterruptedException ignore) 
		             {}
		             
		             progress = logica.Fabrica.getInstancia().getSistema().getPorgresoDeAvance();
		        //   System.out.println(progress+"    pprogres");
		             setProgress(Math.min(progress, 100));
		         }
		         return null;
		     }
			 @Override
			 public void done()
			 {
				 	done=true;
				 	diw.termino();
			 }
		 }
		
		
		
		private class Hilo extends Thread 
		{
			public Hilo() 
		    {
		    }
		    public void run() 
		    {
		    	try
		    	{
		    		System.out.println("entro aca");
		    		asignaciones=Fabrica.getInstancia().getSistema().asignarCap22(vrp);
		    		Fabrica.getInstancia().getSistema().setFinalizadoOkEstadoConsulta();
		    	}catch(Exception ex){ex.printStackTrace();}
			}
		}
	}
	//********************


//*****************************
	
}
