package com.dk.faculty.graphqlparser.entities;

public class GraphQLRequest {
    private String operationName;
    private String query;

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public GraphQLRequest(String operationName, String query) {
        this.operationName = operationName;
        this.query = query;
    }
}
