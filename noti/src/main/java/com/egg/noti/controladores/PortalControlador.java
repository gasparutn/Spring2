package com.egg.noti.controladores;

import com.egg.noti.excepciones.MiException;
import com.egg.noti.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    UsuarioServicio usuarioservicio;

    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @GetMapping("/registrar")
    public String regitrar() {
        return "user_registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, String password2, ModelMap modelo) {
        try {
            usuarioservicio.registrar(nombre, email, password, password2);
            modelo.put("exito", "Usuario registrado con exito");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "user_registro.html";
        }

    }

    @GetMapping("login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {

            modelo.put("error", "Usuario o Contrasena incorrecto!");
        }

        return "login.html";
    }

    @GetMapping("/inicio")
    public String inicio() {
        return "admin.html";
    }
}
