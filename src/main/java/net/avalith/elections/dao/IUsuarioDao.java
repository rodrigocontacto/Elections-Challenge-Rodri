package net.avalith.elections.dao;

import net.avalith.elections.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsuarioDao extends JpaRepository<Usuario, String> {
    List<Usuario> findByIsFake(boolean isfake);

}
