package com.ex.trace.service.mapper.mobile;

import com.ex.trace.TraceType;
import com.ex.trace.domaine.Trace;
import com.ex.trace.security.JwtUtilisateur;
import com.ex.trace.security.repository.UtilisateurRepository;
import com.ex.trace.service.TraceService;
import com.ex.trace.service.dto.mobile.TraceDTO;
import com.ex.trace.service.dto.mobile.post.PostTraceDTO;
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

    }

    public TraceSoftDTO toTraceSoftDTO ( Trace entity) {
        return new TraceSoftDTO (
            entity.getId(),
            entity.getPositionX(),
            entity.getPositionY(),
            entity.getTraceType(),
            entity.isProprietaire(),
            entity.isVue()
        );

    }

    public TraceDTO toTraceDTO (Trace entity) {
        JwtUtilisateur utilisateur = (JwtUtilisateur)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new TraceDTO (
            entity.getId(),
            entity.getContenu(),
            entity.getDate(),
            entity.getRecommandations().size(),
            entity.getSignalements().size(),
            entity.isRecommande(),
            entity.isSignale(),
            entity.isProprietaire(),
            entity.getTraceType(),
            entity.getVisites().size(),
            entity.getPositionX(),
            entity.getPositionY()
        );

    }

}
