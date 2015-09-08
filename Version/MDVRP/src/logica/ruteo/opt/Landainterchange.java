package logica.ruteo.opt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import util.Ruta;
import datatypes.DTNodo;
import datatypes.DTRuteo;

public class Landainterchange 
{
	static private Landainterchange instancia=null;
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresión con una lista de argumentos vacía. 
	 * La clase se inicializa si ya no se ha inicializado. 
	 * 
	 * @return      Una nueva instancia de la clase
	 */
	static public Landainterchange getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new Landainterchange();
			return instancia;
		}else return instancia;
	}
	
	/** 
	 * Constructor por defecto.
	 * 
	 */
	private Landainterchange()
	{
	}
	
	
	public Collection<DTRuteo> route2(Collection<DTRuteo> cols,int cap)
	{
		int costooptimo=Ruta.getInstancia().getCostoCW(cols, cap);
		int mejorcosto=costooptimo;
		Collection<DTRuteo> mejorruteo=null;
		ArrayList<DTRuteolanda> rutasoptimas=new ArrayList<DTRuteolanda>();
		Iterator<DTRuteo> itrrr=cols.iterator();
		int contador=0;
		while(itrrr.hasNext())
		{
			DTRuteo dtt=itrrr.next();
			DTRuteolanda dtrl=new DTRuteolanda(dtt.getDeposito(),new ArrayList<DTNodo>(dtt.getRuta()),contador);
			Ruta.getInstancia().setCosto(dtrl);
			rutasoptimas.add(dtrl);
			contador++;
		}
		
		
		ArrayList<ArrayList<DTRuteolanda>> combinaciones=this.combinacionesTomadas(rutasoptimas,2);
		Iterator<ArrayList<DTRuteolanda>> it=combinaciones.iterator();
		while(it.hasNext())
		{
			ArrayList<DTRuteolanda> ar=it.next();
			ArrayList<DTRuteolanda> restorutas=new ArrayList<DTRuteolanda>();
			Iterator<DTRuteolanda> itk=rutasoptimas.iterator();
			while(itk.hasNext())
			{
				DTRuteolanda ara=itk.next();
				int id=ara.getId();
				Iterator<DTRuteolanda> iii=ar.iterator();
				boolean encontro=false;
				while(iii.hasNext())
				{
					DTRuteolanda ddd=iii.next();
					if(ddd.getId()==id){encontro=true;}
				}
				if(!encontro)restorutas.add(ara);
			}
			ArrayList<DTRuteolanda> nar=new ArrayList<DTRuteolanda>();
			Iterator<DTRuteolanda> itk2=rutasoptimas.iterator();
			while(itk2.hasNext())
			{
				DTRuteolanda ara=itk2.next();
				Iterator<DTRuteolanda> iii=ar.iterator();
				
				while(iii.hasNext())
				{
					DTRuteolanda ddd=iii.next();
					if(ddd.getId()==ara.getId()){nar.add(ara);}
				}
				
			}

			
			/*System.out.println("combinacion....");
			
			((DTRuteo)nar.toArray()[0]).println();
			((DTRuteo)nar.toArray()[1]).println();
			System.out.println("resto de las rutas");
			Iterator<DTRuteolanda> ilk=restorutas.iterator();
			while(ilk.hasNext())
			{
				DTRuteolanda dmm=ilk.next();
				dmm.println();
			}*/
			
		//	System.out.println(ar+" ar");
			ArrayList<DTRuteolanda> arinv=new ArrayList<DTRuteolanda>();
			arinv.add((DTRuteolanda)nar.toArray()[1]);
			arinv.add((DTRuteolanda)nar.toArray()[0]);
			//System.out.println(arinv+" arinv");

			ArrayList<DTRuteolanda> optis=this.dosRouteBuscaOptimo(nar, cap);
			ArrayList<DTRuteolanda> optis2=this.dosRouteBuscaOptimo(arinv, cap);
			ArrayList<DTRuteolanda> optis3=this.swapBuscaOptimo(nar, cap);
			ArrayList<DTRuteolanda> optis4=this.swapBuscaOptimo2(nar, cap);
			ArrayList<DTRuteolanda> optis5=this.swapBuscaOptimo3(nar, cap);
			ArrayList<DTRuteolanda> optis6=this.swapBuscaOptimo2(arinv, cap);

			rutasoptimas=new ArrayList<DTRuteolanda>();
			rutasoptimas.addAll(restorutas);
			
			int costoptis=getCostoCWl(optis, cap);
			int costoptis2=getCostoCWl(optis2, cap);
			int costoptis3=getCostoCWl(optis3, cap);
			int costoptis4=getCostoCWl(optis4, cap);
			int costoptis5=getCostoCWl(optis5, cap);
			int costoptis6=getCostoCWl(optis6, cap);

			if((costoptis<=costoptis2)&&(costoptis<=costoptis3)&&(costoptis<=costoptis4)&&(costoptis<=costoptis5)&&(costoptis<=costoptis6))
			{
				rutasoptimas.addAll(optis);
			}else
			if((costoptis2<=costoptis)&&(costoptis2<=costoptis3)&&(costoptis2<=costoptis4)&&(costoptis2<=costoptis5)&&(costoptis2<=costoptis6))
			{
				rutasoptimas.addAll(optis2);
			}else
			if((costoptis3<=costoptis)&&(costoptis3<=costoptis2)&&(costoptis3<=costoptis4)&&(costoptis3<=costoptis5)&&(costoptis3<=costoptis6))
			{
				rutasoptimas.addAll(optis3);
			}else
			if((costoptis4<=costoptis)&&(costoptis4<=costoptis2)&&(costoptis4<=costoptis3)&&(costoptis4<=costoptis5)&&(costoptis4<=costoptis6))
			{
				rutasoptimas.addAll(optis4);
			}else
			if((costoptis5<=costoptis)&&(costoptis5<=costoptis2)&&(costoptis5<=costoptis3)&&(costoptis5<=costoptis4)&&(costoptis5<=costoptis6))
			{
				rutasoptimas.addAll(optis5);
			}else
			if((costoptis6<=costoptis)&&(costoptis6<=costoptis2)&&(costoptis6<=costoptis3)&&(costoptis6<=costoptis4)&&(costoptis6<=costoptis5))
			{
				rutasoptimas.addAll(optis6);
			}

			System.out.println(".-............");
		}
		ArrayList<DTRuteo> retu=new ArrayList<DTRuteo>();
		retu.addAll(rutasoptimas);
	/*	if(Ruta.getInstancia().getCostoCW(retu, cap)<costooptimo) 
		{
			mejorcosto=Ruta.getInstancia().getCostoCW(retu, cap);
			mejorruteo=retu;
		}*/

		
		if(Ruta.getInstancia().getCostoCW(retu, cap)<costooptimo) 
		{
			System.out.println(Ruta.getInstancia().getCostoCW(retu, cap)+"+++++"+costooptimo);
			return retu;
		}
		else 
		{
			System.out.println(Ruta.getInstancia().getCostoCW(cols, cap)+"-----"+costooptimo);
			return cols; 
		}
		
	}
	
	
	public ArrayList<ArrayList<DTRuteolanda>> combinacionesTomadas(Collection<DTRuteolanda> c,int ca)
	{
		if(c.size()==ca)
		{
			ArrayList<ArrayList<DTRuteolanda>> req=new ArrayList<ArrayList<DTRuteolanda>>();
			ArrayList<DTRuteolanda> rq=new ArrayList<DTRuteolanda>(c);
			req.add(rq);
			return req;
		}
		if(ca==0) return new ArrayList<ArrayList<DTRuteolanda>>();
		if(ca==1)
		{
			ArrayList<ArrayList<DTRuteolanda>> ar=new ArrayList<ArrayList<DTRuteolanda>>();
			Iterator<DTRuteolanda> it=c.iterator();
			while(it.hasNext())
			{
				DTRuteolanda n=it.next();
				ArrayList<DTRuteolanda> ar1=new ArrayList<DTRuteolanda>();
				ar1.add(n);
				ar.add(ar1);
			}
			return ar;

		}
		else
		{
			
			Iterator<DTRuteolanda> it1=c.iterator();
			ArrayList<ArrayList<DTRuteolanda>> result=new ArrayList<ArrayList<DTRuteolanda>>();
			while(it1.hasNext())
			{
				DTRuteolanda primero=it1.next();
				Iterator<DTRuteolanda> it2=c.iterator();
				ArrayList<DTRuteolanda> resto=new ArrayList<DTRuteolanda>();
				boolean encontro=false;
				while(it2.hasNext())
				{
					DTRuteolanda n=it2.next();
					if(encontro)
					{
						resto.add(n);
					}
					else
					{
						if((!n.getRuta().isEmpty())&&(!primero.getRuta().isEmpty()))
						if(n.getDeposito().getId()==primero.getDeposito().getId())
						if(n.getRuta().iterator().next().getId()==primero.getRuta().iterator().next().getId())
						{
							encontro=true;
						}
					}
				}
				if(resto.size()>=(ca-1))
				{
					ArrayList<ArrayList<DTRuteolanda>> nc=combinacionesTomadas(resto,ca-1);
					Iterator<ArrayList<DTRuteolanda>> it23=nc.iterator();
					while(it23.hasNext())
					{
						ArrayList<DTRuteolanda> cas=it23.next();
						cas.add(primero);
						result.add(cas);
						
					}
				}
			}
			return result;
		
		}
	}

	
	public ArrayList<DTRuteolanda> dosRouteBuscaOptimo(ArrayList<DTRuteolanda> orig,int cap)
	{
		DTRuteolanda primera=(DTRuteolanda)orig.toArray()[0];
		DTRuteolanda segunda=(DTRuteolanda)orig.toArray()[1];
		int mejor=primera.getCosto()+segunda.getCosto();
		
		
		boolean continuar=primera.getRuta().size()>=4;
		int indice1=0;
		
		
		while(continuar)
		{
			int indice2=0;
			boolean continuar2=segunda.getRuta().size()>=4;
			while(continuar2)
			{
		
				DTRuteolanda nprimera=new DTRuteolanda(primera.getDeposito(),primera.getId());
				DTRuteolanda nsegunda=new DTRuteolanda(primera.getDeposito(),segunda.getId());
				for(int i=0;i<indice1;i++)
				{
					nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[i]);
				}
				for(int i=0;i<indice2;i++)
				{
					nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[i]);
				}
				
				nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1]);
				nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1+2]);
				nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1+3]);
				nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2]);
				nsegunda.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1+1]);
				nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2+1]);
				nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2+2]);
				nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2+3]);

				
				for(int i=(indice1+4);i<primera.getRuta().size();i++)
				{
					nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[i]);
				}
				for(int i=(indice2+4);i<segunda.getRuta().size();i++)
				{
					nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[i]);
				}
				
		/*		System.out.println("combinacion rutas antes");
				primera.println();
				segunda.println();
				System.out.println("combinacion rutas despues");
				nprimera.println();
				nsegunda.println();*/
				if((Ruta.getInstancia().getCarga(nsegunda)>cap)||(Ruta.getInstancia().getCarga(nprimera)>cap))
				{
					//System.out.println("combinacion no factible "+Ruta.getInstancia().getCarga(nsegunda)+" "+cap);
				}
				else
				{
					Ruta.getInstancia().setCosto(nsegunda);
					Ruta.getInstancia().setCosto(nprimera);
					int ncosto=nsegunda.getCosto()+nprimera.getCosto();
					if(ncosto<mejor)
					{
						System.out.print("!!!! hay mejora   "+mejor);
						mejor=ncosto;
						System.out.println("!!!! hay mejora   "+mejor);
						primera=nprimera;
						segunda=nsegunda;
						primera.println();
						segunda.println();
					}else {}//System.out.println("no hay mejora "+mejor+" "+ncosto);
				}
				
				indice2++;
				if((indice2+4)>segunda.getRuta().size())continuar2=false;
				if((indice1+4)>primera.getRuta().size())continuar2=false;
			}
			indice1++;
			if((indice1+4)>primera.getRuta().size())continuar=false;
		}
	
		ArrayList<DTRuteolanda> ar=new ArrayList<DTRuteolanda>();
		ar.add(primera);
		ar.add(segunda);
		return ar;
	
	}
	
	
	private class DTRuteolanda extends DTRuteo
	{
		private int id;
		public DTRuteolanda(DTNodo d,ArrayList<DTNodo> ar,int id)
		{
			super(d,ar);
			this.id=id;
		}
		public DTRuteolanda(DTNodo d,int id)
		{
			super(d);
			this.id=id;
		}
		public int getId(){return this.id;}
		public boolean mismaRuta(DTRuteolanda dt)
		{
			return this.id==dt.getId();
		}		
	}
	
	
	public int getCostoCWl(Collection<DTRuteolanda> col,int cap)
	{
		int costo=0;
		Iterator<DTRuteolanda> it=col.iterator();
		while(it.hasNext())
		{
			DTRuteolanda dtr=it.next();
			Ruta.getInstancia().setCosto(dtr);
			costo=costo+dtr.getCosto();
		}
		return costo;
	}
	
	
	public ArrayList<DTRuteolanda> swapBuscaOptimo(ArrayList<DTRuteolanda> orig,int cap)
	{
		DTRuteolanda primera=(DTRuteolanda)orig.toArray()[0];
		DTRuteolanda segunda=(DTRuteolanda)orig.toArray()[1];
		int mejor=primera.getCosto()+segunda.getCosto();
		
		
		boolean continuar=primera.getRuta().size()>=4;
		int indice1=0;
		
		
		while(continuar)
		{
			int indice2=0;
			boolean continuar2=segunda.getRuta().size()>=4;
			while(continuar2)
			{
		
				DTRuteolanda nprimera=new DTRuteolanda(primera.getDeposito(),primera.getId());
				DTRuteolanda nsegunda=new DTRuteolanda(primera.getDeposito(),segunda.getId());
				for(int i=0;i<indice1;i++)
				{
					nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[i]);
				}
				for(int i=0;i<indice2;i++)
				{
					nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[i]);
				}
				
				nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1]);
				nprimera.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2+1]);
				nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1+2]);
				nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1+3]);
				nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2]);
				nsegunda.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1+1]);
				nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2+2]);
				nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2+3]);

				
				for(int i=(indice1+4);i<primera.getRuta().size();i++)
				{
					nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[i]);
				}
				for(int i=(indice2+4);i<segunda.getRuta().size();i++)
				{
					nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[i]);
				}
				
		/*		System.out.println("combinacion rutas antes");
				primera.println();
				segunda.println();
				System.out.println("combinacion rutas despues");
				nprimera.println();
				nsegunda.println();*/
				if((Ruta.getInstancia().getCarga(nsegunda)>cap)||(Ruta.getInstancia().getCarga(nprimera)>cap))
				{
					//System.out.println("combinacion no factible "+Ruta.getInstancia().getCarga(nsegunda)+" "+cap);
				}
				else
				{
					Ruta.getInstancia().setCosto(nsegunda);
					Ruta.getInstancia().setCosto(nprimera);
					int ncosto=nsegunda.getCosto()+nprimera.getCosto();
					if(ncosto<mejor)
					{
						System.out.print("!!!! hay mejora swap  1 "+mejor);
						mejor=ncosto;
						System.out.println("!!!! hay mejora swap  1 "+mejor);
						primera=nprimera;
						segunda=nsegunda;
						primera.println();
						segunda.println();
					}else {}//System.out.println("no hay mejora "+mejor+" "+ncosto);
				}
				
				indice2++;
				if((indice2+4)>segunda.getRuta().size())continuar2=false;
			}
			indice1++;
			if((indice1+4)>primera.getRuta().size())continuar=false;
		}
	
		ArrayList<DTRuteolanda> ar=new ArrayList<DTRuteolanda>();
		ar.add(primera);
		ar.add(segunda);
		return ar;
	
	}
	
	
	public ArrayList<DTRuteolanda> swapBuscaOptimo2(ArrayList<DTRuteolanda> orig,int cap)
	{
		DTRuteolanda primera=(DTRuteolanda)orig.toArray()[0];
		DTRuteolanda segunda=(DTRuteolanda)orig.toArray()[1];
		int mejor=primera.getCosto()+segunda.getCosto();
		
		
		boolean continuar=primera.getRuta().size()>=4;
		int indice1=0;
		
		
		while(continuar)
		{
			int indice2=0;
			boolean continuar2=segunda.getRuta().size()>=4;
			while(continuar2)
			{
		
				DTRuteolanda nprimera=new DTRuteolanda(primera.getDeposito(),primera.getId());
				DTRuteolanda nsegunda=new DTRuteolanda(primera.getDeposito(),segunda.getId());
				for(int i=0;i<indice1;i++)
				{
					nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[i]);
				}
				for(int i=0;i<indice2;i++)
				{
					nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[i]);
				}
				
				nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1]);
				nprimera.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2+1]);
				nprimera.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2+2]);
				nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1+1]);
				nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1+2]);
				nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1+3]);
				nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2]);
				nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2+3]);

				
				for(int i=(indice1+4);i<primera.getRuta().size();i++)
				{
					nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[i]);
				}
				for(int i=(indice2+4);i<segunda.getRuta().size();i++)
				{
					nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[i]);
				}
				
		/*		System.out.println("combinacion rutas antes");
				primera.println();
				segunda.println();
				System.out.println("combinacion rutas despues");
				nprimera.println();
				nsegunda.println();*/
				if((Ruta.getInstancia().getCarga(nsegunda)>cap)||(Ruta.getInstancia().getCarga(nprimera)>cap))
				{
					//System.out.println("combinacion no factible "+Ruta.getInstancia().getCarga(nsegunda)+" "+cap);
				}
				else
				{
					Ruta.getInstancia().setCosto(nsegunda);
					Ruta.getInstancia().setCosto(nprimera);
					int ncosto=nsegunda.getCosto()+nprimera.getCosto();
					if(ncosto<mejor)
					{
						System.out.print("!!!! hay mejora swap 2 "+mejor);
						mejor=ncosto;
						System.out.println("!!!! hay mejora swap 2 "+mejor);
						primera=nprimera;
						segunda=nsegunda;
						primera.println();
						segunda.println();
					}else {}//System.out.println("no hay mejora "+mejor+" "+ncosto);
				}
				
				indice2++;
				if((indice2+4)>segunda.getRuta().size())continuar2=false;
				if((indice1+4)>primera.getRuta().size())continuar2=false;

			}
			indice1++;
			if((indice1+4)>primera.getRuta().size())continuar=false;
		}
	
		ArrayList<DTRuteolanda> ar=new ArrayList<DTRuteolanda>();
		ar.add(primera);
		ar.add(segunda);
		return ar;
	
	}


	public ArrayList<DTRuteolanda> swapBuscaOptimo3(ArrayList<DTRuteolanda> orig,int cap)
	{
		DTRuteolanda primera=(DTRuteolanda)orig.toArray()[0];
		DTRuteolanda segunda=(DTRuteolanda)orig.toArray()[1];
		int mejor=primera.getCosto()+segunda.getCosto();
		
		
		boolean continuar=primera.getRuta().size()>=4;
		int indice1=0;
		
		
		while(continuar)
		{
			int indice2=0;
			boolean continuar2=segunda.getRuta().size()>=4;
			while(continuar2)
			{
		
				DTRuteolanda nprimera=new DTRuteolanda(primera.getDeposito(),primera.getId());
				DTRuteolanda nsegunda=new DTRuteolanda(primera.getDeposito(),segunda.getId());
				for(int i=0;i<indice1;i++)
				{
					nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[i]);
				}
				for(int i=0;i<indice2;i++)
				{
					nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[i]);
				}
				
				nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1]);
				nprimera.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2+1]);
				nprimera.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2+2]);
				nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1+3]);
				nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2]);
				nsegunda.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1+1]);
				nsegunda.agregarCliente((DTNodo)primera.getRuta().toArray()[indice1+2]);
				nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[indice2+3]);

				
				for(int i=(indice1+4);i<primera.getRuta().size();i++)
				{
					nprimera.agregarCliente((DTNodo)primera.getRuta().toArray()[i]);
				}
				for(int i=(indice2+4);i<segunda.getRuta().size();i++)
				{
					nsegunda.agregarCliente((DTNodo)segunda.getRuta().toArray()[i]);
				}
				
		/*		System.out.println("combinacion rutas antes");
				primera.println();
				segunda.println();
				System.out.println("combinacion rutas despues");
				nprimera.println();
				nsegunda.println();*/
				if((Ruta.getInstancia().getCarga(nsegunda)>cap)||(Ruta.getInstancia().getCarga(nprimera)>cap))
				{
					//System.out.println("combinacion no factible "+Ruta.getInstancia().getCarga(nsegunda)+" "+cap);
				}
				else
				{
					Ruta.getInstancia().setCosto(nsegunda);
					Ruta.getInstancia().setCosto(nprimera);
					int ncosto=nsegunda.getCosto()+nprimera.getCosto();
					if(ncosto<mejor)
					{
						System.out.print("!!!! hay mejora swap 3 "+mejor);
						mejor=ncosto;
						System.out.println("!!!! hay mejora swap 3 "+mejor);
						primera=nprimera;
						segunda=nsegunda;
						primera.println();
						segunda.println();
					}else {}//System.out.println("no hay mejora "+mejor+" "+ncosto);
				}
				
				indice2++;
				if((indice2+4)>segunda.getRuta().size())continuar2=false;
			}
			indice1++;
			if((indice1+4)>primera.getRuta().size())continuar=false;
		}
	
		ArrayList<DTRuteolanda> ar=new ArrayList<DTRuteolanda>();
		ar.add(primera);
		ar.add(segunda);
		return ar;
	
	}


}
