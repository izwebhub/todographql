package com.izwebacademy.todographql.resolvers.subscriptions;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.izwebacademy.todographql.annotations.PermissionFactory;
import com.izwebacademy.todographql.annotations.PermissionMetaData;
import com.izwebacademy.todographql.models.Category;
import com.izwebacademy.todographql.repositories.CategoryRepository;

import graphql.kickstart.tools.GraphQLSubscriptionResolver;

@Component
@PermissionFactory
public class SubscriptionResolver implements GraphQLSubscriptionResolver {

	@Autowired
	private CategoryRepository categoryRepository;

	@PermissionMetaData(permissionName = "SUBSCRIBE_CATEGORIES", description = "Realtime Categories")
	public Publisher<List<Category>> categories() {

		return (Subscriber<? super List<Category>> s) -> Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
			List<Category> categories = categoryRepository.findAllByActiveTrue();
			s.onNext(categories);
		}, 0, 1, TimeUnit.SECONDS);

	}
}