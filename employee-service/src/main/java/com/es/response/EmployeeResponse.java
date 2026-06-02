    package com.es.response;

    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import lombok.Data;

    import java.io.Serializable;

    @Data
    public class EmployeeResponse implements Serializable {
        @NotBlank
        private String firstName;
        @NotBlank
        private String middleName;
        @NotBlank
        private String lastName;
        @Email
        private String email;
        @NotBlank
        private String department;
        @NotNull
        private Double salary;
    }
