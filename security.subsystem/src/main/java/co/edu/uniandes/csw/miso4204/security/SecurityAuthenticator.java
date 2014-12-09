/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.miso4204.security;


import co.edu.uniandes.csw.miso4204.security.jwt.JwtToken;
import co.edu.uniandes.csw.miso4204.security.jwt.api.VerifyToken;
import co.edu.uniandes.csw.miso4204.security.logic.SecurityLogic;
import co.edu.uniandes.csw.miso4204.security.logic.dto.UserDTO;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeys;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.ApplicationList;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.authc.AuthenticationRequest;
import com.stormpath.sdk.authc.AuthenticationResult;
import com.stormpath.sdk.authc.UsernamePasswordRequest;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.resource.ResourceException;
import com.stormpath.sdk.tenant.Tenant;
import javax.annotation.PostConstruct;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 *
 * @author estudiante
 */
public class SecurityAuthenticator implements Authenticator {

    protected SecurityLogic securityLogic;
    
    
    @PostConstruct
	public void loadDependencies(){
		securityLogic = new SecurityLogic();
	}
    

    public AuthenticationInfo authenticate(AuthenticationToken at) throws AuthenticationException {
        JwtToken authToken = (JwtToken) at;
        if (authToken.getToken() != null) {
            if (!authToken.getToken().equals("")) {
                //Descifrar token y establecer info de usuario
                UserDTO user = decodeUser(authToken.getToken());
                if (validarToken(user)) {
                    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
                    authenticationInfo.setPrincipals(new SimplePrincipalCollection(user, user.getUsername()));
                    return authenticationInfo;
                }
            }
        }
        throw new AccountException("Token invalido.");
    }

    public UserDTO decodeUser(String token) {
        UserDTO user = null;
        try {
            VerifyToken ver = new VerifyToken();
            user = ver.getDataUser(token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean validarToken(UserDTO user) {
// ###################### With DataBase ############################################         
//        loadDependencies();
//        UserDTO userRecord = securityLogic.getUserSession(user.getId());
//        boolean result = false;
//        if (userRecord != null) {
//            result = (userRecord.getUsername().equals(user.getUsername()) && userRecord.getPassword().equals(user.getPassword()) && user.getTenantID().equals(userRecord.getTenantID()));
//        }
//        return result;
// ##################################################################    
        
        boolean result = false;
        String path = "C:\\Users\\Jj.alarcon10\\Documents\\apiKey.properties";//Colocar la Ubicacion de su archivo apiKey.properties
        ApiKey apiKey = ApiKeys.builder().setFileLocation(path).build();
        Client client = Clients.builder().setApiKey(apiKey).build();

        try {
            AuthenticationRequest request = new UsernamePasswordRequest(user.getUsername(), user.getPassword());
            Tenant tenant = client.getCurrentTenant();
            ApplicationList applications = tenant.getApplications(Applications.where(Applications.name().eqIgnoreCase("My Application")));
            Application application = applications.iterator().next();

            AuthenticationResult resultAuth = application.authenticateAccount(request);
            result = true;
        
        }catch(ResourceException e){
            System.out.println("Error at Authenticating");
        }
        return result;
        
    }

//    public SecurityLogic getSecurityLogic() {
//        return securityLogic;
//    }
//
//    public void setSecurityLogic(SecurityLogic securityLogic) {
//        this.securityLogic = securityLogic;
//    }

}
