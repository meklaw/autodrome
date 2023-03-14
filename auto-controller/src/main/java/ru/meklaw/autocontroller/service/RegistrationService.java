package ru.meklaw.autocontroller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meklaw.autocontroller.models.Manager;
import ru.meklaw.autocontroller.models.Person;
import ru.meklaw.autocontroller.repositories.ManagerRepository;
import ru.meklaw.autocontroller.repositories.PersonRepository;
import ru.meklaw.autocontroller.util.PersonNotCreatedEx;

@Service
public class RegistrationService {
    private final PersonRepository personRepository;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PersonRepository personRepository, ManagerRepository managerRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        if (personRepository.findByUsername(person.getUsername())
                            .isPresent())
            throw new PersonNotCreatedEx("This username is taken");

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);

        if (person.getAuthorities()
                  .stream()
                  .toList()
                  .get(0)
                  .getAuthority()
                  .equals("ROLE_MANAGER")) {
            managerRepository.save((Manager) person);
        }
    }
}
