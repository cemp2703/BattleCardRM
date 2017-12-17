public class VectorCartas implements Cloneable{
	
	private Carta carta[];
	private int MaxNCartas;
	
	public VectorCartas(int ncartas){
		carta=new Carta[ncartas];
		MaxNCartas=ncartas;
	}
	
	public VectorCartas(int pMaxNCartas,Carta pcarta[]){
		MaxNCartas=pMaxNCartas;
		carta=pcarta;
	}

	public VectorCartas clone(){
		
		Carta v[]=new Carta[MaxNCartas];
		
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
		carta[id]=c;
		
		return true;
	}
	
	
	public Carta obtenerCartaxId(int id){
		if(id<0 || id>=MaxNCartas)
			return null;
		return carta[id];
		
	}
	
	public boolean quitarCartaenPos(int id){
		if(id<0 || id>=MaxNCartas)
			return false;
		
		carta[id]=null;
		return true;
	} 
	
	public int quitarUltimaCartaDisponible(){
		for(int i=MaxNCartas-1;i>=0;i--){
			if(carta[i]!=null){
				carta[i]=null;
				return i;
			}
		}
		return -1;//no hay carta disponible que quitar
	}
	
	public boolean existeCartaConId(int id){
		if(id<0 || id>=MaxNCartas)
			return false;
		if(carta[id]!=null){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public int obtenerIdEspacioVacioCartas(){
		
		for(int i=0;i<MaxNCartas;i++){
			if(carta[i]==null){
				return i;
			}
		}
		return -1;
	}
	
	//para el estado
	public int obtenerValorDeCartaMayor(){
		int max = 0;
		for(int i=0;i<MaxNCartas;i++){
			if(carta[i] != null )
				if(	carta[i].getValor() > max )
					max = carta[i].getValor();
		}
		return max;
	}
	

}
