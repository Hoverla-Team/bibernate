package com.hoverla.bibernate.testutil.entity;

import com.hoverla.bibernate.annotation.Column;
import com.hoverla.bibernate.annotation.Entity;
import com.hoverla.bibernate.annotation.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(table = "users")
@Setter
@Getter
@EqualsAndHashCode
public class User {

    @Id
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;
}
