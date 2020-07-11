package com.microservice.house.service.impl;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.microservice.house.service.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.path}")
    private String filePath;

    @Override
    public List<String> getImgPath(List<MultipartFile> files) {
        List<String>paths= Lists.newArrayList();
        files.forEach(file -> {
            File localFile=null;
            //判断上传是否为空
            if(!file.isEmpty()){
                try{
                    localFile=saveToLocal(file,filePath);
                    String path= StringUtils.substringAfterLast(localFile.getAbsolutePath(),filePath);
                    paths.add(path);
                }catch (IOException e){
                    throw new IllegalArgumentException();
                }
            }
        });
        return paths;
    }

    private File saveToLocal(MultipartFile file, String filePath) throws IOException {
        File newFile=new File(filePath+"/"+ Instant.now().getEpochSecond()+"/"+file.getOriginalFilename());
        if(!newFile.exists()){
            //创建上级目录
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        Files.write(file.getBytes(),newFile);
        return newFile;
    }
}
