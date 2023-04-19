package com.javadreamteam.shelteranimalbot.service.photo;

import com.javadreamteam.shelteranimalbot.model.photo.DogPhoto;
import com.javadreamteam.shelteranimalbot.repository.photo.DogPhotoRepository;
import com.javadreamteam.shelteranimalbot.service.DogService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class DogPhotoService {

    @Value("${dog.photo.dir.path}")
    private String dir;

    private final DogService dogService;
    private final DogPhotoRepository dogPhotoRepository;

    public DogPhotoService(DogService dogService, DogPhotoRepository dogPhotoRepository) {
        this.dogService = dogService;
        this.dogPhotoRepository = dogPhotoRepository;
    }

    /**
     * Добавление фото собаки в БД
     *
     * @param id петомца
     * @param file фото
     */
    public void upload(long id, MultipartFile file) throws IOException {
        var dogPhoto = new DogPhoto();
        var dog = dogService.getById(id);
        Path path = Path.of(dir,
                dogPhoto.getId().toString() + "."
                        + FilenameUtils.getExtension(file.getOriginalFilename()));
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);
        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        dogPhoto.setDog(dog);
        dogPhoto.setPath(path.toString());
        dogPhoto.setSize(file.getSize());
        dogPhoto.setMediaType(file.getContentType());
        dogPhoto.setData(file.getBytes());
        dogPhotoRepository.save(dogPhoto);
    }

    public Collection<DogPhoto> getDogPhotos(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return dogPhotoRepository.findAll(pageRequest).getContent();
    }
}
