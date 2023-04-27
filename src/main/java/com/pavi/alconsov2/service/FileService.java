package com.pavi.alconsov2.service;

import com.google.gson.JsonSyntaxException;
import com.pavi.alconsov2.entity.Organism;
import com.pavi.alconsov2.entity.ResultInfo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.zip.DataFormatException;

@Service
public class FileService {

    private final OrganismService organismService;

    public FileService(OrganismService organismService) {
        this.organismService = organismService;
    }

    public void analiseFileContent(File source, ResultInfo resultInfo, boolean useN)  {
        try {
            Organism organism=organismService.getOrganismFromJson(getFileContent(source),source.getAbsolutePath(),resultInfo,useN);
            organismService.addOrganism(organism);
            resultInfo.increaseGenomes_downloaded();
        } catch (DataFormatException | JsonSyntaxException e) {
            resultInfo.addGenomeError(e.getMessage());
            resultInfo.increaseGenomes_ignored();
        } catch(OrganismService.OrganismAlreadyExistsException e) {
            resultInfo.addGenomeError(e.getMessage()+" File "+source.getAbsolutePath()+" is ignored");
            resultInfo.increaseGenomes_ignored();
        } catch(IOException e) {
            resultInfo.addGenomeError("Could not read the file: "+source.getAbsolutePath());
            resultInfo.increaseGenomes_ignored();
        }
    }
    private String getFileContent(File source) throws IOException {
        return Files.readString(source.toPath());
    }


}
