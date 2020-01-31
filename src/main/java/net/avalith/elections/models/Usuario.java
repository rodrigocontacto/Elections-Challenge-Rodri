package net.avalith.elections.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Long id;

	@JsonProperty("first_name")
	@NotEmpty(message ="no puede estar vacio")
	@Size(min=3, max=30, message="el tamaño tiene que estar entre 3 a 30")
	//@Column(name="first_name", nullable = false)
	private String nombre;

	@JsonProperty("last_name")
	@NotEmpty(message ="no puede estar vacio")
	@Size(min=3, max=30, message="el tamaño tiene que estar entre 3 a 30")
	//@Column(name="last_name")
	private String apellido;

	@NotEmpty(message ="no puede estar vacio")
	@Email(message="no es una dirección de correo bien formada")
	@Column(unique=true)
	private String email;

}
