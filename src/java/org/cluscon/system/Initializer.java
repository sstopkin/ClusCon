package org.cluscon.system;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author V-ray
 */

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.cluscon.database.Storage;


public class Initializer implements ServletContextListener {
//    public Thread th=null;
    public Storage storage = null;

    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        storage = new Storage();  
        for (int i=1;i<6;i++){
                storage.addPair(i, "10.0.0.10"+Integer.toString(i), "10.0.0.10"+Integer.toString(i));
      }
        ServletContext sc = event.getServletContext();
        sc.setAttribute("BaseDir","/nfs/mpi/");
        sc.setAttribute("storage", storage);
        sc.setAttribute("firstIP", "8.8.8.1");
        sc.setAttribute("lastIP", "8.8.8.9");
    }
    
    public void contextDestroyed(ServletContextEvent event) {
        try {
//            th.interrupt();
        } catch (Exception ex) {
        }
    }
}