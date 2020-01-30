package net.avalith.elections.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_candidate")
    private Long id;

    @JsonProperty("first_name")
    @NotEmpty(message ="no puede estar vacio")
    @Size(min=3, max=30, message="el tamaño tiene que estar entre 3 a 30")
    //@Column(name="first_name", nullable = false)
    private String firstName;

    @JsonProperty("last_name")
    @NotEmpty(message ="no puede estar vacio")
    @Size(min=3, max=30, message="el tamaño tiene que estar entre 3 a 30")
    //@Column(name="last_name")
    private String lastName;


}
