public class Estado implements Cloneable {
	
	public Jugador Jugador1;
	public Jugador Jugador2;//Aveces se comporta como maquina
	private int Turno;//1: Jugador 1, 2: Jugador 
	private double ValorEstado;
	private Integer Termino;

	public Estado(Jugador pJugador2,Jugador pJugador1,double pValorEstado,int pturno,int pTermino){
		Jugador2=pJugador2;
		Jugador1=pJugador1;
		ValorEstado = pValorEstado;
		Turno=pturno;
		Termino=pTermino;
	}

	public Estado clone(){
		Jugador JH=this.Jugador1.clone();
		Jugador JM=this.Jugador2.clone();
		Estado clon=new Estado(JM,JH,this.ValorEstado,this.Turno,this.Termino);
		return clon;
	}
	
	public void funcionEvaluadora()
	{	////MAQUINA ES JUGADOR 2 
		double hum=0,maq=0;
		int nMaq = Jugador2.ZonaBatalla.Cartas.obtenerNumerodeCartas();
		int nHum = Jugador1.ZonaBatalla.Cartas.obtenerNumerodeCartas();
		
		int barreraHum = Jugador1.Barrera.Cartas.obtenerNumerodeCartas();
	
		//arreglo de una los valores que tiene una carta al enfrentarse a c/u de las oponentes
		double CartasMaq[];
		CartasMaq = new double[3]; //LENAR CON 0
		double CartasHum[];
		CartasHum = new double[3]; //LLENAR CON 0
		
		for (int i = 0; i < 3; i++) {
			CartasMaq[i] = 0;
			CartasHum[i] = 0;
		}
		//variables que se usan para llegar al arreglo
		int valCarMaq, valCarHum;
		int estCarMaq, estCarHum;
		
		//VALOR DE MAQ
		for (int i = 0; i < ZonaBatalla.MAXZONABATALLACARDS; i++) {
			if(Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i)!=null){
				valCarMaq=Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getValor();
				estCarMaq=Jugador2.ZonaBatalla.getPosCartaxId(i);
				if(nHum==0)
					CartasMaq[i] = valCarMaq * 2;
				for(int j = 0; j < ZonaBatalla.MAXZONABATALLACARDS; j++){
					if(Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(j)!=null){
						valCarHum=Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(j).getValor();
						estCarHum=Jugador1.ZonaBatalla.getPosCartaxId(j);
						CartasMaq[i] = evalValorDeCartaParcMAQ(valCarMaq, valCarHum, estCarMaq, estCarHum);					
					}
				}
				maq = maq + evalValorDeCarta(CartasMaq,nMaq);//mandar arreglo
			}
		}	
		
