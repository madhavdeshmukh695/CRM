package com.es.mapper;

import com.es.entity.Employee;
import com.es.request.EmployeeRequest;
import com.es.response.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEntity(EmployeeRequest request);

    EmployeeResponse toResponse(Employee employee);

    void updateEmployeeFromRequest(EmployeeRequest request, @MappingTarget Employee employee);
}
