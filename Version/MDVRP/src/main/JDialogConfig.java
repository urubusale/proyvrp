package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import util.Config;

public class JDialogConfig extends JDialog
{
	public JDialogConfig() 
	{
		super();
		init();
	}
	public void init()
	{
		setSize(300,220);				
	    setLocation(533,  200);
	    setTitle("CONFIGURACIÓN");
	    setResizable(false);
	    
	    final JRadioButton ningunoButton = new JRadioButton("Ninguno");
		ningunoButton.setActionCommand("Ninguno");
		ningunoButton.setSelected(Config.getInstancia().isNinguno());

	    final JRadioButton tiempoButton = new JRadioButton("Tiempo");
	    tiempoButton.setActionCommand("Tiempo");
	    tiempoButton.setSelected(Config.getInstancia().isTiempo());
	    
	    final JRadioButton iteracButton = new JRadioButton("Iteracciones");
	    iteracButton.setActionCommand("Iteracciones");
	    iteracButton.setSelected(Config.getInstancia().isIteracciones());

	    final JRadioButton mejoraButton = new JRadioButton("Mejora");
	    mejoraButton.setActionCommand("Mejora");
	    mejoraButton.setSelected(Config.getInstancia().isMejora());
	    
	    //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(ningunoButton);
	    group.add(tiempoButton);
	    group.add(iteracButton);
	    group.add(mejoraButton);
	    
		final JFormattedTextField fieldTiempo = new JFormattedTextField (new Integer(Config.getInstancia().getTiempo()));
		final JFormattedTextField fieldIterac = new JFormattedTextField (new Integer(Config.getInstancia().getIteracciones()));
		final JFormattedTextField fieldMejora = new JFormattedTextField (new Double(Config.getInstancia().getMejora()));
								
		final JLabel labelTiempo = new JLabel(" segundos");
		final JLabel labelIterac = new JLabel(" Cantidad");
		final JLabel labelMejora = new JLabel(" %");

		if (ningunoButton.isSelected()) {
			fieldTiempo.setEnabled(false);
			labelTiempo.setEnabled(false);
			fieldIterac.setEnabled(false);
			labelIterac.setEnabled(false);
			fieldMejora.setEnabled(false);
			labelMejora.setEnabled(false);
			ningunoButton.setFocusable(true);
		}
		if (tiempoButton.isSelected()) {
			fieldIterac.setEnabled(false);
			labelIterac.setEnabled(false);
			fieldMejora.setEnabled(false);
			labelMejora.setEnabled(false);
			tiempoButton.setFocusable(true);
		}
		if (iteracButton.isSelected()) {
			fieldTiempo.setEnabled(false);
			labelTiempo.setEnabled(false);
			fieldMejora.setEnabled(false);
			labelMejora.setEnabled(false);
			iteracButton.setFocusable(true);
		}
		if (mejoraButton.isSelected()) {
			fieldTiempo.setEnabled(false);
			labelTiempo.setEnabled(false);
			fieldIterac.setEnabled(false);
			labelIterac.setEnabled(false);
			mejoraButton.setFocusable(true);
		}
		
	    ningunoButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {								
				fieldTiempo.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				labelTiempo.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);								
				fieldIterac.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				labelIterac.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				fieldMejora.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				labelMejora.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				fieldTiempo.setValue(0);
				fieldIterac.setValue(0);
				fieldMejora.setValue(0.0);
	        }
	     });
	    
	    tiempoButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				fieldIterac.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				labelIterac.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				fieldMejora.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				labelMejora.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				fieldIterac.setValue(0);
				fieldMejora.setValue(0.0);
	        }
	     });
	    
	    iteracButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				fieldTiempo.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				labelTiempo.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				fieldMejora.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				labelMejora.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				fieldTiempo.setValue(0);
				fieldMejora.setValue(0.0);
	        }
	     });
	    
	    mejoraButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				fieldTiempo.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				labelTiempo.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);								
				fieldIterac.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				labelIterac.setEnabled(arg0.getStateChange() == ItemEvent.DESELECTED);
				fieldTiempo.setValue(0);
				fieldIterac.setValue(0);
	        }
	     });
	    
	    JPanel panel = new JPanel(new GridLayout(0, 3));
	    
	    Border border = BorderFactory.createTitledBorder("Condiciones de Parada");
	    panel.setBorder(border);
	    panel.add(ningunoButton);
	    panel.add(new JLabel(""));
	    panel.add(new JLabel(""));
	    panel.add(tiempoButton);
	    panel.add(fieldTiempo);
	    panel.add(labelTiempo);
	    panel.add(iteracButton);
	    panel.add(fieldIterac);
	    panel.add(labelIterac);
	    panel.add(mejoraButton);
	    panel.add(fieldMejora);
	    panel.add(labelMejora);
	    
	    int porcentajeDep = Config.getInstancia().getPorcentajeDep();
	    
	    JPanel panel2 = new JPanel(new GridLayout(0, 3));
	    
	    Border border2 = BorderFactory.createTitledBorder("Porcentaje capacidad en Depósitos");
	    panel2.setBorder(border2);
	    panel2.add(new JLabel("Capacidad"));
	    final JSpinner jSpinerCap = new JSpinner(new SpinnerNumberModel(porcentajeDep,100,120,1));
	    panel2.add(jSpinerCap);
	    panel2.add(new JLabel(" %"));
	    
	    JPanel Jbotton=new JPanel();
		JButton aceptar=new JButton("ACEPTAR");
		Jbotton.add(aceptar);
		JButton cancelar=new JButton("CANCELAR");
		Jbotton.add(cancelar);
				
		aceptar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{				
				Config.getInstancia().setTiempo(tiempoButton.isSelected());
				if (tiempoButton.isSelected())
					Config.getInstancia().setTiempo((int) fieldTiempo.getValue());
				
				Config.getInstancia().setIteracciones(iteracButton.isSelected());
				if (iteracButton.isSelected())
					Config.getInstancia().setIteracciones((int) fieldIterac.getValue());
				
				Config.getInstancia().setMejora(mejoraButton.isSelected());
				if (mejoraButton.isSelected())
					Config.getInstancia().setMejora((double) fieldMejora.getValue());
				
				Config.getInstancia().setPorcentajeDep((int) jSpinerCap.getValue());
				
				dispose();
			}
		});
		
		cancelar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
	    JPanel p=(JPanel)this.getContentPane();
	    p.add(panel,BorderLayout.NORTH);
	    p.add(panel2);
	    p.add(Jbotton,BorderLayout.SOUTH);
			    
		setVisible(true);
	}
}
