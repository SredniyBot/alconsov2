package com.pavi.alconsov2.entity;

import com.pavi.alconsov2.service.ResultObserver;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@ToString
public class ResultInfo {

//    private List<String> genomeErrorList;
//    private List<String> genomeWarningList;
    private Integer genomeErrorList=0;
    private Integer genomeWarningList=0;
    private Integer genomes_all=0;
    private Integer genomes_downloaded=0;
    private Integer unique_sequences=0;
    private Integer genomes_ignored=0;
    private Integer genomes_analysed=0;
    private Integer sequence_restored=0;
    private ProgramStatus genome_status;
    private final ResultObserver resultObserver;
    public ResultInfo(ResultObserver resultObserver){
        this.resultObserver=resultObserver;
//        genomeErrorList=new CopyOnWriteArrayList<>();
//        genomeWarningList=new CopyOnWriteArrayList<>();
        genome_status=ProgramStatus.SEPARATION;
    }

    public synchronized void addGenomeError(String error){
//        genomeErrorList.add(error);
        genomeErrorList++;
        resultObserver.update(this);
    }
    public synchronized void addGenomeWarning(String error){
//        genomeWarningList.add(error);
        genomeWarningList++;
        resultObserver.update(this);

    }
    public synchronized void setGenomes_all(Integer genomes_all) {
        this.genomes_all = genomes_all;
        resultObserver.update(this);

    }

    public synchronized void increaseGenomes_downloaded() {
        this.genomes_downloaded++;
        resultObserver.update(this);

    }
    public synchronized void increaseUnique_sequences(int i) {
        this.unique_sequences+=i;
        resultObserver.update(this);
    }
    public synchronized void increaseGenomes_ignored() {
        genomes_ignored ++;
        resultObserver.update(this);

    }

    public synchronized void increaseGenomes_analysed() {
        genomes_analysed++;
        resultObserver.update(this);
    }

    public synchronized void increaseSequence_restored() {
        sequence_restored++;
        resultObserver.update(this);

    }

    public synchronized void setGenome_status(ProgramStatus genome_status) {
        this.genome_status = genome_status;
        resultObserver.update(this);

    }

    public synchronized Integer getGenomeErrorList() {
        return genomeErrorList;
    }

    public synchronized Integer getGenomeWarningList() {
        return genomeWarningList;
    }

    public synchronized Integer getGenomes_all() {
        return genomes_all;
    }

    public synchronized Integer getGenomes_downloaded() {
        return genomes_downloaded;
    }

    public synchronized Integer getGenomes_ignored() {
        return genomes_ignored;
    }

    public synchronized Integer getGenomes_analysed() {
        return genomes_analysed;
    }

    public synchronized Integer getSequence_restored() {
        return sequence_restored;
    }

    public synchronized ProgramStatus getGenome_status() {
        return genome_status;
    }

    public synchronized Integer getUnique_sequences(){
        return unique_sequences;
    }

}
