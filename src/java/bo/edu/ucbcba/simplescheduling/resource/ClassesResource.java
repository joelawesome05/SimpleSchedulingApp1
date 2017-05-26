/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.ucbcba.simplescheduling.resource;

import bo.edu.ucbcba.simplescheduling.model.MyClass;
import bo.edu.ucbcba.simplescheduling.model.Student;
import bo.edu.ucbcba.simplescheduling.response.ErrorResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Internet
 */
@Path("v1/classes")
public class ClassesResource {

    @Context
    private UriInfo context;
    private final Gson gson = new Gson();
    // TODO
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createClass(String jsonString) {
        MyClass clas = gson.fromJson(jsonString, MyClass.class);
        String code = clas.getCode();
        if (GenericResource.getClass(code) != null) { // ya exist
            ErrorResponse errorResponse = new ErrorResponse(UUID.randomUUID(), 
                    Response.Status.BAD_REQUEST, "ERR_001", "Creation failed.", 
                    "The class was not created", 
                    Arrays.asList("El usuario ya existe"));
            return Response.ok(gson.toJson(errorResponse), MediaType.APPLICATION_JSON)
                    .status(Response.Status.BAD_REQUEST).build();
        }
        String title = clas.getTitle();
        String description = clas.getDescription();
        List<Integer> Ids = clas.getStudentIds();
        GenericResource.putClass(clas);
        return Response.ok(gson.toJson(clas), 
                MediaType.APPLICATION_JSON)
                .status(Response.Status.CREATED)
                .build();
    }
   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
        public Response getClasses() {
        List<MyClass> classes = 
                new ArrayList<>(GenericResource.getClassMap().values());
        return Response.ok(gson.toJson(classes), 
                MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(@PathParam("code") String code) {
        // search class
        MyClass clas = GenericResource.getClass(code);
        if (clas != null) {
            return Response.ok(gson.toJson(clas), 
                    MediaType.APPLICATION_JSON).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    
    @DELETE
    @Path("{code}")
    public Response deleteClass(@PathParam("code") String code) {
        // search student
        MyClass clas = GenericResource.getClass(code);
        if (code != null) {
            GenericResource.removeClass(code);
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    
    @PUT
    @Path("{code}")
    public Response editClass(String jsonString) {
        MyClass clas = gson.fromJson(jsonString, MyClass.class);
        
        String code = clas.getCode();
        String title = clas.getTitle();
        String description = clas.getDescription();
        List<Integer> studentIds = clas.getStudentIds();
        
        MyClass currentClass = GenericResource.getClass(code);
        if (currentClass != null) {
            if (!title.isEmpty()) {
                currentClass.setTitle(title);
            }
            if (!description.isEmpty()) {
                currentClass.setDescription(description);
            }
            currentClass.setStudentIds(studentIds);
            return Response.ok(gson.toJson(currentClass), 
                    MediaType.APPLICATION_JSON)
                    .status(Response.Status.CREATED)
                    .build();
        }   
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }        
    }
}
