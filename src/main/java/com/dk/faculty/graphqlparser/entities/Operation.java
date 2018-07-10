package com.dk.faculty.graphqlparser.entities;

import java.util.List;

public class Operation {
    private String name;
    private String description;
    private List<Argument> arguments;
    private String type;
    private Boolean isDeprecated;
    private String deprecationReason;

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

    public List<Argument> getArguments() {
        return arguments;
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getDeprecated() {
        return isDeprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        isDeprecated = deprecated;
    }

    public String getDeprecationReason() {
        return deprecationReason;
    }

    public void setDeprecationReason(String deprecationReason) {
        this.deprecationReason = deprecationReason;
    }
}
