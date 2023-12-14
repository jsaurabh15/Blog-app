package com.application.blog;

import com.application.blog.config.AppConstants;
import com.application.blog.dao.RoleDao;
import com.application.blog.models.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {
	@Autowired
	private RoleDao roleDao;

	public static void main(String[] args) {

		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role role1 = new Role();
			role1.setId(AppConstants.ROLE_ADMIN);
			role1.setName("ROLE_ADMIN");

			Role role2 = new Role();
			role2.setId(AppConstants.ROLE_USER);
			role2.setName("ROLE_USER");

			List<Role> roles = List.of(role1, role2);
			List<Role> results = this.roleDao.saveAll(roles);

			results.forEach(r -> System.out.println(r.getName()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
