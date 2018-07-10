package com.dk.faculty.graphqlparser.api.v1.resources;

import com.dk.faculty.graphqlparser.beans.Parser;
import com.dk.faculty.graphqlparser.entities.GraphQLRequest;
import com.dk.faculty.graphqlparser.entities.SchemaObject;
import com.google.gson.Gson;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@ApplicationScoped
@Path("graphql-parser")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParseResource {
    private Client httpClient;

    @Inject
    private Parser parser;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
    }

    @GET
    public Response getSchema(@QueryParam("url") String url) {
        if(url == null || url.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            Response r = httpClient
                    .target(url)
                    .request()
                    .post(Entity.json(new GraphQLRequest("IntrospectionQuery", "query IntrospectionQuery {__schema {queryType {name}mutationType{name} subscriptionType {   name } types {   ...FullType } directives {   name   description   locations   args {     ...InputValue   } }  }}fragment FullType on __Type {  kind  name  description  fields(includeDeprecated: true) { name description args {   ...InputValue } type {   ...TypeRef } isDeprecated deprecationReason  }  inputFields { ...InputValue  }  interfaces { ...TypeRef  }  enumValues(includeDeprecated: true) { name description isDeprecated deprecationReason } possibleTypes { ...TypeRef }}fragment InputValue on __InputValue { name description type { ...TypeRef}defaultValue}fragment TypeRef on __Type{kind name ofType{kind name ofType{kind name ofType {kind name ofType{kind name ofType{kind name ofType{kind name ofType{kind name}}}}}}}}")));
            if(r.getStatusInfo().getStatusCode() == Response.Status.OK.getStatusCode()) {
                Map responseJSON = new Gson().fromJson(r.readEntity(String.class), Map.class);
                Map data = (Map)((Map)responseJSON.get("data")).get("__schema");
                return Response.status(Response.Status.OK).entity(parser.parseSchema(data)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid url provided.").build();
            }
        }
    }
}
