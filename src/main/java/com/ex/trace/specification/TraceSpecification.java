package com.ex.trace.specification;


import com.ex.trace.PaysType;
import com.ex.trace.TraceType;
import com.ex.trace.domaine.Trace;
import com.ex.trace.util.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TraceSpecification implements Specification<Trace> {

    private SearchCriteria criteria;
    private Predicate predicate;

    public TraceSpecification(SearchCriteria criteria) {
        this.criteria   = criteria;
        this.predicate  = null;
    }



    @Override
    public Predicate toPredicate(Root<Trace> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {


        switch (criteria.getKey()){
            case "id":
                predicate = operationSurNombre(root, cb);
                break;

            case "positionX":
                predicate = operationSurNombre(root, cb);
                break;

            case "positionY":
                predicate = operationSurNombre(root, cb);
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


            case "traceType":
                predicate = cb.equal(root.get(criteria.getKey()), TraceType.valueOf(criteria.getValue().toString()));
                break;

            case "contenu":
                if (criteria.getOperation().equalsIgnoreCase(":")) { predicate =  cb.like(root.<String>get(criteria.getKey()), criteria.getValue().toString());  }
                break;

            case "codePays":
                if (criteria.getOperation().equalsIgnoreCase(":")) { predicate =  cb.like(root.<String>get(criteria.getKey()), criteria.getValue().toString());  }
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


    public Predicate operationSurNombre(Root<Trace> root,  CriteriaBuilder cb){
        this.predicate  = null;
        switch (criteria.getOperation()) {
            case "<":
                predicate =  cb.lessThanOrEqualTo( root.<String>get(criteria.getKey()),criteria.getValue().toString());
                break;
            case ">":
                predicate = cb.greaterThanOrEqualTo( root.<String>get(criteria.getKey()), criteria.getValue().toString());
                break;
            case ":":
                predicate =  cb.equal( root.get(criteria.getKey()), criteria.getValue().toString());
                break;
        }
        return predicate;
    }



}