package ru.meklaw.autocontroller.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.meklaw.autocontroller.models.Person;
import ru.meklaw.autocontroller.service.PersonDetailsService;

import java.time.Period;

@Component
public class PersonValidator implements Validator {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }


    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return Period.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        Person person = (Person) target;

        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException e) {
            return;
        }

        errors.rejectValue("username", "", "Человек с таким именем не существует");
    }
}