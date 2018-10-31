package com.gemini.ambacam.search.requerants;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.gemini.ambacam.model.Operateur_;
import com.gemini.ambacam.model.Requerant_;
import com.gemini.ambacam.model.Requerant;

public class RequerantSpecs implements Specification<Requerant> {

	private RequerantCriteria criteria;

	public RequerantSpecs(RequerantCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<Requerant> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		final List<Predicate> predicates = new ArrayList<>();

		if (criteria.getKeyword() != null) {
			Predicate predicateOnNom = cb.like(cb.upper(root.get(Requerant_.nom)),
					"%" + criteria.getKeyword().toUpperCase() + "%");
			Predicate predicateOnPrenom = cb.like(cb.upper(root.get(Requerant_.prenom)),
					"%" + criteria.getKeyword().toUpperCase() + "%");
			predicates.add(cb.or(predicateOnNom, predicateOnPrenom));
		}

		if (criteria.getDateNaissance() != null) {
			predicates.add(cb.equal(root.get(Requerant_.dateNaissance), criteria.getDateNaissance()));
		}

		if (criteria.getCreatorId() != null) {
			predicates.add(cb.equal(root.get(Requerant_.creePar).get(Operateur_.id), criteria.getCreatorId()));
		}

		if (criteria.getCreeLeAfter() != null) {
			predicates.add(cb.greaterThanOrEqualTo(root.get(Requerant_.creeLe), criteria.getCreeLeAfter()));
		}

		if (criteria.getCreeLeBefore() != null) {
			predicates.add(cb.lessThanOrEqualTo(root.get(Requerant_.creeLe), criteria.getCreeLeBefore()));
		}

		query.distinct(true);
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));

	}

}
