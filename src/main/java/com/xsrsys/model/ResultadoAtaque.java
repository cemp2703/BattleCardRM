package Clases.POJO;

import Clases.Jugador;
import Clases.ZonaBatalla;

public class ResultadoAtaque
{
    public int valorCartaAtacante;
    public int elementoCartaAtacante;
    public int valorCartaAtacada;
    public int elementoCartaAtacada;
    public int posicionCartaAtacada;
    public int resultado;
    public int cartaAtacante;
    public int cartaAtacada;
    public int barrera;
    public int idbarrera;

    public ResultadoAtaque(){
        valorCartaAtacante= -1;
        elementoCartaAtacante = -1;
        valorCartaAtacada= -1;
        elementoCartaAtacada = -1;
        posicionCartaAtacada = ZonaBatalla.POSBATALLA.NOHAYCARTA;
        resultado = Jugador.RESULTADOATACARCARTA.NOSECUMPLENCOND;
        cartaAtacante = Jugador.RESULTADOCARTA.UP;
        cartaAtacada = Jugador.RESULTADOCARTA.UP;
        barrera = Jugador.RESULTADOCARTA.UP;
        idbarrera= -1;
    }
}

