package net.avalith.elections.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BodyFakeUserVote {
    @JsonProperty(value = "id_candidate")
    private Long idCandidate;
}
