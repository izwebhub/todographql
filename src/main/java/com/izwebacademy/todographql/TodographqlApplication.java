package com.izwebacademy.todographql;

import com.izwebacademy.todographql.repositories.PermissionRepository;
import com.izwebacademy.todographql.utils.PermissionFactoryScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TodographqlApplication implements CommandLineRunner {

	private Boolean debug = true;

	@Autowired
	private PermissionRepository permissionRepository;

	private String packageToScan = "com.izwebacademy.todographql";

	public static void main(String[] args) {
		SpringApplication.run(TodographqlApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		PermissionFactoryScanner.builder(debug, permissionRepository).findAPermissions(packageToScan).seed();
	}
}
