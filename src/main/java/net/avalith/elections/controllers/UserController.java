package net.avalith.elections.controllers;



import net.avalith.elections.models.User;
import net.avalith.elections.service.UserServiceImpl;
import net.avalith.elections.service.VoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private VoteServiceImpl voteService;

	@GetMapping("/users")
	public List<User> showAll() {

		return userService.findAll();
	}

	@GetMapping("/user/{id}")
	public User show(@PathVariable String id) {

		return this.userService.findById(id);
	}

	@PostMapping("/user")
	@ResponseStatus(HttpStatus.CREATED)
	public User create(@Valid @RequestBody User user, BindingResult result) {

        return userService.createUser(user, result);
    }
	
	@DeleteMapping("/user/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {

		this.userService.delete(id);
	}


}

