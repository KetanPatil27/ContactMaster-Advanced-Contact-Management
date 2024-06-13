package com.smart.Dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Contact;
import com.smart.entities.User;

public  interface ContactRepository extends JpaRepository <Contact ,Integer> {
    //pagination...
    //pageable
    //currentpage-page
    //contact per page -5 
    @Query("from Contact as c where c.user.id=:userId")
    public Page<Contact> findContactsByUser(@Param("userId")int userId,Pageable Pageable);

    // public List<Contact> findContactsByUser(int id);


// for search bar
public List<Contact> findByNameContainingAndUser(String name,User user);

}
