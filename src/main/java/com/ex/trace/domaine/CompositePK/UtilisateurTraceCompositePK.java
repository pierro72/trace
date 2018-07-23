package com.ex.trace.domaine.CompositePK;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UtilisateurTraceCompositePK implements Serializable {


    @Column( name = "id_trace" )
    private long idTrace;

    @Column ( name = "id_utilisateur" )
    private long idUtilisateur;

    public UtilisateurTraceCompositePK(long idUtilisateur, long idtrace){
        this.idTrace        = idtrace;
        this.idUtilisateur  = idUtilisateur;
    }

    public UtilisateurTraceCompositePK(){
    }
    public long getIdTrace() {
        return idTrace;
    }

    public void setIdTrace(long idTrace) {
        this.idTrace = idTrace;
    }

    public long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

}