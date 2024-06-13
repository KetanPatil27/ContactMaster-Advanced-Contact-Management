package com.smart.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.smart.Dao.ContactRepository;
import com.smart.Dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class SearchController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    //search handler
    @GetMapping("/search/{query}")    
    public ResponseEntity<?> search(@PathVariable("query") String query,Principal principal)
    {
        System.out.println(query);
        User user =this.userRepository.findByEmail(principal.getName());
        List<Contact> contacts=this.contactRepository.findByNameContainingAndUser(query, user);
        return ResponseEntity.ok(contacts);
        
    }
}
