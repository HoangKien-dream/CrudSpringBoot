package com.example.crudangular.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<Account> accounts;
    public Role(String name) {
        this.name = name;
    }

}
