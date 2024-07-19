package com.green.miracle.controller.bot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api")
public class ChaBotRestController {

	    @GetMapping("/contacts")
	    public List<Contact> getContacts() {
	        return List.of(
	            new Contact("Alice", "123-456-7890"),
	            new Contact("Bob", "987-654-3210")
	        );
	    }

	    static class Contact {
	        private String name;
	        private String phoneNumber;

	        public Contact(String name, String phoneNumber) {
	            this.name = name;
	            this.phoneNumber = phoneNumber;
	        }

	        public String getName() {
	            return name;
	        }

	        public String getPhoneNumber() {
	            return phoneNumber;
	        }
	    }
	}


