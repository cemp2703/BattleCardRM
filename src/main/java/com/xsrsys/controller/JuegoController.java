package com.xsrsys.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xsrsys.dto.Accion;
import com.xsrsys.service.Juego;

@RequestMapping("/juego")
public class JuegoController {
	Juego j=new Juego();
}
