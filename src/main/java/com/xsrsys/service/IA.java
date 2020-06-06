package com.xsrsys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.xsrsys.model.Estado;
import com.xsrsys.model.Jugador.ResultadoAtacarCarta;
import com.xsrsys.model.Mano;
import com.xsrsys.model.ResultadoAtaque;
import com.xsrsys.model.ZonaBatalla;
import com.xsrsys.model.ZonaBatalla.PosBatalla;

public class IA {
	public List<Estado> EstadosGenerados;
	Estado EstadoInicial;
	
	public IA(){
		EstadosGenerados=new ArrayList<Estado>();
	}
	
	public void cargarEstado(Estado E){
		EstadoInicial=E;
	}
	
	public List<Estado> sistemaDeProduccion(Estado EInicial) throws CloneNotSupportedException{//Crea los estados a partir de nuestro estado inicial

		Estado EI=(Estado)EInicial.clone();
		EstadosGenerados.clear();
		List<Estado> EstadosColocados=new ArrayList<Estado>();
		Estado ENuevo;
		EstadosColocados.add(EI);
		
		//Colocar carta (Se crean los 11 estados iniciales para luego entrar en la generacion por ataque)
		if(EI.Jugador2.ZBatalla.obtenerNumerodeCartas()< ZonaBatalla.MAXZONABATALLACARDS){
			for (int i = 0; i < Mano.MAXMANOCARDS; i++) {
				for(int j=0;j< 2;j++){//colocar en posicion de ataque o defenza
					if(EI.Jugador2.Mano.obtenerCartaxId(i)!=null){//Exista carta en posicion mano
						ENuevo=null;
						ENuevo=(Estado)EI.clone();
						if(j==0)
							ENuevo.Jugador2.ZBatalla.agregarCartaEnEspacioVacio(ENuevo.Jugador2.Mano.obtenerCartaxId(i),PosBatalla.ATAQUE);
						else
							ENuevo.Jugador2.ZBatalla.agregarCartaEnEspacioVacio(ENuevo.Jugador2.Mano.obtenerCartaxId(i),PosBatalla.DEFCARAABAJO);
						EstadosColocados.add(ENuevo);
					}
					else{
						//No hay estado nuevo que generar
						
					}
				}
			}
		}

		//CARTAS PUEDEN PASAR DE DEFENSA A ATAQUE(SI PUEDEN) O NO LO HACEN
		
		List<Estado> EstadosCambiadosATK=new ArrayList<Estado>();

		boolean vcumple[]=new boolean[3];
		int cnt;
		int i=0;
		while(i<EstadosColocados.size()){
			ENuevo=(Estado)EstadosColocados.get(i).clone();
			EstadosCambiadosATK.add(ENuevo);
			for(int j=0;j<EstadosColocados.get(i).Jugador2.ZBatalla.obtenerNumerodeCartas();j++){
				for(int k=0;k<3;k++){//3 significa las posibilidades de convinaciones con j numero de cartas
					ENuevo=(Estado)EstadosColocados.get(i).clone();
					for(int l=0;l<3;l++){
						vcumple[l]=false;
					}
					if(j==2 || (j==1 && k==0) || (j==1 && k==1) || (j==0 && k==0) ){
						if(ENuevo.Jugador2.ZBatalla.posBatalla[0]==PosBatalla.DEFCARAABAJO || ENuevo.Jugador2.ZBatalla.posBatalla[0]==PosBatalla.DEFCARAARRIBA)
							vcumple[0]=ENuevo.Jugador2.accionCambiarPosicionBatalla(0);
					}
					if(j==2 || (j==1 && k==0) ||  (j==1 && k==2) || (j==0 && k==1)){
						if(ENuevo.Jugador2.ZBatalla.posBatalla[1]==PosBatalla.DEFCARAABAJO || ENuevo.Jugador2.ZBatalla.posBatalla[1]==PosBatalla.DEFCARAARRIBA)
						vcumple[1]=ENuevo.Jugador2.accionCambiarPosicionBatalla(1);
					}
					if(j==2 || (j==1 && k==2) || (j==1 && k==1) || (j==0 && k==2)){
						if(ENuevo.Jugador2.ZBatalla.posBatalla[2]==PosBatalla.DEFCARAABAJO || ENuevo.Jugador2.ZBatalla.posBatalla[2]==PosBatalla.DEFCARAARRIBA)
						vcumple[2]=ENuevo.Jugador2.accionCambiarPosicionBatalla(2);
					}
					cnt=0;
					for(int l=0;l<3;l++){
						if(vcumple[l]==true){
							cnt++;
						}
					}
					if(cnt==j+1){
						EstadosCambiadosATK.add(ENuevo);
					}
					if(j==2)
						break;
				}
			}
			i++;
		}
		
		//CARTAS ATACAN(LAS QUE PUEDEN) O NO LO HACEN
		
		List<Estado> EstadosAtacando=new ArrayList<Estado>();
		
		i=0;	
		ResultadoAtaque resp = new ResultadoAtaque();
		int v[]=new int[3];
		boolean atacobarrera;
		while(i<EstadosCambiadosATK.size()){
			ENuevo=(Estado)EstadosCambiadosATK.get(i).clone();
			EstadosAtacando.add(ENuevo);
			for(int n=0;n<EstadosCambiadosATK.get(i).Jugador2.ZBatalla.obtenerNumerodeCartas();n++){
				switch(n){
				case 2:
				case 1:
					for(int p=0;p<6;p++){
						permutacion(p, v);//Se obtiene los escenarios de ataque 
						ENuevo=(Estado)EstadosCambiadosATK.get(i).clone();
						for(int m=0;m<=n;m++){
							vcumple[m]=false;
						}
						for(int j=0;j<=n;j++){//Origenes
							//Ataques
							if(vcumple[j] = ENuevo.Jugador2.posibilidadAtacarCarta(ENuevo.Jugador1, v[j],j)){
								resp= ENuevo.Jugador2.accionAtacarCarta(ENuevo.Jugador1,v[j],j);
							}
							else if(vcumple[j] = ENuevo.Jugador2.posibilidadAtacarBarrera(ENuevo.Jugador1, j)){
								resp.resultado=ENuevo.Jugador2.accionAtacarBarrera( ENuevo.Jugador1, j);
							}
							else{
								//No se genera estado
							}
							if(resp.resultado== ResultadoAtacarCarta.ENEMIGOSINBARRERA){
								ENuevo.setTermino(2);
								break;
							}
						}
						//EstadosAtacando.add(ENuevo);
						
						cnt=0;
						for(int m=0;m<=n;m++){
							if(vcumple[m]==true){
								cnt++;
							}
						}
						if(cnt==n+1 || ENuevo.getTermino()>0){
							EstadosAtacando.add(ENuevo);
						}
						
					}
				break;
				case 0:
					for(int j=0;j<3;j++){
						atacobarrera=false;	
						for(int k=0;k<3;k++){
							if(EstadosCambiadosATK.get(i).Jugador2.posibilidadAtacarCarta(EstadosCambiadosATK.get(i).Jugador1, k,j)){
								ENuevo=(Estado)EstadosCambiadosATK.get(i).clone();
								resp=ENuevo.Jugador2.accionAtacarCarta(ENuevo.Jugador1,k,j);
								if(resp.resultado==ResultadoAtacarCarta.ENEMIGOSINBARRERA)
									ENuevo.setTermino(2);
								EstadosAtacando.add(ENuevo);
							}
							else if(EstadosCambiadosATK.get(i).Jugador2.posibilidadAtacarBarrera(EstadosCambiadosATK.get(i).Jugador1, j)){
								if(!atacobarrera){
									ENuevo=(Estado)EstadosCambiadosATK.get(i).clone();
									resp.resultado = ENuevo.Jugador2.accionAtacarBarrera( ENuevo.Jugador1, j);
									if(resp.resultado == ResultadoAtacarCarta.ENEMIGOSINBARRERA)
										ENuevo.setTermino(2);
									EstadosAtacando.add(ENuevo);
									atacobarrera=true;
								}
							}
							else{
								//No se genera estado
								//No hay objetivo al que atacar en esa posicion (k) , pero si hay mas cartas en otras posiciones
							}
							
						}
					}
				}
			}
			i++;
		}
					
		
		//CARTAS CAMBIAN A DEFENSA(LAS QUE PUEDEN) O NO LO HACEN
		
		List<Estado> EstadosFinales=new ArrayList<Estado>();
		
		i=0;
		while(i<EstadosAtacando.size()){
			ENuevo=(Estado)EstadosAtacando.get(i).clone();
			funcionEvaluadora(ENuevo);
			EstadosFinales.add(ENuevo);
			for(int j=0;j<EstadosAtacando.get(i).Jugador2.ZBatalla.obtenerNumerodeCartas();j++){
				
				for(int k=0;k<3;k++){
					ENuevo=(Estado)EstadosAtacando.get(i).clone();
					
					for(int l=0;l<3;l++){
						vcumple[l]=false;
					}
					
					if(j==2 || (j==1 && k==0) || (j==1 && k==2) || (j==0 && k==0) ){
						if(ENuevo.Jugador2.ZBatalla.posBatalla[0]==PosBatalla.ATAQUE)
						vcumple[0]=ENuevo.Jugador2.accionCambiarPosicionBatalla(0);
					}
					if(j==2 || (j==1 && k==0) ||  (j==1 && k==1) || (j==0 && k==1)){
						if(ENuevo.Jugador2.ZBatalla.posBatalla[1]==PosBatalla.ATAQUE)
						vcumple[1]=ENuevo.Jugador2.accionCambiarPosicionBatalla(1);
					}
					if(j==2 || (j==1 && k==1) || (j==1 && k==2) || (j==0 && k==2)){
						if(ENuevo.Jugador2.ZBatalla.posBatalla[2]==PosBatalla.ATAQUE)
						vcumple[2]=ENuevo.Jugador2.accionCambiarPosicionBatalla(2);
					}
					
					cnt=0;
					for(int l=0;l<3;l++){
						if(vcumple[l]==true){
							cnt++;
						}
					}
					if(cnt==j+1){
						funcionEvaluadora(ENuevo);
						EstadosFinales.add(ENuevo);
					}
					
					if(j==2)
						break;
				}
			}
			i++;
		}
		
		return EstadosFinales;
	}

