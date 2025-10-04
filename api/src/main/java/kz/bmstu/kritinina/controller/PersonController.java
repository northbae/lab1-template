package kz.bmstu.kritinina.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kz.bmstu.kritinina.dto.ErrorResponse;
import kz.bmstu.kritinina.dto.PersonRequest;
import kz.bmstu.kritinina.dto.PersonResponse;
import kz.bmstu.kritinina.dto.ValidationErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("api/v1/persons")
public interface PersonController {
    @Operation(summary = "Get Person by ID", operationId = "getPerson")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person for ID",
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PersonResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found Person for ID",
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/{id}")
    ResponseEntity<PersonResponse> getPersonById(@PathVariable Long id);

    @Operation(summary = "Get all Persons", operationId = "listPersons")
    @ApiResponse(responseCode = "200", description = "All Persons",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PersonResponse.class))})
    @GetMapping
    ResponseEntity<List<PersonResponse>> getAllPersons();

    @Operation(summary = "Create new Person", operationId = "createPerson")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created new Person",
                    headers = @Header(name = "Location", description = "Path to new Person")),
            @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationErrorResponse.class)) })
    })
    @PostMapping
    ResponseEntity<String> createPerson(@RequestBody PersonRequest person);

    @Operation(summary = "Update Person by ID", operationId = "editPerson",
            parameters = @Parameter(name = "id", in = ParameterIn.PATH, required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person for ID was updated",
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PersonResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data",
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationErrorResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found Person for ID",
                    content = { @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PatchMapping("/{id}")
    ResponseEntity<PersonResponse> changePerson(@PathVariable Long id, @RequestBody PersonRequest personChanges);

    @Operation(summary = "Remove Person by ID", operationId = "editPerson_1")
    @ApiResponse(responseCode = "204", description = "Person for ID was removed")
    @DeleteMapping("/{id}")
    ResponseEntity<String> deletePerson(@PathVariable Long id);
}
