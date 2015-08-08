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
	static public Route14opt getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Route14opt();
			return instancia;
		}else return instancia;
	}
	private Route14opt()
	{
	}
	
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
	
	public ArrayList<ArrayList<DTNodo>> combinacionesTomadas(Collection<DTNodo> c,int ca)
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
	
	public ArrayList<DTNodo> permutarBuscaOptimo(ArrayList<DTNodo> orig,ArrayList<DTNodo> permutar,int mejorcosto,DTNodo dep)
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
