/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpServlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import topology.resource.management.Item;

/**
 *
 * @author Gabo
 */
@WebServlet(name = "ItemServlet", urlPatterns = {"/item"})
public class ItemServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ItemServlet.class.getName());
    @EJB
    private IFunctionality proxy;
    @EJB
    private Database database;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Wrong Input</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3><font color=\"red\">Wrong input!</font></h3>");
            out.println("<br><a href=\"item.jsp\">Back</a>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

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
            try {
                int dd = Integer.parseInt(day);
                int mm = Integer.parseInt(month);
                int yy = Integer.parseInt(year);
                int itemQuantity = Integer.parseInt(quantity);
                if (dd > 31) {
                    throw new NumberFormatException();
                }
                if (mm > 12) {
                    throw new NumberFormatException();
                }
                expDate = new Date(yy, mm, dd);
                Item item = new Item(0, itemQuantity, masterDataID, masterData.getDescription(), expDate, null);
                proxy.insertNewItem(item);
                LOGGER.log(Level.INFO, "..............................Input:{0}, {1}, {2}, {3}, {4}....................", new Object[]{masterData.getId(), itemQuantity, dd, mm, yy});
            } catch (NumberFormatException e) {
                processRequest(request, response);
            }
        }

        if (request.getParameter("command").equals("Remove")) {
            masterDataID = request.getParameter("removeMD");
            quantity = request.getParameter("rmQuantity");
            
                        for (MasterDataEntity data : masterDatas) {
                if (data.getId().equals(masterDataID)) {
                    masterData = data;
                }
            }
            
            try {
                int itemQuantity = Integer.parseInt(quantity);
                proxy.removeItem(itemQuantity, masterData);
                LOGGER.log(Level.INFO, "..............................Input:{0}, {1}....................", new Object[]{masterDataID, itemQuantity});
            } catch (NumberFormatException e) {
                processRequest(request, response);
            }
        }
        request.getRequestDispatcher("/item.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
