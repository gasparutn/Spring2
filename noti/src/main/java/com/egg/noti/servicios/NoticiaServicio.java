package com.egg.noti.servicios;

import com.egg.noti.entidades.Noticia;
import com.egg.noti.excepciones.MiException;
import com.egg.noti.repositorios.NotiRepositorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NoticiaServicio {

    @Autowired
    private NotiRepositorio notiRepositorio;

    @Transactional
    public void crearNoticia(Long id, String titulo, String cuerpo, String foto) throws MiException, IOException {

        validar(titulo, cuerpo, foto);
        
        Noticia noticia = new Noticia();
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticia.setFoto(foto);
        noticia.setAlta(new Date());
        notiRepositorio.save(noticia);
    }

    @Transactional
    public void modificarNoticia(Long id, String titulo, String cuerpo, MultipartFile foto) throws MiException {

        Optional<Noticia> respuesta = notiRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            noticia.setAlta(new Date());
            noticia.setFoto(foto.getOriginalFilename());
            notiRepositorio.save(noticia);
        }
    }

    @Transactional(readOnly = true) // propiedad de s�lo lectura sin modificacion de base de Datos
    public List<Noticia> listarNoticias() {
        List<Noticia> noticias = new ArrayList();
        noticias = notiRepositorio.findAll();
        return noticias;
    }

    public Noticia getOne(long id) {
        return notiRepositorio.getOne(id);
    }

    @Transactional(readOnly = true)
    public Noticia listarPorId(long id) throws MiException {
        Optional<Noticia> optional = notiRepositorio.findById(id);
        if (!optional.isPresent()) {
            throw new MiException("No existe noticia con el titulo proporcionado");
        }
        Noticia noticia = optional.get();
        return noticia;
    }

    
    @Transactional
    public void eliminar(long id) {
        notiRepositorio.deleteById(id);

    }

    private void validar(String titulo, String cuerpo, String foto) throws MiException {

        if (titulo == null || titulo.isEmpty()) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if (cuerpo == null || cuerpo.isEmpty()) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if (foto == null || cuerpo.isEmpty()) {
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }

    }

}
