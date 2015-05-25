package util;

import java.util.ArrayList;
import java.util.Collection;

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
				if (rutaNodo.get(rutaNodo.size()-1)==nodo_i) {
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
				if (rutaNodo.get(1)==nodo_i) {
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
		
		solucion.add(RutaUnida);
		return solucion;
	}

}
