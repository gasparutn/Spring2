package com.egg.noti.repositorios;


import com.egg.noti.entidades.Noticia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotiRepositorio extends JpaRepository<Noticia, Long> {
 @Query("SELECT n FROM Noticia n WHERE n.id = :id")
  List<Noticia> obtenerNotiPorId();

   


}
