package com.ex.trace.service.dto.mobile;

import javax.validation.constraints.NotNull;

public class PostCommentaireDTO extends PostMessageDTO {

    @NotNull
    private Long traceId;

    public Long getTraceId() {
        return traceId;
    }

    public void setTraceId(Long traceId) {
        this.traceId = traceId;
    }
}
