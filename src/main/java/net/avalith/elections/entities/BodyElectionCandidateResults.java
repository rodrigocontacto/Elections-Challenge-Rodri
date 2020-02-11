package net.avalith.elections.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BodyElectionCandidateResults {
    @JsonProperty(value = "id_election")
    Long idElection;
    List<BodyCandidate> candidates = new ArrayList<BodyCandidate>();
    @JsonProperty(value = "total_votes")
    long totalVotes;

}
