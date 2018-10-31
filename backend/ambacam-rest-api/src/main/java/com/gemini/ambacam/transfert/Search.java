package com.gemini.ambacam.transfert;

import java.util.Date;

public class Search {
    private String keyword;

    private Long creatorId;

    private Date creeLeBefore;

    private Date creeLeAfter;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreeLeBefore() {
        return creeLeBefore;
    }

    public void setCreeLeBefore(Date creeLeBefore) {
        this.creeLeBefore = creeLeBefore;
    }

    public Date getCreeLeAfter() {
        return creeLeAfter;
    }

    public void setCreeLeAfter(Date creeLeAfter) {
        this.creeLeAfter = creeLeAfter;
    }
}
