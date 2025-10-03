package kz.bmstu.kritinina.service;


import kz.bmstu.kritinina.dto.PersonRequest;
import kz.bmstu.kritinina.dto.PersonResponse;

import java.util.List;

public interface PersonService {
    Long createPerson(PersonRequest personRequest);
    PersonResponse changePerson(Long id, PersonRequest personRequest);
    PersonResponse getPersonById(Long id);
    List<PersonResponse> getAllPersons();
    void deletePersonById(Long id);
}
