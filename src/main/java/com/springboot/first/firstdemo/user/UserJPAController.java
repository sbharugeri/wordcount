package com.springboot.first.firstdemo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJPAController {

	@Autowired
	private UserDaoService userDaoService;

	@Autowired
	private  UserRepository userRepository;

	// retrieve all users
	// GET /users
	@GetMapping("/jpa/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	// retrieve user
	// URI /user/{id}

	@GetMapping("/jpa/user/{id}")
	public EntityModel<Optional<User>> getUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFounfException(String.format("Id %s not found", id));
		}

		EntityModel<Optional<User>> model = EntityModel.of(user);
		
		WebMvcLinkBuilder linkToAllUsers = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getAllUsers());
		model.add(linkToAllUsers.withRel("all-users"));
		return model;
	}

	// input user details
	// output - CREATED and return URI
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User createdUser = userDaoService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/jpa/user/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}

}
