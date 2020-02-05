package net.avalith.elections.controllers;


import net.avalith.elections.entities.BodyFakeUserVote;
import net.avalith.elections.entities.BodyFakeUsersCount;
import net.avalith.elections.models.User;
import net.avalith.elections.service.UserServiceImpl;
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

@RestController
public class UserController {

	@Autowired
	private UserServiceImpl usuarioService;

	@Autowired
	private VoteServiceImpl voteService;

	@GetMapping("/users")
	public List<User> index() {
		return usuarioService.findAll();
	}

	@GetMapping("/user/{id}")
	public User show(@PathVariable String id) {
		return this.usuarioService.findById(id);
	}

	@PostMapping("/user")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
        User userNew = usuarioService.createUser(user, result);
		response.put("mensaje", "El usuario ha sido creado con éxito!");
		response.put("usuario", userNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
	
	@DeleteMapping("/user/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {
		this.usuarioService.delete(id);
	}


}

