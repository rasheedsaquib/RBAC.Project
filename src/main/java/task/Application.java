package task;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import main.Role;
import main.User;
import service.UserService;

@SpringBootApplication
@EntityScan(basePackages = {"main"})
@ComponentScan(basePackages = {"service", "controller" , "repository" , "main"})
@EnableJpaRepositories(basePackages = {"repository"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(UserService userService) {
		return args ->{
			userService.saveRole(new Role(null,  "ROLE_USER"));
			userService.saveRole(new Role(null,  "ROLE_ADMIN"));
			userService.saveRole(new Role(null,  "ROLE_MANAGER"));
			userService.saveRole(new Role(null,  "ROLE_MODERATOR"));


			userService.saveUser(new User(null, "Abdul Rasheed" , "rasheed" , "12345" , new ArrayList<>()));
			userService.saveUser(new User(null, "Sandeep R" , "sandeep" , "12345" , new ArrayList<>()));
			userService.saveUser(new User(null, "Mohd Sohel" , "sohel" , "12345" , new ArrayList<>()));
			userService.saveUser(new User(null, "K Karthik" , "karthik" , "12345" , new ArrayList<>()));

			userService.addRoleToUser("rasheed", "ROLE_USER" );
			userService.addRoleToUser("rasheed", "ROLE_ADMIN" );
			userService.addRoleToUser("sandeep", "ROLE_ADMIN" );
			userService.addRoleToUser("sohel", "ROLE_MANAGER" );
			userService.addRoleToUser("karthik",  "ROLE_MODERATOR" );


		};
	}
}
/*
 * @Bean
	CommandLineRunner run(UserService userService) {
		return args ->{
			userService.saveRole(new Role(null,  "Role_User"));
			userService.saveRole(new Role(null,  "Role_Admin"));
			userService.saveRole(new Role(null,  "Role_Manager"));
			userService.saveRole(new Role(null,  "Role_Moderator"));


			userService.saveUser(new User(null, "Abdul Rasheed" , "rasheed" , "12345" , new ArrayList<>()));
			userService.saveUser(new User(null, "Sandeep R" , "sandeep" , "23456" , new ArrayList<>()));
			userService.saveUser(new User(null, "Mohd Sohel" , "sohel" , "34567" , new ArrayList<>()));
			userService.saveUser(new User(null, "K Karthik" , "karthik" , "45678" , new ArrayList<>()));

			userService.addRoleToUser("rasheed", "Role_User" );
			userService.addRoleToUser("rasheed", "Role_Admin" );
			userService.addRoleToUser("sandeep", "Role_Admin" );
			userService.addRoleToUser("sohel", "Role_Manager" );
			userService.addRoleToUser("karthik",  "Role_Moderator" );


		};
	}
*/
