package com.ex.trace.domaine;

import com.ex.trace.domaine.security.Utilisateur;
import org.hibernate.annotations.CreationTimestamp;

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

    @NotNull
    private int         totalLike;

    @NotNull
    private int         totalSignalement;

    //RELATION
    @OneToMany(mappedBy = "message")
    private Set<Recommandation> recommandations = new HashSet<>();

    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY)
    private Set<Signalement> signalements = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private Utilisateur autheur;


    @PrePersist
    private void onCreate() {
        estVerifier         = false;
        estDouteux          = Pattern.matches("fuck", contenu);
        totalLike           = 0;
        totalSignalement    = 0;
    }

    public Message(String contenu, Utilisateur autheur) {
        this.contenu = contenu;
        this.autheur = autheur;
    }

    public Message() { }


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

    public int getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(int totalLike) {
        this.totalLike = totalLike;
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
}
