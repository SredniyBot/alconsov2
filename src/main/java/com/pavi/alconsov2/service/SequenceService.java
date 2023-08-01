package com.pavi.alconsov2.service;

import com.pavi.alconsov2.entity.Sequence;
import com.pavi.alconsov2.repo.SequenceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SequenceService {

    private final SequenceRepository sequenceRepository;

    @Value("${definingLength}")
    private Integer definingLength;

    public SequenceService(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    public void sliceAndSave(String fasta){
        for(String sequence:sliceVirus(fasta)){
            sequenceRepository.updateStatistics(sequence);
        }
    }

    public HashSet<String> sliceVirus(String fasta){
        HashSet<String> sequences=new HashSet<>();
        for(int i = 0; i<fasta.length()- definingLength; i++) {
            sequences.add(fasta.substring(i,i+ definingLength));
        }
        return sequences;
    }


    public List<Sequence> getBestSequences(int scatter)  {
        Integer best = sequenceRepository.bestConservSequence();
        int minQuantity= (best- (best * scatter) /100);
        Map<String,Integer> l = sequenceRepository.bestSequences(minQuantity);
        List<Sequence> s =new ArrayList<>();
        for (String lk:l.keySet())s.add(new Sequence(lk,l.get(lk)));
        return s;
    }

    public Map<String, Sequence> getBestSequencesAsMap(int scatter){
        List<Sequence> sequences =getBestSequences(scatter);
        HashMap<String, Sequence> sequencesMap = new HashMap<>();
        for(Sequence s:sequences) sequencesMap.put(s.getSequence(),s);
        return sequencesMap;
    }
}
