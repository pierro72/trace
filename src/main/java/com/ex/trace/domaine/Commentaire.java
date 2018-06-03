package com.ex.trace.domaine;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

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
}
