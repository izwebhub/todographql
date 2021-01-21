package com.izwebacademy.todographql.resolvers.subscriptions;

import java.util.List;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.izwebacademy.todographql.annotations.PermissionFactory;
import com.izwebacademy.todographql.annotations.PermissionMetaData;
import com.izwebacademy.todographql.contracts.subscriptions.CategorySubscriptionContract;
import com.izwebacademy.todographql.models.Category;

import graphql.kickstart.tools.GraphQLSubscriptionResolver;

@Component
@PermissionFactory
public class SubscriptionResolver implements GraphQLSubscriptionResolver {

	@Autowired
	private CategorySubscriptionContract subscriptionContract;

	@PermissionMetaData(permissionName = "SUBSCRIPTION_CATEGORY", description = "Subscription Category")
	public Publisher<List<Category>> categories() {
		return subscriptionContract.categories();
	}
}
