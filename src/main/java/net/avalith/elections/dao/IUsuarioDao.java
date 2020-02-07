package net.avalith.elections.dao;

import net.avalith.elections.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsuarioDao extends JpaRepository<User, String> {
    List<User> findByIsFake(boolean isfake);

}
