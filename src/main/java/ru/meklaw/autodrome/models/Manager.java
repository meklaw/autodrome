package ru.meklaw.autodrome.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "manager")
@Getter
@Setter
@NoArgsConstructor
public class Manager {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToMany()
    @JoinTable(
            name = "enterprise_manager",
            joinColumns = @JoinColumn(name = "manager_id"),
            inverseJoinColumns = @JoinColumn(name = "enterprise_id")
    )
    private List<Enterprise> enterprises;

    public String getUsername() {
        return person.getUsername();
    }

    public String getPassword() {
        return person.getPassword();
    }

    public String getRole() {
        return person.getRole();
    }

    public void setUsername(String username) {
        person.setUsername(username);
    }

    public void setPassword(String password) {
        person.setPassword(password);
    }
}
