package com.xsrsys.model;

import com.xsrsys.model.Carta.Elemento;
import com.xsrsys.model.Jugador.ResultadoAtacarCarta;
import com.xsrsys.model.Jugador.ResultadoCarta;
import com.xsrsys.model.ZonaBatalla.PosBatalla;

public class ResultadoAtaque
{
    public int valorCartaAtacante;
    public Elemento elementoCartaAtacante;
    public int valorCartaAtacada;
    public Elemento elementoCartaAtacada;
    public PosBatalla posicionCartaAtacada;
    public ResultadoAtacarCarta resultado;
    public ResultadoCarta cartaAtacante;
    public ResultadoCarta cartaAtacada;
    public ResultadoCarta barrera;
    public int idbarrera;

    public ResultadoAtaque(){
        valorCartaAtacante= -1;
        elementoCartaAtacante = Elemento.COCO;
        valorCartaAtacada= -1;
        elementoCartaAtacada = Elemento.COCO;
        posicionCartaAtacada = ZonaBatalla.PosBatalla.NOHAYCARTA;
        resultado = Jugador.ResultadoAtacarCarta.NOSECUMPLENCOND;
        cartaAtacante = Jugador.ResultadoCarta.UP;
        cartaAtacada = Jugador.ResultadoCarta.UP;
        barrera = Jugador.ResultadoCarta.UP;
        idbarrera= -1;
    }
}

