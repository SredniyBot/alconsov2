package com.pavi.alconsov2.repo;

import com.pavi.alconsov2.entity.LightSequence;
import com.pavi.alconsov2.entity.Sequence;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class SequenceArrayRepository {
    private final Map<String,Integer> sequences;
    SequenceArrayRepository(){
        sequences=new ConcurrentHashMap<>(200000);
    }
    public LightSequence findBySequence(String sequence) {
        if (sequences.containsKey(sequence))return new LightSequence(sequence,sequences.get(sequence));
        return null;
    }
    public void updateStatistics(String sequence) {
        sequences.put(sequence,0);
    }//FIXME

    public List<LightSequence> bestSequences(int minQuantity){
//        List<LightSequence> s = new ArrayList<>(sequences.values());
//        return s.stream().filter(sequence -> sequence.getQuantity()>=minQuantity).collect(Collectors.toList());
        return null;
    }
    public Sequence bestConservSequence() throws IOException{
//        Optional<Sequence> s = sequences.values().stream().max(Comparator.comparingInt(Sequence::getQuantity));
//        if (s.isPresent())return s.get();
        throw new IOException("Sequences are absent");
    }
}
