package com.pavi.alconsov2.repo;

import com.pavi.alconsov2.entity.LightSequence;
import com.pavi.alconsov2.entity.Sequence;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class SequenceRepoFacade {
    private final SequenceRepo repo;
    private final Map<String,Integer> sequences;
    private volatile boolean send;

    private Thread dataToBase;
    public SequenceRepoFacade(SequenceRepo repo) {
        this.repo = repo;
        sequences=new ConcurrentHashMap<>(200000);

    }
    public List<LightSequence> bestSequences(int minQuantity) throws InterruptedException {
        if (send){
            dataToBase.join();
        }
        dataToBase=new Thread(this::toDb);
        dataToBase.start();
        dataToBase.join();

        return repo.bestSequences(minQuantity);
    }
    public Integer bestConservSequence() throws InterruptedException {
        if (send){
            dataToBase.join();
        }
        dataToBase=new Thread(this::toDb);
        dataToBase.start();
        dataToBase.join();
        return repo.bestConservSequence();
    }
    public LightSequence findBySequence(String sequence){
        return repo.findBySequence(sequence).orElse(new LightSequence(sequence,0));
    }

    public void updateStatistics(String sequence) {
        sequences.put(sequence,sequences.getOrDefault(sequence,0)+1);
        if (sequences.size()>200000&&!send){
            send=true;
            dataToBase=new Thread(this::toDb);
            dataToBase.start();
        }
    }
    private void toDb(){
        for (String s:sequences.keySet()){
            LightSequence seq = findBySequence(s);
            seq.setQuantity(sequences.remove(s)+seq.getQuantity());
            repo.save(seq);
        }
        send=false;
    }
}
