package com.javadreamteam.shelteranimalbot.service;

import com.javadreamteam.shelteranimalbot.model.Dog;
import com.javadreamteam.shelteranimalbot.repository.DogRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DogServiceTest {

    @Test
    void create() {
        Dog dog = new Dog(null, "Fido", "Labrador Retriever", 3, "Friendly and playful");
        DogRepository mockRepository = mock(DogRepository.class);
        when(mockRepository.save(any(Dog.class))).thenAnswer(invocation -> {
            Dog savedDog = invocation.getArgument(0);
            savedDog.setId(1L);
            return savedDog;
        });
        DogService service = new DogService(mockRepository);
        Dog createdDog = service.create(dog);
        verify(mockRepository, times(1)).save(dog);
        assertEquals(1L, (long) createdDog.getId());
    }




    @Test
    void getById() {
        DogRepository repository = mock(DogRepository.class);
        DogService service = new DogService(repository);

        Dog dog = new Dog();
        dog.setId(1L);
        dog.setName("Rex");
        dog.setBreed("German Shepherd");
        dog.setAge(3);

        when(repository.findById(1L)).thenReturn(Optional.of(dog));

        Dog foundDog = service.getById(1L);

        assertEquals(foundDog.getId(), dog.getId());
        assertEquals(foundDog.getName(), dog.getName());
        assertEquals(foundDog.getBreed(), dog.getBreed());
        assertEquals(foundDog.getAge(), dog.getAge());
    }

    @Test
    void update() {
        DogRepository repository = mock(DogRepository.class);
        DogService service = new DogService(repository);

        Dog dog = new Dog();
        dog.setId(1L);
        dog.setName("Rex");
        dog.setBreed("German Shepherd");
        dog.setAge(3);

        when(repository.findById(1L)).thenReturn(Optional.of(dog));
        when(repository.save(dog)).thenReturn(dog);

        Dog updatedDog = service.update(dog);

        assertEquals(updatedDog.getId(), dog.getId());
        assertEquals(updatedDog.getName(), dog.getName());
        assertEquals(updatedDog.getBreed(), dog.getBreed());
        assertEquals(updatedDog.getAge(), dog.getAge());
    }

    @Test
    void removeById() {
        DogRepository repository = mock(DogRepository.class);
        DogService service = new DogService(repository);

        Long id = 1L;

        service.removeById(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void getAll() {
        DogRepository repository = mock(DogRepository.class);
        DogService service = new DogService(repository);

        List<Dog> dogs = new ArrayList<>();
        Dog dog1 = new Dog();
        dog1.setId(1L);
        dog1.setName("Rex");
        dog1.setBreed("German Shepherd");
        dog1.setAge(3);
        dogs.add(dog1);

        Dog dog2 = new Dog();
        dog2.setId(2L);
        dog2.setName("Max");
        dog2.setBreed("Labrador Retriever");
        dog2.setAge(5);
        dogs.add(dog2);

        when(repository.findAll()).thenReturn(dogs);

        Collection<Dog> foundDogs = service.getAll();

        assertEquals(foundDogs.size(), dogs.size());
    }
}