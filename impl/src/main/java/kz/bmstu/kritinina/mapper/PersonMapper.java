package kz.bmstu.kritinina.mapper;

import kz.bmstu.kritinina.dto.PersonRequest;
import kz.bmstu.kritinina.dto.PersonResponse;
import kz.bmstu.kritinina.entity.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toPerson(PersonRequest personRequest);

    PersonResponse toPersonResponse(Person person);
}
