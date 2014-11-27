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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;

/**
 *
 * @author Mauricio
 */
@Path("/competitors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompetitorService {

//    @PersistenceContext(unitName = "AplicacionMundialPU")
//    EntityManager entityManager;
    
    protected CompetitorsLogic competitorsLogic;
    
    @PostConstruct
	public void loadDependencies(){
		competitorsLogic = new CompetitorsLogic();
	}
    
    
   

//    @PostConstruct
//    public void init() {
//        try {
//            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @GET
    public Response getAll() {
        return competitorsLogic.getAll();
    }
    
    @POST
    public Response createCompetitor(CompetitorEntity competitor){
        return competitorsLogic.createCompetitor(competitor);
    }
    

//    @POST
//    @Path("/add")
//    public Response createCompetitor(CompetitorEntity competitor) {
//
//        CompetitorEntity c = new CompetitorEntity();
//        JSONObject rta = new JSONObject();
//        c.setAddress(competitor.getAddress());
//        c.setAge(competitor.getAge());
//        c.setCellphone(competitor.getCellphone());
//        c.setCity(competitor.getCity());
//        c.setCountry(competitor.getCountry());
//        c.setName(competitor.getName());
//        c.setSurname(competitor.getSurname());
//        c.setTelephone(competitor.getTelephone());
//
//        try {
//            entityManager.getTransaction().begin();
//            entityManager.persist(c);
//            entityManager.getTransaction().commit();
//            entityManager.refresh(c);
//            rta.put("competitor_id", c.getId());
//        } catch (Throwable t) {
//            t.printStackTrace();
//            if (entityManager.getTransaction().isActive()) {
//                entityManager.getTransaction().rollback();
//            }
//            c = null;
//        } finally {
//            //entityManager.flush();
//        	entityManager.clear();
//        	entityManager.close();
//        }
//
//        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(rta.toJSONString()).build();
//    }

}
