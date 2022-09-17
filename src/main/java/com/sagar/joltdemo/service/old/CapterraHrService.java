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

@Service("OLD-CapterraHrService")
public class CapterraHrService implements EmployeeService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EmployeeRepository employeeRepository;

   // @PostConstruct
    public void init() {
        loadEmployess();
    }

    @Override
    public void loadEmployess() {
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange("https://capterra.mocklab.io/employee/1", HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {
        });
        Map<String, Object> response = responseEntity.getBody();
        Map<String,Object>map=(Map<String,Object>)response.get("employee");

        Employee employee = new Employee();
        employee.setId(map.get("id").toString());
        employee.setFirstName(map.get("fname").toString());
        employee.setLastName(map.get("lName").toString());
        employee.setFullName(employee.getFirstName()+" "+employee.getLastName());
        employee.setSalary(Integer.parseInt(map.get("salary").toString()));
        employee.setDoj(map.get("dateOfJoining").toString());
        employee.setSource(map.get("source").toString());
        Contact contact = new Contact();

        Map<String,Object>contactMap=( Map<String,Object>)map.get("contact");
        contact.setEmailAddress(contactMap.get("emailAddress").toString());
        contact.setMobileNumber(contactMap.get("phoneNumber").toString());
        employee.setContact(contact);
        employeeRepository.save(employee);
    }
}
