package net.avalith.elections.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "elections")
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @JsonProperty("start_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull
    //@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @JsonProperty("end_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull
    //@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    @JsonIgnoreProperties(value = "election")
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "candidate")
    private List<ElectionCandidate> electionCandidates;

}
