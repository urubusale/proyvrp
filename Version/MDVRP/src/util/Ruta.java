package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import datatypes.DTNodo;
import datatypes.DTRuteo;

public class Ruta {
	static private Ruta instancia=null;
	static public Ruta getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Ruta();
			return instancia;
		}else return instancia;
	}
	
	public DTRuteo getRutaDondeClienteEsUltimo(Collection<DTRuteo> ListRutas, DTNodo nodo_i){
		DTRuteo rutaEncontrada = null;
		for (DTRuteo rutaActual : ListRutas) {
			ArrayList<DTNodo> rutaNodo = (ArrayList<DTNodo>) rutaActual.getRuta();
			if (!rutaNodo.isEmpty()) {
				if (rutaNodo.get(rutaNodo.size()-1).getId()==nodo_i.getId()) {
					rutaEncontrada = rutaActual;
				}
			}
		}
		return rutaEncontrada;
	}
	
	public DTRuteo getRutaDondeClienteEsPrimero(Collection<DTRuteo> ListRutas, DTNodo nodo_i){
		DTRuteo rutaEncontrada = null;
		for (DTRuteo rutaActual : ListRutas) {
			ArrayList<DTNodo> rutaNodo = (ArrayList<DTNodo>) rutaActual.getRuta();
			if (!rutaNodo.isEmpty()) {
				if (rutaNodo.get(0).getId()==nodo_i.getId()) {
					rutaEncontrada = rutaActual;
				}
			}
		}
		return rutaEncontrada;
	}

	public Collection<DTRuteo> mergeRutas(Collection<DTRuteo> solucion, DTRuteo ruta1, DTRuteo ruta2) {
		
		// Remuevo las rutas de la solucion
		solucion.remove(ruta1);
		solucion.remove(ruta2);
		
		DTNodo deposito = ruta1.getDeposito();
		DTRuteo RutaUnida = new DTRuteo(deposito);
		// Ruta 1
		for (DTNodo nodoRuta : ruta1.getRuta()) {
			RutaUnida.agregarCliente(nodoRuta);
		}
		// Ruta 2
		for (DTNodo nodoRuta : ruta2.getRuta()) {
			RutaUnida.agregarCliente(nodoRuta);
		}
		
		setCosto(RutaUnida);
		solucion.add(RutaUnida);
		return solucion;
	}
	
	public void setCosto(DTRuteo dt)
	{
		DTNodo dep=dt.getDeposito();
		Iterator<DTNodo> it=dt.getRuta().iterator();
		DTNodo previo=dep;
		int costo=0;
		while(it.hasNext())
		{
			DTNodo n=it.next();
			costo=costo+util.Distancia.getInstancia().getDistancia(previo, n);
			previo=n;
		}
		costo=costo+util.Distancia.getInstancia().getDistancia(previo, dep);
		dt.setCosto(costo);
		
	}
	
	public int getCarga(DTRuteo dt)
	{
		DTNodo dep=dt.getDeposito();
		Iterator<DTNodo> it=dt.getRuta().iterator();
		int carga=0;
		while(it.hasNext())
		{
			DTNodo n=it.next();
			carga=carga+n.getDemanda();
		}
		return carga;
	}
	public boolean sonMismaRuta(DTRuteo uno,DTRuteo dos)
	{
		if(uno.getDeposito().getId()==dos.getDeposito().getId())
		{
			if(uno.getRuta().size()==00&&dos.getRuta().size()==0)
			{
				return true;
				
			}else
			{
				if(uno.getRuta().size()>0&&dos.getRuta().size()>0)
				{
					if(uno.getRuta().iterator().next().getId()==dos.getRuta().iterator().next().getId())return true;
					else return false;
				}else return false;
			}
		}else return false;
	}
}
