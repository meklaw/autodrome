package ru.meklaw.autocontroller.controllers.rest;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autocontroller.dto.PersonDTO;
import ru.meklaw.autocontroller.models.Person;
import ru.meklaw.autocontroller.security.JwtUtil;
import ru.meklaw.autocontroller.service.RegistrationService;
import ru.meklaw.autocontroller.util.PersonErrorResponse;
import ru.meklaw.autocontroller.util.PersonNotCreatedEx;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final ModelMapper modelMapper;

    private final RegistrationService registrationService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthRestController(ModelMapper modelMapper, RegistrationService registrationService, JwtUtil jwtUtil) {
        this.modelMapper = modelMapper;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> performRegistration(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(fieldError -> {
                errorMsg.append(fieldError);
                errorMsg.append(" - ");
                errorMsg.append(fieldError.getDefaultMessage());
                errorMsg.append(";");
            });

            throw new PersonNotCreatedEx(errorMsg.toString());
        }

        Person person = convertToPerson(personDTO);

        registrationService.register(person);
        String token = jwtUtil.generateToken(person.getUsername());

        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> exceptionHandler(PersonNotCreatedEx ex) {
        PersonErrorResponse response = new PersonErrorResponse(ex.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }


}
