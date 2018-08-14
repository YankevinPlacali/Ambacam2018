package com.ambacam.transfert.requetes;

import java.util.Date;

import com.ambacam.model.StatusRequete;
import com.ambacam.model.TypeRequete;
import com.ambacam.transfert.operateurs.OperateurReadTO;
import com.ambacam.transfert.requerants.RequerantReadTO;

public class RequeteReadTO {

    private Long id;
    
    private TypeRequete type;
    
    private StatusRequete status;
    
    private Date date;
    
    private RequerantReadTO requerant;
    
    private OperateurReadTO operateur;
    
    public RequeteReadTO() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeRequete getType() {
        return type;
    }

    public void setType(TypeRequete type) {
        this.type = type;
    }

    public StatusRequete getStatus() {
        return status;
    }

    public void setStatus(StatusRequete status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public RequerantReadTO getRequerant() {
        return requerant;
    }

    public void setRequerant(RequerantReadTO requerantReadTO) {
        this.requerant = requerantReadTO;
    }

    public OperateurReadTO getOperateur() {
        return operateur;
    }

    public void setOperateur(OperateurReadTO operateur) {
        this.operateur = operateur;
    }
    
 }
