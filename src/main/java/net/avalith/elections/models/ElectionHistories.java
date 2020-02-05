package net.avalith.elections.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "election_histories")
public class ElectionHistories {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "candidate")
    private List<ElectionCandidate> electionCandidates;

    @JsonIgnoreProperties("electionHistories")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id")
    private Election election;

    private Float percentage;
    private Timestamp date;
    private Integer votes;

}

