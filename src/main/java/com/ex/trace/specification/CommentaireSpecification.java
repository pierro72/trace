package com.ex.trace.specification;


import com.ex.trace.domaine.Commentaire;
import com.ex.trace.domaine.Trace;
import com.ex.trace.util.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CommentaireSpecification implements Specification<Commentaire> {


    private SearchCriteria criteria;
    private Predicate predicate;

    public CommentaireSpecification(SearchCriteria criteria) {
        this.criteria   = criteria;
        this.predicate  = null;

    }

    @Override
    public Predicate toPredicate (Root<Commentaire> root,  CriteriaQuery<?> cq, CriteriaBuilder cb) {


        Join<Commentaire, Trace> trace = root.join("trace");


        switch (criteria.getKey()) {

            case "contenu":
                if (criteria.getOperation().equalsIgnoreCase(":")) {
                    predicate =  cb.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
                }
                break;

            case "id":
                switch (criteria.getOperation()) {
                    case ">":
                        predicate = cb.greaterThanOrEqualTo( root.<String>get(criteria.getKey()), criteria.getValue().toString());
                        break;
                    case ":":
                        predicate =  cb.equal( root.get(criteria.getKey()), criteria.getValue());
                        break;
                    case "<":
                        predicate =  cb.lessThanOrEqualTo( root.<String>get(criteria.getKey()),criteria.getValue().toString());
                        break;
                }
                break;

            case "traceId":
                predicate = cb.conjunction();
                switch (criteria.getOperation()) {
                    case ">":
                        predicate = cb.greaterThanOrEqualTo( trace.get("id"), criteria.getValue().toString());
                        break;
                    case ":":
                        predicate =  cb.equal( trace.get("id"), criteria.getValue());
                        break;
                    case "<":
                        predicate =  cb.lessThanOrEqualTo( trace.get("id"), criteria.getValue().toString());
                        break;
                }
                break;

            case "estDouteux":
                if (criteria.getOperation().equalsIgnoreCase(":")) {
                    predicate = cb.equal(root.get(criteria.getKey()), Boolean.valueOf(criteria.getValue().toString()));
                }
                break;

            case "estVerifier":
                if (criteria.getOperation().equalsIgnoreCase(":")) {
                    predicate = cb.equal(root.get(criteria.getKey()), Boolean.valueOf(criteria.getValue().toString()));
                }
                break;

            case "date":
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date date = formatter.parse(criteria.getValue().toString());
                    switch (criteria.getOperation()) {
                        case ">":
                            predicate =  cb.greaterThanOrEqualTo(root.<Date>get(criteria.getKey()),   date);
                            break;
                        case ":":
                            predicate =  cb.equal(root.<Date>get(criteria.getKey()),   date);
                            break;
                        case "<":
                            predicate =  cb.lessThanOrEqualTo(root.<Date>get(criteria.getKey()),   date);
                            break;
                    }
                } catch (ParseException e) { e.printStackTrace(); }
                break;

        }

        return  predicate;
    }


}

