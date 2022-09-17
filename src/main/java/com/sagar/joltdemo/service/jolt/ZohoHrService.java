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
import java.util.List;
import java.util.Map;

@Service("Jolt-ZohoHrService")
public class ZohoHrService implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RestTemplate restTemplate;
    @PostConstruct
    public void init() {
        loadEmployess();
    }

    @Override
    public void loadEmployess() {
        ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange("https://zohohr.mocklab.io/get/emp/1", HttpMethod.GET, null, new ParameterizedTypeReference<List<Map<String, Object>>>() {
        });
        List<Map<String, Object>> response = responseEntity.getBody();
        Employee employee = JoltUtil.getEntity("/zohoSpec.json", Employee.class, response);
        employeeRepository.save(employee);
    }
}
