package bdatos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.ArrayList;
public class ControladorArchivo 
{
	static private ControladorArchivo instancia=null;
	
	/**
	 * Crea una nueva instancia de la clase que representa este objeto. 
	 * La clase se instancia como por una nueva expresión con una lista de argumentos vacía. 
	 * La clase se inicializa si ya no se ha inicializado. 
	 * 
	 * @return      Una nueva instancia de la clase
	 */
	static public ControladorArchivo getInstancia()
	{
		if (instancia==null)  
		{
			instancia= new ControladorArchivo();
			return instancia;
		}
		else return instancia;
	}
	
	/** 
	 * Constructor por defecto.
	 * 
	 */
	private ControladorArchivo()
	{
	}

	/** 
	 * Lee las lineas que contiene el archivo de depositos de VRP.
	 * 
	 * @param	path Path donde se encuentra el archivo.
	 * @return      Devuelve la colección de lineas que contiene el archivo.
	 * 
	 */
	public Collection<String> leerArchivoDepositoVRP(String path)
	{
		ArrayList<String> ar=new ArrayList<String>();
		BufferedReader entrada;
		try
		{
			File f = new File(path);
			entrada = new BufferedReader( new FileReader( f ) );
			boolean seguir=true;
			if (f.exists() )
			{
				while(seguir)
				{
					String linea=entrada.readLine();
					if(linea!=null)
					{
						if(linea.compareTo("EOF")==0) seguir=false;
						else 
						{
							System.out.println(linea);
							ar.add(linea);
						}
					}
					else seguir=false;
				}
			}
			entrada.close();
		}
		catch(Exception ex){ex.printStackTrace();}
    	return ar;
	}


}
