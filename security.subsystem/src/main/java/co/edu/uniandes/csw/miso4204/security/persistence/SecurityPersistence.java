/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.miso4204.security.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author estudiante
 */
public class SecurityPersistence extends _SecurityPersistence{
        
    public SecurityPersistence(){
        emf = Persistence.createEntityManagerFactory("SecurityPU");
        
    }
    
}
