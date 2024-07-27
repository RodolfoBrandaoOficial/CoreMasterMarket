package com.rodolfobrandao.coremastermarket.security.models.user;

//ADICIONAR UIDD para id
public record AuthInfo( Long id, String login, String role, String name, String fullname, String username, String profileImg) {
}
