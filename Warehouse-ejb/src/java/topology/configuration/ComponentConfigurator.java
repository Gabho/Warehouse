/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.configuration;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import persistence.MasterDataEntity;
import topology.resource.management.IShelf;
import topology.resource.management.Item;
import topology.resource.management.ProxyShelf;
import topology.resource.management.ResourceCache;
import topology.storage.Aisle;
import topology.storage.IObjectManager;
import topology.storage.IStorageComponent;
import topology.storage.Rack;

/**
 *
 * @author Mao
 */
@Stateless
public class ComponentConfigurator {

    File config;
    @EJB
    IObjectManager storage;
    @EJB
    ResourceCache<IShelf> cache;
    /*
     * Constructor of Component Configurator.
     */

    public ComponentConfigurator() {
        String filePath = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        int endindex = filePath.indexOf("dist/gfdeploy");
        filePath = filePath.substring(6, endindex);
        filePath = filePath + ("Warehouse-ejb/src/java/topology/configuration/load.txt");

        config = new File(filePath);
    }

    /*
     * Configure components from script at start of system.
     */
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
                    setShelfsToRack((Rack) instance);
                }
                System.out.println("#Loading " + instance.getClass().getName() + " " + tokens[1] + ": OK");
            }
            // dispose all the resources after using them.
            fis.close();
            bis.close();
            dis.close();
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
        // storage.removeItem(count, new MasterDataEntity());
    }

    /*
     * Configure component at runtime from admin command. Format of commands:
     * load path.to.Component string_name_component int_capacity remove
     * string_name_component configure string_name_component
     * string_new_name_component int_capacity [string_insert_to_upper_componet]
     *
     * Example: load topology.storage.Aisle A1 5; remove A1; configure R2 R1 5
     * A1
     *
     */
    public String processTask(String task) {
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
                return "Load: OK";
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
            if (object != null) {
                System.out.println(object.info());
                object.suspend();
                //remove object from Object Manager
                storage.remove(object.getCode());
                if (!(object instanceof Aisle)) {
                    removeFromParent((Rack) object);
                }
                return "Remove: OK!";
            } else {
                return "Error: No match to object!";
            }
        } else if (tokens[0].equals("configure")) {
            try {
                AbstractComponent object = (AbstractComponent) storage.find(tokens[1]);
                object.suspend();
                IStorageComponent cObject = (IStorageComponent) object;
                cObject.setCode(tokens[2]);
                cObject.setCapacity(Integer.valueOf(tokens[3]));
                if (!(cObject instanceof Aisle)) {
                    IStorageComponent storeComponent = (IStorageComponent) storage.find(tokens[4]);
                    String parent = removeFromParent((Rack) object);
                    if (storeComponent.addComponent(object) == 0) {
                        storeComponent = (IStorageComponent) storage.find(parent);
                        storeComponent.addComponent(object);
                        object.resume();
                        return "Configure: Error! Storage component is full!";
                    }
                }
                object.resume();
                return "Configure: OK!";
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (tokens[0].equals("print")) {

            return storage.printStorage();
        }
        return "Error: Unknown command!";
    }

    /*
     * Load class to system at runtime.
     */
    private Object loadMyClass(String classToLoad) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader loader = ComponentConfigurator.class.getClassLoader();
        Class classLoaded = loader.loadClass(classToLoad);
        Object instance = classLoaded.newInstance();
        return instance;
    }
    int count = 0;
    int aisleID = 1;
    /*
     * Create depends of store components.
     */

    private void setRacksToAisle(Rack rack) {
        if (count > 4) {
            count = 0;
            aisleID += 1;
        }
        Aisle aisle = (Aisle) storage.find("A" + aisleID);
        try {
            aisle.getRacks().add(rack);
        } catch (Exception ex) {
            Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
        }
        count += 1;
    }
    int shelfID = 0;

    private void setShelfsToRack(Rack rack) {
        for (int i = 1; i < 6; i++) {
            rack.addComponent(new ProxyShelf(shelfID++, cache));
        }
    }

    private String removeFromParent(Rack rack) {
        for (int i = 1; i < aisleID; i++) {
            try {
                Aisle aisle = (Aisle) storage.find("A" + i);
                if (aisle == null) {
                    i++;
                    aisle = (Aisle) storage.find("A" + i);
                }
                List<Rack> racks = aisle.getRacks();
                for (int j = 0; j < racks.size(); j++) {
                    Rack rack1 = racks.get(j);
                    if (rack1.equals(rack)) {
                        aisle.removeComponent(rack);
                        return aisle.getCode();

                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(ComponentConfigurator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
