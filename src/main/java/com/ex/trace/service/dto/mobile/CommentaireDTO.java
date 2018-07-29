package com.ex.trace.service.dto.mobile;


import javax.validation.constraints.NotNull;
import java.util.Date;

public class CommentaireDTO extends MessageDTO{

    @NotNull
    private Long  traceId;

    public Long getTraceId() {
        return traceId;
    }

    public void setTraceId(Long traceId) {
        this.traceId = traceId;
    }

    public CommentaireDTO(){}

    public CommentaireDTO(Long id, String contenu, Date date, int totalRecommandation, int totalSignalement, boolean recommande, boolean signale, boolean autheur, Long traceId ) {
        super (id, contenu, date, totalRecommandation, totalSignalement, recommande, signale, autheur );
        this.traceId = traceId;
    }

}
