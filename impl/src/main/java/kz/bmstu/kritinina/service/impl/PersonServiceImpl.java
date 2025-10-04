package kz.bmstu.kritinina.service.impl;

import kz.bmstu.kritinina.dto.PersonRequest;
import kz.bmstu.kritinina.dto.PersonResponse;
import kz.bmstu.kritinina.entity.Person;
import kz.bmstu.kritinina.exception.NotFoundException;
import kz.bmstu.kritinina.mapper.PersonMapper;
import kz.bmstu.kritinina.repository.PersonRepository;
import kz.bmstu.kritinina.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    @Transactional
    public Long createPerson(PersonRequest personRequest) {
        Person person = personRepository.save(personMapper.toPerson(personRequest));
        return person.getId();
    }

    @Override
    public PersonResponse getPersonById(Long id) {
        return personRepository.findById(id).map(personMapper::toPersonResponse).orElseThrow(() -> new NotFoundException("Person not found"));
    }

    @Override
    public List<PersonResponse> getAllPersons() {
        return personRepository.findAll().stream().map(personMapper::toPersonResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PersonResponse changePerson(Long id, PersonRequest personChanges) {
        Person person = personRepository.findById(id).orElseThrow(() -> new NotFoundException("Person not found"));
        if (personChanges.getName() != null) {
            person.setName(personChanges.getName());
        }
        if (personChanges.getAge() != null) {
            person.setAge(personChanges.getAge());
        }
        if (personChanges.getAddress() != null) {
            person.setAddress(personChanges.getAddress());
        }
        if (personChanges.getWork() != null) {
            person.setWork(personChanges.getWork());
        }
        return personMapper.toPersonResponse(personRepository.save(person));
    }

    @Override
    public void deletePersonById(Long id) {
        personRepository.deleteById(id);
    }
}