	public Estado estrategiaRandom(List<Estado> LE){
		if(LE.size()==0)
			return null;
		Random rm=new Random();
		int n=rm.nextInt(LE.size());
		
		return LE.get(n);
	}

	public static class MEJOR {
		public final static int MENOR = -1;
		public final static int MAYOR = 1;
	}

	public Estado estrategiaPrimeroElMejor(List<Estado> LE,int mejor){//Mejor 1: Mayor , -1: Menor
		if(LE.size()==0)
			return null;
		int idmejor=0,i=0;//mejorve: Mejor valor de Clases.Estado
		double mejorve = 0;
		while(i<LE.size()){
			if(i==0){
				mejorve=LE.get(i).getValorEstado()*mejor;
				idmejor=i;
			}
			else{
				if(mejorve<LE.get(i).getValorEstado()*mejor){
					mejorve=LE.get(i).getValorEstado()*mejor;
					idmejor=i;
				}
			}
			i++;
		}
		return LE.get(idmejor);
	}

	public Estado estrategiaMinMax(List<Estado> LE) throws CloneNotSupportedException{
		if(LE.size()==0)
			return null;
		
		for(int i = 0; i<LE.size(); i++){
			List<Estado> inside = new ArrayList<Estado>();
			inside=sistemaDeProduccion( LE.get(i));
			LE.get(i).setValorEstado(estrategiaPrimeroElMejor(inside, 1).getValorEstado());
		}
	
		return  estrategiaPrimeroElMejor(LE, -1);
	}

