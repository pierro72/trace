package com.ex.trace.service;

import com.ex.trace.domaine.Trace;
import com.ex.trace.repository.TraceRepository;
import com.ex.trace.service.dto.TraceDTO;
import com.ex.trace.service.mapper.TraceMapperComplet;
import com.ex.trace.service.mapper.TraceMapper;
import com.ex.trace.specification.TraceSpecification;
import com.ex.trace.util.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation de service pour  le management de  Trace.
 */
@Service
@Transactional
public class TraceService {

    private final Logger log = LoggerFactory.getLogger(TraceService.class);

    private final TraceRepository traceRepository;

    private final TraceMapper traceMapper;

    private final TraceMapperComplet traceMapperComplet;

    public TraceService(TraceRepository traceRepository, TraceMapper traceMapper, TraceMapperComplet traceMapperComplet) {
        this.traceRepository    = traceRepository;
        this.traceMapper        = traceMapper;
        this.traceMapperComplet = traceMapperComplet;
    }

    /**
     * Save a trace.
     *
     * @param traceDTO the entity pour sauvegarder
     * @return the persisted entity
     */
    public TraceDTO save(TraceDTO traceDTO) {
        log.debug("Request pour sauvegarder Trace : {}", traceDTO);
        Trace trace = traceMapperComplet.toEntity(traceDTO);
        trace.setDate(Calendar.getInstance().getTime());
        trace = traceRepository.save(trace);
        return traceMapperComplet.toDto(trace);
    }

    /**
     * Get all the trace.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TraceDTO> findAll() {
        List<Trace> trace = traceRepository.findAll();
        return traceMapper.toDto(trace);
    }

    /**
     * Get one trace by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TraceDTO findOne(Long id) {
        log.debug("Request to get Trace : {}", id);
        Trace trace = traceRepository.findOne(id);

        return traceMapperComplet.toDto(trace);
    }

    /**
     * Delete the trace by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Trace2 : {}", id);
        Trace trace = traceRepository.findOne(id);
        traceRepository.delete(trace);
    }

    private Specification<Trace> specificationBuild(List<SearchCriteria> params ) {
        if (params.size() == 0) { return null; }

        List<Specification<Trace>> specs = new ArrayList<Specification<Trace>>();
        for (SearchCriteria param : params) {
            specs.add(new TraceSpecification(param));
        }

        Specification<Trace> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }

}
