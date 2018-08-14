package com.ambacam.transfert.requetes;

public class RequeteSearch {
    private Long operateurId;
    private Long requerantId;

    public RequeteSearch() {

    }

    public RequeteSearch(Long operateurId, Long requerantId) {
        this.operateurId = operateurId;
        this.requerantId = requerantId;
    }

    public Long getOperateurId() {
        return operateurId;
    }

    public void setOperateurId(Long operateurId) {
        this.operateurId = operateurId;
    }

    public RequeteSearch operateurId(Long operateurId) {
        this.operateurId = operateurId;
        return this;
    }

    public Long getRequerantId() {
        return requerantId;
    }

    public void setRequerantId(Long requerantId) {
        this.requerantId = requerantId;
    }

    public RequeteSearch requerantId(Long requerantId) {
        this.requerantId = requerantId;
        return this;
    }

}
