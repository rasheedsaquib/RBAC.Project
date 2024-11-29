package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import main.User;


public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);

}
