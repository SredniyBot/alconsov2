package com.pavi.alconsov2.controller;

import com.pavi.alconsov2.entity.ProgramStatus;
import com.pavi.alconsov2.entity.ResultInfo;
import com.pavi.alconsov2.service.GenomeAnaliseService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
public class StatusController {

    private final GenomeAnaliseService genomeAnaliseService;

    public StatusController(GenomeAnaliseService genomeAnaliseService) {
        this.genomeAnaliseService = genomeAnaliseService;
    }

    @GetMapping("/status")
    public ResultInfo getResultInfo(){
        if (!genomeAnaliseService.isAnalysing())return null;
        return genomeAnaliseService.getResultInfo();
    }
    @GetMapping("/download")
    public ResponseEntity<Object> downloadFile(RedirectAttributes redirectAttributes) {
        if (!genomeAnaliseService.isAnalysing()&&
                genomeAnaliseService.getResultInfo().getGenome_status()!= ProgramStatus.DONE)return null;
        File file = new File("result.json");
        InputStreamResource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"result.json\"")
                .contentType(MediaType.parseMediaType("application/txt")).body(resource);
    }

}
