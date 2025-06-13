package com.smartmanage.api.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "client")
public class Client extends User {

    @Column(name = "associate_member", nullable = false)
    private Boolean associateMember;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Address> addresses;
}