	void permutacion(int np,int v[]){
		switch(np){
			case 0:
				v[0]=0;v[1]=1;v[2]=2;
				break;
			case 1:
				v[0]=1;v[1]=0;v[2]=2;
				break;
			case 2:
				v[0]=2;v[1]=0;v[2]=1;
				break;
			case 3:
				v[0]=0;v[1]=2;v[2]=1;
				break;
			case 4:
				v[0]=1;v[1]=2;v[2]=0;
				break;
			case 5:
				v[0]=2;v[1]=1;v[2]=0;
				break;
		}


	}

	public void funcionEvaluadora(Estado E)
	{	////MAQUINA ES JUGADOR 2
		double hum=0,maq=0;
		int nMaq = E.Jugador2.ZBatalla.obtenerNumerodeCartas();
		int nHum = E.Jugador1.ZBatalla.obtenerNumerodeCartas();

		int barreraHum = E.Jugador1.Barrera.obtenerNumerodeCartas();

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
		PosBatalla estCarMaq, estCarHum;

		//VALOR DE MAQ
		for (int i = 0; i < ZonaBatalla.MAXZONABATALLACARDS; i++) {
			if(E.Jugador2.ZBatalla.obtenerCartaxId(i)!=null){
				valCarMaq=E.Jugador2.ZBatalla.obtenerCartaxId(i).getValor();
				estCarMaq=E.Jugador2.ZBatalla.getPosBatallaxId(i);
				if(nHum==0)
					CartasMaq[i] = valCarMaq * 2;
				for(int j = 0; j < ZonaBatalla.MAXZONABATALLACARDS; j++){
					if(E.Jugador1.ZBatalla.obtenerCartaxId(j)!=null){
						valCarHum=E.Jugador1.ZBatalla.obtenerCartaxId(j).getValor();
						estCarHum=E.Jugador1.ZBatalla.getPosBatallaxId(j);
						CartasMaq[i] = evalValorDeCartaParcMAQ(valCarMaq, valCarHum, estCarMaq, estCarHum);
					}
				}
				maq = maq + evalValorDeCarta(CartasMaq,nMaq);//mandar arreglo
			}
		}

		//VALOR DE HUM
		for (int i = 0; i < ZonaBatalla.MAXZONABATALLACARDS; i++) {
			if(E.Jugador1.ZBatalla.obtenerCartaxId(i)!=null){
				valCarHum=E.Jugador1.ZBatalla.obtenerCartaxId(i).getValor();
				estCarHum=E.Jugador1.ZBatalla.getPosBatallaxId(i);
				if(nMaq==0)
					CartasHum[i] = valCarHum * 2;
				for(int j = 0; j < ZonaBatalla.MAXZONABATALLACARDS; j++){
					if(E.Jugador2.ZBatalla.obtenerCartaxId(j)!=null){
						valCarMaq=E.Jugador2.ZBatalla.obtenerCartaxId(j).getValor();
						estCarMaq=E.Jugador2.ZBatalla.getPosBatallaxId(j);
						CartasHum[i] = evalValorDeCartaParcHUM(valCarMaq, valCarHum, estCarMaq, estCarHum);
					}
				}
				hum = hum + evalValorDeCarta(CartasHum, nHum);//mandar arreglo
			}
		}
		E.setValorEstado( (maq) - (hum) - (5*barreraHum) );
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
	 * Funcion evaluadora de Carta Parc BOT (IA)
	 * @param vCM Valor Carta de BOT (MAQUINA)
	 * @param vCH Valor Carta de JUGADOR (HUMANO)
	 * @param eCM Estado Carta de BOT (MAQUINA)
	 * @param eCH Estado Carta de JUGADOR (HUMANO)
	 * @return Retorna un valor en factor al valor recibido como parametro
	 */
	private double evalValorDeCartaParcMAQ(int vCM, int vCH, PosBatalla eCM, PosBatalla eCH ){
		//Si carta humana esta boca abajo en def
		if(eCH == ZonaBatalla.PosBatalla.DEFCARAABAJO)
			return vCM;
		//Si carta bot esta en defensa en def
		if(eCM == ZonaBatalla.PosBatalla.DEFCARAARRIBA || eCM == ZonaBatalla.PosBatalla.DEFCARAABAJO){
			if(vCM > vCH)
				return vCM * FACTORBOT.DEFVICTORIA;
			if(vCM == vCH)
				return vCM * FACTORBOT.DEFEMPATE;
			if(vCM < vCH)
				return vCM * FACTORBOT.DEFDERROTA;
		}
		//Si carta bot esta en atk
		if(eCM == ZonaBatalla.PosBatalla.ATAQUE){
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
	private double evalValorDeCartaParcHUM(int vCM, int vCH, PosBatalla eCM, PosBatalla eCH ){
		//Si carta enemiga esta boca abajo en def
		if(eCH == ZonaBatalla.PosBatalla.DEFCARAABAJO)
			return FACTORJUG.CARTAHUMANABOCAABAJO;
		//Si carta maq en def
		if(eCM == ZonaBatalla.PosBatalla.DEFCARAARRIBA || eCM == ZonaBatalla.PosBatalla.DEFCARAABAJO){
			if(vCH > vCM)
				return vCH * FACTORJUG.DEFVICTORIA;
			if(vCH == vCM)
				return vCH * FACTORJUG.DEFEMPATE;
			if(vCH < vCM)
				return vCH * FACTORJUG.DEFDERROTA;
		}
		//Si carta maq en atk
		if(eCM == ZonaBatalla.PosBatalla.ATAQUE){
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

}
