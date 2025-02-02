package com.example.demo.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AddUser;
import com.example.demo.model.License;
import com.example.demo.model.UserListWithStatus;
import com.example.demo.repository.AddUserRepository;
import com.example.demo.repository.licenseRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v2/")
public class AddUserController {

	@Autowired
	private AddUserRepository adduserrepository;
	
    @Autowired
    private licenseRepository licenseRepository;
	
	@PostMapping("/AddUser")
	public String AddUser(@RequestBody AddUser data) {
		adduserrepository.save(data);
		return "success";
	}
	
//	@GetMapping("/GetAllUser")
//	public ResponseEntity<UserListWithStatus> getAllUser() {
//	    List<AddUser> getUser = adduserrepository.findAll();
//	    String name=getUser.getUsername();
//	    Iterable<License> licenseIterable = licenseRepository.findAll();
//    	List<License> licenseList = StreamSupport.stream(licenseIterable.spliterator(), false)
//    	                                         .collect(Collectors.toList());
//	    boolean isEmpty = licenseList.isEmpty();
//	    boolean valid =this.getall();
//	   
//	    
//	    UserListWithStatus userListWithStatus = new UserListWithStatus(getUser, isEmpty, valid);
//	   
//	    return new ResponseEntity<>(userListWithStatus,HttpStatus.OK);
//	}
//	
//	 public boolean getall(){
//	    	
//	    	Iterable<License> licenseIterable = licenseRepository.findAll();
//	    	List<License> licenseList = StreamSupport.stream(licenseIterable.spliterator(), false)
//	    	                                         .collect(Collectors.toList());
//	    	LocalDate currentDate = LocalDate.now();
//            java.util.Date Datecurrent = java.sql.Date.valueOf(currentDate);
//            long milliseconds = Datecurrent.getTime(); // Get the time in milliseconds
//            java.sql.Timestamp timestamp = new java.sql.Timestamp(milliseconds);
//	    	for (License license : licenseList) {
//	    		 System.out.println("---------------------------------------------------");
//	    	    System.out.println("ID: " + license.getId());
//	    	    System.out.println("Company Name: " + license.getCompany_name());
//	    	    System.out.println("Product Name: " + license.getProduct_name());
//	    	    System.out.println("key " + license.getKey());
//	    	    System.out.println("start_date: " + license.getStart_date());
//	    	    System.out.println("end_date: " + license.getEnd_date());
////	            java.util.Date licenseStartDateUtil = java.sql.Date.valueOf(license.getStart_date().toLocaleString());
//	    	    System.out.println(" start date :"+license.getStart_date()+" present date :"+Datecurrent+" is equal"+license.getEnd_date().equals(timestamp));
//	    	    // Print other fields as needed
//	    	}
//	    	 boolean valid = false; // Initialize valid to false
//	    	
//	    	 System.out.println("out of the loop"+valid);
//	    	    for (License license : licenseList) {
//	    	        // Check the validity condition
//	    	        if (license.getEnd_date().equals(timestamp)) {
////	    -------------------------------------testarea-----------------------------
////	    	        	  if (license.getStart_date().equals(timestamp)) {  
//	    	        	
//	    	            valid =  false; // Set valid to true if at least one license is valid
//	    	            System.out.println("inside of the loop"+valid);
//	    	            break; // No need to continue checking, we already found a valid license
//	    	        }
//	    	        else
//	    	        {
//	    	        	valid=true;
//	    	        	 System.out.println("inside of the loop"+valid);
//	    	        }
//	    	    }
//
////	    	  List<Addaudio1> allAudio = audiorepository.findAll();
//	    	
//	    	return valid;
//	    }
	
//	@GetMapping("/GetAllUser")
//	public ResponseEntity<UserListWithStatus> getAllUser() {
//	    // Retrieve all users
//	    List<AddUser> getUsers = adduserrepository.findAll();
//	    
//	    // Extract usernames from the list of users
//	    List<String> usernames = getUsers.stream()
//	                                    .map(AddUser::getUsername)
//	                                    .collect(Collectors.toList());
//	    
//	    // Retrieve all licenses
//	    Iterable<License> licenseIterable = licenseRepository.findAll();
//	    List<License> licenseList = StreamSupport.stream(licenseIterable.spliterator(), false)
//	                                             .collect(Collectors.toList());
//	    
//	    // Check if the license list is empty
//	    boolean isEmpty = licenseList.isEmpty();
//	    
//	    // Check license validity
//	    boolean valid = this.getall();
//	    
//	    // Create UserListWithStatus object
////	    UserListWithStatus userListWithStatus = new UserListWithStatus(usernames, isEmpty, valid);
//	    UserListWithStatus userListWithStatus = new UserListWithStatus(getUsers, isEmpty, valid);
//	    
//	    // Return response entity
//	    return new ResponseEntity<>(userListWithStatus, HttpStatus.OK);
//	}

