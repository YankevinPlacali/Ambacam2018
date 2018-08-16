package com.ambacam.transfert;

import java.util.HashSet;
import java.util.Set;

public class AddRemoveTO {
        
    private Set<Long> add = new HashSet<>();
    
    private Set<Long> remove = new HashSet<>();
    
    public AddRemoveTO() {
        
    }
    
    public Set<Long> getAdd() {
        return add;
    }
    
    public void setAdd(Set<Long> add) {
        this.add = add;
    }
    
    public Set<Long> getRemove() {
        return remove;
    }
    
    public void setRemove(Set<Long> remove) {
        this.remove = remove;
    }
}
