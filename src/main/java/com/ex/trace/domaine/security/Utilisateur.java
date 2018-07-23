package com.ex.trace.domaine.security;

import com.ex.trace.domaine.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Utilisateur {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long    id;

    @Column(length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String  username;

    @Column( length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String  password;

    @Column( length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String  email;

    @NotNull
    private Boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date    lastPasswordResetDate;

    @ManyToMany(cascade=CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name                = "utilisateur_authority",
            joinColumns         = {@JoinColumn( name = "utilisateur_id", referencedColumnName = "id")},
            inverseJoinColumns  = {@JoinColumn( name = "authority_id", referencedColumnName = "id")})
    private List<Authority>     authorities;

    //RELATION
    @OneToMany(mappedBy = "autheur", fetch = FetchType.LAZY)
    private List<Message>       messages;

    /*FIXME:*/
    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    private Set<Visite> visites;

    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    private List<Recommandation> recommandations;

    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    private List<Signalement> signalements;

    @PrePersist
    private void onCreate() {
        enabled                     = true;
        lastPasswordResetDate       = new Date();
    }
    public void grantRole(Authority authority) {
        if (authorities == null) {
            authorities = new ArrayList<>();
        }
        authorities.add(authority);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }


    public Set<Visite> getVisites() {
        return visites;
    }

    public void setVisites(Set<Visite> visites) {
        this.visites = visites;
    }

    public List<Recommandation> getRecommandations() {
        return recommandations;
    }

    public void setRecommandations(List<Recommandation> recommandations) {
        this.recommandations = recommandations;
    }

    public List<Signalement> getSignalements() {
        return signalements;
    }

    public void setSignalements(List<Signalement> signalements) {
        this.signalements = signalements;
    }


}