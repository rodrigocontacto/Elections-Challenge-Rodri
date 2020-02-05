package net.avalith.elections.entities;

import lombok.Data;

@Data
public class BodyFakeUser {
    private BodyFakeUserDetails name;
    private BodyFakeUserUUID login;
    private String email;
}
