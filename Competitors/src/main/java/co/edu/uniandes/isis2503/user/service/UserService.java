/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.user.service;

import co.edu.uniandes.csw.miso4204.security.jwt.api.JsonWebToken;
import co.edu.uniandes.csw.miso4204.security.jwt.api.JwtHashAlgorithm;
//import co.edu.uniandes.csw.miso4204.security.logic.SecurityLogic;
import co.edu.uniandes.csw.miso4204.security.logic.dto.UserDTO;
import com.google.gson.Gson;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeys;
import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.stormpath.sdk.tenant.*;
import com.stormpath.sdk.application.*;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.group.Group;
import com.stormpath.sdk.group.GroupCriteria;
import com.stormpath.sdk.group.GroupList;
import com.stormpath.sdk.group.Groups;
import com.stormpath.sdk.resource.ResourceException;

/**
 *
 * @author Jj.alarcon10
 */
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

//    protected SecurityLogic securityLogic;
//
//    @PostConstruct
//    public void loadDependencies() {
//        securityLogic = new SecurityLogic();
//    }

    @Path("/login")
    @POST
    public Response login(UserDTO user) {
        int status = 500; //Codigo de error en el servidor
        String token = "User and/or password wrong";
        UserDTO userStorm = new UserDTO();
        String path = "C:\\Users\\Jj.alarcon10\\Documents\\apiKey.properties";//Colocar la Ubicacion de su archivo apiKey.properties
        ApiKey apiKey = ApiKeys.builder().setFileLocation(path).build();
        Client client = Clients.builder().setApiKey(apiKey).build();

        try {
            AuthenticationRequest request = new UsernamePasswordRequest(user.getUsername(), user.getPassword());
            Tenant tenant = client.getCurrentTenant();
            ApplicationList applications = tenant.getApplications(Applications.where(Applications.name().eqIgnoreCase("My Application")));
            Application application = applications.iterator().next();

            AuthenticationResult result = application.authenticateAccount(request);
            Account account = result.getAccount();
            userStorm.setEmail(account.getEmail());
            userStorm.setName(account.getFullName());
            userStorm.setUsername(account.getUsername());
            userStorm.setPassword(user.getPassword());
            userStorm.setLevelAccess("Admin");
            token = new Gson().toJson(JsonWebToken.encode(userStorm, "Un14nd3s2014@", JwtHashAlgorithm.HS256));
            status = 200;

//          UserDTO userDb = securityLogic.getUserSession(user.getUsername());
//          if(userDb!=null){
//                if(userDb.getUsername().equals(user.getUsername()) && userDb.getPassword().equals(user.getPassword())){
//                    token = new Gson().toJson(JsonWebToken.encode(userDb,"Un14nd3s2014@" ,JwtHashAlgorithm.HS256));
//                    status = 200;
//                }
//            
//            }
        } catch (ResourceException ex) {
            System.out.println(ex.getStatus() + " " + ex.getMessage());
        }

        return Response.status(status).entity(token).build();
    }

}
