package net.avalith.elections.service;

import java.util.*;
import java.util.stream.Collectors;

import net.avalith.elections.dao.IUsuarioDao;
import net.avalith.elections.entities.BodyFakeUser;
import net.avalith.elections.entities.BodyFakeUsersCount;
import net.avalith.elections.entities.BodyFakeUsersResults;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


@Service
public class UsuarioServiceImpl{

	@Autowired
	private IUsuarioDao usuarioDao;

	@Autowired
	private CandidateServiceImpl candidateService;

	@Transactional(readOnly=true)
	public List<Usuario> findAll() {
		return (List<Usuario>)usuarioDao.findAll();
	}

	@Transactional
	public Usuario save(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

	@Transactional(readOnly=true)
	public Usuario findById(String id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Transactional
	public void delete(String id) {
		usuarioDao.deleteById(id);

	}

	public ResponseEntity<?> createUser(@Valid @RequestBody Usuario usuario, BindingResult result){
		Usuario usuarioNew = null;
		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			usuario.setId(UUID.randomUUID().toString());
			usuarioNew = this.save(usuario);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario ha sido creado con éxito!");
		response.put("usuario", usuarioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	public void saveAll(List<Usuario> users){
		for(Usuario user : users){
			this.save(user);
		}
	}

	public ResponseEntity<?> createFakeUser(BodyFakeUsersCount bodyFakeUsersCount){
		Map<String, Object> response = new HashMap<>();
		String url = "https://randomuser.me/api/?results="+ bodyFakeUsersCount.getQuantity();
		RestTemplate restTemplate = new RestTemplate();
		BodyFakeUsersResults fakeUsers = restTemplate.getForObject(url, BodyFakeUsersResults.class);

		if(fakeUsers != null){
			List<Usuario> users = fakeUsers.getResults().stream()
					.map(fakeUser -> Usuario.builder()
							.id(fakeUser.getLogin().getUuid())
							.nombre(fakeUser.getName().getFirst())
							.apellido(fakeUser.getName().getLast())
							.email(fakeUser.getEmail())
							.isFake(true)
							.build())
					.collect(Collectors.toList());

			this.saveAll(users);
		}else{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"error al crear user fake");
		}

		response.put("mensaje", "Los usuarios fake han sido creados con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	public List<Usuario> findFakes(){
		return usuarioDao.findByIsFake(true);
	}
	

}
