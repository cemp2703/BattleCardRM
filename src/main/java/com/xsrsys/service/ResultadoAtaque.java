package com.xsrsys.model;

import com.xsrsys.model.Carta.Elemento;
import com.xsrsys.model.Jugador.EstadoCarta;
import com.xsrsys.model.Jugador.VeredictoAtaque;
import com.xsrsys.model.ZonaBatalla.PosBatalla;

public class ResultadoAtaque
{
	public Carta cartaAtacante;
	public Carta cartaAtacada;
    public PosBatalla posicionCartaAtacada;
    public VeredictoAtaque veredicto;
    public EstadoCarta estadoCartaAtacante;
    public EstadoCarta estadoCartaAtacada;
    public EstadoCarta estadoBarrera;
    
}

