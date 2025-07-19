package com.smartmanage.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
@EqualsAndHashCode(callSuper = true)
public class Client extends User {

    @Column(name = "associate_member", nullable = false)
    private Boolean associateMember;
}
