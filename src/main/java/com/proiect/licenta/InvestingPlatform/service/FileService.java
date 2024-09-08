package com.proiect.licenta.InvestingPlatform.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public String uploadFile(MultipartFile file);
}
