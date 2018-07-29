package com.ex.trace.domaine;

import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.security.JwtUtilisateur;
import com.ex.trace.security.repository.UtilisateurRepository;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long      id;

    @Size(max = 1024) @Column (length = 1024)  @NotNull
    private String      contenu;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column( nullable = false)
    private Date        date;

    @NotNull
    private boolean     estDouteux;

    @NotNull
    private boolean     estVerifier;

    //RELATION
    @OneToMany(mappedBy = "message")
    private Set<Recommandation> recommandations = new HashSet<>();

    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY)
    private Set<Signalement> signalements = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private Utilisateur autheur;

    //CALCULATED
    @Transient
    private int         totalRecommandation;

    @Transient
    private int         totalSignalement;

    @Transient
    private boolean     recommande;

    @Transient
    private boolean     signale;

    @Transient
    private boolean     proprietaire;

    //CONSTRUCTEUR
    public Message(String contenu, Utilisateur autheur) {
        this.contenu = contenu;
        this.autheur = autheur;
    }

    public Message() { }

    //METHODE
    @PrePersist
    private void onCreate() {
        estVerifier         = false;
        estDouteux          = Pattern.matches("fuck", contenu);
    }

    @PostLoad
    private void postLoadMessage() {

        JwtUtilisateur utilisateur = (JwtUtilisateur)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.totalRecommandation    = this.getRecommandations().size();
        this.totalSignalement       = this.getSignalements().size();
        this.recommande             = this.estRecommande ( utilisateur);
        this.proprietaire           = this.estProprietaire ( utilisateur);
        this.signale                = this.estSignale ( utilisateur);
    }

    private  boolean estProprietaire (JwtUtilisateur utilisateur) {
        return this.getAutheur().getId().longValue() == utilisateur.getId().longValue();
    }

    private  boolean estRecommande(JwtUtilisateur utilisateur) {
        boolean estLike = false;
        for ( Recommandation recommandation : this.getRecommandations()){
            if ( recommandation.getUtilisateur().getId().longValue() == utilisateur.getId().longValue() ){
                estLike = true;
                break;
            }
        }
        return estLike;
    }

    private  boolean estSignale ( JwtUtilisateur utilisateur){
        boolean estSignale = false;
        for ( Signalement signalement : this.getSignalements()){
            if ( signalement.getUtilisateur().getId().longValue() == utilisateur.getId().longValue()  ){
                estSignale = true;
                break;
            }
        }
        return estSignale;
    }

    //GETTER SETTER
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isEstDouteux() {
        return estDouteux;
    }

    public void setEstDouteux(boolean estDouteux) {
        this.estDouteux = estDouteux;
    }

    public boolean isEstVerifier() {
        return estVerifier;
    }

    public void setEstVerifier(boolean estVerifier) {
        this.estVerifier = estVerifier;
    }

    public Set<Recommandation> getRecommandations() {
        return recommandations;
    }

    public void setRecommandations(Set<Recommandation> recommandations) {
        this.recommandations = recommandations;
    }

    public Set<Signalement> getSignalements() {
        return signalements;
    }

    public void setSignalements(Set<Signalement> signalements) {
        this.signalements = signalements;
    }

    public Utilisateur getAutheur() {
        return autheur;
    }

    public void setAutheur(Utilisateur autheur) {
        this.autheur = autheur;
    }

    public int getTotalSignalement() {
        return totalSignalement;
    }

    public void setTotalSignalement(int totalSignalement) {
        this.totalSignalement = totalSignalement;
    }


    public int getTotalRecommandation() {
        return totalRecommandation;
    }

    public void setTotalRecommandation(int totalRecommandation) {
        this.totalRecommandation = totalRecommandation;
    }

    public boolean isRecommande() {
        return recommande;
    }

    public void setRecommande(boolean recommande) {
        this.recommande = recommande;
    }

    public boolean isSignale() {
        return signale;
    }

    public void setSignale(boolean signale) {
        this.signale = signale;
    }

    public boolean isProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(boolean proprietaire) {
        this.proprietaire = proprietaire;
    }
}
