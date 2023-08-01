package com.pavi.alconsov2.repo;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class SequenceRepository {

    private final Map<String,Integer> sequences;

    SequenceRepository(){
        sequences=new HashMap<>(200000);
    }
    public synchronized void updateStatistics(String s){
        sequences.put(s,sequences.getOrDefault(s,0)+1);
    }

    public Integer bestConservSequence() {
        return sequences.values().stream().max(Integer::compare).orElse(0);
    }

    public Map<String, Integer> bestSequences(int minQuantity) {
        Set<String> set = new HashSet<>(sequences.keySet());
        for (String s: set)if(sequences.get(s)<minQuantity)sequences.remove(s);
        return sequences;
    }
}
