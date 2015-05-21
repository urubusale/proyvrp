package main;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logica.Fabrica;
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

	private JButton rutear;
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
	
	public JPanelEtiquetas()
	{
		super();
		this.capacidadt=new JTextField(20);
		this.cargat=new JTextField(20);
		this.costototalt=new JTextField(20);
		this.capacidadt.setEditable(false);
		this.cargat.setEditable(false);
		this.costototalt.setEditable(false);
		this.superior=new JPanel();
		this.superior.setLayout(new GridLayout(16,1));
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
		p9.setLayout(new GridLayout(1,2));
		this.asignar=new JButton("ASIGNAR");
		this.asignar.setEnabled(false);
		this.rutear=new JButton("RUTEAR");
		this.rutear.setEnabled(false);
		p9.add(asignar);
		p9.add(rutear);
		asignar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				asignaciones=Fabrica.getInstancia().getSistema().asignar(vrp);
				inferior.setAsignaciones(asignaciones);
				inferior2.setAsignaciones(asignaciones);

				mapa.setAsignaciones(asignaciones);
				asignado=true;
				rutear.setEnabled(true);
				panrutas.setRutas(new ArrayList<DTRuteo>());
			}
		});
		
		rutear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
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
				rutear.setEnabled(false);
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
				asignaciones=Fabrica.getInstancia().getSistema().asignarCap(vrp);
				inferior.setAsignaciones(asignaciones);
				inferior2.setAsignaciones(asignaciones);
				mapa.setAsignaciones(asignaciones);
				asignado=true;
				rutear.setEnabled(true);
				panrutas.setRutas(new ArrayList<DTRuteo>());
			}
		});
		
		JPanel p11=new JPanel();
		p11.setLayout(new GridLayout(1,2));
		
		this.asignarCap2=new JButton("ASIGNAR con CAPACIDADES FASE 1 Y 2");
		this.asignarCap2.setEnabled(false);
		p11.add(asignarCap2);
		asignarCap2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				asignaciones=Fabrica.getInstancia().getSistema().asignarCap2(vrp);
				inferior.setAsignaciones(asignaciones);
				inferior2.setAsignaciones(asignaciones);
				mapa.setAsignaciones(asignaciones);
				asignado=true;
				rutear.setEnabled(true);
				panrutas.setRutas(new ArrayList<DTRuteo>());
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

		this.rutear.setEnabled(false);
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
	
}
