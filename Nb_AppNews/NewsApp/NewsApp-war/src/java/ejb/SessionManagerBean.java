/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author rgamb
 */
@Singleton
@LocalBean
@WebListener
public class SessionManagerBean implements HttpSessionListener
{
    private static int counter = 0;
    
    @Override
    public void sessionCreated(HttpSessionEvent se) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        counter++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        counter--;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public int getActiveSessionsCount() {
        return counter;
    }
}
