package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Destinacija;
import model.Drzava;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import util.HibernateUtil;

@WebServlet(name = "state", urlPatterns = {"/state"})
public class state extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<title>Georgios Tours - Leto 2017</title>"
                    + "<link rel=icon href=images/icons/airplane.png>"
                    + "<link rel=\"stylesheet\" type=\"text/css\" href=\"images/style.css\">"
                    + "</head>"
                    + "<body>"
                    + "<div id=\"page-container\">"
                    + "<div id=\"header\">"
                    + "<img class=\"img-header\" src=\"images/header.jpg\">"
                    + "</div>"
                    + "<div class=\"omot\" style=\"height:500px!important\">"
                    + "<div id=\"menu\">"
                    + "<div class=\"padding\"> <a href=../choose><u>Početna</u></a> | <a href=/GeorgiosTours/choose/check_in_form><u>Čekiranje</u></a> | <a href=/GeorgiosTours/choose/kontakt.html><u>Kontakt</u></a> </div>"
                    + "</div>"
                    + "<div id=\"content\">");

            Session session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();

            try {
                int trazeniIdDrzave = Integer.parseInt(request.getParameter("id_drz"));
                Drzava nekaDrzava = (Drzava) session.get(Drzava.class, trazeniIdDrzave);
                if (nekaDrzava != null) {

                    out.println("<h1>LETO 2017 - " + nekaDrzava.getImeDrzave().toUpperCase() + "</h1><br />");
                    Query q1 = session.createQuery("FROM Drzava");
                    List<Drzava> drzave = q1.list();
                    out.print("<div>Država:  ");
                    out.print("<select onchange='window.location=\"state?id_drz=\"+this.value'>");
                    out.print("<option value='" + nekaDrzava.getIdDrzave() + "' selected>" + nekaDrzava.getImeDrzave().toUpperCase() + "</option>");
                    for (Drzava d : drzave) {
                        if (!d.getImeDrzave().equals(nekaDrzava.getImeDrzave())) {
                            out.print("<option value='" + d.getIdDrzave() + "'>" + d.getImeDrzave() + "</option>");
                        } else {
                            continue;
                        }
                    }
                    out.print("</select></div><br /><br /><br />");

                    Query q2 = session.createQuery("FROM Destinacija WHERE drzava.idDrzave = :id");
                    q2.setParameter("id", nekaDrzava.getIdDrzave());
                    List<Destinacija> destinacije = q2.list();

                    out.print("<h3 style=\"margin-top:30px!important\"><u>Izaberite destinaciju</u>: </h3><br />");
                    out.print("<select onchange='window.location=\"destination?id_dest=\"+this.value'>");
                    out.print("<option disabled selected>*izaberite*</option>");
                    for (Destinacija dest : destinacije) {
                        out.print("<option value='" + dest.getIdDestinacije() + "'>" + dest.getImeDestinacije() + "</option>");
                    }
                    out.print("</select></div><br />");
                } else {
                    response.getWriter().write("<h1>Trenutno nemamo u ponudi.</h1>");
                    return;
                }
            } catch (Exception ex) {
                out.print("<h1>Oops! Something went wrong.</h1>");
            }

            session.getTransaction().commit();

            if (session.isOpen() && session != null) {
                session.close();
            }
            out.println("</div>"
                    + "<img src=\"images/icons/logo.png\" width=400 height=400>"
                    + "<div id=\"footer\"> Copyright &copy; 2017 Georgios Tours - Design: Đorđije Čupić"
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
