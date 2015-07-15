package logica.asignacion.urgencias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import logica.Fabrica;
import logica.Sistema;
import util.Config;
import util.Distancia;
import datatypes.DTAsignacion;
import datatypes.DTDepositoVRP;
import datatypes.DTNodo;
import datatypes.DTRuteo;

public class UrgenciasCap22 {
	static private UrgenciasCap22 instancia=null;
	static public UrgenciasCap22 getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new UrgenciasCap22();
			return instancia;
		}else return instancia;
	}
	private UrgenciasCap22()
	{
	}
	
	private Collection<ClienteCap2> clientes;
	private Collection<Deposito> depositos;
	private Collection<Enagenado> enagenados;
	
	public Collection<DTAsignacion> asignar(DTDepositoVRP d)
	{

		System.out.println("Asignar capacidad2 fase 2");
		
		clientes=new ArrayList<ClienteCap2>();
		depositos=new ArrayList<Deposito>();
		enagenados=new ArrayList<Enagenado>();
		
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
			System.out.println("Clientes mas cercanos " +  cliente.getNodo().getId() + " y nodos " + cliente.getClieteMasCercano1().getNodo().getId() +  " ," 
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
		ArrayList<DTAsignacion> ccc=new ArrayList<DTAsignacion>();
		Iterator<Deposito> itd2=depositos.iterator();
		while(itd2.hasNext())
		{
			Deposito dep=itd2.next();
			DTAsignacion dta=new DTAsignacion(dep.getNodo());
			Iterator<Cliente> itcli=dep.getAsignados().iterator();
			while(itcli.hasNext())
			{
				Cliente cli=itcli.next();
				dta.agregarCliente(cli.getNodo());
			}
			ccc.add(dta);
		}
		Sistema.getInstancia().setParciales(ccc);
		
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
		ArrayList<DTNodo> resaltado=null;

		// Inicializo el Tiempo en Config
		Config.getInstancia().empezarAlgoritmo(costomenor);
		
		boolean terminarPorConfig=false;
		boolean terminar=false;
		String mensaje=null;
		while(!terminar)
		{
			resaltado=new ArrayList<DTNodo>();
			cantidadIteraciones++;
			this.calcularEnagenamiento();
			// Aplico todos los cambios de la lista de enagenados para ver que pasa.
			int costoahora=costomenor;
			Iterator<Enagenado> itena= this.enagenados.iterator();
			while(itena.hasNext())
			{
				Enagenado ena=itena.next();
				//System.out.println(ena.getCliente().getNodo().getId()+" enagenado! ");
				if(ena.getDepositoDestino().getCapacidadLibre()>=ena.getCliente().getNodo().getDemanda())
				{
					//hacer copia de los datos
					//ArrayList<Deposito> respaldoDep=new ArrayList<Deposito>();
					Iterator<Deposito> respita=this.depositos.iterator();
					int costo=0;
					while(respita.hasNext())
					{
						Deposito dep=respita.next();
						Deposito nuevo=new Deposito(dep);
						if(nuevo.getNodo().getId()==ena.getDeposito().getNodo().getId())
							nuevo.sacarCliente(ena.getCliente());
						if(nuevo.getNodo().getId()==ena.getDepositoDestino().getNodo().getId())
							nuevo.agregarCliente(ena.getCliente());
					//	respaldoDep.add(nuevo);
						
						DTAsignacion dta=new DTAsignacion(nuevo.getNodo());
						Iterator<Cliente> itcli=nuevo.getAsignados().iterator();
						while(itcli.hasNext())
						{
							Cliente cli=itcli.next();
							dta.agregarCliente(cli.getNodo());
						}
						Iterator<DTRuteo> itrut=Fabrica.getInstancia().getRuteo().rutear(dta, capacidadvehiculo).iterator();
						while(itrut.hasNext())
						{
							DTRuteo next=itrut.next();
							costo=costo+next.getCosto();
						}
					}
					if(costo<costomenor)
					{
						ena.getDeposito().sacarCliente(ena.getCliente());
						ena.getDepositoDestino().agregarCliente(ena.getCliente());
						costomenor=costo;
						mensaje="*1 mejora en iteracion "+cantidadIteraciones+", costo: "+costomenor +" se pasó al cliente "+ena.getCliente().getNodo().getId()+ " desde el deposito "+ ena.getDeposito().getNodo().getId()+" al deposito destino "+ena.getDepositoDestino().getNodo().getId();
						System.out.println(mensaje);
						resaltado.add(ena.getCliente().getNodo());
						resaltado.add(ena.getDeposito().getNodo());
						resaltado.add(ena.getDepositoDestino().getNodo());
						
						break;
					}else System.out.println("el costo dio "+costo);
				}else
				{
					int demanda=ena.getCliente().getNodo().getDemanda();
					Iterator<Deposito> itnnn=this.depositos.iterator();
					int masposible=Integer.MAX_VALUE;
					Deposito candidato=null;
					while(itnnn.hasNext())
					{
						Deposito depo=itnnn.next();
						if(depo.getNodo().getId()!=ena.getDeposito().getNodo().getId())
							if(depo.getNodo().getId()!=ena.getDepositoDestino().getNodo().getId())
							{
								if(depo.getCapacidadLibre()>=demanda)
								{
									int dist=Distancia.getInstancia().getDistancia(depo.getNodo(),ena.getCliente().getNodo());
									if(dist<masposible)
									{
										candidato=depo;
										masposible=dist;
									}
								}
							}
					}
					if(candidato!=null)
					{
						Iterator<Deposito> respita=this.depositos.iterator();
						int costo=0;
						while(respita.hasNext())
						{
							Deposito dep=respita.next();
							Deposito nuevo=new Deposito(dep);
							if(nuevo.getNodo().getId()==ena.getDeposito().getNodo().getId())
								nuevo.sacarCliente(ena.getCliente());
							if(nuevo.getNodo().getId()==candidato.getNodo().getId())
								nuevo.agregarCliente(ena.getCliente());
						//	respaldoDep.add(nuevo);
							
							DTAsignacion dta=new DTAsignacion(nuevo.getNodo());
							Iterator<Cliente> itcli=nuevo.getAsignados().iterator();
							while(itcli.hasNext())
							{
								Cliente cli=itcli.next();
								dta.agregarCliente(cli.getNodo());
							}
							Iterator<DTRuteo> itrut=Fabrica.getInstancia().getRuteo().rutear(dta, capacidadvehiculo).iterator();
							while(itrut.hasNext())
							{
								DTRuteo next=itrut.next();
								costo=costo+next.getCosto();
							}
						}
						if(costo<costomenor)
						{
							ena.getDeposito().sacarCliente(ena.getCliente());
							candidato.agregarCliente(ena.getCliente());
							costomenor=costo;
							System.out.println("se encontró una mejora en iteracion "+cantidadIteraciones+" nuevo costo ***********"+costomenor);
							break;
						}else System.out.println("el costo dio**** "+costo);
					}
				}
	
			}
			if(costoahora==costomenor)
				terminar=true;
			if(Config.getInstancia().terminarPorConfig(cantidadIteraciones,costomenor))
			{
				terminar=true;
				terminarPorConfig = true;
			}
			Sistema.getInstancia().adelantarPorgresoDeAvance((float)100/this.enagenados.size());
			ArrayList<DTAsignacion> par=new ArrayList<DTAsignacion>();
			
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
				par.add(dta);
			}
			//Sistema.getInstancia().setParciales(par);
			if(!terminar)Sistema.getInstancia().setResaltados(resaltado);
			if(!terminar)Sistema.getInstancia().setMensaje(mensaje);
		
		}
	
		terminar=false;
		while(!terminar && !terminarPorConfig)
		{
			
			System.out.println("lll");
			cantidadIteraciones++;
			this.calcularEnagenamiento();
			// Aplico todos los cambios de la lista de enagenados para ver que pasa.
			int costoahora=costomenor;
			Iterator<Enagenado> itena= this.enagenados.iterator();
			while(itena.hasNext())
			{
				Enagenado ena=itena.next();

				int demanda=ena.getCliente().getNodo().getDemanda();
				Deposito hacerlugar=ena.getDepositoDestino();
				int lugarnecesario=demanda-ena.getDepositoDestino().getCapacidadLibre();
				Cliente candidato=null;
				Iterator<Enagenado> iten=this.enagenados.iterator();
				while(iten.hasNext())
				{
					Enagenado nex=iten.next();
					Iterator<Cliente> itcli=hacerlugar.getAsignados().iterator();
					while(itcli.hasNext())
					{
						Cliente es=itcli.next();
						if(es.getNodo().getId()==nex.getCliente().getNodo().getId()&&es.getNodo().getDemanda()>lugarnecesario)
						{
							candidato=es;
							break;
						}
					}
				}
				if(candidato!=null)
				{
					Iterator<Deposito> itnnn=this.depositos.iterator();
					int masposible=Integer.MAX_VALUE;
					Deposito candidatodep=null;
					while(itnnn.hasNext())
					{
						Deposito depo=itnnn.next();
						if(depo.getNodo().getId()!=ena.getDepositoDestino().getNodo().getId())
						{
							if(depo.getCapacidadLibre()>=candidato.getNodo().getDemanda())
							{
								int dist=Distancia.getInstancia().getDistancia(depo.getNodo(),candidato.getNodo());
								if(dist<masposible)
								{
									candidatodep=depo;
									masposible=dist;
									}
								}
								if(depo.getNodo().getId()==ena.getDeposito().getNodo().getId())
								if((depo.getCapacidadLibre()-ena.getCliente().getNodo().getDemanda())>=candidato.getNodo().getDemanda())
								{
									int dist=Distancia.getInstancia().getDistancia(depo.getNodo(),candidato.getNodo());
									if(dist<masposible)
									{
										candidatodep=depo;
										masposible=dist;
									}
								}
							}
						}
						if(candidatodep!=null)
						{
							Iterator<Deposito> respita=this.depositos.iterator();
							int costo=0;
							while(respita.hasNext())
							{
								Deposito dep=respita.next();
								Deposito nuevo=new Deposito(dep);
								if(nuevo.getNodo().getId()==ena.getDeposito().getNodo().getId())
									nuevo.sacarCliente(ena.getCliente());
								if(nuevo.getNodo().getId()==ena.getDepositoDestino().getNodo().getId())
								{
									nuevo.agregarCliente(ena.getCliente());
									nuevo.sacarCliente(candidato);
								}
								if(nuevo.getNodo().getId()==candidatodep.getNodo().getId())
									nuevo.agregarCliente(candidato);
								
							//	respaldoDep.add(nuevo);
								
								DTAsignacion dta=new DTAsignacion(nuevo.getNodo());
								Iterator<Cliente> itcli=nuevo.getAsignados().iterator();
								while(itcli.hasNext())
								{
									Cliente cli=itcli.next();
									dta.agregarCliente(cli.getNodo());
								}
								Iterator<DTRuteo> itrut=Fabrica.getInstancia().getRuteo().rutear(dta, capacidadvehiculo).iterator();
								while(itrut.hasNext())
								{
									DTRuteo next=itrut.next();
									costo=costo+next.getCosto();
								}
							}
							if(costo<costomenor)
							{
								ena.getDeposito().sacarCliente(ena.getCliente());
								ena.getDepositoDestino().agregarCliente(ena.getCliente());
								candidatodep.agregarCliente(candidato);
								ena.getDepositoDestino().sacarCliente(candidato);
								costomenor=costo;
								mensaje="*2 mejora en iteracion "+cantidadIteraciones+", costo: "+costomenor +" se  pasó al cliente " +ena.getCliente().getNodo().getId()
										+ " desde el depostio "+ena.getDeposito().getNodo().getId()	+" al deposito "+ena.getDepositoDestino().getNodo().getId() +" y para hacer \n lugar se pasó al cliente "+ candidato.getNodo().getId() +" del deposito"
										+ ena.getDepositoDestino().getNodo().getId() +" al deposito "+candidatodep.getNodo().getId(); 
								System.out.println(mensaje);
								resaltado.add(ena.getCliente().getNodo());
								resaltado.add(ena.getDeposito().getNodo());
								resaltado.add(ena.getDepositoDestino().getNodo());
								resaltado.add(candidato.getNodo());
								resaltado.add(candidatodep.getNodo());
								break;
							}else System.out.println("el costo dio**** "+costo);
						}else
						{
							//	int demanda=ena.getCliente().getNodo().getDemanda();
							itnnn=this.depositos.iterator();
							masposible=Integer.MAX_VALUE;
							Deposito candidato2=null;
							while(itnnn.hasNext())
							{
								Deposito depo=itnnn.next();
								if(depo.getNodo().getId()!=ena.getDeposito().getNodo().getId())
									if(depo.getNodo().getId()!=ena.getDepositoDestino().getNodo().getId())
									{
										if(depo.getCapacidadLibre()>=demanda)
										{
											int dist=Distancia.getInstancia().getDistancia(depo.getNodo(),ena.getCliente().getNodo());
											if(dist<masposible)
											{
												candidato2=depo;
												masposible=dist;
											}
										}
									}
							}
							if(candidato2!=null)
							{
								Iterator<Deposito> respita=this.depositos.iterator();
								int costo=0;
								while(respita.hasNext())
								{
									Deposito dep=respita.next();
									Deposito nuevo=new Deposito(dep);
									if(nuevo.getNodo().getId()==ena.getDeposito().getNodo().getId())
										nuevo.sacarCliente(ena.getCliente());
									if(nuevo.getNodo().getId()==candidato2.getNodo().getId())
										nuevo.agregarCliente(ena.getCliente());
								//	respaldoDep.add(nuevo);
									
									DTAsignacion dta=new DTAsignacion(nuevo.getNodo());
									Iterator<Cliente> itcli=nuevo.getAsignados().iterator();
									while(itcli.hasNext())
									{
										Cliente cli=itcli.next();
										dta.agregarCliente(cli.getNodo());
									}
									Iterator<DTRuteo> itrut=Fabrica.getInstancia().getRuteo().rutear(dta, capacidadvehiculo).iterator();
									while(itrut.hasNext())
									{
										DTRuteo next=itrut.next();
										costo=costo+next.getCosto();
									}
								}
								if(costo<costomenor)
								{
									ena.getDeposito().sacarCliente(ena.getCliente());
									candidato2.agregarCliente(ena.getCliente());
									costomenor=costo;
									mensaje="*3 mejora en iteracion "+cantidadIteraciones+", costo: "+costomenor +" se  pasó al cliente " +ena.getCliente().getNodo().getId()
											+ " desde el depostio "+ena.getDeposito().getNodo().getId()+	" al deposito "+candidato2.getNodo().getId();
									System.out.println(mensaje) ;
									resaltado.add(ena.getCliente().getNodo());
									resaltado.add(ena.getDeposito().getNodo());
									resaltado.add(candidato2.getNodo());
	
									resaltado.add(candidato2.getNodo());
									
									break;
								}else System.out.println("el costo dio---- "+costo);
	
						}
						}
						//*******
						
					}
					else
					{
					//	int demanda=ena.getCliente().getNodo().getDemanda();
						Iterator<Deposito> itnnn=this.depositos.iterator();
						int masposible=Integer.MAX_VALUE;
						Deposito candidato2=null;
						while(itnnn.hasNext())
						{
							Deposito depo=itnnn.next();
							if(depo.getNodo().getId()!=ena.getDeposito().getNodo().getId())
								if(depo.getNodo().getId()!=ena.getDepositoDestino().getNodo().getId())
								{
									if(depo.getCapacidadLibre()>=demanda)
									{
										int dist=Distancia.getInstancia().getDistancia(depo.getNodo(),ena.getCliente().getNodo());
										if(dist<masposible)
										{
											candidato2=depo;
											masposible=dist;
										}
									}
								}
						}
						if(candidato2!=null)
						{
							Iterator<Deposito> respita=this.depositos.iterator();
							int costo=0;
							while(respita.hasNext())
							{
								Deposito dep=respita.next();
								Deposito nuevo=new Deposito(dep);
								if(nuevo.getNodo().getId()==ena.getDeposito().getNodo().getId())
									nuevo.sacarCliente(ena.getCliente());
								if(nuevo.getNodo().getId()==candidato2.getNodo().getId())
									nuevo.agregarCliente(ena.getCliente());
							//	respaldoDep.add(nuevo);
								
								DTAsignacion dta=new DTAsignacion(nuevo.getNodo());
								Iterator<Cliente> itcli=nuevo.getAsignados().iterator();
								while(itcli.hasNext())
								{
									Cliente cli=itcli.next();
									dta.agregarCliente(cli.getNodo());
								}
								Iterator<DTRuteo> itrut=Fabrica.getInstancia().getRuteo().rutear(dta, capacidadvehiculo).iterator();
								while(itrut.hasNext())
								{
									DTRuteo next=itrut.next();
									costo=costo+next.getCosto();
								}
							}
							if(costo<costomenor)
							{
								ena.getDeposito().sacarCliente(ena.getCliente());
								candidato2.agregarCliente(ena.getCliente());
								costomenor=costo;
								mensaje="*4 mejora en iteracion "+cantidadIteraciones+", costo: "+costomenor +" se  pasó al cliente " +ena.getCliente().getNodo().getId()
										+ " desde el depostio "+ena.getDeposito().getNodo().getId()+	" al deposito "+candidato2.getNodo().getId();
								System.out.println(mensaje) ;
								resaltado.add(ena.getCliente().getNodo());
								resaltado.add(ena.getDeposito().getNodo());
								resaltado.add(candidato2.getNodo());
	
								resaltado.add(candidato2.getNodo());
								
								break;
							}else System.out.println("el costo dio---- "+costo);
						}
					}
				}
			if(costoahora==costomenor)
				terminar=true;
			if(Config.getInstancia().terminarPorConfig(cantidadIteraciones,costomenor))
			{
				terminar=true;
				terminarPorConfig = true;
			}
			Sistema.getInstancia().adelantarPorgresoDeAvance((float)100/this.enagenados.size());
			ArrayList<DTAsignacion> par=new ArrayList<DTAsignacion>();
			
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
				par.add(dta);
			}
			//Sistema.getInstancia().setParciales(par);

			if(!terminar)Sistema.getInstancia().setResaltados(resaltado);
			if(!terminar)Sistema.getInstancia().setMensaje(mensaje);

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
		return ar;
	}
	
	private void calcularEnagenamiento()
	{
		Iterator<Deposito> itd1=depositos.iterator();
		this.enagenados=new ArrayList<Enagenado>();
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
							this.enagenados.add(new Enagenado(((ClienteCap2)clientebase), dep1, dep2));
							break;
						}
					}
				}
			}	
		}
		
		// limpio lista de enegenados por la distancia al deposito.
		int distCaC1, distCaC2, distDep;
		Iterator<Enagenado> itena= this.enagenados.iterator();
		ArrayList<Enagenado> asacar=new ArrayList<Enagenado>();
		while(itena.hasNext())
		{
			Enagenado ena=itena.next();
			distCaC1 = Distancia.getInstancia().getDistancia(ena.getCliente().getClieteMasCercano1().getNodo(), ena.getCliente().getNodo());
			distCaC2 = Distancia.getInstancia().getDistancia(ena.getCliente().getClieteMasCercano2().getNodo(), ena.getCliente().getNodo());
			distDep =  Distancia.getInstancia().getDistancia(ena.getCliente().getNodo(), ena.getDeposito().getNodo());
			
			//System.out.println("Clientes mas cercanos a " + ena.getCliente().getNodo().getId() + " ( " + ena.getDeposito().getNodo().getId() + " ). c 1 y c2 " + ena.getCliente().getClieteMasCercano1().getNodo().getId() +  " ," 
			//		 +  ena.getCliente().getClieteMasCercano2().getNodo().getId() + "(" +ena.getDepositoDestino().getNodo().getId() + ")" );
			
			
			if (distCaC1 + distCaC2 > distDep)
				asacar.add(ena);//this.enagenados.remove(ena);
			else ena.setMu(distDep-(distCaC1+distCaC2));
		}
		Iterator<Enagenado> itsacar=asacar.iterator();
		while(itsacar.hasNext()){this.enagenados.remove(itsacar.next());}
		this.enagenados=new TreeSet<Enagenado>(this.enagenados);
	}
	
	private int calcularMu(ClienteCap2 c,Collection<Deposito> dep)
	{
		Deposito  masCercano=null;
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
		return sumatoriaDistancias-distanciaMasCercano;
	}
	
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
