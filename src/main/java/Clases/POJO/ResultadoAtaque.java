package Clases.POJO;

import Clases.Jugador;

public class ResultadoAtaque
{
    public int resultado;
    public int cartaAtacante;
    public int cartaAtacado;
    public int barrera;
    public int idbarrera;

    public ResultadoAtaque(){
        resultado = Jugador.RESULTADOATACARCARTA.NOSECUMPLENCOND;
        cartaAtacante = Jugador.RESULTADOCARTA.UP;
        cartaAtacado = Jugador.RESULTADOCARTA.UP;
        barrera = Jugador.RESULTADOCARTA.UP;
        idbarrera= -1;
    }
}
