package kz.bmstu.kritinina.controller;

import kz.bmstu.kritinina.dto.PersonRequest;
import kz.bmstu.kritinina.dto.PersonResponse;
import kz.bmstu.kritinina.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonControllerImpl implements PersonController{
    private final PersonService personService;

    @Override
    public ResponseEntity<PersonResponse> getPersonById(Long id) {
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @Override
    public ResponseEntity<List<PersonResponse>> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @Override
    public ResponseEntity<String> createPerson(PersonRequest personRequest) {
        Long id = personService.createPerson(personRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location","api/v1/persons/" + id);
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PersonResponse> changePerson(Long id, PersonRequest personRequest) {
        return ResponseEntity.ok(personService.changePerson(id, personRequest));
    }

    @Override
    public ResponseEntity<String> deletePerson(Long id) {
        personService.deletePersonById(id);
        return new ResponseEntity<>("Person with id: " + id + " deleted", HttpStatus.NO_CONTENT);
    }
}
