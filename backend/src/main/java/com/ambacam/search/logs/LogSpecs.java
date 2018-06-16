package com.ambacam.search.logs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ambacam.model.Log;
import com.ambacam.model.Log_;

public class LogSpecs implements Specification<Log> {

	private LogCriteria criteria;

	public LogCriteria getCriteria() {
		return criteria;
	}

	public LogSpecs(LogCriteria criteria) {
		this.criteria = criteria;
	}

	public void setCriteria(LogCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		final List<Predicate> predicates = new ArrayList<>();

		if (criteria.getKeyword() != null) {
			predicates.add(
					cb.like(cb.upper(root.get(Log_.description)), "%" + criteria.getKeyword().toUpperCase() + "%"));
		}

		if (criteria.getDateAfter() != null) {
			predicates.add(cb.greaterThanOrEqualTo(root.get(Log_.date), criteria.getDateAfter()));
		}

		if (criteria.getDateBefore() != null) {
			predicates.add(cb.lessThanOrEqualTo(root.get(Log_.date), criteria.getDateBefore()));
		}

		query.distinct(true);
		return cb.and(predicates.toArray(new Predicate[predicates.size()]));

	}

}
