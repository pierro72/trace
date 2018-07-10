package com.ex.trace.service;

import com.ex.trace.domaine.security.AuthorityType;
import com.ex.trace.domaine.security.Authority;
import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.security.repository.AuthorityRepository;
import com.ex.trace.security.repository.UtilisateurRepository;
import com.ex.trace.security.JwtUtilisateurFactory;
import com.ex.trace.validator.EmailExistsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementation de service pour  le management d'utilisateur
 */
@Service
@Transactional
public class UtilisateurService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;
    private final UtilisateurRepository utilisateurRepository;
    private final AuthorityRepository authorityRepository;
    
    public UtilisateurService(UtilisateurRepository utilisateurRepository, AuthorityRepository authorityRepository,PasswordEncoder passwordEncoder) {
        this.utilisateurRepository      = utilisateurRepository;
        this.authorityRepository        = authorityRepository;
        this.passwordEncoder            = passwordEncoder;
    }

    /**
     * Creer un utilisateur
     *
     * @param utilisateur l'utilisateur a sauvegarder
     * @return L'utilisateur persisté
     */
    public Utilisateur enregistrerUtilisateurMobile(Utilisateur utilisateur) throws EmailExistsException  {
        if (emailExist(utilisateur.getEmail())) {
            throw new EmailExistsException("There is an account with that email address:"  + utilisateur.getEmail());
        }
        Authority utilisateurMobileAuthority = authorityRepository.findByAuthorityType(AuthorityType.valueOf("ROLE_USER"));
        utilisateur.grantRole( utilisateurMobileAuthority );
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        Utilisateur result = utilisateurRepository.save(utilisateur);
        return result;
    }

    private boolean emailExist(String email) {
        Utilisateur utlisateur = utilisateurRepository.findOneByEmail(email);
        if (utlisateur != null) {
            return true;
        }
        return false;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username);

        if (utilisateur == null) {
            throw new UsernameNotFoundException(String.format("Pas d'ulisateur trouvé avec le username '%s'.", username));
        } else {
            return JwtUtilisateurFactory.create(utilisateur);
        }
    }
}
