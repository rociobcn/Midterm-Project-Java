package com.Ironhack.MidTermProject;

import com.Ironhack.MidTermProject.Models.User.Admin;
import com.Ironhack.MidTermProject.Repositories.AdminRepository;
import com.Ironhack.MidTermProject.Repositories.RoleRepository;
import com.Ironhack.MidTermProject.Repositories.UserRepository;
import com.Ironhack.MidTermProject.Models.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MidTermProjectApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(MidTermProjectApplication.class, args);
	}
		@Autowired
		UserRepository userRepository;

		@Autowired
		AdminRepository adminRepository;

		@Autowired
		RoleRepository roleRepository;

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		@Override
		public void run(String... args) throws Exception {

			Admin admin1 = userRepository.save(new Admin("la_jefa", passwordEncoder.encode("1234"), "Rocio Ramos Lopez" ));
			admin1.setRole("ADMIN");
			adminRepository.save(admin1);
	}
}
