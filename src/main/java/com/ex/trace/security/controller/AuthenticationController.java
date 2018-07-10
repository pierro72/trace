package com.ex.trace.security.controller;

import com.ex.trace.exception.AuthenticationException;
import com.ex.trace.security.JwtRequeteAuthentification;
import com.ex.trace.security.JwtTokenUtil;
import com.ex.trace.security.JwtUtilisateur;
import com.ex.trace.security.JwtReponseAuthentification;
import com.ex.trace.service.UtilisateurService;
import com.ex.trace.service.mapper.UtilisateurMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class AuthenticationController {

    @Value("${jwt.header}") private String tokenHeader;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    private UtilisateurService utilisateurService;
    private UtilisateurMapper utilisateurMapper;

/*    @Autowired
    @Qualifier("utilisateurService")
    private UserDetailsService utilisateurService;*/


    public AuthenticationController( UtilisateurService utilisateurService, UtilisateurMapper utilisateurMapper){
        this.utilisateurService = utilisateurService;
        this.utilisateurMapper = utilisateurMapper;

    }

/*    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, Device device) throws org.springframework.security.core.AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails, device);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }*/

    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> creerTokenAuthentification(@RequestBody JwtRequeteAuthentification requete, Device device) throws AuthenticationException {
        
        this.authentifier( requete.getUsername(), requete.getPassword());
        final UserDetails userDetails   = utilisateurService.loadUserByUsername( requete.getUsername());
        final String token              = jwtTokenUtil.generateToken( userDetails, device);

        return ResponseEntity.ok( new JwtReponseAuthentification(token) );
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> rafraichirEtObtenirTokenAuthentification(HttpServletRequest requete) {
        String authToken            = requete.getHeader(tokenHeader);
        final String token          = authToken.substring(7);
        String username             = jwtTokenUtil.getUsernameFromToken(token);
        JwtUtilisateur utilisateur  = (JwtUtilisateur) utilisateurService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed( token, utilisateur.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtReponseAuthentification(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * authentifier l'utilisateur. If something is wrong, an {@link AuthenticationException} will be thrown
     */
    private void authentifier(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try { authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) { throw new AuthenticationException("Utilisateur is disabled!", e);
        } catch (BadCredentialsException e) { throw new AuthenticationException("Bad credentials!", e);
        }
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}
