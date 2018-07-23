package com.ex.trace.service.dto.admin;

import javax.validation.constraints.NotNull;

public class CommentaireDTO extends MessageDTO{

    @NotNull
    private Long        traceId;

    public Long getTraceId() {
        return traceId;
    }

    public void setTraceId(Long traceId) {
        this.traceId = traceId;
    }

}
