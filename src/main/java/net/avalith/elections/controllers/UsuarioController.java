package net.avalith.elections.controllers;


import net.avalith.elections.models.entity.Usuario;
import net.avalith.elections.models.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping("/usuarios")
	public List<Usuario> index() {
		return usuarioService.findAll();
	}

	@GetMapping("/usuarios/{id}")
	public Usuario show(@PathVariable Long id) {
		return this.usuarioService.findById(id);
	}

	@PostMapping("/usuarios")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario create(@RequestBody Usuario usuario) {
		this.usuarioService.save(usuario);
		return usuario;
	}

	@PutMapping("/usuarios/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario update(@RequestBody Usuario usuario, @PathVariable Long id) {
		Usuario currentUsuario= this.usuarioService.findById(id);
		currentUsuario.setNombre(usuario.getNombre());
		currentUsuario.setApellido(usuario.getApellido());
		currentUsuario.setEmail(usuario.getEmail());
		return currentUsuario;
	}
	
	@DeleteMapping("/usuarios/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		this.usuarioService.delete(id);
	}
}

