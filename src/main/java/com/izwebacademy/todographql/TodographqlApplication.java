package com.izwebacademy.todographql;

import javax.security.auth.Subject;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.izwebacademy.todographql.repositories.PermissionRepository;
import com.izwebacademy.todographql.repositories.UserRepository;
import com.izwebacademy.todographql.utils.PermissionFactoryScanner;

import graphql.kickstart.servlet.context.DefaultGraphQLWebSocketContext;
import graphql.kickstart.servlet.context.GraphQLWebSocketContext;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TodographqlApplication implements CommandLineRunner {

	private Boolean debug = true;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	private String packageToScan = "com.izwebacademy.todographql";

	public static void main(String[] args) {
		SpringApplication.run(TodographqlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String password = encoder.encode("12345");
		PermissionFactoryScanner.builder(debug, permissionRepository, userRepository).findAPermissions(packageToScan)
				.seedWithAdmin("admin", password);

	}
}
