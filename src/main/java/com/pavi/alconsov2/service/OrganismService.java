package com.pavi.alconsov2.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pavi.alconsov2.entity.Organism;
import com.pavi.alconsov2.entity.ResultInfo;
import com.pavi.alconsov2.repo.OrganismRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.zip.DataFormatException;

@Service
public class OrganismService {

    @Value("${definingLength}")
    private Integer definingLength;
    private final OrganismRepository organismRepository;
    private final SequenceService sequenceService;

    public OrganismService(OrganismRepository organismRepository, SequenceService sequenceService) {
        this.organismRepository = organismRepository;
        this.sequenceService = sequenceService;
    }

    public Organism getOrganismFromJson(String json, String path,ResultInfo resultInfo,boolean useN)
            throws DataFormatException,JsonSyntaxException {
        Organism organism;
        try {
            organism=new Gson().fromJson(json, (Type) Organism.class);
        }catch (JsonSyntaxException e){
            throw new JsonSyntaxException("Json format error: "+path);
        }

        if(organism==null){
            throw new DataFormatException("Data format error (The genome is not taken into account):\n\t "+ path+
                    "\n\t\t"+"Fasta is empty");
        }
        String seq =organism.getSequence().replaceAll(" ", "").toLowerCase();
        String deletedChars = seq.replaceAll("a", "")
                                 .replaceAll("t", "")
                                 .replaceAll("g", "")
                                 .replaceAll("c", "")
                                 .replaceAll("n", "");

        if (!useN&&seq.contains("n")){
            throw new DataFormatException("Genome contains 'N' (The genome is not taken into account):\n\t "+ path);
        }

        if(Strings.isBlank(seq)|| seq.equals("")){
            throw new DataFormatException("Data format error (The genome is not taken into account):\n\t "+
                    path+ "\n\t\t"+"Fasta is empty");
        }

        if ((seq.length()-deletedChars.length()<= definingLength)) {
            throw new DataFormatException("Data format error (The genome is not taken into account):\n\t "+
                    path+ "\n\t\t"+"Fasta is too small");
        }

        organism.setDeletedChars(deletedChars);
        if (!deletedChars.equals("")){
            StringBuilder message= new StringBuilder();
            while (!deletedChars.equals("")){
                seq = seq.replaceAll(String.valueOf(deletedChars.charAt(0)),"");
                message.append(deletedChars.charAt(0));
                deletedChars=deletedChars.replaceAll(String.valueOf(deletedChars.charAt(0)),"");
            }
            resultInfo.addGenomeWarning("Data format warning \n\t "+path+ "\n\t\t"+
                    "The following characters have been replaced by n: "+message);
        }
        organism.setSequence(seq.toLowerCase());
        return organism;
    }

    public void addOrganism(Organism organism) throws IOException {
        if(organismRepository.contains(organism.getDataset()))
            throw new OrganismAlreadyExistsException("The organism: " + organism.getDataset() + " has already been injected");
        organismRepository.save(organism);   //TODO теоретически можно сохранять строки в организме, чтобы потом как-то восстанавливать, но это накладно
        sequenceService.sliceAndSave(organism.getSequence());
    }

    public static class OrganismAlreadyExistsException extends IOException{
        public OrganismAlreadyExistsException(String message) {
            super(message);
        }
    }

}
