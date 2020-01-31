package net.avalith.elections.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "elections_candidates")
public class ElectionCandidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_election_candidate")
    private Long id;

    @JsonIgnoreProperties(value = "electionCandidates")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id",
            referencedColumnName = "id")
    private Election election;

    @JsonIgnoreProperties(value = "electionsCandidates")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id",
            referencedColumnName = "id")
    private Candidate candidate;


}
