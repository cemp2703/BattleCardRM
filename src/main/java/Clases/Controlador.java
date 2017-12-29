package Clases;

import java.util.Random;


public class Controlador {
	
	private final int MAXVALORCARTA = 13;
	private final int MAXNUMEROELEMENTOCARTAS = 4;
	
	private boolean cartaselegidas[][];
	public Controlador(){
		cartaselegidas=new boolean[MAXNUMEROELEMENTOCARTAS][MAXVALORCARTA];
	}
	
	public void repartirCartas(Jugador jug){
		Random rm;
		int n,m,maximoendeck,cartasrepartidas;
		
		maximoendeck=(Deck.MAXDECK - Mano.MAXMANOCARDS - Barrera.MAXBARRERACARDS);
		
		for(int i=0;i<cartaselegidas.length;i++){
			for(int j=0;j<cartaselegidas[i].length;j++){
				cartaselegidas[i][j] = false;
			}
		}
		
		cartasrepartidas=0;
		
		while(cartasrepartidas < Deck.MAXDECK ){
			rm=new Random();
			n=rm.nextInt(MAXNUMEROELEMENTOCARTAS);
			rm=new Random();
			m=rm.nextInt(MAXVALORCARTA);
			
			if(!cartaselegidas[n][m]){
				cartaselegidas[n][m]=true;
				
				cartasrepartidas++;
				
				if(jug.Barrera.obtenerNumerodeCartas()<Barrera.MAXBARRERACARDS){
					jug.Barrera.agregarCartaEnEspacioVacio(new Carta(n,m+1));
				}
				else if(jug.Mano.obtenerNumerodeCartas()<Mano.MAXMANOCARDS){
					jug.Mano.agregarCartaEnEspacioVacio(new Carta(n,m+1));
				}
				else if(jug.Deck.obtenerNumeroElementos()< maximoendeck ){
					jug.Deck.agregarUnaCarta(new Carta(n,m+1));
				}
				
			}
		}
	}
}
