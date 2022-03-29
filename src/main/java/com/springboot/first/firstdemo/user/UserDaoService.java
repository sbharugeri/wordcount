package com.springboot.first.firstdemo.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;


import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	
	private static List<User> users = new ArrayList<>();
	
	private static int userId =3;
	
	static {
		users.add(new User(1, "chennappa", new Date()));
		users.add(new User(2, "rangappa", new Date()));
		users.add(new User(3, "ramappa", new Date()));
	}
	
	public List<User> listAll() {
		return users;
	}
	
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(++userId);
		}
		
		users.add(user);
		return user;
		
	}
	
	public User findOne(int  id) {
		for(User user:users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
		
	}
	
	public User deleteOne(int  id) {
		Iterator<User> iterator = users.iterator();
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId() == id) {
				iterator.remove();
				return user;
			}
		}
	
		return null;
		
	}
	

}
