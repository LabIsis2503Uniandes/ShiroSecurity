/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.miso4204.security.logic;

import co.edu.uniandes.csw.miso4204.security.persistence.SecurityPersistence;

/**
 *
 * @author estudiante
 */
public class SecurityLogic extends _SecurityLogic {

    public SecurityLogic() {
        persistance = new SecurityPersistence();
    }

}