	public boolean getall() {
	    // Retrieve all licenses
	    Iterable<License> licenseIterable = licenseRepository.findAll();
	    List<License> licenseList = StreamSupport.stream(licenseIterable.spliterator(), false)
	                                             .collect(Collectors.toList());
	    
	    // Get current date
	    LocalDate currentDate = LocalDate.now();
	    java.util.Date Datecurrent = java.sql.Date.valueOf(currentDate);
	    long milliseconds = Datecurrent.getTime(); // Get the time in milliseconds
	    java.sql.Timestamp timestamp = new java.sql.Timestamp(milliseconds);
	    
	    // Iterate over licenses
	    for (License license : licenseList) {
	        System.out.println("---------------------------------------------------");
	        System.out.println("ID: " + license.getId());
	        System.out.println("Company Name: " + license.getCompany_name());
	        System.out.println("Product Name: " + license.getProduct_name());
	        System.out.println("Key: " + license.getKey());
	        System.out.println("Start Date: " + license.getStart_date());
	        System.out.println("End Date: " + license.getEnd_date());
	        System.out.println("Is End Date Equal to Current Date: " + license.getEnd_date().equals(timestamp));
	        // Print other fields as needed
	    }
	    
	    boolean valid = false; // Initialize valid to false
	    
	    System.out.println("out of the loop" + valid);
	    
	    // Check license validity
	    for (License license : licenseList) {
	        // Check the validity condition
	        if (license.getEnd_date().equals(timestamp)) {
	            valid = false; // Set valid to false if at least one license is valid
	            System.out.println("inside of the loop" + valid);
	            break; // No need to continue checking, we already found a valid license
	        } else {
	            valid = true;
	            System.out.println("inside of the loop" + valid);
	        }
	    }
	    
	    return valid;
	}

	 
	 
	 @GetMapping("/GetUserId/{userId}")
	 public ResponseEntity<UserListWithStatus> getUser(@PathVariable Long userId) {
	     // Assuming adduserrepository is your repository for AddUser entity
	     Optional<AddUser> userOptional = adduserrepository.findById(userId);
	     if (userOptional.isPresent()) {
	         AddUser user = userOptional.get();
	         // Retrieve the user's data
	         
	         // Now you can construct the response as needed
	         // For example:
	         return new ResponseEntity<>(new UserListWithStatus(Collections.singletonList(user), false, true), HttpStatus.OK);
	     } else {
	         // User not found with the given ID
	         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	     }
	 }


	
	
	
	@DeleteMapping("/DeleteUser/{UserId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long UserId) {
        try {
            // Assuming you have a method to delete a category by ID in your repository
        	adduserrepository.deleteById(UserId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@PatchMapping("/UpdateUser/{userId}")
	public ResponseEntity<String> updateUserDetails(@PathVariable Long userId, @RequestBody AddUser updatedUserData) {
	    try {
	        // Retrieve existing user data from the repository
	        AddUser existingUser = adduserrepository.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        // Apply partial updates to the existing user data
	        if (updatedUserData.getUsername() != null) {
	            existingUser.setUsername(updatedUserData.getUsername());
	        }

	        // Update additional fields
	        if (updatedUserData.getEmail() != null) {
	            existingUser.setEmail(updatedUserData.getEmail());
	        }

	        
	        if (updatedUserData.getMobnum() != null) {
	            existingUser.setMobnum(updatedUserData.getMobnum());
	        }

	        if (updatedUserData.getCompname() != null) {
	            existingUser.setCompname(updatedUserData.getCompname());
	        }

	        if (updatedUserData.getPincode() != null) {
	            existingUser.setPincode(updatedUserData.getPincode());
	        }

	        if (updatedUserData.getCountry() != null) {
	            existingUser.setCountry(updatedUserData.getCountry());
	        }

	        if (updatedUserData.getPassword() != null) {
	            existingUser.setPassword(updatedUserData.getPassword());
	        }

	        if (updatedUserData.getConfirmPassword() != null) {
	            existingUser.setConfirmPassword(updatedUserData.getConfirmPassword());
	        }

	        if (updatedUserData.getAddress() != null) {
	            existingUser.setAddress(updatedUserData.getAddress());
	        }

	        // Save the updated user data back to the repository
	        adduserrepository.save(existingUser);

	        return new ResponseEntity<>("User details updated successfully", HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Error updating user details", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


}
