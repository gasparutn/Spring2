package com.egg.noti.controladores;

import com.egg.noti.entidades.Noticia;
import com.egg.noti.excepciones.MiException;
import com.egg.noti.servicios.NoticiaServicio;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class NotiControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;

    @GetMapping("/registrar")   //localhost:8080/noticia/registrar
    public String registrar() {

        return "cargar_noticia.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Noticia> noticias = noticiaServicio.listarNoticias();

        modelo.addAttribute("noticias", noticias);

        return "lista_noticia.html";
    }

    @PostMapping("admin/guardar")
    public String guardar(@RequestParam(required = false) Long id,
            @RequestParam String titulo, @RequestParam String cuerpo,
            @RequestParam("File") MultipartFile foto, ModelMap modelo) throws IOException {

        if (!foto.isEmpty()) {
            //  Path directorioImagenes = Paths.get("src//main//resources//static//foto");
            String rutaAbsoluta = "C://Users/gaspa//Documents//NetBeansProjects//SPRING_1//images";
            try {
                byte[] bytesImg = foto.getBytes();
                Path rutacompleta = Paths.get(rutaAbsoluta + "//" + foto.getOriginalFilename());
                Files.write(rutacompleta, bytesImg);

            } catch (IOException ex) {
            }
        }
        try {

            noticiaServicio.crearNoticia(id, titulo, cuerpo, foto.getOriginalFilename());
            modelo.put("exito", "La Noticia fue cargada correctamente!");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "cargar_noticia.html";
        }
        return "cargar_noticia.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo) {

        modelo.put("noticia", noticiaServicio.getOne(id));
        return "modifica_noticia.html";

    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, String titulo, String cuerpo, MultipartFile foto, ModelMap modelo) {

        try {
            noticiaServicio.modificarNoticia(id, titulo, cuerpo, foto);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return "modifica_noticia.html";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(Model model, @PathVariable long id) {
        noticiaServicio.eliminar(id);
        return "redirect:../lista";
    }
}
