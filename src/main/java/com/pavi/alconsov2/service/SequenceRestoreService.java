package com.pavi.alconsov2.service;

import com.pavi.alconsov2.entity.ProgramStatus;
import com.pavi.alconsov2.entity.ResultInfo;
import com.pavi.alconsov2.entity.Sequence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

@Component
public class SequenceRestoreService implements FilesResultObserver{

    private final SequenceService sequenceService;

    public SequenceRestoreService(SequenceService sequenceService) {
        this.sequenceService = sequenceService;
    }
    @Value("${definingLength}")
    private Integer definingLength;
    public String analisePiecesAndGetResult(HashMap<String, Sequence> map,int scatter,boolean useN,ResultInfo resultInfo) {
        resultInfo.increaseUnique_sequences(map.size());
        resultInfo.setGenome_status(ProgramStatus.ALIGNMENT);
        StringBuilder result = new StringBuilder();
        ArrayList<Sequence> array = collect(getSortedList(map,resultInfo),resultInfo);
        int max= resultInfo.getGenomes_all()-resultInfo.getGenomeErrorList();
        result

                .append("{\n")
                .append("\"definingLength\": \"")
                .append(definingLength).append("\",\n")
                .append("\"scatterInResults\": \"")
                .append(scatter)
                .append("\",\n").append(" \"numberOfGenomes\": \"")
                .append(resultInfo.getGenomes_all())
                .append("\",\n").append(" \"useNGenomes\": \"")
                .append(useN).append("\",\n").append(" \"numberOfAnalysedGenomes\": \"");
        if(useN){
            result.append(max).append("\",\n");
        }else {
            result.append(max/*processInfo.getNumberOfNGenomes()*/).append("\",\n");
        }

        result.append(" \"genomes\": [\n");

        for (Sequence seq : array) {
            result.append(seq.getMessage(max)).append(",\n");
        }
        result.deleteCharAt(result.length()-1);
        result.deleteCharAt(result.length()-1);
        result.append("\n]\n}");
        System.out.println(result);
        return result.toString();
    }
    private ArrayList<LinkedHashSet<Sequence>> getSortedList(HashMap<String, Sequence> map,ResultInfo resultInfo) {
        ArrayList<LinkedHashSet<Sequence>> sequenceArray = new ArrayList<>();
        while (!map.isEmpty()) {
            LinkedHashSet<Sequence> sequenceLinkedSet = new LinkedHashSet<>();
            List<Sequence> currentList = new ArrayList<>(map.values());
            for (Sequence currentSeq : currentList) {
                map.remove(currentSeq.getSequence());
                Sequence rightSeq = getSeqFromMap(map, currentSeq.getSequence().substring(1));
                Sequence leftSeq = getSeqFromMap(map, currentSeq.getSequence().substring(0, currentSeq.getLength() - 1));
                if (rightSeq != null || leftSeq != null) {
                    sequenceLinkedSet.add(currentSeq);

                    while (rightSeq != null) {
                        sequenceLinkedSet.add(rightSeq);
                        map.remove(rightSeq.getSequence());
                        rightSeq = getSeqFromMap(map, rightSeq.getSequence().substring(1));
                    }
                    while (leftSeq != null) {
                        LinkedHashSet<Sequence> temp = new LinkedHashSet<>(sequenceLinkedSet);
                        sequenceLinkedSet.clear();
                        sequenceLinkedSet.add(leftSeq);
                        sequenceLinkedSet.addAll(temp);
                        map.remove(leftSeq.getSequence());
                        leftSeq = getSeqFromMap(map, leftSeq.getSequence().substring(0, leftSeq.getLength() - 1));
                    }
                    sequenceArray.add(sequenceLinkedSet);
                    break;
                }
            }
            resultInfo.increaseSequence_restored();
        }
        return sequenceArray;
    }
    private ArrayList<Sequence> collect(ArrayList<LinkedHashSet<Sequence>> sourceList,ResultInfo resultInfo){
        ArrayList<Sequence> trueSeq=new ArrayList<>();
        for(LinkedHashSet<Sequence> sequenceSet:sourceList){
            boolean first=true;
            Sequence resultSequence = null;
            resultInfo.increaseGenomes_analysed();
            for(Sequence sequence:sequenceSet){
                if(first){
                    first=false;
                    resultSequence=sequence;
                    continue;
                }
                resultSequence.wideLeftSequence(sequence);
                resultInfo.increaseGenomes_analysed();

            }
            trueSeq.add(resultSequence);
        }
        return trueSeq;
    }
    private Sequence getSeqFromMap(HashMap<String, Sequence> list, String searchStr) {
        return list.values().stream()
                .filter(sequence -> sequence.getSequence().contains(searchStr)).findFirst().orElse(null);
    }
    @Override
    public String updateAndGetResult(int scatter, boolean useN, ResultInfo resultInfo) {
        return analisePiecesAndGetResult(sequenceService.getBestSequencesAsMap(scatter),scatter,useN,resultInfo);
    }


}