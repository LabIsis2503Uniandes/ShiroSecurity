/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.user.service;

import co.edu.uniandes.csw.miso4204.security.jwt.api.JsonWebToken;
import co.edu.uniandes.csw.miso4204.security.jwt.api.JwtHashAlgorithm;
import co.edu.uniandes.csw.miso4204.security.logic.SecurityLogic;
import co.edu.uniandes.csw.miso4204.security.logic.dto.UserDTO;
import co.edu.uniandes.isis2503.competitors.logic.CompetitorsLogic;
import com.google.gson.Gson;
import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jj.alarcon10
 */
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

    protected SecurityLogic securityLogic;

    
    
    @PostConstruct
	public void loadDependencies(){
		securityLogic = new SecurityLogic();
	} 
    
    
    @Path("/login")
    @POST
    public Response login(UserDTO user) {
        int status = 500; //Codigo de error en el servidor
        String token = "User and/or password wrong";
        
        try{
            UserDTO userDb = securityLogic.getUserSession(user.getUsername());
            if(userDb!=null){
                if(userDb.getUsername().equals(user.getUsername()) && userDb.getPassword().equals(user.getPassword())){
                    token = new Gson().toJson(JsonWebToken.encode(userDb,"Un14nd3s2014@" ,JwtHashAlgorithm.HS256));
                    status = 200;
                }
            
            }
           
        }catch(Exception e){
            e.printStackTrace();
        
        }

        return Response.status(status).entity(token).build();
    }

}
