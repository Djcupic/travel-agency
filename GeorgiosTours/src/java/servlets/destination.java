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
import model.Hotel;
import model.Slika;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

@WebServlet(name = "destination", urlPatterns = {"/destination"})
public class destination extends HttpServlet {

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
                    + "<div class=\"omot\">"
                    + "<div id=\"menu\">"
                    + "<div class=\"padding\"> <a href=../choose><u>Početna</u></a> | <a href=/GeorgiosTours/choose/check_in_form><u>Čekiranje</u></a> | <a href=/GeorgiosTours/choose/kontakt.html><u>Kontakt</u></a> </div>"
                    + "</div>"
                    + "<div id=\"content\">");

            Session session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();

            try {
                int trazeniIdDestinacije = Integer.parseInt(request.getParameter("id_dest"));
                Destinacija nekaDestinacija = (Destinacija) session.get(Destinacija.class, trazeniIdDestinacije);
                if (nekaDestinacija != null) {

                    out.println("<h1>LETO 2017 - " + nekaDestinacija.getImeDestinacije() + "&nbsp(" + nekaDestinacija.getDrzava().getImeDrzave() + ")" + "</h1>");

                    out.print("<br /><h3><i>" +"\""+ nekaDestinacija.getOpisDestinacije() +"\""+ "</i></h3><br />");

                    Query q1 = session.createQuery("FROM Hotel WHERE destinacija.idDestinacije = :id");
                    q1.setParameter("id", nekaDestinacija.getIdDestinacije());
                    List<Hotel> hoteli = q1.list();

                    out.print("<h3><b><u>Ponuda Hotela</u>: </b></h3>");
                    for (Hotel hot : hoteli) {
                        out.print("<div class=\"hotel\">");
                        out.print("<a href='hotel?id_hotela=" + hot.getIdHotela() + "'style=\"text-decoration: none\">" + hot.getImeHotela() + "<br />");
                        for (int i = 1; i <= 5; i++) {
                            if (i <= hot.getZvezdice()) {
                                out.print("<img src=images/icons/puna.png>");
                            } else {
                                out.print("<img src=images/icons/prazna.png>");
                            }
                        }
                        out.print("<br />");
                        Query q2 = session.createQuery("FROM Slika WHERE hotel.idHotela= :id");
                        q2.setParameter("id", hot.getIdHotela());
                        List<Slika> slike = q2.list();
                        Slika prvaSlika = slike.get(0);
                        out.println("<img width=200 height=100 src='" + prvaSlika.getPutanjaSlike() + "'>");
                        out.print("</div>");
                    }

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
                    + "</div>"
                    + "<div id=\"footer\"> Copyright &copy; 2017 Georgios Tours - Design: Đorđije Čupić</div>"
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
