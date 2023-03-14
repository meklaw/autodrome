package ru.meklaw.autocontroller.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "manager")
@DiscriminatorValue("Manager")
@Getter
@Setter
@NoArgsConstructor
public class Manager extends Person {
    @ManyToMany()
    @JoinTable(
            name = "enterprise_manager",
            joinColumns = @JoinColumn(name = "manager_id"),
            inverseJoinColumns = @JoinColumn(name = "enterprise_id")
    )
    private List<Enterprise> enterprises;
}
