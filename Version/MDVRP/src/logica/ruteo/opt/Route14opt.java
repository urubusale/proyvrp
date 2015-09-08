package logica.ruteo.opt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import datatypes.DTNodo;
import datatypes.DTRuteo;

public class Route14opt 
{
	static private Route14opt instancia=null;
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresión con una lista de argumentos vacía. 
	 * La clase se inicializa si ya no se ha inicializado. 
	 * 
	 * @return      Una nueva instancia de la clase
	 */
	static public Route14opt getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Route14opt();
			return instancia;
		}else return instancia;
	}
	
	/** 
	 * Constructor por defecto.
	 * 
	 */
	private Route14opt()
	{
	}
	
	/**
	 * Mejora parte de una ruta, prueba todas las combinaciones posibles que surgen de cambiar <code>opt</code> aristas del grafo a la vez.
	 * <p> 		
	 * Viendo a la ruta como un grafo con vértices y aristas donde el vértice representa al cliente y la arista el camino entre dos clientes. 
	 * <p> 
	 * De esta forma se busca el óptimo local en el espacio de soluciones
	 * 
	 * @param	dt <code>DTRuteo</code> con el deposito <code>d</code> y ruta de clientes.
	 * @param	opt numero de caminos entre dos clientes a cambiar.
	 * @return      <code>DTRuteo</code> con el deposito <code>d</code> y nueva ruta de clientes optimizada.
	 * 
	 */
	public DTRuteo opt4(DTRuteo dt,int opt)
	{
		
		util.Ruta.getInstancia().setCosto(dt);
		int costooptimo=dt.getCosto();
		ArrayList<DTNodo> optimo=new ArrayList<DTNodo>(dt.getRuta());
		ArrayList<DTNodo> noptimo=null;
		ArrayList<DTNodo> noptimo2=null;
		ArrayList<ArrayList<DTNodo>> aa=combinacionesTomadas(dt.getRuta(),opt);
		Iterator<ArrayList<DTNodo>> it=aa.iterator();
		while(it.hasNext())
		{
			ArrayList<DTNodo> nn=it.next();
			noptimo=this.permutarBuscaOptimo(optimo, nn, costooptimo, dt.getDeposito());
			if(noptimo!=null)
			{
				DTRuteo dtn=new DTRuteo(dt.getDeposito(),noptimo);
				util.Ruta.getInstancia().setCosto(dtn);
				if(dtn.getCosto()<costooptimo)
				{
					costooptimo=dtn.getCosto();
					noptimo2=noptimo;
				}
			}
		}
		if(noptimo2==null) noptimo2=new ArrayList<DTNodo>(dt.getRuta());
		DTRuteo retu=new DTRuteo(dt.getDeposito(),noptimo2);
		util.Ruta.getInstancia().setCosto(retu);
		return retu;
	}
	
	/**
	 * Retorna todas las combinaciones posibles tomando <code>ca</code> clientes de la colección de clientes <code>c</code>.
	 * 
	 * @param	c Colección de clientes que pertenecen a la ruta.
	 * @param	ca numero de clientes a tomar para realizar todas las combinaciones posibles.
	 * @return      Colección de colección clientes con todas las combinaciones posibles.
	 * 
	 */	
	private ArrayList<ArrayList<DTNodo>> combinacionesTomadas(Collection<DTNodo> c,int ca)
	{
		if(c.size()==ca)
		{
			ArrayList<ArrayList<DTNodo>> req=new ArrayList<ArrayList<DTNodo>>();
			ArrayList<DTNodo> rq=new ArrayList<DTNodo>(c);
			req.add(rq);
			return req;
		}
		if(ca==0) return new ArrayList<ArrayList<DTNodo>>();
		if(ca==1)
		{
			ArrayList<ArrayList<DTNodo>> ar=new ArrayList<ArrayList<DTNodo>>();
			Iterator<DTNodo> it=c.iterator();
			while(it.hasNext())
			{
				DTNodo n=it.next();
				ArrayList<DTNodo> ar1=new ArrayList<DTNodo>();
				ar1.add(n);
				ar.add(ar1);
			}
			return ar;

		}
		else
		{
			
			Iterator<DTNodo> it1=c.iterator();
			ArrayList<ArrayList<DTNodo>> result=new ArrayList<ArrayList<DTNodo>>();
			while(it1.hasNext())
			{
				DTNodo primero=it1.next();
				Iterator<DTNodo> it2=c.iterator();
				ArrayList<DTNodo> resto=new ArrayList<DTNodo>();
				boolean encontro=false;
				while(it2.hasNext())
				{
					DTNodo n=it2.next();
					if(encontro)
					{
						resto.add(n);
					}else
					if(n.getId()==primero.getId())
					{
						encontro=true;
					}
				}
				if(resto.size()>=(ca-1))
				{
					ArrayList<ArrayList<DTNodo>> nc=combinacionesTomadas(resto,ca-1);
					Iterator<ArrayList<DTNodo>> it23=nc.iterator();
					while(it23.hasNext())
					{
						ArrayList<DTNodo> cas=it23.next();
						cas.add(primero);
						result.add(cas);
						
					}
				}
			}
			return result;
		}
	}
		
	/**
	 * Retorna la ruta optimizada luego de realizar la la permutación <code>permutar</code>. 
	 * <p>
	 * Si no se encuentra mejora retorna la ruta origianl <code>orig</code>.
	 * 
	 * @param	orig Ruta de clientes original.
	 * @param	permutar Ruta de clientes a permutar.
	 * @param	mejorcosto Mejor costo de la ruta.
	 * @param	dep Deposito origen de la ruta <code>orig</code>.
	 * @return      Ruta nueva optimizada.
	 * 
	 */
	private ArrayList<DTNodo> permutarBuscaOptimo(ArrayList<DTNodo> orig,ArrayList<DTNodo> permutar,int mejorcosto,DTNodo dep)
	{
		int mejor=mejorcosto;
		ArrayList<DTNodo> mejora=orig;
		Collection<List<DTNodo>> perm= org.apache.commons.collections4.CollectionUtils.permutations(permutar);
		Iterator<List<DTNodo>> it=perm.iterator();
		while(it.hasNext())
		{
			ArrayList<DTNodo> nuevaopcion=new ArrayList<DTNodo>();
			List<DTNodo> lista=it.next();
			Iterator<DTNodo> iit=orig.iterator();
			Iterator<DTNodo> itlista=lista.iterator();
			while(iit.hasNext())
			{
				DTNodo prox=iit.next();
				if(this.pertenece(prox,lista))
				{
					DTNodo nuevo=itlista.next();
					nuevaopcion.add(nuevo);
				}else nuevaopcion.add(prox);
			}
			DTRuteo rut=new DTRuteo(dep,nuevaopcion);
			util.Ruta.getInstancia().setCosto(rut);
			if(rut.getCosto()<mejor)
			{
				mejor=rut.getCosto();
				mejora=nuevaopcion;
			}
		}
		return mejora;
	}
	
	/**
	 * Retorna <code>true</code> si <code>d</code> pertenece a <code>l</code>.
	 * 
	 * @param	d <code>DTNodo</code> Cliente.
	 * @param	l Lista de Clientes.
	 * @return      <code>true</code> si <code>d</code> pertenece a <code>l</code>.
	 * 
	 */
	private boolean pertenece(DTNodo d,List<DTNodo> l)
	{
		boolean ret=false;
		Iterator<DTNodo> it=l.iterator();
		while(it.hasNext())
		{
			if(d.getId()==it.next().getId())
				return true;
		}
		return ret;
	}
}
