package com.startup.oda.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.startup.oda.dto.response.ProfileImageDataDto;
import com.startup.oda.entity.FileData;
import com.startup.oda.entity.User;
import com.startup.oda.exception.exceptionsList.ImageNotFoundException;
import com.startup.oda.exception.exceptionsList.UserNotFoundException;
import com.startup.oda.exception.exceptionsList.WrongProfileImageType;
import com.startup.oda.repository.FileDataRepository;
import com.startup.oda.repository.UserRepository;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
public class FileService {
    private final UserRepository userRepository;
    private final FileDataRepository fileDataRepository;
    private final AmazonS3 s3Client;
    @Value("${aws.s3.access-key}")
    private String accessKey = "AKIAZQ3DPQOBO5RQJYFO";
    @Value("${aws.s3.secret-key}")
    private String secretKey = "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";
    @Value("${aws.s3.bucket}")
    private String bucketName;


    public FileService(UserRepository userRepository, FileDataRepository fileDataRepository) {
        this.fileDataRepository = fileDataRepository;
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
        this.userRepository = userRepository;
    }

    public String uploadProfileImage(String email, MultipartFile file) throws IOException {
        if (!ContentType.IMAGE_JPEG.toString().equals(file.getContentType())
                && !ContentType.IMAGE_PNG.toString().equals(file.getContentType())){
            throw new WrongProfileImageType();
        }
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        File tempFile = convertMultiPartToFile(file);
        String fileName = generateFileName(file);

        s3Client.putObject(new PutObjectRequest(bucketName, fileName, tempFile)).setContentMd5(file.getContentType());
        tempFile.delete();

        FileData fileData = new FileData(file.getOriginalFilename(), file.getContentType(), fileName);
        fileDataRepository.save(fileData);

        user.setFileData(fileData);
        userRepository.save(user);
        return fileName;
    }

    public ProfileImageDataDto getProfileImage(String email) throws IOException {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        if (user.getFileData() == null || user.getFileData().getPath().isEmpty()){
            throw new ImageNotFoundException();
        }
        S3Object s3object = s3Client.getObject(bucketName, user.getFileData().getPath());
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        byte[] imageData = IOUtils.toByteArray(inputStream);
        String contentType = s3object.getObjectMetadata().getContentType();
        return new ProfileImageDataDto(contentType, imageData);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return "profile/" +
                System.currentTimeMillis() +
                "-" +
                Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }
}
