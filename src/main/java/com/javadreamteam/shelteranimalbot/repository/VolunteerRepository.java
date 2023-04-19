package com.javadreamteam.shelteranimalbot.repository;


import com.javadreamteam.shelteranimalbot.model.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {


@Query(value = "SELECT * FROM volunteer ORDER BY RANDOM() LIMIT 1", nativeQuery = true)

List <Volunteer> getRandomVolunteer ();

}
