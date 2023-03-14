package ru.meklaw.autocontroller.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.meklaw.autocontroller.models.Manager;


@Service
public class ManagerService {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ManagerService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Manager getManager() {
        return (Manager) SecurityContextHolder.getContext()
                                              .getAuthentication()
                                              .getPrincipal();
    }

    public void encryptPassword(@NotNull Manager manager) {
        manager.setPassword(passwordEncoder.encode(manager.getPassword()));
    }
}
