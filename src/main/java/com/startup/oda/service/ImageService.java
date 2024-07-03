package com.startup.oda.service;

import com.startup.oda.entity.ImageData;
import com.startup.oda.entity.User;
import com.startup.oda.exception.exceptionsList.ImageNotFoundException;
import com.startup.oda.exception.exceptionsList.ImageUploadException;
import com.startup.oda.exception.exceptionsList.UserNotFoundException;
import com.startup.oda.repository.ImageDataRepository;
import com.startup.oda.repository.UserRepository;
import com.startup.oda.utils.ImageUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ImageService.class);
    private final ImageDataRepository imageRepository;
    private final UserRepository userRepository;

    public ImageService(ImageDataRepository imageRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public String uploadUserImage(String email, MultipartFile file) throws IOException {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        try {
            ImageData imageData = new ImageData(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    ImageUtils.compressImage(file.getBytes()),
                    user
            );

            imageRepository.save(imageData);
            user.setImage(imageData);
            userRepository.save(user);

            return imageData.getName();
        } catch (RuntimeException e){
            LOGGER.error("Error while uploading image: " + e);
            throw new ImageUploadException();
        }
    }
    @Transactional
    public byte[] getUserImage(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        ImageData imageData = imageRepository.findByUser(user).orElseThrow(ImageNotFoundException::new);

        return ImageUtils.decompressImage(imageData.getImageData());
    }
}
