package httpServlets;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.Database;
import persistence.MasterDataEntity;
import topology.activeobject.IFunctionality;

/**
 * Trieda dediaca od HttpServletu, ktorá slúži na obslúženie vstupu od používateľa.
 * Zabezpečuje zachytávanie udalostí pre vkladanie a odstraňovanie master dát.
 * @author Gabriel Cervenak
 */
@WebServlet(name = "MasterDataServlet", urlPatterns = {"/masterData"})
public class MasterDataServlet extends HttpServlet {

    @EJB
    private IFunctionality proxy;
    @EJB
    private Database database;

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
        request.getRequestDispatcher("/masterData.jsp").forward(request, response);
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
        if (request.getParameter("command").equals("Insert")) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            if(name.equals(""))
                request.setAttribute("nullName", "Enter name");
            if(description.equals(""))
                request.setAttribute("nullDesc", "Enter description");
            if ((!name.equals("")) && (!description.equals(""))) {
                name = name.toUpperCase();
                if (checkName(name) == true) {
                    request.setAttribute("error", "Master data with such ID exist!");
                } else {
                    MasterDataEntity masterData = new MasterDataEntity(name, description);
                    proxy.insertMasterData(masterData);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }
        if (request.getParameter("command").equals("Remove")) {

            String value = (String) request.getParameter("selectMD");
            proxy.removeMasterData(value);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
        request.getRequestDispatcher("/masterData.jsp").forward(request, response);
    }

    //Metóda vráti true ak sa daný reťazec nachádza ako ID v master datach
    private boolean checkName(String name) {
        boolean result = false;
        List<MasterDataEntity> masterDatas = database.getMasterData();
        for (MasterDataEntity entity : masterDatas) {
            if (entity.getId().equals(name)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Vráti krátky opis servletu.
     *
     * @return reťazec, ktorý obsahuje opis servletu.
     */
    @Override
    public String getServletInfo() {
        return "Servlet for adding and removing master data";
    }// </editor-fold>
}
