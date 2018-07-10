package com.ex.trace.domaine;

import com.ex.trace.domaine.security.Utilisateur;
import javax.persistence.*;

@Entity
public class CommentaireLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long        id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private Commentaire commentaire;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(nullable = false)
    private Utilisateur utilisateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commentaire getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(Commentaire commentaire) {
        this.commentaire = commentaire;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
