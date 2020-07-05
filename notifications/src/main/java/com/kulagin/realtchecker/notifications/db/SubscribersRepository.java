package com.kulagin.realtchecker.notifications.db;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class SubscribersRepository {
    private final Set<Long> ids = new HashSet<>();
    public void add(long id){
        ids.add(id);
    }
    
    public Set<Long> get(){
        return Collections.unmodifiableSet(ids);
    }
}
