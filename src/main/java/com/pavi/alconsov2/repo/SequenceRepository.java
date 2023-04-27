package com.pavi.alconsov2.repo;

import com.pavi.alconsov2.entity.Sequence;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SequenceRepository {

    private final Map<String,Sequence> sequences;

    SequenceRepository(){
        sequences=new ConcurrentHashMap<>(200000);
    }
    public Sequence findBySequence(String sequence) {
        if (sequences.containsKey(sequence))return sequences.get(sequence);
        return null;
    }

    public void save(Sequence sequence) {
        sequences.put(sequence.getSequence(),sequence);
    }

    public List<Sequence> getSequences() {
        return  new ArrayList<>(sequences.values());
    }
}
