package net.avalith.elections.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "election_candidate",
        uniqueConstraints = @UniqueConstraint(name = "unq_election_candidate",columnNames = {"election_id" , "candidate_id"}))
public class ElectionCandidate {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnoreProperties("electionCandidates")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id")
    private Election election;

    @JsonIgnoreProperties("electionCandidates")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "electionCandidate")
    private List<Vote> votes;

    private int countVotes;
}
