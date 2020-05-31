package com.xsrsys.cli;

import java.io.IOException;
import java.util.Scanner;

import com.xsrsys.model.Jugador;
import com.xsrsys.model.ZonaBatalla;
import com.xsrsys.service.Juego;
import com.xsrsys.service.Juego.Dialogo;
import com.xsrsys.service.Juego.MomentoJuego;
import com.xsrsys.service.Juego.Pantalla;

public class MainConsola {
	
    public static void main(String[] args) throws IOException, InterruptedException{
       JuegoCLI jcli=new JuegoCLI();
       jcli.hiloJuego();
    }
}
