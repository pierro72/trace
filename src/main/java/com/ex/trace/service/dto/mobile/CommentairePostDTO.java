package com.ex.trace.service.dto.mobile;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class CommentairePostDTO {

    @Size(max = 1024) @Column(length = 1024)  @NotNull
    private String contenu;

    @NotNull
    private Long traceId;


    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Long getTraceId() {
        return traceId;
    }

    public void setTraceId(Long traceId) {
        this.traceId = traceId;
    }
}
