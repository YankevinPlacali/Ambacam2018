package com.ambacam.transfert.requetes;

import javax.validation.constraints.NotNull;

public class RequeteTO {
    
    @NotNull(message = "The typeId must not be null")
    private Long typeId;

    public RequeteTO() {

    }

    public RequeteTO(Long typeId) {
        this.typeId = typeId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
