package communication;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import persistence.Database;
import persistence.MasterDataEntity;
import topology.activeobject.IProxyLocal;

/**
 * Enterprise Java Bean zabezečujúci komunikáciu medzi skladmi
 *
 * @author kopytko
 */
@Singleton
public class Communication implements CommunicationLocal, CommunicationRemote {

    @EJB
    private Database database;
    @EJB
    private IProxyLocal proxy;
    private List<String> IPaddress = new ArrayList<String>();
    private InetAddress addr;

    /**
     * Nadviazanie spojenia so skladmi definovanými na daných ip-adresách a
     * vyhľadanie položiek na základe reťazca.
     *
     * @param string vyhľadávací reťazec.
     * @return zoznam reťazcov, ktoré predstavujú výsledok vyhľadávania.
     */
    @Override
    public List<String> searchRemote(String string) {
        List<String> resultList = new ArrayList<String>();

        IPaddress.clear();
        getAllIP();

        System.out.println("........................................STARTING COMMUNICATION");

        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial",
                "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty("java.naming.factory.url.pkgs",
                "com.sun.enterprise.naming");
        props.setProperty("java.naming.factory.state",
                "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

        for (String ip : IPaddress) {
            props.setProperty("org.omg.CORBA.ORBInitialHost", ip);
            try {
                addr = InetAddress.getByName(ip);
                if (addr.isReachable(2000) == true) {
                    InitialContext ic = new InitialContext(props);
                    CommunicationRemote communication = (CommunicationRemote) ic.lookup("java:global/Warehouse/Warehouse-ejb/Communication!communication.CommunicationRemote");
                    resultList.addAll(communication.searchRemoteI(string));
                }
            } catch (NamingException ex) {
                Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                System.out.println("..............................COMMUNICATION CANNOT CONNECT");
                Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return resultList;
    }

    /**
     * Vyhľadávanie na lokálnom sklade, pričom funkcia bola zavolaná zo
     * vzdialeného skladu.
     *
     * @param search vyhľadávací reťazec.
     * @return zoznam reťazcov, ktoré predstavujú výsledok vyhľadávania.
     */
    @Override
    public List<String> searchRemoteI(String search) {
        List<String> resultList = new ArrayList<String>();
        List<MasterDataEntity> masterData = database.search(search);
        String id = proxy.getWarehouseID();
        for (MasterDataEntity master : masterData) {
            resultList.add(("WAREHOUSE:" + id + ": " + master.getId() + " ,quantity=" + Integer.toString(master.getQuantity())));
        }
        return resultList;
    }

    /**
     * Načítanie IP adries zo súboru IPaddress.txt.
     */
    private void getAllIP() {
        try {
            FileInputStream fstream;
            String filePath = getClass().getProtectionDomain().getCodeSource().getLocation().toString();

            //file path calculation when deployed in NetBeans
//            int endindex = filePath.indexOf("dist/gfdeploy");
//            filePath = filePath.substring(6, endindex);
//            filePath = filePath + ("Warehouse-ejb/src/java/communication/IPaddress.txt");

            //file path calculation when deployed in cmd
            filePath = filePath.substring(6);
            filePath = filePath + ("communication/IPaddress.txt");

            fstream = new FileInputStream(filePath);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                IPaddress.add(line);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("..............................COMMUNICATION - File IPAddress.txt not found");
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
