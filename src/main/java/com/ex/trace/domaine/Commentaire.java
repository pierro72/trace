package com.ex.trace.domaine;
import com.ex.trace.domaine.security.Utilisateur;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity @Cacheable @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Commentaire {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Size (max = 1024) @Column (length = 1024)  @NotNull
    private String contenu;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private Trace trace;

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }

    @NotNull
    private boolean     estDouteux;

    @NotNull
    private boolean     estVerifier;

    @NotNull
    private int         totalLike;

    //RELATION
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private Utilisateur autheur;

    @OneToMany( mappedBy = "commentaire")
    private List<CommentaireLike> commentaireLike;

    @OneToMany( mappedBy = "commentaire")
    private List<CommentaireSignalement> commentaireSignalement;


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

    public Trace getTrace() {
        return trace;
    }

    public void setTrace(Trace trace) {
        this.trace = trace;
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

    public Utilisateur getAutheur() {
        return autheur;
    }

    public void setAutheur(Utilisateur autheur) {
        this.autheur = autheur;
    }

    public List<CommentaireLike> getCommentaireLike() {
        return commentaireLike;
    }

    public void setCommentaireLike(List<CommentaireLike> commentaireLike) {
        this.commentaireLike = commentaireLike;
    }

    public List<CommentaireSignalement> getCommentaireSignalement() {
        return commentaireSignalement;
    }

    public void setCommentaireSignalement(List<CommentaireSignalement> commentaireSignalement) {
        this.commentaireSignalement = commentaireSignalement;
    }

    public int getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(int totalLike) {
        this.totalLike = totalLike;
    }
}
