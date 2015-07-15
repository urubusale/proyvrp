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
	    
	    // Condiciones de Parada
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
	    
	    JPanel panelCond = new JPanel(new GridLayout(0, 3));
	    
	    Border border = BorderFactory.createTitledBorder("Condiciones de Parada");
	    panelCond.setBorder(border);
	    panelCond.add(ningunoButton);
	    panelCond.add(new JLabel(""));
	    panelCond.add(new JLabel(""));
	    panelCond.add(tiempoButton);
	    panelCond.add(fieldTiempo);
	    panelCond.add(labelTiempo);
	    panelCond.add(iteracButton);
	    panelCond.add(fieldIterac);
	    panelCond.add(labelIterac);
	    panelCond.add(mejoraButton);
	    panelCond.add(fieldMejora);
	    panelCond.add(labelMejora);
	    
	    // Holgura en los Depósitos
	    int holguraDep = Config.getInstancia().getHolguraDep();
	    JPanel panelHolgura = new JPanel(new GridLayout(0, 3));
	    Border borderHolgura = BorderFactory.createTitledBorder("Holgura en los Depósitos");
	    panelHolgura.setBorder(borderHolgura);
	    panelHolgura.add(new JLabel("Holgura"));
	    final JSpinner jSpinerHolgura = new JSpinner(new SpinnerNumberModel(holguraDep,100,200,1));
	    panelHolgura.add(jSpinerHolgura);
	    panelHolgura.add(new JLabel(" %"));
	    
	    // Lambda-Opt
	    JPanel panelLambda = new JPanel(new GridLayout(0, 3));
	    Border borderLambda = BorderFactory.createTitledBorder("Lambda-Opt");
	    panelLambda.setBorder(borderLambda);
	    panelLambda.add(new JLabel("Cantidad"));
	    final JFormattedTextField fieldLambda = new JFormattedTextField (new Integer(Config.getInstancia().getLambdaOpt()));
	    panelLambda.add(fieldLambda);
	    panelLambda.add(new JLabel(" Segmentos"));
	    
	    // Botones
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
				
				Config.getInstancia().setHolguraDep((int) jSpinerHolgura.getValue());
				
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
	    p.add(panelCond,BorderLayout.NORTH);
	    p.add(panelHolgura);
	    p.add(panelLambda);
	    p.add(Jbotton,BorderLayout.SOUTH);
			    
		setVisible(true);
	}
}
