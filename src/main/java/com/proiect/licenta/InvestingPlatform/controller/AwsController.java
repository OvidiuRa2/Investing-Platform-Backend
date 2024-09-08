package com.proiect.licenta.InvestingPlatform.controller;

import com.proiect.licenta.InvestingPlatform.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/aws")
public class AwsController {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/image")
    public List<String> uploadImage(@RequestParam("file") List<MultipartFile> files){
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            String imageUrl = s3Service.uploadFile(file);
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }

}
