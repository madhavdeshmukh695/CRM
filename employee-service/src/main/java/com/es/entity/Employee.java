package com.es.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(
        indexes = {
                @Index(name = "idx_employee_department", columnList = "department"),
                @Index(name = "idx_employee_email", columnList = "email")
        }
)
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String department;
    private Double salary;
}
