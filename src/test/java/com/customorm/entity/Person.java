package com.customorm.entity;


import com.hoverla.bibernate.annotation.Column;
import com.hoverla.bibernate.annotation.Table;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Table(value = "person")
public class Person {

    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}
