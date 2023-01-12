package ru.meklaw.autodrome.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Manager(Person person) {
        this.person = person;
    }

    public String getUsername() {
        return person.getUsername();
    }

    public void setUsername(String username) {
        person.setUsername(username);
    }

    public String getPassword() {
        return person.getPassword();
    }

    public void setPassword(String password) {
        person.setPassword(password);
    }

    public String getRole() {
        return person.getRole();
    }
}
