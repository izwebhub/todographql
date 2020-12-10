package com.izwebacademy.todographql.utils;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class BaseEntity {

	@Column(name = "active", nullable = false)
	private boolean active = true;

	@CreationTimestamp
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime updatedAt;

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
