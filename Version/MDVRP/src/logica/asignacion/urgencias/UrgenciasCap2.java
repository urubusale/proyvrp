package logica.asignacion.urgencias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import logica.Fabrica;
import util.*;
import datatypes.DTAsignacion;
import datatypes.DTDepositoVRP;
import datatypes.DTNodo;
import datatypes.DTRuteo;

public class UrgenciasCap2 {
	static private UrgenciasCap2 instancia=null;

	private Collection<ClienteCap2> clientes;
	private Collection<Deposito> depositos;
	private Collection<Enajenado> enajenados;
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresión con una lista de argumentos vacía. 
	 * La clase se inicializa si ya no se ha inicializado. 
	 * 
	 * @return      Una nueva instancia de la clase
	 */
	static public UrgenciasCap2 getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new UrgenciasCap2();
			return instancia;
		}else return instancia;
	}
	
	/** 
	 * Constructor por defecto.
	 * 
	 */
	private UrgenciasCap2()
	{
		// UrgenciasCap2 - Algoritmo = 0
		Config.getInstancia().setAlgoritmo(Config.urgenciasCapRapido);
	}
	
	/**
	 * El metodo se encarga de realizar la asignación Hibrida para depósitos con capacidad limitada (Algoritmo 1).
	 * <p>
	 * Recibe por parámetro <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * <p>
	 * Retorna una colección de <code>DTAsignacion</code>. Cada <code>DTAsignacion</code> contiene un deposito y una colección de clientes que estan asignados al deposito.
	 *
	 * @param	d <code>DTDepositoVRP</code> donde contiene toda la información del problema a resolver.
	 * @return      Devuelve una colección de <code>DTAsignacion</code>.. 
	 * 
	 */
	public Collection<DTAsignacion> asignar(DTDepositoVRP d)
	{

		System.out.println("Asignar capacidad");
		
		clientes=new ArrayList<ClienteCap2>();
		depositos=new ArrayList<Deposito>();
		enajenados=new ArrayList<Enajenado>();
		
		Iterator<DTNodo> it=d.getNodos().iterator();
		while(it.hasNext())
		{
			DTNodo dt=it.next();
			if(dt.getEsDesposito())
			{
				Deposito dep=new Deposito(dt);
				depositos.add(dep);
			}
		}
		
		Iterator<DTNodo> it2=d.getNodos().iterator();
		while(it2.hasNext())
		{
			DTNodo dt=it2.next();
			if(!dt.getEsDesposito())
			{
				ClienteCap2 cli=new ClienteCap2(dt);
				cli.setMu(calcularMu(cli,depositos));
				clientes.add(cli);
			}
		}
		
		// Calculo los 2 clientes mas cercanos para cada cliente (precalculo para fase 2)
		Iterator<ClienteCap2> it3=this.clientes.iterator();

		while(it3.hasNext())
		{
			ClienteCap2 cliente=it3.next();
			cliente = addClientesMasCercanos(cliente);
			System.out.println("Clientes mas cercanos" +  cliente.getNodo().getId() + " y nodos " + cliente.getClieteMasCercano1().getNodo().getId() +  " ," 
					 +  cliente.getClieteMasCercano2().getNodo().getId());
		}		
		
		
	
		// Asigno los nodos por urgencia (fase 1).
		while(clientes.size()>0)
		{
			TreeSet<ClienteCap2> tr=new TreeSet<ClienteCap2>(clientes);
			Iterator<ClienteCap2> itc=tr.iterator();
			ClienteCap2 proximo=itc.next();
			proximo.getMasCercano().agregarCliente(proximo); //agrego el cliente en el deposito mas cercano.
			clientes.remove(proximo);
			Iterator<ClienteCap2> itcli=clientes.iterator();
			while(itcli.hasNext())
			{
				ClienteCap2 n=itcli.next();
				n.setMu(calcularMu(n,depositos));
			}
		}
		
		// agrego a lista si los 2 cliente + cercanos a cada cliente pertencen al mismo deposito deposito (que no es el mismo que el deposito asignado para el cliente).
		
		//acaaaa
		int capacidadvehiculo=Integer.valueOf(d.getCAPACITY());
		System.out.println("cap del vehiculo "+capacidadvehiculo);

		ArrayList<DTAsignacion> ar=null;
		int costomenor=0;

		Iterator<Deposito> itdd=this.depositos.iterator();
		while(itdd.hasNext())
		{
			Deposito dep=itdd.next();
			DTAsignacion dta=new DTAsignacion(dep.getNodo());
			Iterator<Cliente> itcli=dep.getAsignados().iterator();
			while(itcli.hasNext())
			{
				Cliente cli=itcli.next();
				dta.agregarCliente(cli.getNodo());
			}
			Iterator<DTRuteo> itrut=Fabrica.getInstancia().getRuteo().rutear(dta, capacidadvehiculo).iterator();
			while(itrut.hasNext())
			{
				DTRuteo next=itrut.next();
				costomenor=costomenor+next.getCosto();
			}
		}
		System.out.println("costo inicial "+costomenor);
		int cantidadIteraciones=0;

		// Inicializo el Tiempo en Config
		Config.getInstancia().empezarAlgoritmo(costomenor);
		ArrayList<Integer> aux,tmp;
		tmp = new ArrayList<Integer>();
		boolean terminar=false;
		while(!terminar)
		{
			cantidadIteraciones++;
			System.out.println("Cantidad Iteraciones "+cantidadIteraciones);
			this.calcularEnagenamiento();
			// Aplico todos los cambios de la lista de enajenados ....

			aux = new ArrayList<Integer>();
			Iterator<Enajenado> itena= this.enajenados.iterator();
			while(itena.hasNext())
			{
				Enajenado ena=itena.next();
				
				aux.add(ena.getCliente().getNodo().getId());
				
				if(ena.getDepositoDestino().getCapacidadLibrePonderada()>=ena.getCliente().getNodo().getDemanda())
				{
					Iterator<Deposito> respita=this.depositos.iterator();
					while(respita.hasNext())
					{
						Deposito dep=respita.next();
						Deposito nuevo=new Deposito(dep);
						if(nuevo.getNodo().getId()==ena.getDeposito().getNodo().getId())
							nuevo.sacarCliente(ena.getCliente());
						if(nuevo.getNodo().getId()==ena.getDepositoDestino().getNodo().getId())
							nuevo.agregarCliente(ena.getCliente());

						
						DTAsignacion dta=new DTAsignacion(nuevo.getNodo());
						Iterator<Cliente> itcli=nuevo.getAsignados().iterator();
						while(itcli.hasNext())
						{
							Cliente cli=itcli.next();
							dta.agregarCliente(cli.getNodo());
						}
					}
					ena.getDeposito().sacarCliente(ena.getCliente());
					ena.getDepositoDestino().agregarCliente(ena.getCliente());
				}
			}
			
			boolean equalLists = ((aux.size() == tmp.size()) && (tmp.containsAll(aux)));
			tmp = new ArrayList<Integer>();
			tmp.addAll(aux);
			
			
			if((Config.getInstancia().terminarPorConfig(cantidadIteraciones,costomenor)) || equalLists)
				terminar=true;
		}
		// construir DT de salida.
		ar=new ArrayList<DTAsignacion>();
		
		Iterator<Deposito> itd=depositos.iterator();
		while(itd.hasNext())
		{
			Deposito dep=itd.next();
			DTAsignacion dta=new DTAsignacion(dep.getNodo());
			Iterator<Cliente> itcli=dep.getAsignados().iterator();
			while(itcli.hasNext())
			{
				Cliente cli=itcli.next();
				dta.agregarCliente(cli.getNodo());
			}
			ar.add(dta);
		}
		
		Penalization.getInstancia().getCalculoPenalidad(ar);
		
		return ar;
	}
	
	/**
	 * Calcula los nodos que son enajendados.
	 * 
	 *  
	 */
	private void calcularEnagenamiento()
	{
		Iterator<Deposito> itd1=depositos.iterator();
		this.enajenados=new ArrayList<Enajenado>();
		while(itd1.hasNext())
		{ 
			Deposito dep1 = itd1.next();
			Iterator<Cliente> itc1=dep1.getAsignados().iterator();

			while(itc1.hasNext())
			{
				Cliente clientebase=itc1.next();
				Iterator<Deposito> it4=depositos.iterator();
				while(it4.hasNext())
				{ 
					boolean encontre1=false;
					boolean encontre2=false;
					Deposito dep2 = it4.next();
					if (dep1.getNodo().getId() !=  dep2.getNodo().getId())
					{
					
						Iterator<Cliente>  it5=dep2.getAsignados().iterator();
						while(it5.hasNext())
						{
							Cliente cliente=it5.next();
							if (((ClienteCap2)clientebase).getClieteMasCercano1().getNodo().getId() == cliente.getNodo().getId()) 
								encontre1= true;
							if (((ClienteCap2)clientebase).getClieteMasCercano2().getNodo().getId() == cliente.getNodo().getId()) 
								encontre2= true;
								
						}
						if (encontre1 && encontre2) 
						{
							this.enajenados.add(new Enajenado(((ClienteCap2)clientebase), dep1, dep2));
							break;
						}
					}
				}
			}	
		}
		
		// limpio lista de enegenados por la distancia al deposito.
		int distCaC1, distCaC2, distDep;
		Iterator<Enajenado> itena= this.enajenados.iterator();
		ArrayList<Enajenado> asacar=new ArrayList<Enajenado>();
		while(itena.hasNext())
		{
			Enajenado ena=itena.next();
			distCaC1 = Distancia.getInstancia().getDistancia(ena.getCliente().getClieteMasCercano1().getNodo(), ena.getCliente().getNodo());
			distCaC2 = Distancia.getInstancia().getDistancia(ena.getCliente().getClieteMasCercano2().getNodo(), ena.getCliente().getNodo());
			distDep =  Distancia.getInstancia().getDistancia(ena.getCliente().getNodo(), ena.getDeposito().getNodo());
			
			System.out.println("Clientes mas cercanos a " + ena.getCliente().getNodo().getId() + " ( " + ena.getDeposito().getNodo().getId() + " ). c 1 y c2 " + ena.getCliente().getClieteMasCercano1().getNodo().getId() +  " ," 
					 +  ena.getCliente().getClieteMasCercano2().getNodo().getId() + "(" +ena.getDepositoDestino().getNodo().getId() + ")" );
			
			
			if (distCaC1 + distCaC2 > distDep)
				asacar.add(ena);//this.enajenados.remove(ena);
			else ena.setMu(distDep-(distCaC1+distCaC2));

		}
		Iterator<Enajenado> itsacar=asacar.iterator();
		while(itsacar.hasNext()){this.enajenados.remove(itsacar.next());}

	}
	
	/**
	 * Calcula el valor de <code>mu</code>. 
	 *
	 * @param	c Cliente.
	 * @param	dep Colección de depósitos.
	 * 
	 */
	private int calcularMu(ClienteCap2 c,Collection<Deposito> dep)
	{
		/*Deposito  masCercano=null;
		if(dep.size()>0)
		{
			masCercano=dep.iterator().next();
		}
		else
		{
			return 0;
		}
		int distanciaMasCercano=Integer.MAX_VALUE;
		Iterator<Deposito> it=dep.iterator();
		while(it.hasNext())
		{
			Deposito n=it.next();
			if(n.getCapacidadLibre()>=c.getNodo().getDemanda())
			{
				if(Distancia.getInstancia().getDistancia(n.getNodo(), c.getNodo())<distanciaMasCercano)
				{
					masCercano=n;
					distanciaMasCercano=Distancia.getInstancia().getDistancia(n.getNodo(), c.getNodo());
				}
			}
		}
		
		int sumatoriaDistancias=0;
		Iterator<Deposito> it2=dep.iterator();
		while(it2.hasNext())
		{
			Deposito n=it2.next();
			if(n.getCapacidadLibre()>=c.getNodo().getDemanda())
			{
				if(n.getNodo().getId()!=masCercano.getNodo().getId())
				{
					sumatoriaDistancias=sumatoriaDistancias+Distancia.getInstancia().getDistancia(n.getNodo(),c.getNodo());
				}
			}
		}
		c.setMasCercano(masCercano);
		return sumatoriaDistancias-distanciaMasCercano;*/
	Deposito  masCercano=null;
	
		
		int distanciaMasCercano=Integer.MAX_VALUE;
		Iterator<Deposito> it=dep.iterator();
		while(it.hasNext())
		{
			Deposito n=it.next();
			if(n.getCapacidadLibre()>=c.getNodo().getDemanda())
			{
				if(Distancia.getInstancia().getDistancia(n.getNodo(), c.getNodo())<distanciaMasCercano)
				{
					masCercano=n;
					distanciaMasCercano=Distancia.getInstancia().getDistancia(n.getNodo(), c.getNodo());
				}
			}
		}
		
		if(masCercano!=null)
		{
			int sumatoriaDistancias=0;
			Iterator<Deposito> it2=dep.iterator();
			while(it2.hasNext())
			{
				Deposito n=it2.next();
				if(n.getCapacidadLibre()>=c.getNodo().getDemanda())
				{
					if(n.getNodo().getId()!=masCercano.getNodo().getId())
					{
						sumatoriaDistancias=sumatoriaDistancias+Distancia.getInstancia().getDistancia(n.getNodo(),c.getNodo());
					}
				}
			}
			c.setMasCercano(masCercano);
			return sumatoriaDistancias-distanciaMasCercano;
		}else
		{
			int distanciaMasCercano2=Integer.MAX_VALUE;
			Iterator<Deposito> it2=dep.iterator();
			while(it2.hasNext())
			{
				Deposito n=it2.next();
				if(Distancia.getInstancia().getDistancia(n.getNodo(), c.getNodo())<distanciaMasCercano2)
				{
					masCercano=n;
					distanciaMasCercano2=Distancia.getInstancia().getDistancia(n.getNodo(), c.getNodo());
				}
			}
			c.setMasCercano(masCercano);
			return 0;
		}
	}
	
	/**
	 * Agrega al ClienteCap2 <code>c</code> los dos cliente mas cercanos.
	 * <p>
	 * El método se encarga invocar a <code>setClieteMasCercano1</code>, <code>setClieteMasCercano2</code> de <code>ClienteCap2</code>
	 * 
	 * @param	c <code>ClienteCap2</code> donde contiene toda la información del problema a resolver.
	 * @return      Devuelve <code>c</code> con la información de los dos clientes mas cercanos.
	 * 
	 */
	private ClienteCap2 addClientesMasCercanos(ClienteCap2 c) 
	{
		Iterator<ClienteCap2> it=this.clientes.iterator();
		int distanciaMasCercano1=Integer.MAX_VALUE;
		int distanciaMasCercano2=Integer.MAX_VALUE;
		
		while(it.hasNext()  )
		{
			ClienteCap2 clientetmp=it.next();
				if((Distancia.getInstancia().getDistancia(clientetmp.getNodo(), c.getNodo())< distanciaMasCercano1) && (clientetmp.getNodo().getId() != c.getNodo().getId()))
				{
					c.setClieteMasCercano1(clientetmp);
					distanciaMasCercano1 = Distancia.getInstancia().getDistancia(clientetmp.getNodo(), c.getNodo());
				}
				else 
					if((Distancia.getInstancia().getDistancia(clientetmp.getNodo(), c.getNodo())< distanciaMasCercano2) && (clientetmp.getNodo().getId() != c.getNodo().getId()))
					{
						c.setClieteMasCercano2(clientetmp);
						distanciaMasCercano2 = Distancia.getInstancia().getDistancia(clientetmp.getNodo(), c.getNodo());
					}
		}

		return c; 
	}	
}