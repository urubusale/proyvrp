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
	
	/**
	 * Constructor por defecto.
	 * 
	 */
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
	
	/**
	 * Setea el atributo <code>NAME</code> al <code>DTDepositoVRP</code>.
	 *
	 * @param	n NAME.
	 *  
	 */
	public void setNAME(String n){this.NAME=n;}
	
	/**
	 * Retorna el atributo <code>NAME</code> de <code>DTDepositoVRP</code>. 
	 *
	 * @return      Devuelve el valor del atributo <code>NAME</code> de <code>DTDepositoVRP</code>. 
	 * 
	 */
	public String getNAME(){return this.NAME;}
	
	/**
	 * Setea el atributo <code>COMMENT</code> al <code>DTDepositoVRP</code>.
	 *
	 * @param	n COMMENT.
	 *  
	 */
	public void setCOMMENT(String n){this.COMMENT=n;}
	
	/**
	 * Retorna el atributo <code>COMMENT</code> de <code>DTDepositoVRP</code>. 
	 *
	 * @return      Devuelve el valor del atributo <code>COMMENT</code> de <code>DTDepositoVRP</code>. 
	 * 
	 */
	public String getCOMMENT(){return this.COMMENT;}
	
	/**
	 * Setea el atributo <code>DIMENSION</code> al <code>DTDepositoVRP</code>.
	 *
	 * @param	n DIMENSION.
	 *  
	 */
	public void setDIMENSION(String n){this.DIMENSION=n;}
	
	/**
	 * Retorna el atributo <code>DIMENSION</code> de <code>DTDepositoVRP</code>. 
	 *
	 * @return      Devuelve el valor del atributo <code>DIMENSION</code> de <code>DTDepositoVRP</code>. 
	 * 
	 */
	public String getDIMENSION(){return this.DIMENSION;}
	
	/**
	 * Setea el atributo <code>TYPE</code> al <code>DTDepositoVRP</code>.
	 *
	 * @param	n TYPE.
	 *  
	 */
	public void setTYPE(String n){this.TYPE=n;}
	
	/**
	 * Retorna el atributo <code>TYPE</code> de <code>DTDepositoVRP</code>. 
	 *
	 * @return      Devuelve el valor del atributo <code>TYPE</code> de <code>DTDepositoVRP</code>. 
	 * 
	 */
	public String getTYPE(){return this.TYPE;}
	
	/**
	 * Setea el atributo <code>EDGE_WEIGHT_TYPE</code> al <code>DTDepositoVRP</code>.
	 *
	 * @param	n EDGE_WEIGHT_TYPE.
	 *  
	 */
	public void setEDGE_WEIGHT_TYPE(String n){this.EDGE_WEIGHT_TYPE=n;}
	
	/**
	 * Retorna el atributo <code>EDGE_WEIGHT_TYPE</code> de <code>DTDepositoVRP</code>. 
	 *
	 * @return      Devuelve el valor del atributo <code>EDGE_WEIGHT_TYPE</code> de <code>DTDepositoVRP</code>. 
	 * 
	 */
	public String getEDGE_WEIGHT_TYPE(){return this.EDGE_WEIGHT_TYPE;}
	
	/**
	 * Setea el atributo <code>EDGE_WEIGHT_FORMAT</code> al <code>DTDepositoVRP</code>.
	 *
	 * @param	n EDGE_WEIGHT_FORMAT.
	 *  
	 */
	public void setEDGE_WEIGHT_FORMAT(String n){this.EDGE_WEIGHT_FORMAT=n;}
	
	/**
	 * Retorna el atributo <code>EDGE_WEIGHT_FORMAT</code> de <code>DTDepositoVRP</code>. 
	 *
	 * @return      Devuelve el valor del atributo <code>EDGE_WEIGHT_FORMAT</code> de <code>DTDepositoVRP</code>. 
	 * 
	 */
	public String getEDGE_WEIGHT_FORMAT(){return this.EDGE_WEIGHT_FORMAT;}
	
	/**
	 * Setea el atributo <code>DISPLAY_DATA_TYPE</code> al <code>DTDepositoVRP</code>.
	 *
	 * @param	n DISPLAY_DATA_TYPE.
	 *  
	 */
	public void setDISPLAY_DATA_TYPE(String n){this.DISPLAY_DATA_TYPE=n;}
	
	/**
	 * Retorna el atributo <code>DISPLAY_DATA_TYPE</code> de <code>DTDepositoVRP</code>. 
	 *
	 * @return      Devuelve el valor del atributo <code>DISPLAY_DATA_TYPE</code> de <code>DTDepositoVRP</code>. 
	 * 
	 */
	public String getDISPLAY_DATA_TYPE(){return this.DISPLAY_DATA_TYPE;}
	
	/**
	 * Setea el atributo <code>CAPACITY</code> al <code>DTDepositoVRP</code>.
	 *
	 * @param	n CAPACITY.
	 *  
	 */
	public void setCAPACITY(String n){this.CAPACITY=n;}
	
	/**
	 * Retorna el atributo <code>CAPACITY</code> de <code>DTDepositoVRP</code>. 
	 *
	 * @return      Devuelve el valor del atributo <code>CAPACITY</code> de <code>DTDepositoVRP</code>. 
	 * 
	 */
	public String getCAPACITY(){return this.CAPACITY;}
	
	/**
	 * Setea la colección de nodos al <code>DTDepositoVRP</code>.
	 *
	 * @param	d Colección de nodos.
	 *  
	 */
	public void setNodos(Collection<DTNodo> d){this.nodos=d;}
	
	/**
	 * Retorna la colección de nodos de <code>DTDepositoVRP</code>. 
	 *
	 * @return      Devuelve el valor de la colección de nodos de <code>DTDepositoVRP</code>. 
	 * 
	 */
	public Collection<DTNodo> getNodos(){return this.nodos;}
	
}
