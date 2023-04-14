package com.javadreamteam.shelteranimalbot.service.photo;

import com.javadreamteam.shelteranimalbot.model.photo.ReportPhoto;
import com.javadreamteam.shelteranimalbot.repository.photo.ReportPhotoRepository;
import com.javadreamteam.shelteranimalbot.service.ReportService;
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
public class ReportPhotoService {

    @Value("${report.photo.dir.path}")
    private String dir;

    private final ReportService reportService;
    private final ReportPhotoRepository reportPhotoRepository;

    public ReportPhotoService(ReportService ReportService, ReportPhotoRepository ReportPhotoRepository) {
        this.reportService = ReportService;
        this.reportPhotoRepository = ReportPhotoRepository;
    }

    /**
     * Добавление фото собаки в БД
     *
     * @param id петомца
     * @param file фото
     */
    public void upload(long id, MultipartFile file) throws IOException {
        var reportPhoto = new ReportPhoto();
        var report = reportService.findReportById(id);
        Path path = Path.of(dir,
                reportPhoto.getId().toString() + "."
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
        reportPhoto.setReport(report);
        reportPhoto.setPath(path.toString());
        reportPhoto.setSize(file.getSize());
        reportPhoto.setMediaType(file.getContentType());
        reportPhoto.setData(file.getBytes());
        reportPhotoRepository.save(reportPhoto);
    }

    public Collection<ReportPhoto> getReportPhotos(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return reportPhotoRepository.findAll(pageRequest).getContent();
    }

}
