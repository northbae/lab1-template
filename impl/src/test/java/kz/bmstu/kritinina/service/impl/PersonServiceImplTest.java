package kz.bmstu.kritinina.service.impl;

import kz.bmstu.kritinina.dto.PersonRequest;
import kz.bmstu.kritinina.dto.PersonResponse;
import kz.bmstu.kritinina.entity.Person;
import kz.bmstu.kritinina.exception.NotFoundException;
import kz.bmstu.kritinina.mapper.PersonMapper;
import kz.bmstu.kritinina.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void createPerson_WhenPersonIsValid_ShouldReturnPersonId() {
        Long id = 1L;
        PersonRequest request = new PersonRequest("name", 10, "address", "work");
        Person person = new Person(id, "name", 10, "address", "work");

        when(personMapper.toPerson(request)).thenReturn(person);
        when(personRepository.save(person)).thenReturn(person);

        Long result = personService.createPerson(request);

        assertNotNull(result);
        assertEquals(id, result);
        verify(personMapper).toPerson(request);
        verify(personRepository).save(person);
    }

    @Test
    void deletePerson_WhenPersonDoesNotExist_ShouldThrowException() {
        final Long id = 1L;
        doThrow(NotFoundException.class).when(personRepository).deleteById(id);

        assertThrows(NotFoundException.class, () -> personService.deletePersonById(id));

        verify(personRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void getPersonById_WhenPersonExists_ShouldReturnPersonResponse() {
        Long id = 1L;
        Person person = new Person(id, "name", 10, "address", "work");
        PersonResponse expectedResponse = new PersonResponse(id, "name", 10, "address", "work");

        when(personRepository.findById(id)).thenReturn(Optional.of(person));
        when(personMapper.toPersonResponse(person)).thenReturn(expectedResponse);

        PersonResponse result = personService.getPersonById(id);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(personRepository).findById(id);
        verify(personMapper).toPersonResponse(person);
    }

    @Test
    void getPersonById_WhenPersonNotExists_ShouldThrowNotFoundException() {
        Long id = 1L;

        when(personRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> personService.getPersonById(id));
        verify(personRepository).findById(id);
        verify(personMapper, never()).toPersonResponse(any());
    }

    @Test
    void getAllPersons_WhenPersonsExist_ShouldReturnListOfPersonResponses() {
        Person person = new Person(1L, "name", 10, "address", "work");

        PersonResponse personResponse = new PersonResponse(1L, "name", 10, "address", "work");

        List<Person> persons = List.of(person);
        List<PersonResponse> expectedResponses = List.of(personResponse);

        when(personRepository.findAll()).thenReturn(persons);
        when(personMapper.toPersonResponse(person)).thenReturn(personResponse);

        List<PersonResponse> result = personService.getAllPersons();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(expectedResponses, result);
        verify(personRepository).findAll();
        verify(personMapper, times(1)).toPersonResponse(any(Person.class));
    }

    @Test
    void getAllPersons_WhenNoPersons_ShouldReturnEmptyList() {
        when(personRepository.findAll()).thenReturn(List.of());

        List<PersonResponse> result = personService.getAllPersons();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(personRepository).findAll();
        verify(personMapper, never()).toPersonResponse(any());
    }
}