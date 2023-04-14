package com.javadreamteam.shelteranimalbot.repository.photo;

import com.javadreamteam.shelteranimalbot.model.photo.DogPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogPhotoRepository extends JpaRepository<DogPhoto, Long> {
}
