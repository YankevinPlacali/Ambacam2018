package com.ambacam.search.operateurs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ambacam.model.Operateur;
import com.ambacam.model.Operateur_;

public class OperateurSpecs implements Specification<Operateur> {

	private OperateurCriteria criteria;

	public OperateurCriteria getCriteria() {
		return criteria;
	}

	public OperateurSpecs(OperateurCriteria criteria) {
		this.criteria = criteria;
	}

	public void setCriteria(OperateurCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<Operateur> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		final List<Predicate> predicates = new ArrayList<>();

		if (criteria.getKeyword() != null) {
			Predicate predicateOnNom = cb.like(cb.upper(root.get(Operateur_.nom)),
					"%" + criteria.getKeyword().toUpperCase() + "%");
			Predicate predicateOnPrenom = cb.like(cb.upper(root.get(Operateur_.prenom)),
					"%" + criteria.getKeyword().toUpperCase() + "%");
			predicates.add(cb.or(predicateOnNom, predicateOnPrenom));
		}

		if (criteria.getCreatorId() != null) {
			predicates.add(cb.equal(root.get(Operateur_.creePar).get(Operateur_.id), criteria.getCreatorId()));
		}

		if (criteria.getCreeLeAfter() != null) {
			predicates.add(cb.greaterThanOrEqualTo(root.get(Operateur_.creeLe), criteria.getCreeLeAfter()));
		}

		if (criteria.getCreeLeBefore() != null) {
			predicates.add(cb.lessThanOrEqualTo(root.get(Operateur_.creeLe), criteria.getCreeLeBefore()));
		}

		query.distinct(true);
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));

	}

}
