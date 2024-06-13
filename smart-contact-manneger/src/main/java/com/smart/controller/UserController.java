package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.Dao.ContactRepository;
import com.smart.Dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ContactRepository contactRepository;

  // method for adding common data to responce
  @ModelAttribute
  public void addCommonData(Model model, Principal principal) {

    String userName = principal.getName();
    System.out.println("username:- " + userName);
    // get the user using username (email)

    User user = userRepository.findByEmail(userName);
    System.out.println("USER " + user);
    model.addAttribute("user", user);
  }

  // dashboard home
  @RequestMapping("/index")
  public String dashboard(Model model, Principal principal) {
    model.addAttribute("title", "User Dashboard");
    return "normal/user_dashboard";
  }

  // open add form controller
  @GetMapping("/add-contact")
  public String openAddContactForm(Model model) {
    model.addAttribute("title", "Add Contact");
    model.addAttribute("contact", new Contact());
    return "normal/add-contact-form";
  }

  // processing add contact
  @PostMapping("/process-contact")
  public String processContact(
      @ModelAttribute Contact contact,
      @RequestParam("profileimage") MultipartFile file,
      Principal principal, HttpSession session) {

    try {
      String name = principal.getName();
      User user = this.userRepository.findByEmail(name);

      // test eroor for submit contact
      // if (3>2) {
      // throw new Exception();
      // }

      // processing and uploding file..
      if (file.isEmpty()) {
        // if the file is empty then try our message
        System.out.println("file is empty please uplode image");
        contact.setImage("contact.png");
      } else {
        // uplode the file to the folder and upade to name to contact database
        contact.setImage(file.getOriginalFilename());
        File saveFile = new ClassPathResource("static/image").getFile();

        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("image is uploded");
      }

      contact.setUser(user);
      user.getContacts().add(contact);
      this.userRepository.save(user);
      System.out.println("Data " + contact);
      System.out.println("Added to the data base...");

      // message sucess....
      session.setAttribute("message", new Message("your Contact is added...! Add More...!", "success"));
    } catch (Exception e) {
      // TODO: handle exception
      System.out.println("ERROR " + e.getMessage());
      e.printStackTrace();
      // error message...
      session.setAttribute("message", new Message("Something Went Wrong...!Try Again...", "danger"));

    }
    return "normal/add-contact-form";
  }

  // show contacts handler
  // per page =5[n]
  // current page = 0[page]
  @GetMapping("/show-contacts/{page}")
  public String showContact(@PathVariable("page") Integer page, Model model, Principal principal) {
    model.addAttribute("title", "Show User Contacts");

    // here view contacts
    // old method
    // String userName =principal.getName();
    // User user = this.userRepository.findByEmail(userName);
    // List<Contact> contacts= user.getContacts();

    String userName = principal.getName();
    User user = this.userRepository.findByEmail(userName);

    // pageable
    // currentpage-page
    // contact per page -5
    Pageable pageable = PageRequest.of(page, 5);

    Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(), pageable);

    model.addAttribute("Contacts", contacts);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", contacts.getTotalPages());
    return "normal/show_contacts";
  }

  // showing perticular contact details
  @RequestMapping("/{cId}/contact")
  public String showContactDetails(@PathVariable("cId") Integer cId, Model model, Principal principal) {
    System.out.println("cId " + cId);
    Optional<Contact> contactoptional = this.contactRepository.findById(cId);
    Contact contact = contactoptional.get();

    // check
    String userName = principal.getName();
    User user = this.userRepository.findByEmail(userName);

    if (user.getId() == contact.getUser().getId()) {
      model.addAttribute("Contact", contact);
      model.addAttribute("title", contact.getName());
    }

    // model.addAttribute("Contact", contact);
    return "normal/contact_detail";
  }

  // Delete contact handler
  @GetMapping("/delete/{cid}")
  public String deleteContact(@PathVariable("cid") Integer cId, Model model, HttpSession session,Principal principal) {
    // Optional<Contact> contactoptional = this.contactRepository.findById(cId);
    // Contact contact = contactoptional.get();
    Contact contact = this.contactRepository.findById(cId).get();
    // check...

    //bug syntax for delete
    // contact.setUser(null);
    // this.contactRepository.delete(contact);

    //new syntax
    User user=this.userRepository.findByEmail(principal.getName());
    user.getContacts().remove(contact);
    this.userRepository.save(user);



    System.out.println("DELETED...");
    session.setAttribute("message", new Message("Contact Deleted Succesfully", "success"));
    return "redirect:/user/show-contacts/0";

  }

  // update form handeler
  @PostMapping("/update-contact/{cid}")
  public String updateForm(@PathVariable("cid") Integer cid, Model model) {
    model.addAttribute("title", "Update Contact");
    Contact contact = this.contactRepository.findById(cid).get();
    model.addAttribute("contact", contact);
    return "normal/update_form";
  }

  // upadte contact handler
  @RequestMapping(value = "/process-update", method = RequestMethod.POST)
  public String updateHandler(@ModelAttribute Contact contact, @RequestParam("profileimage") MultipartFile file,
      Model model, HttpSession session, Principal principal) {

    try {
      // old contact details
      Contact oldcontactDetails = this.contactRepository.findById(contact.getcId()).get();

      // image...
      if (!file.isEmpty()) {
        // file work
        // rewrite...



        // delete old photo
        File deleteFile = new ClassPathResource("static/image").getFile();
        File file2=new File(deleteFile,oldcontactDetails.getImage());
        file2.delete();



        // update new photo
        File saveFile = new ClassPathResource("static/image").getFile();

        Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        contact.setImage(file.getOriginalFilename());
      } else {
        contact.setImage(oldcontactDetails.getImage());
      }

      User user = this.userRepository.findByEmail(principal.getName());
      contact.setUser(user);
      this.contactRepository.save(contact);
      // message
      session.setAttribute("message",new Message("Your Contact is Updated...", "success"));

    } catch (Exception e) {

      e.printStackTrace();
    }

    System.out.println("Contact Name " + contact.getName());
    System.out.println("Contact Id " + contact.getcId());
    return "redirect:/user/" + contact.getcId() + "/contact";

  }

  //Your profile handler
  @GetMapping("/profile")
  public String yourProfile(Model model){
    model.addAttribute("title", "Profile Page");
    return "normal/profile";
  }
}
