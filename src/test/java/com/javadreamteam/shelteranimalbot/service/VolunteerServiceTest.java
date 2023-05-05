//package com.javadreamteam.shelteranimalbot.service;
//
//import com.javadreamteam.shelteranimalbot.model.Volunteer;
//import com.javadreamteam.shelteranimalbot.repository.VolunteerRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//
//
//class VolunteerServiceTest {
//
//    @Mock
//    private VolunteerRepository volunteerRepository;
//
//    @InjectMocks
//    private VolunteerService volunteerService;
//
//
//    @Test
//    void createVolunteer() {
//        Volunteer volunteer = new Volunteer();
//        volunteer.setName("John");
//        volunteer.setUsername("john123");
//        volunteer.setPhone("1234567890");
//        volunteer.setChatId(12345L);
//        volunteer.setUserId(67890L);
//
//        Mockito.when(volunteerRepository.save(volunteer)).thenReturn(volunteer);
//
//        Volunteer savedVolunteer = volunteerService.createVolunteer(volunteer);
//
//        assertNotNull(savedVolunteer.getId());
//        assertEquals(volunteer.getName(), savedVolunteer.getName());
//        assertEquals(volunteer.getUsername(), savedVolunteer.getUsername());
//        assertEquals(volunteer.getPhone(), savedVolunteer.getPhone());
//        assertEquals(volunteer.getChatId(), savedVolunteer.getChatId());
//        assertEquals(volunteer.getUserId(), savedVolunteer.getUserId());
//
//        Mockito.verify(volunteerRepository, Mockito.times(1)).save(volunteer);
//    }
//
//
//
//    @Test
//    void getById() {
//        Volunteer volunteer = new Volunteer();
//        volunteer.setId(1L);
//        volunteer.setName("John");
//        volunteer.setUsername("john123");
//        volunteer.setPhone("1234567890");
//        volunteer.setChatId(12345L);
//        volunteer.setUserId(67890L);
//
//        Mockito.when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));
//
//        Volunteer foundVolunteer = volunteerService.getById(1L);
//
//        assertNotNull(foundVolunteer);
//        assertEquals(volunteer.getId(), foundVolunteer.getId());
//        assertEquals(volunteer.getName(), foundVolunteer.getName());
//        assertEquals(volunteer.getUsername(), foundVolunteer.getUsername());
//        assertEquals(volunteer.getPhone(), foundVolunteer.getPhone());
//        assertEquals(volunteer.getChatId(), foundVolunteer.getChatId());
//        assertEquals(volunteer.getUserId(), foundVolunteer.getUserId());
//
//        Mockito.verify(volunteerRepository, Mockito.times(1)).findById(1L);
//    }
//
//    @Test
//    void updateVolunteer() {
//        Volunteer volunteer = new Volunteer();
//        volunteer.setId(1L);
//        volunteer.setName("John");
//        volunteer.setUsername("john123");
//        volunteer.setPhone("1234567890");
//        volunteer.setChatId(12345L);
//        volunteer.setUserId(67890L);
//
//        Volunteer updatedVolunteer = new Volunteer();
//        updatedVolunteer.setId(1L);
//        updatedVolunteer.setName("John Doe");
//        updatedVolunteer.setUsername("johndoe123");
//        updatedVolunteer.setPhone("0987654321");
//        updatedVolunteer.setChatId(54321L);
//        updatedVolunteer.setUserId(98760L);
//
//        Mockito.when(volunteerRepository.findById(1L)).thenReturn(Optional.of(volunteer));
//        Mockito.when(volunteerRepository.save(updatedVolunteer)).thenReturn(updatedVolunteer);
//
//        Volunteer returnedVolunteer = volunteerService.updateVolunteer(1L, updatedVolunteer);
//
//        assertNotNull(returnedVolunteer);
//        assertEquals(updatedVolunteer.getId(), returnedVolunteer.getId());
//        assertEquals(updatedVolunteer.getName(), returnedVolunteer.getName());
//        assertEquals(updatedVolunteer.getUsername(), returnedVolunteer.getUsername());
//        assertEquals(updatedVolunteer.getPhone(), returnedVolunteer.getPhone());
//        assertEquals(updatedVolunteer.getChatId(), returnedVolunteer.getChatId());
//        assertEquals(updatedVolunteer.getUserId(), returnedVolunteer.getUserId());
//
//        Mockito.verify(volunteerRepository, Mockito.times(1)).findById(1L);
//        Mockito.verify(volunteerRepository, Mockito.times(1)).save(updatedVolunteer);
//    }
//
//    @Test
//    void getRandomVolunteer() {
//        Volunteer volunteer = new Volunteer();
//        volunteer.setId(1L);
//        volunteer.setName("John");
//        volunteer.setUsername("john123");
//        volunteer.setPhone("1234567890");
//        volunteer.setChatId(12345L);
//        volunteer.setUserId(67890L);
//
//        Mockito.when(volunteerRepository.getRandomVolunteer()).thenReturn(Optional.of(volunteer));
//
//        Volunteer returnedVolunteer = volunteerService.getRandomVolunteer();
//
//        assertNotNull(returnedVolunteer);
//        assertEquals(volunteer.getId(), returnedVolunteer.getId());
//        assertEquals(volunteer.getName(), returnedVolunteer.getName());
//        assertEquals(volunteer.getUsername(), returnedVolunteer.getUsername());
//        assertEquals(volunteer.getPhone(), returnedVolunteer.getPhone());
//        assertEquals(volunteer.getChatId(), returnedVolunteer.getChatId());
//        assertEquals(volunteer.getUserId(), returnedVolunteer.getUserId());
//
//        Mockito.verify(volunteerRepository, Mockito.times(1)).getRandomVolunteer();
//    }
//}