package com.izwebacademy.todographql.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.izwebacademy.todographql.utils.BaseEntity;

@Entity
@Table(name = "graphql_users")
public class User extends BaseEntity {
    @Id
    @SequenceGenerator(name = "graphl_users_seq", sequenceName = "graphl_users_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "graphl_users_seq")
    private Long id;

    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username", unique = true)
    private String username;

    private String password;

    @ManyToMany
    @JoinTable(name = "graphql_user_permissions", joinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private List<Permission> permissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
