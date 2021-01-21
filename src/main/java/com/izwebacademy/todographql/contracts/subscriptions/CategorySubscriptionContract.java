package com.izwebacademy.todographql.contracts.subscriptions;

import java.util.List;

import org.reactivestreams.Publisher;

import com.izwebacademy.todographql.models.Category;

public interface CategorySubscriptionContract {

	Publisher<List<Category>> categories();
}
