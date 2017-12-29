package Clases;

public class Estado implements Cloneable {
	
	public Jugador Jugador1;
	public Jugador Jugador2;//Aveces se comporta como maquina
	private int Turno;
	public static class TURNO {
		public final static int JUGADOR1 = 1; //Jugador 1
		public final static int JUGADOR2BOT = 2;  //Jugador 2 o BOT
	}
	private double Estado;
	public Integer Termino; //Reglas que finalizan el juego
	public static class TERMINO {
		public final static int NOTERMINO = 0; //El juego aun no llega a una condicion de victoria/derrota
		public final static int SINCARTAS = 1;  //Jugador actual se quedo sin cartas
		public final static int SINBARRERAS = 2;  //Jugador Anterior perdió todas sus barreras
	}

	public Estado(Jugador pJugador1,Jugador pJugador2,double pEstado,int pturno,int pTermino){
		Jugador2=pJugador2;
		Jugador1=pJugador1;
		Estado = pEstado;
		Turno=pturno;
		Termino=pTermino;
	}

	public Estado clone(){
		Jugador JH=this.Jugador1.clone();
		Jugador JM=this.Jugador2.clone();
		Estado clon=new Estado(JM,JH,this.Estado,this.Turno,this.Termino);
		return clon;
	}

	public double getValorEstado() {
		return Estado;
	}
	public void setValorEstado(double pEstado) {
		Estado = pEstado;
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

	public void funcionEvaluadora()
	{	////MAQUINA ES JUGADOR 2
		double hum=0,maq=0;
		int nMaq = Jugador2.ZonaBatalla.obtenerNumerodeCartas();
		int nHum = Jugador1.ZonaBatalla.obtenerNumerodeCartas();
		
		int barreraHum = Jugador1.Barrera.obtenerNumerodeCartas();
	
		//arreglo de una los valores que tiene una carta al enfrentarse a c/u de las oponentes
		double CartasMaq[];
		CartasMaq = new double[ZonaBatalla.MAXZONABATALLACARDS]; //LENAR CON 0
		double CartasHum[];
		CartasHum = new double[ZonaBatalla.MAXZONABATALLACARDS]; //LLENAR CON 0
		
		for (int i = 0; i < ZonaBatalla.MAXZONABATALLACARDS; i++) {
			CartasMaq[i] = 0;
			CartasHum[i] = 0;
		}
		//variables que se usan para llegar al arreglo
		int valCarMaq, valCarHum;
		int estCarMaq, estCarHum;
		
		//VALOR DE MAQ
		for (int i = 0; i < ZonaBatalla.MAXZONABATALLACARDS; i++) {
			if(Jugador2.ZonaBatalla.obtenerCartaxId(i)!=null){
				valCarMaq=Jugador2.ZonaBatalla.obtenerCartaxId(i).getValor();
				estCarMaq=Jugador2.ZonaBatalla.getPosCartaxId(i);
				if(nHum==0)
					CartasMaq[i] = valCarMaq * 2;
				for(int j = 0; j < ZonaBatalla.MAXZONABATALLACARDS; j++){
					if(Jugador1.ZonaBatalla.obtenerCartaxId(j)!=null){
						valCarHum=Jugador1.ZonaBatalla.obtenerCartaxId(j).getValor();
						estCarHum=Jugador1.ZonaBatalla.getPosCartaxId(j);
						CartasMaq[i] = evalValorDeCartaParcMAQ(valCarMaq, valCarHum, estCarMaq, estCarHum);					
					}
				}
				maq = maq + evalValorDeCarta(CartasMaq,nMaq);//mandar arreglo
			}
		}	
		
		//VALOR DE HUM
		for (int i = 0; i < ZonaBatalla.MAXZONABATALLACARDS; i++) {
			if(Jugador1.ZonaBatalla.obtenerCartaxId(i)!=null){
				valCarHum=Jugador1.ZonaBatalla.obtenerCartaxId(i).getValor();
				estCarHum=Jugador1.ZonaBatalla.getPosCartaxId(i);
				if(nMaq==0)
					CartasHum[i] = valCarHum * 2;
				for(int j = 0; j < ZonaBatalla.MAXZONABATALLACARDS; j++){
					if(Jugador2.ZonaBatalla.obtenerCartaxId(j)!=null){
						valCarMaq=Jugador2.ZonaBatalla.obtenerCartaxId(j).getValor();
						estCarMaq=Jugador2.ZonaBatalla.getPosCartaxId(j);
						CartasHum[i] = evalValorDeCartaParcHUM(valCarMaq, valCarHum, estCarMaq, estCarHum);
					}					
				}
				hum = hum + evalValorDeCarta(CartasHum, nHum);//mandar arreglo
			}
		}
		Estado = (maq) - (hum) - (5*barreraHum);
	}

	public static class FACTORBOT {
		public final static double DEFDERROTA = 0.5; //factores al cambiar una carta defenza a ataque y luego disponerse a atacar a una carta enemiga
		public final static double DEFEMPATE = 1.25;
		public final static double DEFVICTORIA = 1.5;
		public final static double ATKDERROTA = 0; //factores al atacar con una carta en modo de ataque
		public final static double ATKEMPATE = 0.75;
		public final static double ATKVICTORIA = 2;
		public final static double ELSE = -1; //alguna otra condicion
	}

	/**
	 * Funcion evaluadora de Carta Parc BOT (Maquina)
	 * @param vCM Valor Carta de BOT (MAQUINA)
	 * @param vCH Valor Carta de JUGADOR (HUMANO)
	 * @param eCM Estado Carta de BOT (MAQUINA)
	 * @param eCH Estado Carta de JUGADOR (HUMANO)
	 * @return Retorna un valor en factor al valor recibido como parametro
	 */
	private double evalValorDeCartaParcMAQ(int vCM, int vCH, int eCM, int eCH ){
		//Si carta humana esta boca abajo en def
		if(eCH == ZonaBatalla.POSCARTA.DEFCARAABAJO)
			return vCM;
		//Si carta bot esta en defensa en def
		if(eCM == ZonaBatalla.POSCARTA.DEFCARAARRIBA || eCM == ZonaBatalla.POSCARTA.DEFCARAABAJO){
			if(vCM > vCH)
				return vCM * FACTORBOT.DEFVICTORIA;
			if(vCM == vCH)
				return vCM * FACTORBOT.DEFEMPATE;
			if(vCM < vCH)
				return vCM * FACTORBOT.DEFDERROTA;
		}
		//Si carta bot esta en atk
		if(eCM == ZonaBatalla.POSCARTA.ATAQUE){
			if(vCM > vCH)
				return vCM * FACTORBOT.ATKVICTORIA;
			if(vCM == vCH)
				return vCM * FACTORBOT.ATKEMPATE;
			if(vCM < vCH)
				return vCM * FACTORBOT.ATKDERROTA;
		}
		return FACTORBOT.ELSE;
	}

	public static class FACTORJUG {
		public final static double DEFDERROTA = 0.5; //factores al cambiar una carta defenza a ataque y luego disponerse a atacar a una carta enemiga
		public final static double DEFEMPATE = 1.25;
		public final static double DEFVICTORIA = 1.5;
		public final static double ATKDERROTA = 0; //factores al atacar con una carta en modo de ataque
		public final static double ATKEMPATE = 1.25;
		public final static double ATKVICTORIA = 2;
		public final static double CARTAHUMANABOCAABAJO= 7;
		public final static double ELSE = -1; //alguna otra condicion
	}

	/**
	 * Funcion evaluadora de Carta Parc Jugador (Humano)
	 * @param vCM Valor Carta de BOT (MAQUINA)
	 * @param vCH Valor Carta de JUGADOR (HUMANO)
	 * @param eCM Estado Carta de BOT (MAQUINA)
	 * @param eCH Estado Carta de JUGADOR (HUMANO)
	 * @return Retorna un valor en factor al valor recibido como parametro
	 */
	private double evalValorDeCartaParcHUM(int vCM, int vCH, int eCM, int eCH ){
		//Si carta enemiga esta boca abajo en def
		if(eCH == ZonaBatalla.POSCARTA.DEFCARAABAJO)
			return FACTORJUG.CARTAHUMANABOCAABAJO;
		//Si carta maq en def
		if(eCM == ZonaBatalla.POSCARTA.DEFCARAARRIBA || eCM == ZonaBatalla.POSCARTA.DEFCARAABAJO){
			if(vCH > vCM)
				return vCH * FACTORJUG.DEFVICTORIA;
			if(vCH == vCM)
				return vCH * FACTORJUG.DEFEMPATE;
			if(vCH < vCM)
				return vCH * FACTORJUG.DEFDERROTA;
		}
		//Si carta maq en atk
		if(eCM == ZonaBatalla.POSCARTA.ATAQUE){
			if(vCH > vCM)
				return vCH * FACTORJUG.ATKVICTORIA;
			if(vCH == vCM)
				return vCH * FACTORJUG.ATKEMPATE;
			if(vCH < vCM)
				return vCH * FACTORJUG.ATKDERROTA;
		}
		return FACTORJUG.ELSE;
	}
	
	private double evalValorDeCarta(double arr[],int nCar){
		//con el arreglo devolver el valor promedio final que tendra la carta
		
		double sum = 0;
		for (int i = 0; i < arr.length; i++) 
			sum = sum + arr[i];			
		
		return (sum/nCar);
	}
	
	public void imprimeEstado(){
		System.out.println("*****************************************************************************");
		System.out.println("Jugador 1");
		System.out.println("Mano");
		for(int i=0;i < Mano.MAXMANOCARDS;i++){
			if(Jugador1.Mano.obtenerCartaxId(i) != null)
				System.out.print(Jugador1.Mano.obtenerCartaxId(i).getValor() + " " +
					Carta.devuelveUnicode(Jugador1.Mano.obtenerCartaxId(i).getElemento()) + " | ");
			else
				System.out.print("VACIO | ");
		}
		System.out.println();
		System.out.println("Barrera");
		for(int i=0;i < Barrera.MAXBARRERACARDS;i++){
			if(Jugador1.Barrera.obtenerCartaxId(i) != null)
				System.out.print("BARRERA| ");
			else
				System.out.print("VACIO  | ");
		}
		System.out.println();
		System.out.println("ZonaBatalla");
		for(int i=0;i < ZonaBatalla.MAXZONABATALLACARDS;i++){
			if(Jugador1.ZonaBatalla.obtenerCartaxId(i) != null)
				System.out.print(Jugador1.ZonaBatalla.obtenerCartaxId(i).getValor()+ " " +
						Carta.devuelveUnicode(Jugador1.ZonaBatalla.obtenerCartaxId(i).getElemento()) + " " +
						ZonaBatalla.devuelveposcarta(Jugador1.ZonaBatalla.poscarta[i]) + " | ");
			else
				System.out.print("VACIO | ");
		}
		System.out.println();


		System.out.println("Jugador 2");
		System.out.println("Mano");
		for(int i=0;i < Mano.MAXMANOCARDS;i++){
			if(Jugador2.Mano.obtenerCartaxId(i) != null)
				System.out.print(Jugador2.Mano.obtenerCartaxId(i).getValor() + " " +
						Carta.devuelveUnicode(Jugador2.Mano.obtenerCartaxId(i).getElemento()) + " | ");
			else
				System.out.print("VACIO | ");
		}
		System.out.println();
		System.out.println("Barrera");
		for(int i=0;i < Barrera.MAXBARRERACARDS;i++){
			if(Jugador2.Barrera.obtenerCartaxId(i) != null)
				System.out.print("BARRERA| ");
			else
				System.out.print("VACIO | ");
		}
		System.out.println();
		System.out.println("ZonaBatalla");
		for(int i=0;i < ZonaBatalla.MAXZONABATALLACARDS;i++){
			if(Jugador2.ZonaBatalla.obtenerCartaxId(i) != null)
				System.out.print(Jugador2.ZonaBatalla.obtenerCartaxId(i).getValor() + " " +
						Carta.devuelveUnicode(Jugador2.ZonaBatalla.obtenerCartaxId(i).getElemento()) + " " +
						ZonaBatalla.devuelveposcarta(Jugador2.ZonaBatalla.poscarta[i]) + " | ");
			else
				System.out.print("VACIO | ");
		}
		System.out.println();
		System.out.println();
	}
}
