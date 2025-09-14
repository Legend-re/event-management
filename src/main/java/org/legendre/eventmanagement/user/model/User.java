package org.legendre.eventmanagement.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.legendre.eventmanagement.user.model.response.UserResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table
@Entity
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    @JsonIgnore
    private String password;
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Reason behind ROLE_ is for the security filter chain to requires the prefix especially because we used .hasRole("ADMIN") for the endpoints
        // If we used .hasAuthority("ADMIN"), then we'd be allowed to use just ADMIN and ROLE bellow
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
    }

    public UserResponse toResponse() {
        return new UserResponse(username, firstName, lastName, role);
    }
}
