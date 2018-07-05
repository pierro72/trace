package com.ex.trace.validator;

import com.ex.trace.service.dto.mobile.UtilisateurInscriptionDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UtilisateurInscriptionDTO user = (UtilisateurInscriptionDTO) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
