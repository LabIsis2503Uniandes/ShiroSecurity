/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.isis2503.competitors.service;

import co.edu.uniandes.isis2503.competitors.logic.CompetitorsLogic;
import co.edu.uniandes.isis2503.competitors.persistence.entity.CompetitorEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;

/**
 *
 * @author estudiante
 */
@Path("/competitors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompetitorService {


    
    protected CompetitorsLogic competitorsLogic;
    
    @PostConstruct
	public void loadDependencies(){
		competitorsLogic = new CompetitorsLogic();
	}
   

    @GET
    public Response getAll() {
        return competitorsLogic.getAll();
    }
    
    @POST
    public Response createCompetitor(CompetitorEntity competitor){
        return competitorsLogic.createCompetitor(competitor);
    }
    
    @DELETE
    @Path("{id}")
    public Response deleteCompetitor(@PathParam("id") Long id){
        return competitorsLogic.deleteCompetitor(id);
    }
    
    @PUT
    @Path("{id}")
    public Response updateCompetitor(@PathParam("id") Long id, CompetitorEntity competitor){
        return competitorsLogic.updateCompetitor(id,competitor);
    
    }
    
    
    
    
    
}
