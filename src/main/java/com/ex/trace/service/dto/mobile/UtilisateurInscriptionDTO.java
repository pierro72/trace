package com.ex.trace.service.dto.mobile;


import com.ex.trace.validator.PasswordMatches;
import com.ex.trace.validator.ValidEmail;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * A DTO representing a user, with his authorities.
 */
@PasswordMatches
public class UtilisateurInscriptionDTO {

    @NotBlank
    @Size(min = 1, max = 50)
    private String username;

    @ValidEmail
    @NotNull
    private String email;

    @Column( length = 100) @NotNull @Size(min = 4, max = 100)
    private String password;

    private String matchingPassword;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

}
