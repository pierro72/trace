package com.ex.trace.domaine.CompositePK;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class UtilisateurMessageCompositePK implements Serializable {

    public UtilisateurMessageCompositePK( long idUtilisateur, long idMessage){
        this.idMessage          = idMessage;
        this.idUtilisateur      = idUtilisateur;
    }

    public UtilisateurMessageCompositePK(){
    }

    @Column( name = "id_message" )
    private long idMessage;

    @Column ( name = "id_utilisateur" )
    private long idUtilisateur;

    public long getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(long idMessage) {
        this.idMessage = idMessage;
    }

    public long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

}
