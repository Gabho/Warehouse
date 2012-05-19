package httpServlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.Database;
import persistence.MasterDataEntity;
import topology.activeobject.IFunctionality;
import topology.activeobject.IFuture;
import topology.resource.management.Item;

/**
 * Trieda dediaca od HttpServletu, ktorá slúži na obslúženie vstupu od
 * používateľa pri vkladaní a odstraňovaní položiek.
 * @author Gabriel Cervenak
 */
@WebServlet(name = "ItemServlet", urlPatterns = {"/item"})
public class ItemServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ItemServlet.class.getName());
    @EJB
    private IFunctionality proxy;
    @EJB
    private Database database;
    private IFuture resultFututre = null;
    private static boolean wasInsert;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Boolean result = (Boolean) resultFututre.get();
        if (wasInsert == true) {
            if (result == true) {
                request.setAttribute("error", "Item succesfully inserted!");
            }
            else if(result == false)
                request.setAttribute("error", "Insertion of item failed");
        }
        else if(wasInsert == false){
            if(result = true)
                request.setAttribute("error", "Item succesfully removed");
            else if(result = false)
                request.setAttribute("error", "Removal of item failed");
        }
        request.getRequestDispatcher("/item.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<MasterDataEntity> masterDatas = database.getMasterData();
        MasterDataEntity masterData = null;
        Date expDate;
        String masterDataID;
        String quantity;

        //Vkladanie údajov (itemov) do databázy
        if (request.getParameter("command").equals("Insert")) {
            wasInsert = true;
            try {
                masterDataID = request.getParameter("selectMD");
                quantity = request.getParameter("quantity");
                String day = request.getParameter("day");
                String month = request.getParameter("month");
                String year = request.getParameter("year");

                for (MasterDataEntity data : masterDatas) {
                    if (data.getId().equals(masterDataID)) {
                        masterData = data;
                    }
                }

                if (quantity.equals("")) {
                    request.setAttribute("nullQuantity", "Enter quantity");
                }
                if (day.equals("") || month.equals("") || year.equals("")) {
                    request.setAttribute("nullDate", "Enter date");
                }

                int dd = Integer.parseInt(day);
                int mm = Integer.parseInt(month);
                int yy = Integer.parseInt(year) - 1900;
                int itemQuantity = Integer.parseInt(quantity);
                if (dd > 31) {
                    request.setAttribute("nullDate", "Wrong date!");
                    throw new NumberFormatException();
                }
                if (mm > 12) {
                    request.setAttribute("nullDate", "Wrong date!");
                    throw new NumberFormatException();
                }
                expDate = new Date(yy, mm, dd);
                Item item = new Item(0, itemQuantity, masterDataID, masterData.getDescription(), expDate, null);
                resultFututre = proxy.insertNewItem(item);
                LOGGER.log(Level.INFO, "..............................Input:{0}, {1}, {2}, {3}, {4}....................", new Object[]{masterData.getId(), itemQuantity, dd, mm, yy});
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Wrong input format!");
                request.getRequestDispatcher("/item.jsp").forward(request, response);
            }
        }

        //Odstránenie itemov z databázy
        if (request.getParameter("command").equals("Remove")) {
            wasInsert = false;
            try {
                masterDataID = request.getParameter("removeMD");
                quantity = request.getParameter("rmQuantity");

                for (MasterDataEntity data : masterDatas) {
                    if (data.getId().equals(masterDataID)) {
                        masterData = data;
                    }
                }

                if (quantity.equals("")) {
                    request.setAttribute("nullQuantityRm", "Enter quantity");
                }
                int itemQuantity = Integer.parseInt(quantity);
                if (itemQuantity > masterData.getQuantity()) {
                    request.setAttribute("nullQuantityRm", ("In stock only " + masterData.getQuantity() + " items"));
                } else {
                    resultFututre = proxy.removeItem(itemQuantity, masterData);
                    LOGGER.log(Level.INFO, "..............................Input:{0}, {1}....................", new Object[]{masterDataID, itemQuantity});
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Wrong input format!");
                request.getRequestDispatcher("/item.jsp").forward(request, response);
            }
        }
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
