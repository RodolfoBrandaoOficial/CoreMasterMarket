package com.rodolfobrandao.coremastermarket.security.models.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Table(name = "users")
@Entity(name = "users")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@Builder
@ToString
@Data
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    private String id;
    private String login;
    private String password;
    private UserRole role;
    private String name;
    private String fullname;
    private String username;


    @Column(name = "profile_img", columnDefinition = "TEXT")
    private String profileImg;



    public User(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "{\n" +
                "            \"id\": \"" + id + "\",\n" +
                "            \"login\": \"" + login + "\",\n" +
                "            \"password\": \"" + password + "\",\n" +
                "            \"role\": \"" + role + "\",\n" +
                "            \"name\": \"" + name + "\",\n" +
                "            \"fullname\": \"" + fullname + "\",\n" +
                "            \"username\": \"" + username + "\",\n" +
                "            \"profileImg\": " + profileImg + "\n" +
                "        }";
    }


    public String getId() {
        return String.valueOf(this.id);
    }

//    public User() {
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }


    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
