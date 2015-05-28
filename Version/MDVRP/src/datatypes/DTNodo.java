package datatypes;
public class DTNodo 
{
	private int x;
	private int y;
	private int demanda;
	private boolean esDeposito;
	private int id;
	public DTNodo(int id)
	{
		this.id=id;
		esDeposito=false;
		demanda=0;
	}
	public DTNodo(DTNodo n)
	{
		this.id=n.getId();
		esDeposito=n.getEsDesposito();
		demanda=n.getDemanda();
		x=n.getX();
		y=n.getY();
	}
	public int getId(){return this.id;}
	public void setX(int x){this.x=x;}
	public void setY(int y){this.y=y;}
	public void setDemanda(int x){this.demanda=x;}
	public int getX(){return x;}
	public int getY(){return y;}
	public int getDemanda(){return demanda;}
	public void setEsDeposito(boolean d){this.esDeposito=d;}
	public boolean getEsDesposito(){return this.esDeposito;}
}
