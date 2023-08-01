package com.pavi.alconsov2.service;

import com.pavi.alconsov2.entity.ProgramStatus;
import com.pavi.alconsov2.entity.ResultInfo;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Getter
public class GenomeAnaliseService {
    private final ResultInfo resultInfo;
    private boolean analysing;

    private final ObtainingDataFromFilesService obtainingDataFromFilesService;
    private final SequenceRestoreService sequenceRestoreService;
    public GenomeAnaliseService(ObtainingDataFromFilesService obtainingDataFromFilesService,
                                ResultInfo resultInfo,
                                SequenceRestoreService sequenceRestoreService) {
        this.obtainingDataFromFilesService = obtainingDataFromFilesService;
        this.resultInfo=resultInfo;
        this.sequenceRestoreService = sequenceRestoreService;
    }

    public void startAnaliseGenome(String folderDestination,Integer scatter,boolean useN) throws Exception {
        File source=new File(folderDestination);
        if (!source.exists()) throw new Exception("SOURCE_FOLDER_DOESNT_EXISTS: "+source);
        analysing=true;
        resultInfo.setGenome_status(ProgramStatus.READING);
        obtainingDataFromFilesService.obtainFilesData(source,resultInfo,useN,scatter,sequenceRestoreService);
    }


}
