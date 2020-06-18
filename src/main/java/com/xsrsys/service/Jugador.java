package com.xsrsys.service;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xsrsys.service.ZonaBatalla.PosBatalla;

public class Jugador implements Cloneable{
	private String Nombre;
	private int NTurnos;
	public Deck Deck;
	public Mano Mano;
	public Barrera Barrera;
	public ZonaBatalla ZBatalla;
	
	protected static final Logger logger = LogManager.getLogger(Jugador.class);

	public Jugador(String pNombre){
		Nombre=pNombre;
		Deck=new Deck();
		Mano=new Mano();
		Barrera=new Barrera();
		ZBatalla=new ZonaBatalla();
		NTurnos=0;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	//region Operaciones (Reglas)

	public void accionIniciarTurno(){
		NTurnos++;
		ZBatalla.renovarPosibilidades();
	}

	public enum ResultadoCojerUnaCarta{
		MANOLLENA, // No se puede cojer una carta porque la mano esta llena
		DECKVACIO, // El deck esta vacio
		EXITO 
	}
	
	public ResultadoCojerUnaCarta accionCogerUnaCartaDelDeck(){
		if(Mano.obtenerNumerodeCartas() >= Mano.getMaxNCartas())
			return ResultadoCojerUnaCarta.MANOLLENA;

		Carta ncarta= Deck.sacarUnaCarta();
		if(ncarta == null ) {
        	logger.debug("Fin del Juego!!!\n");
        	logger.debug(getNombre()+" se quedó sin cartas en el mazo!!\n");
			return ResultadoCojerUnaCarta.DECKVACIO;
		}
		else{
			Mano.agregarCartaEnEspacioVacio(ncarta);
			logger.debug("Se coge una carta del deck a la mano\n");
			return ResultadoCojerUnaCarta.EXITO;
		}
	}
	
	public boolean puedeColocarCartaEnZB(){
		return !ZBatalla.cartaColocada;
	}
	public boolean posibilidadColocarCartaEnPosicion(int idCartaZB,int idCartaMano) {
		if( ! puedeColocarCartaEnZB())
			return false;
		if(this.Mano.obtenerCartaxId(idCartaMano) == null)
			return false;
		if(this.ZBatalla.obtenerCartaxId(idCartaZB) != null)
			return false;
		return true;
	}
	public boolean accionColocarCarta(int idCartaZB,int idCartaMano,PosBatalla posCarta){
		boolean respuesta = false;
		if(posibilidadColocarCartaEnPosicion(idCartaZB,idCartaMano)){
			Carta c=this.Mano.obtenerCartaxId(idCartaMano);
			this.Mano.quitarCartaenPos(idCartaMano);
			this.ZBatalla.agregarCartaEnPos(c,idCartaZB,posCarta);
			respuesta=true;
        	logger.debug("Carta Colocada!!\n");
		}
		return respuesta;
	}

	public boolean puedeAtacarBarreras( Jugador pJugadorAtacado){
		if(ZBatalla.obtenerNumerodeCartas() == 0)
			return false;
		if(pJugadorAtacado.ZBatalla.obtenerNumerodeCartas() > 0)
			return false;
		if(ZBatalla.nAtaquesDisponibles == 0)
			return false;
		if(NTurnos == 1)
			return false;
		return true;
	}
	public boolean posibilidadAtacarBarrera(Jugador JugadorAtacado,int idCartaAtacante) {
		if(!puedeAtacarBarreras(JugadorAtacado))
			return false;
		if (ZBatalla.obtenerCartaxId(idCartaAtacante) == null)
			return false;
		if(ZBatalla.posBatalla[idCartaAtacante] != ZonaBatalla.PosBatalla.ATAQUE)
			return false;
		if(ZBatalla.dispAtaque[idCartaAtacante] != ZonaBatalla.DispAtaque.DISPONIBLE)
			return false;

		return true;
	}

	public VeredictoAtaque accionAtacarBarrera(Jugador JugadorAtacado,int idCartaAtacante){
		VeredictoAtaque respuesta=VeredictoAtaque.NOSECUMPLENCOND;
		if( posibilidadAtacarBarrera(JugadorAtacado,idCartaAtacante) ){
			JugadorAtacado.Barrera.quitarUltimaCartaDisponible();
			this.ZBatalla.nAtaquesDisponibles--;
			if(JugadorAtacado.Barrera.obtenerNumerodeCartas() >  0){
				respuesta=VeredictoAtaque.BARRERADESTRUIDA;
	        	logger.debug("Ataque Realizado!!\n");
	        	logger.debug("Barrera Destruida\n");
			}
			else{
				respuesta= VeredictoAtaque.ENEMIGOSINBARRERA;
				logger.debug("Fin del Juego!!!\n");
				logger.debug(JugadorAtacado.getNombre()+" se quedó sin barreras!!\n");
			}
            this.ZBatalla.dispAtaque[idCartaAtacante] = ZonaBatalla.DispAtaque.NODISPONIBLE;
            this.ZBatalla.dispCambio[idCartaAtacante] = ZonaBatalla.DispCambio.NODISPONIBLE;

		}
		return respuesta;
	}
	
	public boolean puedeAtacarCartas( Jugador pJugadorAtacado) {
		if(ZBatalla.obtenerNumerodeCartas() == 0) 
			return false;
		if(pJugadorAtacado.ZBatalla.obtenerNumerodeCartas() == 0) 
			return false;
		if(ZBatalla.nAtaquesDisponibles == 0)
			return false;
		if(NTurnos == 1)
			return false;
		return true;
	}
	public boolean posibilidadAtacarCarta(Jugador JugadorAtacado,int idCartaAtacada,int idCartaAtacante){
		if(!puedeAtacarCartas(JugadorAtacado))
			return false;
		if (ZBatalla.obtenerCartaxId(idCartaAtacante) == null)
			return false;
		if (JugadorAtacado.ZBatalla.obtenerCartaxId(idCartaAtacada) == null)
			return false;
		if(ZBatalla.posBatalla[idCartaAtacante] != ZonaBatalla.PosBatalla.ATAQUE)
			return false;
		if(ZBatalla.dispAtaque[idCartaAtacante] != ZonaBatalla.DispAtaque.DISPONIBLE)
			return false;

		return true;
	}

	public enum EstadoCarta{
		ACTIVA, // Carta activa
		DESTRUIDA // Carta destruida
	}

	public enum VeredictoAtaque{
		NOSECUMPLENCOND, //-1: No se cumplen las condiciones de ataque
		EMPATE, 
		GANAATACANTE,  //Gana Atacante contra carta en Zona de Batalla
		PIERDEATACANTE, //Pierde Atacante
		BARRERADESTRUIDA, //Destruye una barrera
		ENEMIGOSINBARRERA //Termina el juego porque enemigo se quedo sin cartas de barrera (Termino 2)
	}
    public String ataqueCartaRealizadoDialogo(ResultadoAtaque resATK) {
    	String resp="";
    	resp+="Ataque Realizado!!\n";
        if(resATK.veredicto == Jugador.VeredictoAtaque.GANAATACANTE)
        	resp+="Victoria!!\n";
        else if(resATK.veredicto == Jugador.VeredictoAtaque.PIERDEATACANTE)
        	resp+="Derrota!!\n";
        else if(resATK.veredicto == Jugador.VeredictoAtaque.EMPATE)
        	resp+="Empate!!\n";
        resp+="     Tu Carta     |        Enemigo         \n";
        resp+= resATK.cartaAtacante.getValor()+" ("+Carta.obtenerStringElementoUnicode(resATK.cartaAtacante.getElemento())+") " +
                "(Al Ataque)"+
                "  |   " +
                resATK.cartaAtacada.getValor()+" ("+Carta.obtenerStringElementoUnicode(resATK.cartaAtacada.getElemento())+") "+
                (resATK.posicionCartaAtacada == ZonaBatalla.PosBatalla.ATAQUE? "(Al Ataque)" : "(A la Defensa) ") +"\n";
        if(resATK.estadoBarrera == Jugador.EstadoCarta.DESTRUIDA)
            resp+="Barrera enemiga destruida\n";
        if(resATK.estadoCartaAtacante == Jugador.EstadoCarta.DESTRUIDA)
            resp+="Tu Carta en Zona de Batalla ha sido destruida\n";
        if(resATK.estadoCartaAtacada == Jugador.EstadoCarta.DESTRUIDA)
            resp+="Carta enemiga en Zona de Batalla destruida\n";
        return resp;
    }
	public ResultadoAtaque accionAtacarCarta(Jugador JugadorAtacado, int idCartaAtacada, int idCartaAtacante){//Sistema de produccion
		ResultadoAtaque rsAtaque = new ResultadoAtaque();
		rsAtaque.veredicto = VeredictoAtaque.NOSECUMPLENCOND;
		rsAtaque.estadoBarrera = EstadoCarta.ACTIVA;
		Carta cartaAtacante = null;
		Carta cartaAtacada = null;

		if( posibilidadAtacarCarta(JugadorAtacado,idCartaAtacada,idCartaAtacante) ){

			//Jugador Atacado en defensa cara abajo, se revela la carta.
			if(JugadorAtacado.ZBatalla.posBatalla[idCartaAtacada] == ZonaBatalla.PosBatalla.DEFCARAABAJO){
				JugadorAtacado.ZBatalla.posBatalla[idCartaAtacada] = ZonaBatalla.PosBatalla.DEFCARAARRIBA;
			}

			cartaAtacante = this.ZBatalla.obtenerCartaxId(idCartaAtacante);
			cartaAtacada = JugadorAtacado.ZBatalla.obtenerCartaxId(idCartaAtacada);
			rsAtaque.cartaAtacante = cartaAtacante;
			rsAtaque.cartaAtacada = cartaAtacada;

			if(rsAtaque.cartaAtacante.getValor() > rsAtaque.cartaAtacada.getValor() ){
				rsAtaque.veredicto = VeredictoAtaque.GANAATACANTE;//gana
				rsAtaque.estadoCartaAtacante = EstadoCarta.ACTIVA;
				rsAtaque.estadoCartaAtacada = EstadoCarta.DESTRUIDA;
			}
			else if(rsAtaque.cartaAtacante.getValor()  < rsAtaque.cartaAtacada.getValor()){
				rsAtaque.veredicto = VeredictoAtaque.PIERDEATACANTE;//pierde
				rsAtaque.estadoCartaAtacante = EstadoCarta.DESTRUIDA;
				rsAtaque.estadoCartaAtacada = EstadoCarta.ACTIVA;
			}
			else{
				rsAtaque.veredicto = VeredictoAtaque.EMPATE;//empata
			}

			//Jugador Atacado al Ataque
			if(JugadorAtacado.ZBatalla.posBatalla[idCartaAtacada] == ZonaBatalla.PosBatalla.ATAQUE){
				rsAtaque.posicionCartaAtacada = ZonaBatalla.PosBatalla.ATAQUE;
				if(rsAtaque.veredicto == VeredictoAtaque.GANAATACANTE){
					JugadorAtacado.ZBatalla.quitarCartaenPos(idCartaAtacada);
					JugadorAtacado.Barrera.quitarUltimaCartaDisponible();
					rsAtaque.estadoBarrera = EstadoCarta.DESTRUIDA;
					if(JugadorAtacado.Barrera.obtenerNumerodeCartas() == 0){
						rsAtaque.veredicto = VeredictoAtaque.ENEMIGOSINBARRERA;
					}
				}
				else if(rsAtaque.veredicto == VeredictoAtaque.PIERDEATACANTE){//pierde atacante
					this.ZBatalla.quitarCartaenPos(idCartaAtacante);
				}
				else{//Empate
					JugadorAtacado.ZBatalla.quitarCartaenPos(idCartaAtacada);
					this.ZBatalla.quitarCartaenPos(idCartaAtacante);
					rsAtaque.estadoCartaAtacante = EstadoCarta.DESTRUIDA;
					rsAtaque.estadoCartaAtacada = EstadoCarta.DESTRUIDA;
				}
			}
			else if(JugadorAtacado.ZBatalla.posBatalla[idCartaAtacada] == ZonaBatalla.PosBatalla.DEFCARAARRIBA ){//Jugador Atacado a la Defensa
				rsAtaque.posicionCartaAtacada = ZonaBatalla.PosBatalla.DEFCARAARRIBA;
				if(rsAtaque.veredicto == VeredictoAtaque.GANAATACANTE){//gana atacante
					JugadorAtacado.ZBatalla.quitarCartaenPos(idCartaAtacada);
				}
				else if(rsAtaque.veredicto == VeredictoAtaque.PIERDEATACANTE){//pierde atacante
					this.ZBatalla.quitarCartaenPos(idCartaAtacante);
				}
				else{//Empate
					rsAtaque.estadoCartaAtacante = EstadoCarta.ACTIVA;
					rsAtaque.estadoCartaAtacada = EstadoCarta.ACTIVA;
				}
			}
			
			this.ZBatalla.nAtaquesDisponibles--;

			this.ZBatalla.dispAtaque[idCartaAtacante] = ZonaBatalla.DispAtaque.NODISPONIBLE;
            this.ZBatalla.dispCambio[idCartaAtacante] = ZonaBatalla.DispCambio.NODISPONIBLE;
		}
		logger.debug(ataqueCartaRealizadoDialogo(rsAtaque));
		return rsAtaque;
	}
	
	public boolean puedeCambiarPosicion(){		
		return ZBatalla.puedeCambiarPosicion();
	}
	public boolean posibilidadCambiarPosicionBatallaEnCarta(int idCartaZBJAct) {
		return ZBatalla.posibilidadCambiarPosicionBatallaEnCarta(idCartaZBJAct);
	}
	public boolean accionCambiarPosicionBatalla(int idCartaZBJAct){
		boolean resp=ZBatalla.cambiarPosicionBatalla(idCartaZBJAct);
		if(resp) {
			logger.debug("Cambio de Posición Realizado!!\n");
		}
		return resp;
	}

	//endregion


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Jugador)) return false;

		Jugador jugador = (Jugador) o;

		if (NTurnos != jugador.NTurnos) return false;
		if (Nombre != null ? !Nombre.equals(jugador.Nombre) : jugador.Nombre != null) return false;
		if (Deck != null ? !Deck.equals(jugador.Deck) : jugador.Deck != null) return false;
		if (Mano != null ? !Mano.equals(jugador.Mano) : jugador.Mano != null) return false;
		if (Barrera != null ? !Barrera.equals(jugador.Barrera) : jugador.Barrera != null) return false;
		return ZBatalla != null ? ZBatalla.equals(jugador.ZBatalla) : jugador.ZBatalla == null;
	}
	public Object clone() throws CloneNotSupportedException{
		Jugador clon= (Jugador) super.clone();
		clon.Deck=Deck;
		clon.Mano=Mano;
		clon.Barrera=Barrera;
		clon.ZBatalla=ZBatalla;
		clon.Nombre=Nombre;
		return clon;
	}
	
	public void repartirCartas(){
    	logger.debug("repartirCartas");
    	logger.debug("Jugador: {}",getNombre());
    	boolean cartasElegidas[][];
    	cartasElegidas=new boolean[Carta.getNumeroElementosCartas()][Carta.MAXVALORCARTA];
    	Random rm;
        int n,m,cartasRepartidas;

        for(int i=0;i<cartasElegidas.length;i++){
            for(int j=0;j<cartasElegidas[i].length;j++){
            	cartasElegidas[i][j] = false;
            }
        }

        cartasRepartidas=0;

        int numeroElementosCartas = Carta.getNumeroElementosCartas();
        
        while(cartasRepartidas < Deck.MAXDECK ){
            rm=new Random();
           
            n=rm.nextInt(numeroElementosCartas);
            rm=new Random();
            m=rm.nextInt(Carta.MAXVALORCARTA);

            if(!cartasElegidas[n][m]){
            	cartasElegidas[n][m]=true;

                cartasRepartidas++;
                Carta c=new Carta(m+1,Carta.Elemento.values()[n]);
                if(Barrera.obtenerNumerodeCartas()<Barrera.MAXBARRERACARDS){
                    Barrera.agregarCartaEnEspacioVacio(c);
                    logger.debug("Barrera: {} {}", c.getValor(), c.getElemento());
                }
                else if(Mano.obtenerNumerodeCartas()<Mano.MAXMANOCARDS){
                    Mano.agregarCartaEnEspacioVacio(c);
                    logger.debug("Mano: {} {}", c.getValor(), c.getElemento());
                }
                else{
                    Deck.agregarUnaCarta(c);
                    logger.debug("Deck: {} {}", c.getValor(), c.getElemento());
                }

            }
        }
    }
}
