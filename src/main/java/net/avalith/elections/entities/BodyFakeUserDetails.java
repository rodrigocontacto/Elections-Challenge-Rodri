package net.avalith.elections.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BodyFakeUserDetails {
    private String first;
    private String last;
}
