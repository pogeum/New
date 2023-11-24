package com.webProject.webProject.Photo;

import com.webProject.webProject.Review.Review;
import com.webProject.webProject.Review.ReviewRepository;
import com.webProject.webProject.Review_tag.Review_tag;
import com.webProject.webProject.Tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final ReviewRepository reviewRepository;

    @Value("${ImgLocation}")
    public String imgLocation;

    public void saveImgsForReview(Review review, List<MultipartFile> files) throws Exception {
        if (review != null && files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String projectPath = imgLocation;
                    UUID uuid = UUID.randomUUID();
                    String fileName = uuid + "_" + file.getOriginalFilename();
                    File saveFile = new File(projectPath, fileName);
                    file.transferTo(saveFile);

                    Photo photo = new Photo();
                    photo.setFileName(fileName);
                    photo.setFilePath(saveFile.getAbsolutePath());
                    photo.setReview(review);

                    this.photoRepository.save(photo);
                }
            }
        }
    }

    public void deletePhotosByReview(Review review) {
        List<Photo> photos = review.getPhotoList();

        if (photos != null && !photos.isEmpty()) {
            for (Photo photo : new ArrayList<>(photos)) {
                File imageFile = new File(photo.getFilePath());
                if (imageFile.exists()) {
                    imageFile.delete();
                }
                this.photoRepository.delete(photo);
                photos.remove(photo);
            }
        }
    }
}