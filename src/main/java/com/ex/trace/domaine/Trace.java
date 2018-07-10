package com.ex.trace.domaine;

import com.ex.trace.TraceType;
import com.ex.trace.domaine.security.Utilisateur;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.regex.Pattern;

@Entity @Cacheable @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Trace {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long        id;

    @Size   (max = 1024) @Column (length = 1024)  @NotNull
    private String      contenu;

    @NotNull
    private double      positionX;

    @NotNull
    private double      positionY;

    @Temporal(TemporalType.DATE)
    private Date        date;

    @NotNull
    private boolean     estDouteux;

    @NotNull
    private boolean     estVerifier;

    @NotNull
    private String      codePays;

    @NotNull
    private TraceType   traceType;

    @NotNull
    private int         totalVue;

    @NotNull
    private int         totalLike;

    //RELATION
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private Utilisateur         autheur;

    @OneToMany(mappedBy = "trace")
    private Set<Commentaire>    commentaires = new HashSet<>();

    @OneToMany(mappedBy = "trace")
    private List<TraceVue> traceVues;

    @OneToMany(mappedBy = "trace")
    private List<TraceLike> traceLikes;

    @OneToMany(mappedBy = "trace")
    private List<TraceSignalement> traceSignalements;


    @PrePersist
    private void onCreate() {
        String[] countryCodes = Locale.getISOCountries();
        date        = new Date();
        estVerifier = false;
        estDouteux  = Pattern.matches("fuck", contenu);
        totalVue    = 0;
        totalLike   = 0;
    }


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

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public TraceType getTraceType() {
        return traceType;
    }

    public void setTraceType(TraceType traceType) {
        this.traceType = traceType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public String getCodePays() {
        return codePays;
    }

    public void setCodePays(String codePays) {
        this.codePays = codePays;
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

    public int getTotalVue() {
        return totalVue;
    }

    public void setTotalVue(int totalVue) {
        this.totalVue = totalVue;
    }

    public Utilisateur getAutheur() {
        return autheur;
    }

    public void setAutheur(Utilisateur autheur) {
        this.autheur = autheur;
    }

    public List<TraceVue> getTraceVues() {
        return traceVues;
    }

    public void setTraceVues(List<TraceVue> traceVues) {
        this.traceVues = traceVues;
    }

    public List<TraceLike> getTraceLikes() {
        return traceLikes;
    }

    public void setTraceLikes(List<TraceLike> traceLikes) {
        this.traceLikes = traceLikes;
    }

    public List<TraceSignalement> getTraceSignalements() {
        return traceSignalements;
    }

    public void setTraceSignalements(List<TraceSignalement> traceSignalements) {
        this.traceSignalements = traceSignalements;
    }

    public int getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(int totalLike) {
        this.totalLike = totalLike;
    }
}
