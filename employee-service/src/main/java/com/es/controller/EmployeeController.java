package com.es.controller;

import com.es.request.EmployeeRequest;
import com.es.response.EmployeeResponse;
import com.es.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD and Additional Required API's For Employee-Service",
        description = "Operation relates to employee-service like POST GET PUT DELETE ex.Register employee, Fetch Employee, Delete Employee details, Update Employee details"
)
@RestController
@RequestMapping("/api/v1/employees")
@Validated
public class EmployeeController {

    private final EmployeeService eService;

    public EmployeeController(EmployeeService eService) {
        this.eService = eService;
    }

    @Operation(
            summary = "Save Employee With CRM",
            description = "POST API for save employee details with employee service"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Employee Saved Successfully",
            content = @Content(schema = @Schema(implementation = EmployeeResponse.class))
    )
    @PostMapping
    public ResponseEntity<EmployeeResponse> saveEmployee(@Valid @RequestBody EmployeeRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eService.saveEmployee(request));
    }

    @Operation(
            summary = "Fetch employee details",
            description = "GET API for fetch employee from employee service"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee Fetched Successfully",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Fetch operation failed please try again or contact to dev team for more details",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> fetchEmployee(
            @PathVariable
            @Positive(message = "Id must be positive")
            Long id) {
        return ResponseEntity.ok(eService.fetchEmployee(id));
    }


    @Operation(
            summary = "Update Employee Details",
            description = "PUT API for Update employee details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee Updated Successfully",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Something went wrong, Please try again later or contact to Dev Team"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployeeById(
            @PathVariable
            @Positive(message = "Id must be positive")
            Long id,
            @Valid
            @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(eService.updateEmployeeById(id, request));
    }

    @Operation(
            summary = "Update Employee Details",
            description = "PUT API for Update employee details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee Updated Successfully",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Something went wrong, Please try again later or contact to Dev Team"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable
            @Positive(message = "Id must be positive")
            Long id) {
        return ResponseEntity.ok(eService.deleteEmployee(id));
    }


    @Operation(
            summary = "Fetch all employees details with pagination",
            description = "GET API for fetch all employees from employee service"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employees Fetched Successfully",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Fetch operation failed please try again or contact to dev team for more details",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return ResponseEntity.ok(
                eService.getEmployees(
                        page,
                        size,
                        sortBy,
                        sortDirection));
    }

    @Operation(
            summary = "Search employee details by department with pagination",
            description = "GET API for fetch employee from employee service"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee Fetched Successfully",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Fetch operation failed please try again or contact to dev team for more details",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))
            )
    })
    @GetMapping("/search")
    public ResponseEntity<Page<EmployeeResponse>> searchByDepartment(
            @RequestParam String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                eService.searchByDepartment(
                        department,
                        page,
                        size));
    }

    @Operation(
            summary = "Fetch employee details by email",
            description = "GET API for fetch employee by email from employee service"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee Fetched Successfully",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Fetch operation failed please try again or contact to dev team for more details",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))
            )
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeResponse> fetchEmployeeByEmail(
            @PathVariable
            @Email(message = "must be a well-formed email address")
            String email) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(eService.fetchEmployeeByEmail(email));
    }

}
