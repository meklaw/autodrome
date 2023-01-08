package ru.meklaw.autodrome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.meklaw.autodrome.models.Enterprise;
import ru.meklaw.autodrome.models.Person;
import ru.meklaw.autodrome.repositories.EnterprisesRepository;

import java.util.Collections;
import java.util.List;

@Service
public class EnterprisesService {
    private final EnterprisesRepository enterprisesRepository;

    @Autowired
    public EnterprisesService(EnterprisesRepository enterprisesRepository) {
        this.enterprisesRepository = enterprisesRepository;
    }

    public List<Enterprise> findAll() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = (Person) authentication.getPrincipal();

        return enterprisesRepository.findAllByManagersIn(Collections.singleton(person.getManager()));
    }
}
