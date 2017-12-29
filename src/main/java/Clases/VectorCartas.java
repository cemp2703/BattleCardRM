package Clases;

import Vistas.JCarta;

public class VectorCartas implements Cloneable{
	
	private JCarta carta[];
	private int MaxNCartas;
	
	public VectorCartas(int ncartas){
		carta=new JCarta[ncartas];
		MaxNCartas=ncartas;
	}
	
	public VectorCartas(int pMaxNCartas,JCarta pcarta[]){
		MaxNCartas=pMaxNCartas;
		carta=pcarta;
	}

	public VectorCartas clone(){
		
		JCarta v[]=new JCarta[MaxNCartas];
		
		for(int i=0; i<MaxNCartas;i++){
			v[i]=this.carta[i];
		}
		
		VectorCartas clon=new VectorCartas(this.MaxNCartas,v);
		return clon;
	}
	
	public int obtenerNumerodeCartas(){
		int n=0;
		for(int i=0;i<MaxNCartas;i++){
			if(carta[i]!=null){
				if(carta[i].carta!=null)
					n++;
			}
		}
		return n;
	}
	
	public int agregarCartaEnEspacioVacio(Carta c){
		if(obtenerNumerodeCartas()==MaxNCartas)
			return -1;
		
		int i=0;
		while(true){
			if(carta[i] != null){
				if(carta[i].carta == null){
					carta[i].carta = c;
					return i;
				}
			}
			i++;
		}
	}

	public int agregarJCartaEnEspacioVacio(JCarta c){
		if(obtenerNumerodeCartas()==MaxNCartas)
			return -1;

		int i=0;
		while(true){
			if(carta[i]==null){
				carta[i]=c;
				return i;
			}
			i++;
		}
	}


	public boolean agregarCartaEnPos(Carta c,int id){
		if(id<0 || id>=MaxNCartas)
			return false;
		if(carta[id]!=null) {
			carta[id].carta = c;
			return true;
		}
		else
			return false;

	}
	
	public Carta obtenerCartaxId(int id){
		if(id<0 || id>=MaxNCartas)
			return null;
		if(carta[id]!=null)
			return carta[id].carta;
		return null;
	}

	public JCarta obtenerJCartaxId(int id){
		if(id<0 || id>=MaxNCartas)
			return null;
		return carta[id];
	}


	public boolean quitarCartaenPos(int id){
		if(id<0 || id>=MaxNCartas)
			return false;
		if (carta[id] != null) {
			carta[id].carta=null;
			return true;
		}
		return false;
	} 

	public int quitarUltimaCartaDisponible(){
		for(int i=MaxNCartas-1;i>=0;i--){
			if(carta[i]!=null){
				if(carta[i].carta != null)
					carta[i].carta=null;
				return i;
			}
		}
		return -1;//no hay carta disponible que quitar
	}
}
