package datatypes;
import java.util.ArrayList;
import java.util.Collection;

public class DTDepositoVRP 
{
	private String NAME;
	private String COMMENT;
	private String DIMENSION;
	private String TYPE;
	private String EDGE_WEIGHT_TYPE;
	private String EDGE_WEIGHT_FORMAT;
	private String DISPLAY_DATA_TYPE;
	private String CAPACITY;
	private Collection<DTNodo> nodos;
	public DTDepositoVRP()
	{
		NAME="";
		COMMENT="";
		DIMENSION="";
		TYPE="";
		EDGE_WEIGHT_TYPE="";
		EDGE_WEIGHT_FORMAT="";
		DISPLAY_DATA_TYPE="";
		CAPACITY="";
		nodos=new ArrayList<DTNodo>();
	}
	public void setNAME(String n){this.NAME=n;}
	public String getNAME(){return this.NAME;}
	
	public void setCOMMENT(String n){this.COMMENT=n;}
	public String getCOMMENT(){return this.COMMENT;}
	
	public void setDIMENSION(String n){this.DIMENSION=n;}
	public String getDIMENSION(){return this.DIMENSION;}
	
	public void setTYPE(String n){this.TYPE=n;}
	public String getTYPE(){return this.TYPE;}
	
	public void setEDGE_WEIGHT_TYPE(String n){this.EDGE_WEIGHT_TYPE=n;}
	public String getEDGE_WEIGHT_TYPE(){return this.EDGE_WEIGHT_TYPE;}
	
	public void setEDGE_WEIGHT_FORMAT(String n){this.EDGE_WEIGHT_FORMAT=n;}
	public String getEDGE_WEIGHT_FORMAT(){return this.EDGE_WEIGHT_FORMAT;}
	
	public void setDISPLAY_DATA_TYPE(String n){this.DISPLAY_DATA_TYPE=n;}
	public String getDISPLAY_DATA_TYPE(){return this.DISPLAY_DATA_TYPE;}
	
	public void setCAPACITY(String n){this.CAPACITY=n;}
	public String getCAPACITY(){return this.CAPACITY;}
	
	public void setNodos(Collection<DTNodo> d){this.nodos=d;}
	public Collection<DTNodo> getNodos(){return this.nodos;}
	
}
