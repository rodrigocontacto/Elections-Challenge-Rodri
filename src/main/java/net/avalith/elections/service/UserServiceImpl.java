package net.avalith.elections.service;

import java.util.*;
import java.util.stream.Collectors;

import net.avalith.elections.dao.IUsuarioDao;
import net.avalith.elections.entities.BodyFakeUsersCount;
import net.avalith.elections.entities.BodyFakeUsersResults;
import net.avalith.elections.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


@Service
public class UserServiceImpl {

	@Autowired
	private IUsuarioDao usuarioDao;

	@Autowired
	private CandidateServiceImpl candidateService;

	@Transactional(readOnly=true)
	public List<User> findAll() {
		return (List<User>)usuarioDao.findAll();
	}

	@Transactional
	public User save(User user) {
		return usuarioDao.save(user);
	}

	@Transactional(readOnly=true)
	public User findById(String id) {
		return usuarioDao.findById(id).orElseThrow(() -> new IllegalStateException("El usuario no existe "));
	}

	@Transactional
	public void delete(String id) {
		usuarioDao.deleteById(id);

	}

	public User createUser(@Valid @RequestBody User user, BindingResult result){
		User userNew = null;


		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"errors");
		}

		try {
			user.setId(UUID.randomUUID().toString());
			userNew = this.save(user);
		} catch(DataAccessException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al realizar el insert en la base de datos");
		}

		return userNew;

	}
	@Value("${URL.Rest}")
	private String urlRest;

	public void createFakeUser(BodyFakeUsersCount bodyFakeUsersCount){

		String url = urlRest + bodyFakeUsersCount.getQuantity();
		RestTemplate restTemplate = new RestTemplate();
		BodyFakeUsersResults fakeUsers = restTemplate.getForObject(url, BodyFakeUsersResults.class);
		Optional.ofNullable(restTemplate.getForObject(url, BodyFakeUsersResults.class)).orElseThrow(() -> new IllegalStateException("No se obtienen users "));

		List<User> users = fakeUsers.getResults().stream()
				.map(fakeUser -> User.builder()
						.id(fakeUser.getLogin().getUuid())
						.nombre(fakeUser.getName().getFirst())
						.apellido(fakeUser.getName().getLast())
						.email(fakeUser.getEmail())
						.isFake(true)
						.build())
				.collect(Collectors.toList());

		usuarioDao.saveAll(users);
	}

	public List<User> findFakes(){
		return usuarioDao.findByIsFake(true);
	}
	

}
