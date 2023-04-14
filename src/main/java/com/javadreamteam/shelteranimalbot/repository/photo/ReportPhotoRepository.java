package com.javadreamteam.shelteranimalbot.repository.photo;

import com.javadreamteam.shelteranimalbot.model.photo.ReportPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportPhotoRepository extends JpaRepository<ReportPhoto, Long> {
}
