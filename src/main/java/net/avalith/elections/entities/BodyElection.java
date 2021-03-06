package net.avalith.elections.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BodyElection {

    @JsonProperty("start_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull
    //@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull
    //@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @JsonProperty("candidate_ids")
    private List<Long> electionCandidates;

}
