package com.izwebacademy.todographql.utils;

import com.izwebacademy.todographql.annotations.PermissionFactory;
import com.izwebacademy.todographql.annotations.PermissionMetaData;
import com.izwebacademy.todographql.models.Permission;
import com.izwebacademy.todographql.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.transaction.Transactional;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
public class PermissionFactoryScanner {

    @Autowired
    private PermissionRepository permissionRepository;

    private String packageToScan = "";

    List<Permission> permissions = null;

    private Boolean debug;

    public PermissionFactoryScanner() {
    }

    // 1st Call
    public PermissionFactoryScanner(Boolean debug,
                                    PermissionRepository permissionRepo) {
        this.debug = debug;
        this.permissionRepository = permissionRepo;
    }

    // 2nd Call
    public PermissionFactoryScanner findAnnotatedClasses(String scanPackage) {

        this.permissions = new ArrayList<>();

        ClassPathScanningCandidateComponentProvider provider = createComponentScanner();
        for (BeanDefinition beanDef : provider.findCandidateComponents(scanPackage)) {
            printMetadata(beanDef);
        }

        return this;

    }

    // 3rd Call
    public void commit() {
        List<Permission> perms = this.permissions;
        for (Permission perm : perms) {
            String name = perm.getName();
            Optional<Permission> dbrole = this.permissionRepository.findByActiveTrueAndName(name);
            if (!dbrole.isPresent()) {
                if (debug) {
                    this.permissionRepository.save(perm);
                }
            }
        }
    }

    private ClassPathScanningCandidateComponentProvider createComponentScanner() {
        // Don't pull default filters (@Component, etc.):
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter((Class<? extends Annotation>) PermissionFactory.class));
        return provider;
    }

    private void printMetadata(BeanDefinition beanDef) {
        try {
            Class<?> cl = Class.forName(beanDef.getBeanClassName());

            for (Method method : cl.getMethods()) {
                if (method.isAnnotationPresent(PermissionMetaData.class)) {

                    String permname = method.getAnnotation(PermissionMetaData.class).roleName();
                    String description = method.getAnnotation(PermissionMetaData.class).description();

                    Permission availablePerm = new Permission();
                    availablePerm.setName(permname);
                    availablePerm.setDescription(description);

                    this.permissions.add(availablePerm);

                    if (this.permissions.contains(availablePerm)) {
                        if (debug) {

                            System.out.println("Permission Name: " + permname);
                            System.out.println("Description: " + description);
                            System.out.println("");

                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Got exception: " + e.getMessage());
        }
    }


}
