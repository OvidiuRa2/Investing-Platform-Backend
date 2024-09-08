package com.proiect.licenta.InvestingPlatform.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service implements FileService{

    public static final String OVIDIU_IMAGES_BUCKET = "ovidiuimagesbucket";
    private final AmazonS3 awsS3Client;


    @Override
    public String uploadFile(MultipartFile file) {
       var filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

       var key= UUID.randomUUID().toString() + "." + filenameExtension;
       var metdata = new ObjectMetadata();
       metdata.setContentLength(file.getSize());
       metdata.setContentType(file.getContentType());

        try {
            awsS3Client.putObject(OVIDIU_IMAGES_BUCKET,key,file.getInputStream(),metdata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return awsS3Client.getUrl(OVIDIU_IMAGES_BUCKET,key).toString();
    }
}
