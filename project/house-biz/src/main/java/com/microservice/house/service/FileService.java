package com.microservice.house.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ Description   :  将图像保存到本地
 * @ Author        :  1910959369@qq.com
 * @ CreateDate    :  2020/7/5 14:50
 */


public interface FileService {
    //---------------------------------------------------------------------------------------------
    public List<String>getImgPath(List<MultipartFile>files);
}
