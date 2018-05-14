package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public  class HibernateUtil {                              
        
        public static SessionFactory sessionFactory = null;
        
        public static SessionFactory getSessionFactory(){   
            if(sessionFactory == null){
                Configuration config = new Configuration();
                config.configure();            
                ServiceRegistry serciveRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
                sessionFactory = config.buildSessionFactory(serciveRegistry);                           
            }
            return sessionFactory;            
        }
   
}

