package com.gemini.ambacam.transfert.requetes;

import javax.validation.constraints.NotNull;

public class RequeteStatusTO {

    @NotNull(message = "The statusId must not be null")
    private Long statusId;
    
    public RequeteStatusTO() {
        
    }
    
public RequeteStatusTO(Long statusId) {
        this.statusId=statusId;
    }

public Long getStatusId() {
    return statusId;
}

public void setStatusId(Long statusId) {
    this.statusId = statusId;
}
}
