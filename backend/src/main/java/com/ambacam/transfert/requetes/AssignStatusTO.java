package com.ambacam.transfert.requetes;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class AssignStatusTO {

    @NotNull(message = "The requeteIds must not be null")
    @NotEmpty(message = "The requeteIds must not be empty")
    private Set<Long> requeteIds= new HashSet<>();

    @NotNull(message = "The statusId must not be null")
    private Long statusId;
    
    public AssignStatusTO() {
        
    }

    public Set<Long> getRequeteIds() {
        return requeteIds;
    }

    public void setRequeteIds(Set<Long> requeteIds) {
        this.requeteIds = requeteIds;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}