		//VALOR DE HUM
		for (int i = 0; i < ZonaBatalla.MAXZONABATALLACARDS; i++) {
			if(Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(i)!=null){
				valCarHum=Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(i).getValor();
				estCarHum=Jugador1.ZonaBatalla.getPosCartaxId(i);
				if(nMaq==0)
					CartasHum[i] = valCarHum * 2;
				for(int j = 0; j < ZonaBatalla.MAXZONABATALLACARDS; j++){
					if(Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(j)!=null){
						valCarMaq=Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(j).getValor();
						estCarMaq=Jugador2.ZonaBatalla.getPosCartaxId(j);
						CartasHum[i] = evalValorDeCartaParcHUM(valCarMaq, valCarHum, estCarMaq, estCarHum);
					}					
				}
				hum = hum + evalValorDeCarta(CartasHum, nHum);//mandar arreglo
			}
		}
		ValorEstado = (maq) - (hum) - (5*barreraHum);
	}
	
	private double evalValorDeCartaParcMAQ(int vCM, int vCH, int eCM, int eCH ){
		//pos carta -> 0: No hay carta, 1: Ataque, 2: Def arriba, 3: Def abajo).
				
		//Si carta enemiga esta boca abajo en def
		if(eCH==3)
			return vCM;
		//Si carta maq en def
		if(eCM == 2 || eCM ==3){
			if(vCM > vCH)
				return vCM * 1.5;
			if(vCM == vCH)
				return vCM * 1.25;
			if(vCM < vCH)
				return vCM * 0.5;			
		}
		//Si carta maq en atk
		if(eCM == 1){
			if(vCM > vCH)
				return vCM * 2;
			if(vCM == vCH)
				return vCM * 0.75;
			if(vCM < vCH)
				return vCM * 0;			
		}
		return -1;
	}
	
	private double evalValorDeCartaParcHUM(int vCM, int vCH, int eCM, int eCH ){
		//pos carta -> 0: No hay carta, 1: Ataque, 2: Def arriba, 3: Def abajo).
		int valCarHumDefBocAbajo = 7;
		
		//Si carta enemiga esta boca abajo en def
		if(eCH==3)
			return valCarHumDefBocAbajo;
		//Si carta maq en def
		if(eCM == 2 || eCM ==3){
			if(vCH > vCM)
				return vCH * 1.5;
			if(vCH == vCM)
				return vCH * 0.75;
			if(vCH < vCM)
				return vCH * 0.5;			
		}
		//Si carta maq en atk
		if(eCM == 1){
			if(vCH > vCM)
				return vCH * 2;
			if(vCH == vCM)
				return vCH * 1.25;
			if(vCH < vCM)
				return vCH * 0;			
		}
		
		return -1;		
	}
	
	private double evalValorDeCarta(double arr[],int nCar){
		//con el arreglo devolver el valor promedio final que tendra la carta
		
		double sum = 0;
		for (int i = 0; i < arr.length; i++) 
			sum = sum + arr[i];			
		
		return (sum/nCar);
	}

	public double getValorEstado() {
		return ValorEstado;
	}

	public void setValorEstado(double valorEstado) {
		ValorEstado = valorEstado;
	}
	public int getTurno() {
		return Turno;
	}
	public void setTurno(int turno) {
		Turno = turno;
	}
	public Integer getTermino() {
		return Termino;
	}
	public void setTermino(Integer termino) {
		Termino = termino;
	}
	
	void imprimeEstado(){
		System.out.println("*****************************************************************************");
		System.out.println("Turno: "+Turno);
		System.out.println("Jugador 1");
		System.out.println("Mano");
		for(int i=0;i<Mano.MAXMANOCARDS;i++){
			if(Jugador1.Mano.Cartas.obtenerCartaxId(i)!=null)
			System.out.print(i+" - "+Jugador1.Mano.Cartas.obtenerCartaxId(i).getElemento()+" "+Jugador1.Mano.Cartas.obtenerCartaxId(i).getValor()+" | ");
			else
				System.out.print("VACIO | ");
		}
		System.out.println();
		System.out.println("Barrera");
		for(int i=0;i<Barrera.MAXBARRERACARDS;i++){
			if(Jugador1.Barrera.Cartas.obtenerCartaxId(i)!=null)
				System.out.print(i+" - "+Jugador1.Barrera.Cartas.obtenerCartaxId(i).getElemento()+" | ");
			else
				System.out.print("VACIO | ");
		}
		System.out.println();
		System.out.println("ZonaBatalla");
		for(int i=0;i<ZonaBatalla.MAXZONABATALLACARDS;i++){
			if(Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(i)!=null)
			System.out.print(i+" - "+Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(i).getElemento()+" "+Jugador1.ZonaBatalla.Cartas.obtenerCartaxId(i).getValor()+" "
					+Jugador1.ZonaBatalla.poscarta[i]+ " "+Jugador1.ZonaBatalla.dispataque[i]+" "+Jugador1.ZonaBatalla.dispcambio[i]+" | ");
			else
				System.out.print("VACIO | ");
		}
		System.out.println();
		
		
		System.out.println("Jugador 2");
		System.out.println("Mano");
		for(int i=0;i<Mano.MAXMANOCARDS;i++){
			if(Jugador2.Mano.Cartas.obtenerCartaxId(i)!=null)
			System.out.print(i+" - "+Jugador2.Mano.Cartas.obtenerCartaxId(i).getElemento()+" "+Jugador2.Mano.Cartas.obtenerCartaxId(i).getValor()+" | ");
			else
				System.out.print("VACIO | ");
		}
		System.out.println();
		System.out.println("Barrera");
		for(int i=0;i<Barrera.MAXBARRERACARDS;i++){
			if(Jugador2.Barrera.Cartas.obtenerCartaxId(i)!=null)
			System.out.print(i+" - "+Jugador2.Barrera.Cartas.obtenerCartaxId(i).getElemento()+" | ");
			else
				System.out.print("VACIO | ");
		}
		System.out.println();
		System.out.println("ZonaBatalla");
		for(int i=0;i<ZonaBatalla.MAXZONABATALLACARDS;i++){
			if(Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i)!=null)
			System.out.print(i+" - "+Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getElemento()+" "+Jugador2.ZonaBatalla.Cartas.obtenerCartaxId(i).getValor()+" "
					+Jugador2.ZonaBatalla.poscarta[i]+ " "+Jugador2.ZonaBatalla.dispataque[i]+" "+Jugador2.ZonaBatalla.dispcambio[i]+" | ");
			else
				System.out.print("VACIO | ");
		}
		System.out.println();
	}
}
