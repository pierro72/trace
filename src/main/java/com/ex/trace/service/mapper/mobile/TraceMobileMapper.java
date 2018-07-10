package com.ex.trace.service.mapper.mobile;

import com.ex.trace.domaine.Trace;
import com.ex.trace.domaine.TraceLike;
import com.ex.trace.domaine.TraceSignalement;
import com.ex.trace.domaine.TraceVue;
import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.service.TraceService;
import com.ex.trace.service.dto.mobile.TraceDTO;
import com.ex.trace.service.dto.mobile.TracePostDTO;
import com.ex.trace.service.dto.mobile.TraceSoftDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { TraceService.class })
public abstract class TraceMobileMapper {


    public abstract List<TraceSoftDTO> toTraceSoftDTO (List<Trace> trace);

    public Trace toTrace ( TracePostDTO traceDTO){
        Trace trace = new Trace();
        trace.setPositionX( traceDTO.getPositionX());
        trace.setPositionY( traceDTO.getPositionY());
        trace.setContenu( traceDTO.getContenu() );
        return trace;
    }

    public TraceSoftDTO toTraceSoftDTO (Trace trace) {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TraceSoftDTO traceDTO = new TraceSoftDTO();
        traceDTO.setId( trace.getId() );
        traceDTO.setPositionX( trace.getPositionX() );
        traceDTO.setPositionY( trace.getPositionY() );
        traceDTO.setAutheur( estProprietaire( trace, utilisateur) );
        traceDTO.setVue( estVue( trace, utilisateur ) );
        traceDTO.setTraceType( trace.getTraceType() );
        return traceDTO;
    }

    public TraceDTO toTraceDTO (Trace trace) {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TraceDTO traceDTO = new TraceDTO();
        traceDTO.setId( trace.getId() );
        traceDTO.setPositionX( trace.getPositionX() );
        traceDTO.setPositionY( trace.getPositionY() );
        traceDTO.setAutheur( estProprietaire( trace, utilisateur) );
        traceDTO.setTotalVue( trace.getTotalVue() );
        traceDTO.setTotalLike( trace.getTotalLike() );
        traceDTO.setDate( trace.getDate() );
        traceDTO.setLike( estLike( trace, utilisateur));
        traceDTO.setSignale( estSignale( trace, utilisateur) );
        traceDTO.setTraceType( trace.getTraceType());
        return traceDTO;
    }


    private boolean estProprietaire(Trace trace, Utilisateur utilisateur){
        return trace.getAutheur().getId().longValue() == utilisateur.getId().longValue();
    }

    private boolean estVue(Trace trace, Utilisateur utilisateur){
        boolean estVue = false;
        List<TraceVue> traceVues =  trace.getTraceVues();
        for ( TraceVue vue : traceVues){
            if ( vue.getUtilisateur().getId().longValue() == utilisateur.getId().longValue() ){
                estVue = true;
                break;
            }
        }
        return estVue;
    }

    private boolean estLike(Trace trace, Utilisateur utilisateur){
        boolean estLike = false;
        List<TraceLike> traceLikes =  trace.getTraceLikes();
        for ( TraceLike like : traceLikes){
            if ( like.getUtilisateur().getId().longValue() == utilisateur.getId().longValue() ){
                estLike = true;
                break;
            }
        }
        return estLike;
    }

    private boolean estSignale(Trace trace, Utilisateur utilisateur){
        boolean estSignale = false;
        List<TraceSignalement> traceSignalements =  trace.getTraceSignalements();
        for ( TraceSignalement signalement : traceSignalements){
            if ( signalement.getUtilisateur().getId().longValue() == utilisateur.getId().longValue() ){
                estSignale = true;
                break;
            }
        }
        return estSignale;
    }




}
