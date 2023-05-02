package com.javadreamteam.shelteranimalbot.service;
import com.javadreamteam.shelteranimalbot.exceptions.CatException;
import com.javadreamteam.shelteranimalbot.model.Cat;
import com.javadreamteam.shelteranimalbot.repository.CatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CatServiceTest {
    @Mock
    private CatRepository catRepository;

    private CatService catService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        catService = new CatService(catRepository);
    }

    @Test
    void create() {
        Cat cat = new Cat();
        cat.setName("Tom");
        cat.setAge(2);

        when(catRepository.save(cat)).thenReturn(cat);

        Cat createdCat = catService.create(cat);

        assertNotNull(createdCat);
        assertEquals(cat.getName(), createdCat.getName());
        assertEquals(cat.getAge(), createdCat.getAge());

        verify(catRepository, times(1)).save(cat);
    }
    @Test
    void getById() {
        Long id = 1L;
        Cat cat = new Cat();
        cat.setId(id);
        cat.setName("Tom");
        cat.setAge(2);

        when(catRepository.findById(id)).thenReturn(Optional.of(cat));

        Cat foundCat = catService.getById(id);

        assertNotNull(foundCat);
        assertEquals(cat.getId(), foundCat.getId());
        assertEquals(cat.getName(), foundCat.getName());
        assertEquals(cat.getAge(), foundCat.getAge());

        verify(catRepository, times(1)).findById(id);
    }
    @Test
    void getByIdNotFound() {
        Long id = 1L;

        when(catRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CatException.class, () -> catService.getById(id));

        verify(catRepository, times(1)).findById(id);
    }
    @Test
    void update() {
        Long id = 1L;
        Cat cat = new Cat();
        cat.setId(id);
        cat.setName("Tom");
        cat.setAge(2);

        when(catRepository.findById(id)).thenReturn(Optional.of(cat));
        when(catRepository.save(cat)).thenReturn(cat);

        Cat updatedCat = catService.update(cat);

        assertNotNull(updatedCat);
        assertEquals(cat.getId(), updatedCat.getId());
        assertEquals(cat.getName(), updatedCat.getName());
        assertEquals(cat.getAge(), updatedCat.getAge());

        verify(catRepository, times(1)).findById(id);
        verify(catRepository, times(1)).save(cat);
    }
    @Test
    void updateNotFound() {
        Cat cat = new Cat();
        cat.setId(1L);
        cat.setName("Tom");
        cat.setAge(2);

        when(catRepository.findById(cat.getId())).thenReturn(Optional.empty());

        assertThrows(CatException.class, () -> catService.update(cat));

        verify(catRepository, times(1)).findById(cat.getId());
    }
    @Test
    void removeById() {
        Long id = 1L;

        catService.removeById(id);

        verify(catRepository, times(1)).deleteById(id);
    }
    @Test
    void getAll() {
        Collection<Cat> cats = new ArrayList<>();
        Cat cat1 = new Cat();
        cat1.setId(1L);
        cat1.setName("Tom");
        cat1.setAge(2);
        cats.add(cat1);

        Cat cat2 = new Cat();
        cat2.setId(2L);
        cat2.setName("Jerry");
        cat2.setAge(3);
        cats.add(cat2);

        when(catRepository.findAll()).thenReturn((List<Cat>) cats);

        Collection<Cat> foundCats = catService.getAll();

        assertNotNull(foundCats);
        assertEquals(cats.size(), foundCats.size());

        for (Cat foundCat : foundCats) {
            assertTrue(cats.contains(foundCat));
        }

        verify(catRepository, times(1)).findAll();
    }

}