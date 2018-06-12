package com.ex.trace.specification;


import com.ex.trace.TraceType;
import com.ex.trace.domaine.Trace;
import com.ex.trace.util.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class TraceSpecification implements Specification<Trace> {

    private SearchCriteria criteria;

    public TraceSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Trace> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

        String key          = criteria.getKey();
        String op           = criteria.getOperation();
        Object val          = criteria.getValue();
        Predicate predicate = null;


        //id
        if (key.matches("id|positionX|positionY")) {
            switch (op) {
                case ">":
                    predicate = cb.greaterThanOrEqualTo( root.<String>get(key), val.toString());
                    break;
                case ":":
                    predicate =  cb.equal( root.get(key), val);
                    break;
                case "<":
                    predicate =  cb.lessThanOrEqualTo( root.<String>get(key),val.toString());
                    break;
            }
        }

        //traceType
        if ( key.matches("traceType")){
            predicate = cb.equal(root.get(key), TraceType.valueOf(val.toString()));
        }

        //contenu
        if (key.equalsIgnoreCase("contenu")) {
            
            if (op.equalsIgnoreCase(":")) {
                predicate =  cb.like(root.<String>get(key), "%" + val + "%");
            }
        }

        return  predicate;
    }
}