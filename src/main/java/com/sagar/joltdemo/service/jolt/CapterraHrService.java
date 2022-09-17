package com.sagar.joltdemo.service.jolt;

import com.sagar.joltdemo.entity.Employee;
import com.sagar.joltdemo.repository.EmployeeRepository;
import com.sagar.joltdemo.service.EmployeeService;
import com.sagar.joltdemo.util.JoltUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service("Jolt-CapterraHrService")
public class CapterraHrService implements EmployeeService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EmployeeRepository employeeRepository;

   @PostConstruct
    public void init() {
        loadEmployess();
    }

    @Override
    public void loadEmployess() {
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange("https://capterra.mocklab.io/employee/1", HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        Map<String, Object> response = responseEntity.getBody();
        Employee employee = JoltUtil.getEntity("/capterraSpec.json", Employee.class, response);
        employeeRepository.save(employee);
    }
}
