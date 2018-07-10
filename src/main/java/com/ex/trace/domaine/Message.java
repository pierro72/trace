package com.ex.trace.domaine;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Inheritance
public abstract class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Size(max = 1024) @Column (length = 1024)  @NotNull
    private String      contenu;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
