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
import java.util.List;
import java.util.Map;

@Service("OLD-ZohoHrService")
public class ZohoHrService implements EmployeeService {
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
        ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange("https://zohohr.mocklab.io/get/emp/1", HttpMethod.GET, null, new ParameterizedTypeReference<List<Map<String, Object>>>() {
        });
        List<Map<String, Object>> response = responseEntity.getBody();
        Map<String, Object> map = (Map<String, Object>) response.get(0);
        Employee employee = new Employee();
        employee.setId(map.get("id").toString());
        employee.setFirstName(map.get("name").toString());
        employee.setLastName(null);
        employee.setFullName(employee.getFirstName());
        employee.setSalary(Integer.parseInt(map.get("amount").toString()));
        employee.setDoj(map.get("doj").toString());
        employee.setSource(map.get("source").toString());
        Contact contact = new Contact();
        contact.setEmailAddress(map.get("email").toString());
        contact.setMobileNumber(map.get("mobile").toString());
        employee.setContact(contact);
        employeeRepository.save(employee);
    }
}
