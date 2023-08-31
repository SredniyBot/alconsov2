package com.pavi.alconsov2.service;

import com.pavi.alconsov2.entity.ProgramStatus;
import com.pavi.alconsov2.entity.ResultInfo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ObtainingDataFromFilesService {
    private final FileService fileService;

    public ObtainingDataFromFilesService(FileService fileService) {
        this.fileService = fileService;
    }

    @Async
    public void obtainFilesData(File source,
                                ResultInfo resultInfo,
                                boolean useN,
                                int scatter,
                                FilesResultObserver observer) throws Exception {
        List<File> files = countFilesAndGet(source,resultInfo);
        ExecutorService executor = Executors.newCachedThreadPool();
        for(File file: files) {
            executor.execute(() -> fileService.analiseFileContent(file,resultInfo,useN));
        }
        try {
            executor.shutdown();
            executor.awaitTermination(10000, TimeUnit.DAYS);
        }catch (Exception e){
            e.printStackTrace();
        }
        writeToFile(observer.updateAndGetResult(scatter,useN,resultInfo));
        resultInfo.setGenome_status(ProgramStatus.DONE);
    }

    private List<File> countFilesAndGet(File source, ResultInfo resultInfo) throws Exception {
        if(source.isDirectory()){
            List<File> answer =getFiles(source,resultInfo);
            if(answer.size()==0) throw new Exception("NO_JSON_FILE_IN_DIRECTORY: "+source.getAbsolutePath());
            resultInfo.setGenomes_all(answer.size());
            return answer;
        }else {
            throw new Exception("CHOSEN_FILE_IS_NOT_DIRECTORY: "+source.getAbsolutePath());
        }
    }

    private List<File> getFiles(File sourceFile,ResultInfo resultInfo){
        ArrayList<File> list =new ArrayList<>();
        if(sourceFile==null) return list;
        File[] source=sourceFile.listFiles();
        if(source==null) return list;
        for (File s:source) {
            if (s.isDirectory()){
                list.addAll(getFiles(s,resultInfo));
            } else if(getFileExtension(s).equals("json")){
                list.add(s);
                resultInfo.setGenomes_all(list.size());
            }
        }
        return list;
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    private void writeToFile(String res) throws IOException {
        Path path = Paths.get("result.json");
        byte[] bytes = res.getBytes();
        Files.write(path, bytes);
    }

}
