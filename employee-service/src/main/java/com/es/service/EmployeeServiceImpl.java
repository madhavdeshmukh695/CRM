package com.es.service;

import com.es.entity.Employee;
import com.es.exception.EmployeeAlreadyExists;
import com.es.exception.EmployeeNotFoundException;
import com.es.mapper.EmployeeMapper;
import com.es.repository.EmployeeRepository;
import com.es.request.EmployeeRequest;
import com.es.response.EmployeeResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeMapper mapper;
    private final EmployeeRepository eRepo;

    public EmployeeServiceImpl(EmployeeRepository eRepo,EmployeeMapper mapper) {
        this.eRepo = eRepo;
        this.mapper = mapper;
    }

    public EmployeeResponse saveEmployee(EmployeeRequest request) {
        eRepo.findByEmail(request.getEmail()).ifPresent(employee -> {
            throw new EmployeeAlreadyExists("Employee Already Exists with this email...!");
        });
        Employee employee =  mapper.toEntity(request);
        Employee emp = eRepo.save(employee);
        log.info("Saving employee with email {}", request.getEmail());
        return mapper.toResponse(emp);
    }

    @Cacheable(
            value = "employees",
            key = "#id")
    public EmployeeResponse fetchEmployee(Long id) {
        System.out.println("DB HIT");

        Employee emp =  eRepo.findById(id).orElseThrow(
                ()->new EmployeeNotFoundException("Employee not found with id: "+ id));
        log.info("Fetching employee with id {}", id);

        return mapper.toResponse(emp);
    }

    @CachePut(
            value = "employees",
            key = "#id"
    )
    @Transactional
    public EmployeeResponse updateEmployeeById(Long id, EmployeeRequest employeeRequest) {
        Employee existEmployee = eRepo.findById(id).orElseThrow(()-> new EmployeeNotFoundException("Employee not Found with this id...!"));
        eRepo.findByEmail(employeeRequest.getEmail()).ifPresent(employee -> {
            throw new EmployeeAlreadyExists("Employee Already Exists with this email...!");
        });
        mapper.updateEmployeeFromRequest(employeeRequest,existEmployee);
        return mapper.toResponse(eRepo.saveAndFlush(existEmployee));
    }

    @CacheEvict(
            value = "employees",
            key = "#id"
    )
    @Transactional
    public String deleteEmployee(Long id) {
        Employee emp = eRepo.findById(id).orElseThrow(()-> new EmployeeNotFoundException("Employee Not Exists !"));
        eRepo.delete(emp);
        return "Employee Deleted Successfully...!";
    }

    public Page<EmployeeResponse> getEmployees(
            int page,
            int size,
            String sortBy,
            String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page,size,sort);

        return eRepo.findAll(pageable)
                .map(mapper :: toResponse);
    }

    public Page<EmployeeResponse> searchByDepartment(String department, int page, int size) {

        Pageable pageable = PageRequest.of(page,size);

        return eRepo.findByDepartment(department, pageable)
                .map(mapper :: toResponse);
    }

    public EmployeeResponse fetchEmployeeByEmail(String email) {
        Employee employee = eRepo.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found With This Email"));

        return mapper.toResponse(employee);
    }

}
