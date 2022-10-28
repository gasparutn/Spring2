package com.egg.noti.controladores;

import com.egg.noti.entidades.Noticia;
import com.egg.noti.servicios.NoticiaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class adminControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;

    @GetMapping
    public String listarNoticias(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listarNoticias();

        modelo.addAttribute("noticias", noticias);

        return "admin.html";
    }

    

    @GetMapping("/detalle/{id}")
    public String detalleNoticia(@PathVariable long id, ModelMap model) {
        model.put("noticia", noticiaServicio.getOne(id));

        return "imagen_noticia.html";
    }
}
