package com.ambacam.search.requetes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ambacam.model.Operateur_;
import com.ambacam.model.Requerant_;
import com.ambacam.model.Requete;
import com.ambacam.model.Requete_;

public class RequeteSpecs implements Specification<Requete> {
    private RequeteCriteria criteria;

    public RequeteSpecs(RequeteCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Requete> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        final List<Predicate> predicates = new ArrayList<>();

        if (criteria.getOperateurId() != null) {
            predicates.add(cb.equal(root.get(Requete_.operateur).get(Operateur_.id), criteria.getOperateurId()));
        }

        if (criteria.getRequerantId() != null) {
            predicates.add(cb.equal(root.get(Requete_.requerant).get(Requerant_.id), criteria.getRequerantId()));
        }

        query.distinct(true);
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));

    }
}
