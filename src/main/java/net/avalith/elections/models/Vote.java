package net.avalith.elections.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "votes",uniqueConstraints = @UniqueConstraint(columnNames = {"id_usuario", "id_election_candidate"}))
public class Vote {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_election_candidate", referencedColumnName = "id")
    private ElectionCandidate electionCandidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private User user;
}
