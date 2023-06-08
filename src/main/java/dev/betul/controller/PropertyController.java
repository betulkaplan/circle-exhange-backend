package dev.betul.controller;

import dev.betul.model.Property;
import dev.betul.model.User;
import dev.betul.repository.PropertyRepository;
import dev.betul.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("api/property")
public class PropertyController {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Autowired
    public PropertyController(PropertyRepository propertyRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Property>> getProperties(){
        List<Property> properties = propertyRepository.findAll();
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    record NewPropertyRequest(String title, UUID owner){

    }

    @PostMapping
    public ResponseEntity<Property> createProperty(@RequestBody NewPropertyRequest request){
        User owner = userRepository.findById(request.owner())
                .orElseThrow(()->new NoSuchElementException("Owner User not found"));

        Property property = new Property(owner, request.title());
        Property createdProperty = propertyRepository.save(property);
        return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);
    }
}
