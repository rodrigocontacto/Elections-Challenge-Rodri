package net.avalith.elections.models.service;

import net.avalith.elections.models.entity.Usuario;

import java.util.List;

public interface IUsuarioService {
	public List<Usuario> findAll();

	public Usuario save(Usuario usuario);

	public Usuario findById(Long id);

	public void delete(Long id);

}
