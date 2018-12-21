package com.ambacam.service;

import java.util.Observable;
import java.util.Observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ambacam.model.HasStatusHistory;
import com.ambacam.model.StatusHistory;
import com.ambacam.repository.RequeteHistoryRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class StatusHistoryService<E extends HasStatusHistory> implements Observer {

	@Autowired
	private RequeteHistoryRepository requeteHistoryRepository;

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			E entity = (E) arg0;
			StatusHistory history = buildHistory(entity);
			requeteHistoryRepository.save(history);
		} catch (Exception e) {

		}

	}

	private StatusHistory buildHistory(E entity) {
		StatusHistory history = new StatusHistory();
		history.setName(entity.getStatusName());
		history.setClassName(entity.getClass().getName());
		history.setEntityId(entity.getId());

		return history;
	}

}
