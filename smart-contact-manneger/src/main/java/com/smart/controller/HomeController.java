package com.smart.controller;

import com.smart.Dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	// @Autowired
	// private UserRepository userRepository;

	// @GetMapping("/test")
	// @ResponseBody
	// public String test() {

	// User user= new User();
	// user.setName("kp");
	// user.setEmail("kp@gmail.com");
	// userRepository.save(user);

	// return "working";
	// }

	@Autowired

	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	//handler for all pages...
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home-smart-contact-maneger");
		return "home";
	}

	//handler for about page...
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About-smart-contact-maneger");
		return "about";
	}

	//handler for signup page...
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register-smart-contact-maneger");
		model.addAttribute("user", new User());
		return "signup";
	}

	// handler for register user...
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1,


			@RequestParam(value = "aggriment", defaultValue = "false") boolean aggriment, Model model,
			HttpSession session) {

		try {

			if (!aggriment) {
				System.out.println("you have not agreed terms and conditions");
				throw new Exception("you have not agreed terms and conditions");
			}

			if (result1.hasErrors()) {
				System.out.println("ERROR" + result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}

			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageurl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("Aggriment" + aggriment);
			System.out.println("USER" + user);

			User result = this.userRepository.save(user);

			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("successfully register...!!", "alert-success"));
			return "signup";

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("user", user);

			session.setAttribute("message",
					new Message("something went wrong...!!" + " " + e.getMessage(), "alert-danger"));
			return "signup";
		}
	}

	//handler for custom login page...
	@GetMapping("/signin")
	public String customlogin(Model model)
{
	model.addAttribute("title", "Login Page");
	return "login";
}

}

