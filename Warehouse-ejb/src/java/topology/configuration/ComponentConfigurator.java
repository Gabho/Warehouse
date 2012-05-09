/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.configuration;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import topology.storage.Aisle;
import topology.storage.Rack;
import topology.storage.StorageLocal;

/**
 *
 * @author Mao
 */
@Stateless
public class ComponentConfigurator {

    File config;
    @EJB
    StorageLocal storage;

    public ComponentConfigurator() {
        config = new File("C:/Documents and Settings/Mao/My Documents/NetBeansProjects/WJ2EE/WJ2EE-ejb/src/java/topology/configuration/load.txt");

    }

    // Configure components from script at start of system.
    public void configure() {
        FileInputStream fis;
        BufferedInputStream bis;
        DataInputStream dis;

        try {
            fis = new FileInputStream(config);
            // Here BufferedInputStream is added for fast reading.
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);
            // dis.available() returns 0 if the file does not have more lines.
            while (dis.available() != 0) {
                //get task
                String delims = "[ ]";
                String[] tokens = dis.readLine().split(delims);
                //dynamic load class   
                Object instance = loadMyClass(tokens[0]);
                // Object instance = loadClass("topology.storage.Aisle");
                Method myMethod = instance.getClass().getMethod("init", String.class, int.class);

                //initial object  
                String param1 = tokens[1];
                int param2 = Integer.valueOf(tokens[2]);
                myMethod.invoke(instance, param1, param2);

                //insert object to repositary
                storage.insert(instance);
                if (instance instanceof Rack) {
                    setRacksToAisle((Rack) instance);
                }
                System.out.println("#Loading " + instance.getClass().getName() + " " + tokens[1] + ": OK");
            }
            // dispose all the resources after using them.
            fis.close();
            bis.close();
            dis.close();
//            Aisle a = (Aisle) storage.find("A"+1);
//            try {
//                System.out.println(a.getRacks().get(0).info()); 
//                a = (Aisle) storage.find("A"+1);
//                System.out.println(a.getRacks().get(1).info()); 
//                a = (Aisle) storage.find("A"+2);
//                System.out.println(a.getRacks().get(0).info()); 
//                a = (Aisle) storage.find("A"+2);
//                System.out.println(a.getRacks().get(1).info()); 
//            } catch (Exception ex) {
//                Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("IO Error!");
        }
    }

    // Configure component at runtime from admin command.
    public void processTask(String task) {
        System.out.println("Start proccesig task: " + task);
        String delims = "[ ]";
        String[] tokens = task.split(delims);

        if (tokens[0].equals("load")) {
            try {
                Object instance = loadMyClass(tokens[1]);
                // Object instance = loadClass("topology.storage.Aisle");
                Method myMethod = instance.getClass().getMethod("init", String.class, int.class);
                //initial object  
                String param1 = tokens[2];
                int param2 = Integer.valueOf(tokens[3]);
                myMethod.invoke(instance, param1, param2);
                //insert object to repositary
                storage.insert((AbstractComponent) instance);
                System.out.println("#Load: OK");
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (tokens[0].equals("remove")) {
            AbstractComponent object = (AbstractComponent) storage.find(tokens[1]);
            object.suspend(); 
            storage.remove(object);
            System.out.println("#Remove: OK");
        } else if (tokens[0].endsWith("configure")) {
            try {
                AbstractComponent object = (AbstractComponent) storage.find(tokens[1]);
                object.suspend();
                
                //TODO: dorobit set metody a prekonfigurovat komponent.
  
                object.resume();
                System.out.println("#Configure: OK");
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

    private Object loadMyClass(String classToLoad) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader loader = ComponentConfigurator.class.getClassLoader();
        Class classLoaded = loader.loadClass(classToLoad);
        Object instance = classLoaded.newInstance();
        return instance;
    }
    int count = 0;
    int aisleID = 1;

    private void setRacksToAisle(Rack rack) {
        count += 1;
        if (count > 5) {
            count = 0;
            aisleID += 1;
        }
        Aisle aisle = (Aisle) storage.find("A" + aisleID);
        try {
            aisle.getRacks().add(rack);
        } catch (Exception ex) {
            Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
