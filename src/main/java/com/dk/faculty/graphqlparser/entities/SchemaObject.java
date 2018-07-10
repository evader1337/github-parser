package com.dk.faculty.graphqlparser.entities;

import java.util.List;

public class SchemaObject {
    List<Operation> queryOperations;
    List<Operation> mutationOperations;
    List<Operation> subscriptionOperations;
    List<CustomType> customTypes;

    public SchemaObject() {
    }

    public List<Operation> getQueryOperations() {
        return queryOperations;
    }

    public void setQueryOperations(List<Operation> queryOperations) {
        this.queryOperations = queryOperations;
    }

    public List<Operation> getMutationOperations() {
        return mutationOperations;
    }

    public void setMutationOperations(List<Operation> mutationOperations) {
        this.mutationOperations = mutationOperations;
    }

    public List<Operation> getSubscriptionOperations() {
        return subscriptionOperations;
    }

    public void setSubscriptionOperations(List<Operation> subscriptionOperations) {
        this.subscriptionOperations = subscriptionOperations;
    }

    public List<CustomType> getCustomTypes() {
        return customTypes;
    }

    public void setCustomTypes(List<CustomType> customTypes) {
        this.customTypes = customTypes;
    }
}
