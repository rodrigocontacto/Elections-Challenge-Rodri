package net.avalith.elections.controllers;


import net.avalith.elections.entities.BodyFakeUserVote;
import net.avalith.elections.entities.BodyFakeUsersCount;
import net.avalith.elections.models.Usuario;
import net.avalith.elections.service.UsuarioServiceImpl;
import net.avalith.elections.service.VoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/api")
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl usuarioService;

	@Autowired
	private VoteServiceImpl voteService;

	@GetMapping("/users")
	public List<Usuario> index() {
		return usuarioService.findAll();
	}

	@GetMapping("/user/{id}")
	public Usuario show(@PathVariable String id) {
		return this.usuarioService.findById(id);
	}

	@PostMapping("/user")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result) {
        return usuarioService.createUser(usuario, result);
    }
/*
	@PutMapping("/usuarios/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario update(@RequestBody Usuario usuario, @PathVariable String id) {
		Usuario currentUsuario= this.usuarioService.findById(id);
		currentUsuario.setNombre(usuario.getNombre());
		currentUsuario.setApellido(usuario.getApellido());
		currentUsuario.setEmail(usuario.getEmail());
		return currentUsuario;
	}*/
	
	@DeleteMapping("/user/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {
		this.usuarioService.delete(id);
	}

	@PostMapping("/non-alcoholic-beer")
	public ResponseEntity<?> createFakeUser(@RequestBody BodyFakeUsersCount bodyFakeUsersCount){
		return usuarioService.createFakeUser(bodyFakeUsersCount);
	}

	@PostMapping("/non-alcoholic-beer/{id}")
	public ResponseEntity<?> vote(@PathVariable("id")Long electionId, @RequestBody BodyFakeUserVote candidate, BindingResult result){

		return voteService.createFakeVotes(electionId, candidate.getId_candidate(), result);
	}
}

