package com.es.service;

import com.es.request.EmployeeRequest;
import com.es.response.EmployeeResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse saveEmployee(EmployeeRequest request);

    EmployeeResponse fetchEmployee(Long id);

    EmployeeResponse updateEmployeeById(Long id, EmployeeRequest employeeRequest);

    String deleteEmployee(Long id);

    Page<EmployeeResponse> getEmployees(
            int page,
            int size,
            String sortBy,
            String sortDirection);

    Page<EmployeeResponse> searchByDepartment(String department, int page, int size);

    EmployeeResponse fetchEmployeeByEmail(String email);
}
