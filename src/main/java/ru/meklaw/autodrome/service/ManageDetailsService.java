package ru.meklaw.autodrome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.meklaw.autodrome.models.Manager;
import ru.meklaw.autodrome.repositories.ManagerRepository;
import ru.meklaw.autodrome.security.ManagerDetails;

import java.util.Optional;

@Service
public class ManageDetailsService implements UserDetailsService {
    private final ManagerRepository managerRepository;

    @Autowired
    public ManageDetailsService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }
    //упрощает код что-то там
    @Override
    public ManagerDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Manager> manager = managerRepository.findByPersonUsername(username);
        if (manager.isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return new ManagerDetails(manager.get());
    }
}
