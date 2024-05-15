package com.intensive.hibernateApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personal_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalCard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personal_card_id_generator")
    @SequenceGenerator(name = "personal_card_id_generator", sequenceName = "sq_personal_card_id", allocationSize = 1)
    private Long id;

    private String male;
    private int age;
    private int salary;
    private String position;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "current_address")
    private String currentAddress;
}
