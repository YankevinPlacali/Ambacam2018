package com.ambacam.transfert.operateurs;

import java.util.ArrayList;
import java.util.List;

import com.ambacam.transfert.SearchResultTO;

public class OperateurSearchTO extends SearchResultTO {

	private List<OperateurReadTO> operateurs = new ArrayList<>();

	public List<OperateurReadTO> getOperateurs() {
		return operateurs;
	}

	public void setOperateurs(List<OperateurReadTO> operateurs) {
		this.operateurs = operateurs;
	}

}