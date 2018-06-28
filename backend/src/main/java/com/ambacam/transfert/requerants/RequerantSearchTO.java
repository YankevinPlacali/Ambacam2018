package com.ambacam.transfert.requerants;

import java.util.ArrayList;
import java.util.List;

import com.ambacam.transfert.SearchResultTO;

public class RequerantSearchTO extends SearchResultTO {

	private List<RequerantReadTO> requerants = new ArrayList<>();

	public List<RequerantReadTO> getRequerants() {
		return requerants;
	}

	public void setRequerants(List<RequerantReadTO> requerants) {
		this.requerants = requerants;
	}

}
