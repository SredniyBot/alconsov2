package com.pavi.alconsov2.service;

import com.pavi.alconsov2.entity.LightSequence;
import com.pavi.alconsov2.entity.Sequence;
import com.pavi.alconsov2.repo.SequenceRepoFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.*;

@Service
public class SequenceService {

    private final SequenceRepoFacade sequenceArrayRepository;

    @Value("${definingLength}")
    private Integer definingLength;

    public SequenceService(SequenceRepoFacade sequenceArrayRepository) {
        this.sequenceArrayRepository = sequenceArrayRepository;
    }


    public synchronized void sliceAndSave(String fasta){
        for(String sequence:sliceVirus(fasta)){
            sequenceArrayRepository.updateStatistics(sequence);
        }
    }

    public HashSet<String> sliceVirus(String fasta){
        HashSet<String> sequences=new HashSet<>();
        for(int i = 0; i<fasta.length()- definingLength; i++) {
            sequences.add(fasta.substring(i,i+ definingLength));
        }
        return sequences;
    }


    public List<Sequence> getBestSequences(int scatter) throws IOException, InterruptedException {
        Integer best = sequenceArrayRepository.bestConservSequence();
        int minQuantity= (best- (best * scatter) /100);
        List<LightSequence> l = sequenceArrayRepository.bestSequences(minQuantity);
        List<Sequence> s =new ArrayList<>();
        for (LightSequence lk:l)s.add(lk.toSequence());
        return s;
    }

    public Map<String, Sequence> getBestSequencesAsMap(int scatter){
        List<Sequence> sequences;
        try {
            sequences = getBestSequences(scatter);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        Map<String, Sequence> sequencesMap = new HashMap<>();
        for(Sequence s:sequences) sequencesMap.put(s.getSequence(),s);
        return sequencesMap;
    }

}
