package net.avalith.elections.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidateTest {
    private Long id;
    private String name;
    private String lastname;
}
