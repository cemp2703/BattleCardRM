package Clases;

import java.util.Arrays;

public class VectorCartas implements Cloneable{

	public final static int NOCARTASDISPONIBLES = -1;
	public final static int NOSEPUEDEAGREGARCARTAS = -1;

	public Carta carta[];
	private int MaxNCartas;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof VectorCartas)) return false;
		VectorCartas that = (VectorCartas) o;
		return MaxNCartas == that.MaxNCartas &&
				Arrays.equals(carta, that.carta);
	}

	public VectorCartas(int ncartas){
		carta=new Carta[ncartas];
		MaxNCartas=ncartas;
	}

	public Object clone() throws CloneNotSupportedException{
		VectorCartas clon=(VectorCartas)super.clone();


		Carta c[]=new Carta[MaxNCartas];

		for(int i=0; i<MaxNCartas;i++){
			if(carta[i] != null)
			c[i]=(Carta)carta[i].clone();
		}


		clon.carta=c;
		return clon;
	}

	//region Operaciones

	public int obtenerNumerodeCartas(){
		int n=0;
		for(int i=0;i<MaxNCartas;i++){
			if(carta[i]!=null){
					n++;
			}
		}
		return n;
	}
	
	public int agregarCartaEnEspacioVacio(Carta c){
		if(obtenerNumerodeCartas()==MaxNCartas)
			return NOSEPUEDEAGREGARCARTAS;

		for(int i=0;i< MaxNCartas;i++){
			if(agregarCartaEnPos(c,i))
				return i;
		}
		return NOSEPUEDEAGREGARCARTAS;
	}

	public boolean agregarCartaEnPos(Carta c,int id){
		if(id<0 || id>=MaxNCartas)
			return false;
		if(carta[id] == null) {
			carta[id]= c;
			return true;
		}
		else
			return false;

	}
	
	public Carta obtenerCartaxId(int id){
		if(id<0 || id>=MaxNCartas)
			return null;
		if(carta[id]!=null)
			return carta[id];
		return null;
	}

	public boolean quitarCartaenPos(int id){
		if(id<0 || id>=MaxNCartas)
			return false;
		if (carta[id] != null) {
			carta[id]=null;
			return true;
		}
		return false;
	} 

	public int quitarUltimaCartaDisponible(){
		for(int i=MaxNCartas-1;i>=0;i--){
			if(carta[i]!=null){
				if(carta[i] != null) {
					carta[i] = null;
					return i;
				}
			}
		}
		return NOCARTASDISPONIBLES;//no hay carta disponible que quitar
	}

	//endregion

	public int getMaxNCartas() {
		return MaxNCartas;
	}

	public void setMaxNCartas(int maxNCartas) {
		MaxNCartas = maxNCartas;
	}
}
