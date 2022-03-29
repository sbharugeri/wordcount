package com.springboot.first.firstdemo.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

	@Autowired
	private UserDaoService userDaoService;

	// retrieve all users
	// GET /users
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userDaoService.listAll();
	}

	// retrieve user
	// URI /user/{id}

	@GetMapping("/user/{id}")
	public EntityModel<User> getUser(@PathVariable int id) {
		User user = userDaoService.findOne(id);
		if (user == null) {
			throw new UserNotFounfException(String.format("Id %s not found", id));
		}

		EntityModel<User> model = EntityModel.of(user);
		
		WebMvcLinkBuilder linkToAllUsers = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getAllUsers());
		model.add(linkToAllUsers.withRel("all-users"));
		return model;
	}

	// input user details
	// output - CREATED and return URI
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User createdUser = userDaoService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = userDaoService.deleteOne(id);
		if (user == null) {
			throw new UserNotFounfException(String.format("Id %s not found", id));
		}
	}

}
