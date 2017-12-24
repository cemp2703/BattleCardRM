package Clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maquina {
	public List<Estado> EstadosGenerados;
	Estado EstadoInicial;
	
	public Maquina(){
		EstadosGenerados=new ArrayList<Estado>();
	}
	
	public void cargarEstado(Estado E){
		EstadoInicial=E;
	}
	
	public List<Estado> sistemaDeProduccion(Regla R,Estado EInicial){//Crea los estados a partir de nuestro estado inicial
		Estado EI=EInicial.clone();
		EstadosGenerados.clear();
		List<Estado> EstadosColocados=new ArrayList<Estado>();
		
		
		
		Estado ENuevo;
		EstadosColocados.add(EI);
		
		//Colocar carta (Se crean los 11 estados iniciales para luego entrar en la generacion por ataque)
		if(EI.Jugador2.ZonaBatalla.Cartas.obtenerNumerodeCartas()<ZonaBatalla.MAXZONABATALLACARDS){
			for (int i = 0; i < Mano.MAXMANOCARDS; i++) {
				for(int j=0;j< 2;j++){//colocar en posicion de ataque o defenza
					if(EI.Jugador2.Mano.Cartas.obtenerCartaxId(i)!=null){//Exista carta en posicion mano
						ENuevo=null;
						ENuevo=EI.clone();
						if(j==0)
							R.accionColocarCartaEnEspacioVacio(ENuevo.Jugador2,i,j+1);
						else
							R.accionColocarCartaEnEspacioVacio(ENuevo.Jugador2,i,j+2);
						EstadosColocados.add(ENuevo);
					}
					else{
						//No hay estado nuevo que generar
						
					}
				}
			}
		}

		//CARTAS PUEDEN PASAR DE DEFENZA A ATAQUE(SI PUEDEN) O NO LO HACEN
		
		List<Estado> EstadosCambiadosATK=new ArrayList<Estado>();

		boolean vcumple[]=new boolean[3];
		int cnt;
		int i=0;
		while(i<EstadosColocados.size()){
			ENuevo=EstadosColocados.get(i).clone();
			EstadosCambiadosATK.add(ENuevo);
			for(int j=0;j<EstadosColocados.get(i).Jugador2.ZonaBatalla.Cartas.obtenerNumerodeCartas();j++){
				
				for(int k=0;k<3;k++){//3 significa las posibilidades de convinaciones con j numero de cartas
					ENuevo=EstadosColocados.get(i).clone();
					
					for(int l=0;l<3;l++){
						vcumple[l]=false;
					}
					
					if(j==2 || (j==1 && k==0) || (j==1 && k==1) || (j==0 && k==0) ){
						if(ENuevo.Jugador2.ZonaBatalla.poscarta[0]>=2)
							vcumple[0]=R.accionCambiarPosicionBatalla(ENuevo.Jugador2,0);
					}
					if(j==2 || (j==1 && k==0) ||  (j==1 && k==2) || (j==0 && k==1)){
						if(ENuevo.Jugador2.ZonaBatalla.poscarta[1]>=2)
						vcumple[1]=R.accionCambiarPosicionBatalla(ENuevo.Jugador2,1);
					}
					if(j==2 || (j==1 && k==2) || (j==1 && k==1) || (j==0 && k==2)){
						if(ENuevo.Jugador2.ZonaBatalla.poscarta[2]>=2)
						vcumple[2]=R.accionCambiarPosicionBatalla(ENuevo.Jugador2,2);
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
		int resp;
		int v[]=new int[3];
		boolean atacobarrera;
		while(i<EstadosCambiadosATK.size()){
			ENuevo=EstadosCambiadosATK.get(i).clone();
			EstadosAtacando.add(ENuevo);
			
			for(int n=0;n<EstadosCambiadosATK.get(i).Jugador2.ZonaBatalla.Cartas.obtenerNumerodeCartas();n++){
				switch(n){
				case 2:
				case 1:
					for(int p=0;p<6;p++){
						permutacion(p, v);//Se obtiene los escenarios de ataque 
						ENuevo=EstadosCambiadosATK.get(i).clone();
						
						for(int m=0;m<=n;m++){
							vcumple[m]=false;
						}
						
						for(int j=0;j<=n;j++){//Origenes
							//Ataques
							resp=-1;
							if(vcumple[j]=R.posibilidadAtacarCarta(ENuevo.Jugador2,ENuevo.Jugador1, v[j],j)){
								resp=R.accionAtacarCarta(ENuevo.Jugador2,ENuevo.Jugador1,v[j],j);
							}
							else if(vcumple[j]=R.posibilidadAtacarBarrera(ENuevo.Jugador2,ENuevo.Jugador1, j)){
								resp=R.accionAtacarBarrera(ENuevo.Jugador2, ENuevo.Jugador1, j);
							}
							else{
								//No se genera estado
							}
							if(resp==3){
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
							if(R.posibilidadAtacarCarta(EstadosCambiadosATK.get(i).Jugador2, EstadosCambiadosATK.get(i).Jugador1, k,j)){
								ENuevo=EstadosCambiadosATK.get(i).clone();
								resp=R.accionAtacarCarta(ENuevo.Jugador2,ENuevo.Jugador1,k,j);
								if(resp==3)
									ENuevo.setTermino(2);
								EstadosAtacando.add(ENuevo);
							}
							else if(R.posibilidadAtacarBarrera(EstadosCambiadosATK.get(i).Jugador2, EstadosCambiadosATK.get(i).Jugador1, j)){
								if(!atacobarrera){
									ENuevo=EstadosCambiadosATK.get(i).clone();
									resp=R.accionAtacarBarrera(ENuevo.Jugador2, ENuevo.Jugador1, j);
									if(resp==3)
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
					
		
		//CARTAS CAMBIAN A DEFENZA(LAS QUE PUEDEN) O NO LO HACEN
		
		List<Estado> EstadosFinales=new ArrayList<Estado>();
		
		i=0;
		while(i<EstadosAtacando.size()){
			ENuevo=EstadosAtacando.get(i).clone();
			ENuevo.funcionEvaluadora();
			EstadosFinales.add(ENuevo);
			for(int j=0;j<EstadosAtacando.get(i).Jugador2.ZonaBatalla.Cartas.obtenerNumerodeCartas();j++){
				
				for(int k=0;k<3;k++){
					ENuevo=EstadosAtacando.get(i).clone();
					
					for(int l=0;l<3;l++){
						vcumple[l]=false;
					}
					
					if(j==2 || (j==1 && k==0) || (j==1 && k==2) || (j==0 && k==0) ){
						if(ENuevo.Jugador2.ZonaBatalla.poscarta[0]==1)
						vcumple[0]=R.accionCambiarPosicionBatalla(ENuevo.Jugador2,0);
					}
					if(j==2 || (j==1 && k==0) ||  (j==1 && k==1) || (j==0 && k==1)){
						if(ENuevo.Jugador2.ZonaBatalla.poscarta[1]==1)
						vcumple[1]=R.accionCambiarPosicionBatalla(ENuevo.Jugador2,1);
					}
					if(j==2 || (j==1 && k==1) || (j==1 && k==2) || (j==0 && k==2)){
						if(ENuevo.Jugador2.ZonaBatalla.poscarta[2]==1)
						vcumple[2]=R.accionCambiarPosicionBatalla(ENuevo.Jugador2,2);
					}
					
					cnt=0;
					for(int l=0;l<3;l++){
						if(vcumple[l]==true){
							cnt++;
						}
					}
					if(cnt==j+1){
						ENuevo.funcionEvaluadora();
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

	public Estado estrategiaMinMax(List<Estado> LE){
		if(LE.size()==0)
			return null;
		Regla R=new Regla();
		
		for(int i = 0; i<LE.size(); i++){
			List<Estado> inside = new ArrayList<Estado>();
			inside=sistemaDeProduccion(R, LE.get(i));
			LE.get(i).setValorEstado(estrategiaPrimeroElMejor(inside, 1).getValorEstado());
		}
	
		return  estrategiaPrimeroElMejor(LE, -1);
	}
}
