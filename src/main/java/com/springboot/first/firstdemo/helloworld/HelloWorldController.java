package com.springboot.first.firstdemo.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//Tell spring that it is a controller
@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	//type of method get or post
	
	// URI - how user must call the method
	
	//Method - "Hello world"
	@GetMapping(path="/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	//return a bean instead of hellowrold string
	//use hello-world-bean
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	//get a path variable from url and use @PathVaribale for accessing 
		@GetMapping(path="/hello-world/path-variable/{name}")
		public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
			return new HelloWorldBean(String.format("Hello World, %s", name));
		}
		
		@GetMapping(path="/hello-world-int")
		public String helloWorldPathVariable(
				//@RequestHeader(name="Accept-Language", required=false) Locale locale
				) {
			
			return messageSource.getMessage("good.morning.message", null, "Default message", 
					//locale
					LocaleContextHolder.getLocale()
					);
			
		}
	
	//Running application into  debug mode so one need to edit application.properties file and add logging.level.org.springframework = debug

}
