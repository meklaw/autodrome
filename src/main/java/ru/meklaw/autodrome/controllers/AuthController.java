package ru.meklaw.autodrome.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.meklaw.autodrome.dto.ManagerDTO;
import ru.meklaw.autodrome.models.Manager;
import ru.meklaw.autodrome.security.JwtUtil;
import ru.meklaw.autodrome.service.RegistrationService;
import ru.meklaw.autodrome.util.ManagerErrorResponse;
import ru.meklaw.autodrome.util.ManagerNotCreatedException;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final ModelMapper modelMapper;
    private final RegistrationService registrationService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(ModelMapper modelMapper, RegistrationService registrationService, JwtUtil jwtUtil) {
        this.modelMapper = modelMapper;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> performRegistration(@RequestBody @Valid ManagerDTO managerDTO,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(
                    fieldError -> errorMsg.append(fieldError)
                            .append(" - ")
                            .append(fieldError.getDefaultMessage())
                            .append(";")
            );

            throw new ManagerNotCreatedException(errorMsg.toString());
        }

        Manager manager = convertToManager(managerDTO);
//      TODO
//        registrationService.register(manager);

        String token = jwtUtil.generateToken(manager.getUsername());
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    private Manager convertToManager(ManagerDTO managerDTO) {
        return modelMapper.map(managerDTO, Manager.class);
    }

    @ExceptionHandler
    private ResponseEntity<ManagerErrorResponse> exceptionHandler(ManagerNotCreatedException e) {
        ManagerErrorResponse response = new ManagerErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
