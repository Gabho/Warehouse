package topology.configuration;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import topology.resource.management.IShelf;
import topology.resource.management.ProxyShelf;
import topology.resource.management.ResourceCache;
import topology.storage.Aisle;
import topology.storage.IObjectManager;
import topology.storage.IStorageComponent;
import topology.storage.Rack;

/**
 * Vzor Komponent Konfigurátor - konfigurácia systém za behu.
 *
 * @author Martin Pakandl
 */
@Stateless
public class ComponentConfigurator {

    private static Logger LOGGER = Logger.getLogger(ComponentConfigurator.class.getName());
    //konfiguračný súbor
    File config;
    //sprístupnenie EJB Objekt Manažéra
    @EJB
    IObjectManager storage;
    //sprístupnenie EJB pamäte typu cache.
    @EJB
    ResourceCache<IShelf> cache;

    /**
     * Konštruktor Komponent Konfigurátora. Načíta konfiguračný súbor.
     */
    public ComponentConfigurator() {
        String filePath = getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        
        int endindex = filePath.indexOf("dist/gfdeploy");
        filePath = filePath.substring(6, endindex);
        filePath = filePath + ("Warehouse-ejb/src/java/topology/configuration/load.txt");
        
//        filePath = filePath.substring(6);
//        filePath = filePath + ("topology/configuration/load.txt");
        
        config = new File(filePath);
    }

    /**
     * Konfigurácia systému pri jeho štarte.
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
    }

    /**
     * Konfigurácia systému počas jeho behu.
     *
     * @param task konfiguračný príkaz
     * @return správu o výsledku konfigurácie
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
                    if (storeComponent.addComponent(object) == false) {
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

    /**
     * Načítanie triedy počas behu systému.
     *
     * @param classToLoad cesta k triede
     * @return objekt triedy
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private Object loadMyClass(String classToLoad) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader loader = ComponentConfigurator.class.getClassLoader();
        Class classLoaded = loader.loadClass(classToLoad);
        Object instance = classLoaded.newInstance();
        return instance;
    }
    //počet priradených regálov
    int count = 0;
    //identifikátor uličky
    int aisleID = 1;

    /**
     * Priradenie regálu do uličky.
     *
     * @param rack regál
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

    /**
     * Priradí regálu poličku.
     *
     * @param rack regál
     */
    private void setShelfsToRack(Rack rack) {
        for (int i = 1; i < 6; i++) {
            rack.addComponent(new ProxyShelf(shelfID++, cache));
        }
    }

    /**
     * Odstránienie regálu z uličky.
     *
     * @param rack regál
     * @return identifikátor uličky z ktorého bol regál odstránený
     */
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
