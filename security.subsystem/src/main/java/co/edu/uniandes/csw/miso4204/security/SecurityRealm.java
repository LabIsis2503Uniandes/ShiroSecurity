/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.miso4204.security;

import co.edu.uniandes.csw.miso4204.security.jwt.api.JwtToken;
import co.edu.uniandes.csw.miso4204.security.jwt.api.VerifyToken;
import co.edu.uniandes.csw.miso4204.security.logic.dto.UserDTO;
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
import com.stormpath.shiro.realm.ApplicationRealm;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 *
 * @author estudiante
 */
public class SecurityRealm extends ApplicationRealm{
    
    public static final String REALM="Ejemplo";
    

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SimpleAccount account=null;
        
        JwtToken authToken = (JwtToken) token;
        if (authToken.getToken() != null) { 
            //Descifrar token y establecer info de usuario
            UserDTO user = decodeUser(authToken.getToken());
            if (validarToken(user)) {
                account = new SimpleAccount(user.getUsername(), user.getPassword(),ByteSource.Util.bytes(authToken.getToken()),REALM);       
            }
        }
        return account;
    }
    
    public UserDTO decodeUser(String token){
        VerifyToken ver = new VerifyToken();
        UserDTO user = ver.getDataUser(token);
        return user;
    }

    public boolean validarToken(UserDTO user) {
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

    
    
}
