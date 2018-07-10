package com.dk.faculty.graphqlparser.entities;

import java.util.List;

public class CustomType {
    private String name;
    private String description;
    private String kind;
    List<Operation> fields;
    List<EnumValue> enumValues;
    List<String> interfaces;
    List<String> possibleTypes;
    List<Argument> inputFields;

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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<Operation> getFields() {
        return fields;
    }

    public void setFields(List<Operation> fields) {
        this.fields = fields;
    }

    public List<EnumValue> getEnumValues() {
        return enumValues;
    }

    public void setEnumValues(List<EnumValue> enumValues) {
        this.enumValues = enumValues;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<String> interfaces) {
        this.interfaces = interfaces;
    }

    public List<String> getPossibleTypes() {
        return possibleTypes;
    }

    public void setPossibleTypes(List<String> possibleTypes) {
        this.possibleTypes = possibleTypes;
    }

    public List<Argument> getInputFields() {
        return inputFields;
    }

    public void setInputFields(List<Argument> inputFields) {
        this.inputFields = inputFields;
    }
}
