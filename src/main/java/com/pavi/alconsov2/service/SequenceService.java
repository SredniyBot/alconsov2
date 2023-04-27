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
        for(Sequence sequence:sliceVirus(fasta)){
            Sequence currentSeq= sequenceRepository.findBySequence(sequence.getSequence());
            if(currentSeq==null){
                sequenceRepository.save(sequence);
            }else {
                currentSeq.increaseQuantity();
                sequenceRepository.save(currentSeq);
            }
        }
    }

    public HashSet<Sequence> sliceVirus(String fasta){
        HashSet<Sequence> sequences=new HashSet<>();
        for(int i = 0; i<fasta.length()- definingLength; i++) {
            sequences.add(new Sequence(fasta.substring(i,i+ definingLength)));
        }
        return sequences;
    }


    public ArrayList<Sequence> getBestSequences(int scatter){               //TODO нуждается в рефакторинге
        List<Sequence> sequences =sequenceRepository.getSequences();
        var reference = new Object() {
            double max = 0;
        };
        for(Sequence s:sequences){
            if(s.getQuantity()> reference.max){
                reference.max =s.getQuantity();
            }
        }
//        processInfo.setMaxSequenceQuantity((int) reference.max);
//        processInfo.setMinSequenceQuantity((int) (reference.max-reference.max*processInfo.getScatterInResults()/100));

        ArrayList<Sequence> sorted = (ArrayList<Sequence>) sequences;
        double i= (reference.max- reference.max*scatter/100);
        sorted.removeIf(sequence -> sequence.getQuantity()< i);
        return sorted;
    }

    public HashMap<String, Sequence> getBestSequencesAsMap(int scatter){
        ArrayList<Sequence> sequences =getBestSequences(scatter);
        HashMap<String, Sequence> sequencesMap = new HashMap<>();
        for(Sequence s:sequences) sequencesMap.put(s.getSequence(),s);
        return sequencesMap;
    }
}
