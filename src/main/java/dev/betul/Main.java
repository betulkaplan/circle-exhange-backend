package dev.betul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args){
        System.out.println("Hello world!");
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    record NewCustomerRequest(String name, String email, Integer age){

    }
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }
    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") UUID id){
        customerRepository.deleteById(id);
    }

    record UpdateCustomerRequest(String name, String email, Integer age){

    }

    @PutMapping("{customerId}")
    public Customer updateCustomer(@PathVariable("customerId") UUID id, @RequestBody UpdateCustomerRequest request){
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Customer not found"));
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        return customerRepository.save(customer);
    }

    @GetMapping("{customerId}")
    public Customer getCustomer(@PathVariable("customerId") UUID id){
        return customerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Customer not found"));
    }
}

