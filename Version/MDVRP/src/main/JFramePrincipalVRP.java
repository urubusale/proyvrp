package main;
import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.border.Border;

import util.Config;
import util.Distancia;
import datatypes.DTDepositoVRP;
import datatypes.DTNodo;
import datatypes.DTRuteo;
import bdatos.ControladorArchivo;

import java.awt.event.WindowListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import logica.Fabrica;
import logica.Sistema;


public class JFramePrincipalVRP extends javax.swing.JFrame implements WindowListener
{
	{
		try 
		{
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
		private DTDepositoVRP vrpcargado;

	//	private ISistema is;
		private JPanel jpgeneral;
		private JToolBar tlb;
		private JPanelEtiquetas etiquetas;
		private JPanelNodos nodos;
		private JPanelMapaZoom2 mapa;
		private JPanelRutas rutas;
		public JFramePrincipalVRP() 
		{
			super("MDVRP");
			vrpcargado=new DTDepositoVRP();
			initGUI();
			initConfiguration();
		}
		
	

		public void initGUI() 
		{
			try 
			{
				setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE );
				
				this.addWindowListener(this);
				jpgeneral=new JPanel(new BorderLayout(3,3));
				etiquetas=new JPanelEtiquetas(this);
				jpgeneral.add(etiquetas, BorderLayout.WEST);
				
				nodos=new JPanelNodos(new JTableNodos());
				jpgeneral.add(nodos, BorderLayout.EAST);
				
				mapa=new JPanelMapaZoom2();
				rutas=new JPanelRutas(new JTableRutas());
				JPanel aux=new JPanel();
				aux.setLayout(new BorderLayout());
				aux.add(new JScrollPane(mapa), BorderLayout.CENTER);
				aux.add(rutas,BorderLayout.SOUTH);
				jpgeneral.add(aux,BorderLayout.CENTER);
				
				
				this.tlb=new JToolBar();
				tlb.setFloatable(false);
				

				JButton abrir=new JButton("ABRIR ARCHIVO VRP");
				tlb.add(abrir);
				abrir.setFont(new java.awt.Font("Goudi",java.awt.Font.PLAIN,10));
				abrir.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							JFileChooser chooser = new JFileChooser();
							int returnVal = chooser.showOpenDialog(getThis());
							if(returnVal == JFileChooser.APPROVE_OPTION) 
							{
								System.out.println("You chose to open this file: " +chooser.getSelectedFile().getName());
								System.out.println("You chose to open this file: " +chooser.getSelectedFile().getAbsolutePath());
								Collection<String> col=ControladorArchivo.getInstancia().leerArchivoDepositoVRP(chooser.getSelectedFile().getAbsolutePath());
							    boolean explicito=false;
							    Iterator<String> it=col.iterator();
							    while(it.hasNext()){
							    	String n=it.next();
							    	if(n.contains("EXPLICIT")){explicito=true;}
							    }
								if(explicito)
								{
									Cargador r=new Cargador(getThis(),col);
								}
								else
								{
									DTDepositoVRP dep=Fabrica.getInstancia().getSistema().ImportarDepositoVRP(col);
									etiquetas.setVRP(dep);
									mapa.setVRP(dep);
									nodos.setVRP(dep);
									nodos.setMapa(mapa);
									etiquetas.setMapa(mapa);
									rutas.setMapa(mapa);
									rutas.setVRP(dep);
									etiquetas.setPanRutas(rutas);
									rutas.setEtiquetas(etiquetas);
									vrpcargado=dep;
										/*
										 * 
					 etiquetas.setVRP(resultado);
				  	 mapa.setVRP(resultado);
					 nodos.setVRP(resultado);
					 nodos.setMapa(mapa);
					 etiquetas.setMapa(mapa);
					 rutas.setMapa(mapa);
					 rutas.setVRP(resultado);
					 etiquetas.setPanRutas(rutas);
					 rutas.setEtiquetas(etiquetas);
					 vrpcargado=resultado;
										 */
								}
								
								
							}
						}
						catch(Exception ex)
				    	{	
				    		JOptionPane.showMessageDialog(null,"Error,"+ex.getMessage());
				    		ex.printStackTrace();	
						}
					}
				});
				jpgeneral.add(tlb,BorderLayout.NORTH);
				tlb.addSeparator();

				JMenuBar principal=new JMenuBar();
				JMenu menu=new JMenu("MENU");
				menu.setFont(new java.awt.Font("Goudi",java.awt.Font.PLAIN,9));
				
				JMenuItem menuitem=new JMenuItem("CONFIGURACIÓN");
				menuitem.setFont(new java.awt.Font("Goudi",java.awt.Font.PLAIN,9));
				menuitem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						JDialogConfig jdc=new JDialogConfig();						
					}
				});
				
				menu.add(menuitem);
				principal.add(menu);
								
				this.getRootPane().setContentPane(jpgeneral);
				this.getRootPane().setJMenuBar(principal);
				this.setSize(800, 600);
				this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
			    Toolkit kit = this.getToolkit();
			    Dimension screenSize = kit.getScreenSize();
			    int screenWidth = screenSize.width;
			    int screenHeight = screenSize.height;
			    Dimension windowSize = this.getSize();
			    int windowWidth = windowSize.width;
			    int windowHeight = windowSize.height;
			    int upperLeftX = (screenWidth - windowWidth)/2;
			    int upperLeftY = (screenHeight - windowHeight)/2;
			    this.setLocation(upperLeftX, upperLeftY);    
				jpgeneral.setBackground(Color.WHITE);
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		
		public void windowActivated(WindowEvent e){} 

        public void windowClosed(WindowEvent e)
        {
        } 
 
        public void windowClosing(WindowEvent e)
        {
        	int i=JOptionPane.showConfirmDialog(this, new String("¿Desea salir?"),"SALIR",JOptionPane.YES_NO_OPTION);
        	if (i==0) 
        	{
        		this.dispose();
        		System.exit(0);
        	}
        } 
 
        public void windowDeactivated(WindowEvent e){} 
         
        public void windowDeiconified(WindowEvent e){} 
         
        public void windowIconified(WindowEvent e){} 
         
        public void windowOpened(WindowEvent e){} 
				
		public JFramePrincipalVRP getThis(){return this;}
		
		private class Cargador extends JDialog implements java.beans.PropertyChangeListener
		{
			private JFramePrincipalVRP padre;
			private TaskWorker task;
			private boolean done;
			private JProgressBar progressBar;
			private Collection<String> datos;
			private Sur sur;
			public Cargador(JFramePrincipalVRP pad,Collection<String> dat) 
			{
				super(pad,true);
				this.padre=pad;
				this.datos=dat;
				init();
			}
			public void init()
			{
				JPanel general=new JPanel(new BorderLayout());
				JPanel centro=new JPanel();
				sur=new Sur();
				general.add(sur,BorderLayout.CENTER);
				general.add(centro,BorderLayout.SOUTH);
				Fabrica.getInstancia().getSistema().setInicioEstadoConsulta();
				setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				setTitle("MAPEANDO MATRIZ DE DISTANCIAS");
				setSize(padre.getWidth()-20,600);
				Toolkit kit = getToolkit();
			    Dimension screenSize = kit.getScreenSize();
			    int screenWidth = screenSize.width;
			    int screenHeight = screenSize.height;
			    Dimension windowSize = getSize();
			    int windowWidth = windowSize.width;
			    int windowHeight = windowSize.height;
			    int upperLeftX = (screenWidth - windowWidth)/2;
			    int upperLeftY = (screenHeight - windowHeight)/2;
			    setLocation(upperLeftX, upperLeftY);
				progressBar = new JProgressBar(0, 100);
				
				centro.add(progressBar);
				this.setContentPane(general);
				progressBar.setValue(0);
				progressBar.setStringPainted(true);
				progressBar.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				done = false;
				task = new TaskWorker(this,this.datos);
				task.addPropertyChangeListener(this);
				task.execute();
				setVisible(true);	
			}
			 public void propertyChange(java.beans.PropertyChangeEvent evt) 
			 {
				    if (!done) 
				    {
				        int progress = task.getProgress();
				        progressBar.setValue(progress);
				        this.sur.setMapeados(Sistema.getInstancia().getMapeados());
				        this.sur.setRadio(Sistema.getInstancia().getRadio());
				        this.sur.repaint();
				    //    taskOutput.append(String.format(		                "Completed %d%% of task.\n", progress));
				    }
			 }
	
				
			private class TaskWorker extends javax.swing.SwingWorker<Void, Void>
			{
				private JDialog di;
				private Collection<String> datos;
				private DTDepositoVRP resultado;
				public TaskWorker(JDialog dia,Collection<String> dat) 
				{
					super();
				 	this.di=dia;
				 	this.datos=dat;
				 }
				 
				 @Override
			     public Void doInBackground() 
				 {
			         int progress = 0;
			         setProgress(0);
			         Hilo h=new Hilo(this,this.datos);
					 h.start();
			         while (Fabrica.getInstancia().getSistema().getEstadoConsulta()==0) 
			         {
			             try 
			             {
			                 Thread.sleep(10);
			               //  System.out.println("sigo vivo");
			            	 
			             } 
			             catch (InterruptedException ignore) 
			             {}
			             
			             progress = logica.Fabrica.getInstancia().getSistema().getPorgresoDeAvance();
			         //    System.out.println(progress+"    pprogres");
			             setProgress(Math.min(progress, 100));
			         }
			         return null;
			     }
				 @Override
				 public void done()
				 {
					 System.out.println("acá entro al done!!!");
					 etiquetas.setVRP(resultado);
				  	 mapa.setVRP(resultado);
					 nodos.setVRP(resultado);
					 nodos.setMapa(mapa);
					 etiquetas.setMapa(mapa);
					 rutas.setMapa(mapa);
					 rutas.setVRP(resultado);
					 etiquetas.setPanRutas(rutas);
					 rutas.setEtiquetas(etiquetas);
					 vrpcargado=resultado;
					 this.di.dispose();

				 }
				 
				 public void setRsultado(DTDepositoVRP v)
				 {
					 this.resultado=v;
				 }

			 }
			
			
			
			private class Hilo extends Thread 
			{
				private TaskWorker worker; 
				private Collection<String> dat;
				private int radio;
				public Hilo(TaskWorker dii,Collection<String> datos) 
			    {
					worker=dii;
					this.dat=datos;
			    }
			    public void run() 
			    {
			    	try
			    	{
			    
			    		DTDepositoVRP a=Fabrica.getInstancia().getSistema().ImportarDepositoVRP(dat);
			    		worker.setRsultado(a);
			    		Fabrica.getInstancia().getSistema().setFinalizadoOkEstadoConsulta();
			    	}catch(Exception ex){ex.printStackTrace();}
				}
			}
		}
		
		private class Sur extends JPanel
		{
			public Sur()
			{
				super ();
				borde=10;
				mapeados=new ArrayList<DTNodo>();
				Sistema.getInstancia().setMapeados(mapeados);
				this.repaint();
			}
			private int borde;

			private Collection<DTNodo> mapeados;
			private DTNodo radio;
			private int xminimo;
			private int yminimo;
			private int xmaximo;
			private int ymaximo;
			private int ancho;
			private int alto;
			private int ancho2;
			private int alto2;
			 public void paint (Graphics g) 
			 {
				 super.paint (g);
				 ancho=this.getWidth()-borde*2;
				 alto=this.getHeight()-borde*2;
					
				 	xminimo=Integer.MAX_VALUE;
				  	xmaximo=Integer.MIN_VALUE;
				 	yminimo=Integer.MAX_VALUE;
				 	ymaximo=Integer.MIN_VALUE;
					if(mapeados!=null)
						if(mapeados.size()>0)
						{
					Iterator<DTNodo> it=mapeados.iterator();
					while(it.hasNext())
					{
						DTNodo d=it.next();
						if(d.getX()<xminimo) xminimo=d.getX();
						if(d.getX()>xmaximo) xmaximo=d.getX();
						if(d.getY()<yminimo) yminimo=d.getY();
						if(d.getY()>ymaximo) ymaximo=d.getY();
					}
				 	ancho2=(xmaximo-xminimo);
				 	alto2=(ymaximo-yminimo);
				 	
					
					//System.out.println("ancho alto "+ancho+" "+alto );
					
					
					Iterator<DTNodo> it2=this.mapeados.iterator();
					while(it2.hasNext())
					{
						DTNodo d=it2.next();
						int radioo=Distancia.getInstancia().getDistancia(d, radio);
				//		System.out.println("radio "+radioo);
						if(d.getEsDesposito())	
						{
							g.setColor(Color.RED);
							g.drawString("D_"+d.getId(), transformarX(d.getX()),transformarY(d.getY()) );
							((Graphics2D)g).drawOval( transformarX(d.getX()-radioo), transformarY(d.getY()-radioo), transformarX(d.getX()+radioo)-transformarX(d.getX()-radioo),transformarY(d.getY()+radioo)-transformarY(d.getY()-radioo));
						}
						else 
						{
							g.setColor(Color.BLUE);
							g.drawString("C_"+d.getId(), transformarX(d.getX()),transformarY(d.getY()) );
							((Graphics2D)g).drawOval( transformarX(d.getX()-radioo), transformarY(d.getY()-radioo),transformarX(d.getX()+radioo)-transformarX(d.getX()-radioo),transformarY(d.getY()+radioo)-transformarY(d.getY()-radioo));

						}
					}
						}
				//	if(vrp.getNodos().size()>0)g.drawLine(transformarX(xminimo), transformarY(yminimo),transformarX(xmaximo),transformarY(ymaximo));
				}
				
				
			 public int transformarX(int x)
				{
					try{
					int posrelativa=x-xminimo;
					return ancho*posrelativa/ancho2+borde;
					}catch(Exception ex)
					{
						ex.printStackTrace();
						System.out.println("alto2"+alto2+"  borde "+borde);
						int posrelativa=x-xminimo;
						return ancho*posrelativa/(ancho2+1)+borde;	
					}
					

				}
				
				public int transformarY(int y)
				{
					try{
					int posrelativa=y-yminimo;
					return alto*posrelativa/alto2+borde;}
					catch(Exception ex){
						ex.printStackTrace();
						System.out.println("alto2"+alto2+"  borde "+borde);
						int posrelativa=y-yminimo;
						return alto*posrelativa/(alto2+1)+borde;
					}
					
				}
				
				public void setMapeados(Collection<DTNodo> m){this.mapeados=m;}
				public void setRadio(DTNodo i){this.radio=i;}
			
		}
		
		public void initConfiguration() 
		{
			Config.getInstancia();			
		}
}
