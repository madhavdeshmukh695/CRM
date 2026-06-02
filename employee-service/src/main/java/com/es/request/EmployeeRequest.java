package com.es.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
    name = "Employee Request",
    description = "Schema to hold Employee information"
)
public class EmployeeRequest {

    @Schema(
            description = "First name of Employee",
            example = "Madhav"
    )
    @NotBlank(message = "First Name should not be empty")
    @Size(max = 50)
    private String firstName;

    @Schema(
            description = "Middle name of employee",
            example = "Shivaji"
    )
    @NotBlank(message = "Middle name should not be empty")
    @Size(max=50)
    private String middleName;

    @Schema(
            description = "Last name of employee",
            example = "Deshmukh"
    )
    @NotBlank(message = "Last name should not be empty")
    @Size(max = 50)
    private String lastName;

    @Schema(
            description = "Email of employee",
            example = "madhav.d@12gmail.com"
    )
    @Email
    private String email;

    @Schema(
            description = "Department of employee",
            example = "ADMIN"
    )
    @NotBlank(message = "Department should not be empty")
    private String department;


    @Schema(
            description = "Salary of employee",
            example = "120000.0"
    )
    @NotNull
    @Positive
    private Double salary;
}
