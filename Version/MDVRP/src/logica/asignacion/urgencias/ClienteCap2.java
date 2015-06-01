package logica.asignacion.urgencias;

import datatypes.DTNodo;

public class ClienteCap2 extends Cliente implements Comparable {

		private ClienteCap2 clieteMasCercano1;
		private ClienteCap2 clieteMasCercano2;		
		public ClienteCap2(DTNodo d)
		{
			 super(d);
		}
		
		public ClienteCap2 getClieteMasCercano1() {
			return clieteMasCercano1;
		}
		public void setClieteMasCercano1(ClienteCap2 clieteMasCercano1) {
			this.clieteMasCercano1 = clieteMasCercano1;
		}
		public ClienteCap2 getClieteMasCercano2() {
			return clieteMasCercano2;
		}
		public void setClieteMasCercano2(ClienteCap2 clieteMasCercano2) {
			this.clieteMasCercano2 = clieteMasCercano2;
		}
		

}
