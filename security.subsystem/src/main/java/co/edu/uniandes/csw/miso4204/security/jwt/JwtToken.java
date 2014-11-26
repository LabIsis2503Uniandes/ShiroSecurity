/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.miso4204.security.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 *
 * @author estudiante
 */
public class JwtToken implements AuthenticationToken{
 
    private String token;

	public JwtToken(String token) {
		this.token = token;
	}

        public String getToken(){
            return token;
        }
        
	public Object getPrincipal() {
		return null;
	}

	public Object getCredentials() {
		return null;
	}
}
