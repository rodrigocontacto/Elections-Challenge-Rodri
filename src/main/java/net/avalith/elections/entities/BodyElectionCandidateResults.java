package net.avalith.elections.entities;

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
    Long id_election;
    List<BodyCandidate> candidates = new ArrayList<BodyCandidate>();
    int total_votes;

}
