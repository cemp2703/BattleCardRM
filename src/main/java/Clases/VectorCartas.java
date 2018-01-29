package Clases;

import Vistas.JCarta;

public class VectorCartas implements Cloneable{
	
	private JCarta jcarta[];
	private int MaxNCartas;

	public final static int NOCARTASDISPONIBLES = -1;

	public VectorCartas(int ncartas){
		jcarta=new JCarta[ncartas];
		MaxNCartas=ncartas;
		for(int i=0;i<ncartas;i++){
			jcarta[i]=new JCarta(i,0);
		}
	}
	
	public VectorCartas(int pMaxNCartas,JCarta pjcarta[]){
		MaxNCartas=pMaxNCartas;
		jcarta = pjcarta;
	}

	public VectorCartas clone(){
		
		JCarta v[]=new JCarta[MaxNCartas];
		
		for(int i=0; i<MaxNCartas;i++){
			v[i]=this.jcarta[i].clone();
		}
		
		VectorCartas clon=new VectorCartas(this.MaxNCartas,v);
		return clon;
	}
	
	public int obtenerNumerodeCartas(){
		int n=0;
		for(int i=0;i<MaxNCartas;i++){
			if(jcarta[i]!=null){
				if(jcarta[i].carta!=null)
					n++;
			}
		}
		return n;
	}
	
	public int agregarCartaEnEspacioVacio(Carta c){
		if(obtenerNumerodeCartas()==MaxNCartas)
			return -1;

		for(int i=0;i< MaxNCartas;i++){
			if(jcarta[i] != null){
				if(jcarta[i].carta == null){
					jcarta[i].carta = c;
					return i;
				}
			}
		}
		return -1;
	}

	public boolean agregarCartaEnPos(Carta c,int id){
		if(id<0 || id>=MaxNCartas)
			return false;
		if(jcarta[id]!=null) {
			jcarta[id].carta = c;
			return true;
		}
		else
			return false;

	}
	
	public Carta obtenerCartaxId(int id){
		if(id<0 || id>=MaxNCartas)
			return null;
		if(jcarta[id]!=null)
			return jcarta[id].carta;
		return null;
	}

	public boolean quitarCartaenPos(int id){
		if(id<0 || id>=MaxNCartas)
			return false;
		if (jcarta[id] != null) {
			jcarta[id].carta=null;
			return true;
		}
		return false;
	} 

	public int quitarUltimaCartaDisponible(){
		for(int i=MaxNCartas-1;i>=0;i--){
			if(jcarta[i]!=null){
				if(jcarta[i].carta != null) {
					jcarta[i].carta = null;
					return i;
				}
			}
		}
		return NOCARTASDISPONIBLES;//no hay carta disponible que quitar
	}

	public JCarta obtenerJCartaxId(int id){
		if(id<0 || id>=MaxNCartas)
			return null;
		return jcarta[id];
	}
}
