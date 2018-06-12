package com.ex.trace.specification;


import com.ex.trace.domaine.Commentaire;
import com.ex.trace.domaine.Trace;
import com.ex.trace.util.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;


public class CommentaireSpecification implements Specification<Commentaire> {


    private SearchCriteria criteria;

    public CommentaireSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate (Root<Commentaire> root,  CriteriaQuery<?> cq, CriteriaBuilder cb) {


        String key          = criteria.getKey();
        String op           = criteria.getOperation();
        Object val          = criteria.getValue();
        Predicate predicate = null;
        Join<Commentaire, Trace> trace = root.join("trace");

        //contenu
        if (key.equalsIgnoreCase("contenu")) {
            if (op.equalsIgnoreCase(":")) {
                predicate =  cb.like(root.<String>get(key), "%" + val + "%");
            }
        }

        //id
        if (key.equalsIgnoreCase("id")) {
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



        //traceId
        if (key.equalsIgnoreCase("traceId")) {
            predicate = cb.conjunction();
            switch (op) {
                case ">":
                    predicate = cb.greaterThanOrEqualTo( trace.get("id"), val.toString());
                    break;
                case ":":
                    predicate =  cb.equal( trace.get("id"), val);
                    break;
                case "<":
                    predicate =  cb.lessThanOrEqualTo( trace.get("id"), val.toString());
                    break;
            }
        }

        return  predicate;
    }
}

