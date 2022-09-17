package com.sagar.joltdemo.service.old;

import com.sagar.joltdemo.entity.Contact;
import com.sagar.joltdemo.entity.Employee;
import com.sagar.joltdemo.repository.EmployeeRepository;
import com.sagar.joltdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service("Old-BambooHrService")
public class BambooHrService implements EmployeeService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EmployeeRepository employeeRepository;

    //@PostConstruct
    public void init() {
        loadEmployess();
    }

    @Override
    public void loadEmployess() {
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange("https://bamboohr.mocklab.io/emp/1", HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        Map<String, Object> response = responseEntity.getBody();
        Employee employee = new Employee();
        employee.setId(response.get("userId").toString());
        employee.setFirstName(response.get("firstName").toString());
        employee.setLastName(response.get("lastName").toString());
        employee.setFullName(employee.getFirstName()+" "+employee.getLastName());
        employee.setSalary(Integer.parseInt(response.get("salary").toString()));
        employee.setDoj(response.get("joiningDate").toString());
        employee.setSource(response.get("source").toString());
        Contact contact = new Contact();
        contact.setEmailAddress(response.get("emailAddress").toString());
        contact.setMobileNumber(response.get("phoneNumber").toString());
        employee.setContact(contact);
        employeeRepository.save(employee);
    }

}
