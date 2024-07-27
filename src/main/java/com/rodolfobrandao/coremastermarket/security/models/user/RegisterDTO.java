package com.rodolfobrandao.coremastermarket.security.models.user;

public record RegisterDTO(String login, String password, UserRole role, String name, String fullname, String username, String profileImg ) {

}
