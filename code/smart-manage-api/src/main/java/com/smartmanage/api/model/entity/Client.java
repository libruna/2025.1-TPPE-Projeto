package com.smartmanage.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

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
