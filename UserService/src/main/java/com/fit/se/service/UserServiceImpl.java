package com.fit.se.service;

import com.fit.se.entity.Department;
import com.fit.se.entity.User;
import com.fit.se.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    private RestTemplate restTemplate;

    @Override
    public User saveUser(User user) {
        ResponseEntity<Department> responseEntity = restTemplate
                .getForEntity("http://localhost:8080/departments/" + user.getDepartment().getId(),
                        Department.class);
        Department department = responseEntity.getBody();
        user.setDepartment(department);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUserById(int id, User newUser) {
        User tempUser = userRepository.findById(id).get();
        tempUser.setFirstName(newUser.getFirstName());
        tempUser.setLastName(newUser.getLastName());
        tempUser.setEmail(newUser.getEmail());
        ResponseEntity<Department> responseEntity = restTemplate
                .getForEntity("http://localhost:8080/departments/" + newUser.getDepartment().getId(),
                        Department.class);
        Department department = responseEntity.getBody();
        tempUser.setDepartment(department);
        return userRepository.save(tempUser);
    }
}
