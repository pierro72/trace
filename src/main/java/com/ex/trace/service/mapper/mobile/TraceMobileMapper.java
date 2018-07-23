package com.ex.trace.service.mapper.mobile;

import com.ex.trace.TraceType;
import com.ex.trace.domaine.Trace;
import com.ex.trace.domaine.security.Utilisateur;
import com.ex.trace.security.repository.UtilisateurRepository;
import com.ex.trace.service.TraceService;
import com.ex.trace.service.dto.mobile.TraceDTO;
import com.ex.trace.service.dto.mobile.PostTraceDTO;
import com.ex.trace.service.dto.mobile.TraceSoftDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = { TraceService.class })
public abstract class TraceMobileMapper {


    @Autowired
    public  UtilisateurRepository utilisateurRepository;

    public abstract List<TraceSoftDTO> toTraceSoftDTO (List<Trace> trace);

    public Trace toTrace ( PostTraceDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        return new Trace(
            dto.getContenu(),
            utilisateurRepository.findByUsername( authentication.getName()),
            dto.getPositionX(),
            dto.getPositionY(),
            dto.getCodePays(),
            TraceType.I
        );

/*        Trace trace = new Trace();
        Utilisateur utilisateur = utilisateurRepository.findByUsername( authentication.getName());
        trace.setAutheur( utilisateur);
        trace.setPositionX( traceDTO.getPositionX());
        trace.setPositionY( traceDTO.getPositionY());
        trace.setContenu( traceDTO.getContenu() );
        trace.setTraceType(TraceType.I);
        trace.setCodePays( traceDTO.getCodePays());
        return trace;*/
    }

    public TraceSoftDTO toTraceSoftDTO ( Trace entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur utilisateur = utilisateurRepository.findByUsername ( authentication.getName());
/*        TraceSoftDTO traceDTO = new TraceSoftDTO();
        traceDTO.setId ( entity.getId() );
        traceDTO.setPositionX ( entity.getPositionX() );
        traceDTO.setPositionY ( entity.getPositionY() );
        traceDTO.setAutheur ( estProprietaire( entity, utilisateur) );
        traceDTO.setVue ( estVue( entity, utilisateur.getId() ) );
        traceDTO.setTraceType ( entity.getTraceType() );
        return traceDTO;*/

        return new TraceSoftDTO (
            entity.getId(),
            entity.getPositionX(),
            entity.getPositionY(),
            entity.getTraceType(),
            MapperUtil.estProprietaire( entity, utilisateur),
            MapperUtil.estVue( entity, utilisateur.getId())
        );

    }

    public TraceDTO toTraceDTO (Trace entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur utilisateur = utilisateurRepository.findByUsername( authentication.getName());

        return new TraceDTO (
            entity.getId(),
            entity.getContenu(),
            entity.getDate(),
            entity.getRecommandations().size(),
            entity.getSignalements().size(),
            MapperUtil.estLike( entity, utilisateur),
            MapperUtil.estSignale ( entity, utilisateur),
            MapperUtil.estProprietaire ( entity, utilisateur),
            entity.getTraceType(),
            entity.getVisites().size(),
            entity.getPositionX(),
            entity.getPositionY()
        );

/*        traceDTO.setId ( trace.getId() );
        traceDTO.setContenu ( trace.getContenu() );
        traceDTO.setPositionX ( trace.getPositionX() );
        traceDTO.setPositionY ( trace.getPositionY() );
        traceDTO.setAutheur ( estProprietaire( trace, utilisateur) );
        traceDTO.setTotalVisite ( trace.getVisites().size() );
        traceDTO.setTotalRecommandation ( trace.getRecommandations().size() );
        traceDTO.setDate ( trace.getDate() );
        traceDTO.setRecommande( estLike( trace, utilisateur));
        traceDTO.setSignale ( estSignale( trace, utilisateur) );
        traceDTO.setTraceType ( trace.getTraceType());*/

    }

}
