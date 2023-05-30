package dev.betul.controller;



import dev.betul.repository.UserRepository;
import dev.betul.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    record NewUserRequest(String email){

    }

    @PostMapping
    public void addCustomer(@RequestBody NewUserRequest request){
        User customer = new User();
        customer.setEmail(request.email());
        userRepository.save(customer);
    }

    @GetMapping("{userId}")
    public User getCustomer(@PathVariable("userId") UUID id){
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable("userId") UUID id){
        userRepository.deleteById(id);
    }

    record UpdateUserRequest(String email){

    }

    @PutMapping("{userId}")
    public User updateCustomer(@PathVariable("userId") UUID id, @RequestBody UpdateUserRequest request){
        User customer = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Customer not found"));
        customer.setEmail(request.email());
        return userRepository.save(customer);
    }
}