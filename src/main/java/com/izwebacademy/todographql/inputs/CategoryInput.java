package com.izwebacademy.todographql.inputs;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CategoryInput {

    @NotEmpty(message = "Name is required")
    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
