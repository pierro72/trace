package com.ex.trace.service.dto.mobile;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public  abstract class PostMessageDTO {

    @Size(max = 1024) @Column(length = 1024)  @NotNull
    protected String      contenu;

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

}
