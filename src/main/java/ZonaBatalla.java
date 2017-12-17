public class ZonaBatalla implements Cloneable{
	
	public static final int MAXZONABATALLACARDS = 3;
	
	public VectorCartas Cartas;
	public int poscarta[]; //0: No hay carta, 1: Ataque, 2: Defensa boca arriba, 3: Defensa boca abajo).
	public int dispataque[]; //0: No Disponible, 1: Disponible para atacar
	public int dispcambio[]; //0: No Disponible, 1: Disponible para cambiar de posicion
	public boolean cartacolocada;
	
	public ZonaBatalla() {
		super();
		
		Cartas=new VectorCartas(MAXZONABATALLACARDS);
		poscarta=new int[MAXZONABATALLACARDS];
		dispataque=new int[MAXZONABATALLACARDS];
		dispcambio=new int[MAXZONABATALLACARDS];
		
		for(int i=0;i<MAXZONABATALLACARDS;i++){
			poscarta[i]=0;
			dispataque[i]=0;
			dispcambio[i]=0;
		}
		cartacolocada=false;
	}
	
	public ZonaBatalla(VectorCartas pVectorCartas,int pposcarta[],int pdispataque[],int pdispcambio[],boolean pcartacolocada){
		this.Cartas=pVectorCartas;
		this.poscarta=pposcarta;
		this.dispataque=pdispataque;
		this.dispcambio=pdispcambio;
		this.cartacolocada=pcartacolocada;
	}
	
	
	public ZonaBatalla clone(){
		VectorCartas vc=this.Cartas.clone();
		int posc[]=new int[MAXZONABATALLACARDS];
		int dataque[]=new int[MAXZONABATALLACARDS];
		int dcambio[]=new int[MAXZONABATALLACARDS];
		
		for(int i=0;i<MAXZONABATALLACARDS;i++){
			posc[i]=poscarta[i];
			dataque[i]=dispataque[i];
			dcambio[i]=dispcambio[i];
		}
		
		ZonaBatalla clon=new ZonaBatalla(vc,posc,dataque,dcambio,this.cartacolocada);
		return clon;
	}   
	
	
	public void renovarDisponibilidades(){
		for(int i=0;i<MAXZONABATALLACARDS;i++){
			if(Cartas.obtenerCartaxId(i)!=null){
				dispataque[i]=1;
				dispcambio[i]=1;
			}
		}
		cartacolocada=false;
	}

	public boolean isCartacolocada() {
		return cartacolocada;
	}

	public void setCartacolocada(boolean cartacolocada) {
		this.cartacolocada = cartacolocada;
	}
	
	public int getPosCartaxId(int id){
		if(id>=0 && id < MAXZONABATALLACARDS ){
			return poscarta[id];			
		}
		else
			return 0;
	}
}


