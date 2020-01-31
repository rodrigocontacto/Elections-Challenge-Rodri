package net.avalith.elections.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
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

    @JsonIgnoreProperties(value = {"candidate","election"})
    @JsonProperty("candidate_ids")
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "election")
    private List<ElectionCandidate> electionsCandidates;




}
