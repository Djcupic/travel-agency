package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CenaSobe;
import model.Destinacija;
import model.Drzava;
import model.Hotel;
import model.Slika;
import model.Soba;
import model.Transport;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

@WebServlet(name = "hotel", urlPatterns = {"/hotel"})
public class hotel extends HttpServlet {

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
                int trazeniIdHotela = Integer.parseInt(request.getParameter("id_hotela"));
                Hotel nekiHotel = (Hotel) session.get(Hotel.class, trazeniIdHotela);
                if (nekiHotel != null) {

                    out.print("<h2>" + nekiHotel.getImeHotela().toUpperCase() + " ");

                    for (int i = 1; i <= 5; i++) {
                        if (i <= nekiHotel.getZvezdice()) {
                            out.print("<img src=images/icons/velika_puna.png>");
                        } else {
                            out.print("<img src=images/icons/velika_prazna.png>");
                        }
                    }
                    out.print("</br><div>" + "<i>" + "(" + nekiHotel.getDestinacija().getImeDestinacije() + ", " + nekiHotel.getDestinacija().getDrzava().getImeDrzave() + ")" + "</i>" + "</div>");
                    out.print("</h2>");

                    out.print("<h3><i>\"" + nekiHotel.getOpisHotela() + "\"</i></h3></br></br>");

                    Query qcena = session.createQuery("FROM CenaSobe WHERE hotel.idHotela= :id_hotela");
                    qcena.setParameter("id_hotela", nekiHotel.getIdHotela());
                    List<CenaSobe> cene = qcena.list();
                    for (CenaSobe cs : cene) {
                        out.print("<b>*cena/noć - " + cs.getTipSobe().getTipSobe() + " soba:  " + Math.round(cs.getCenaPoNocenju()) + "</b><img src=images/icons/evro_plavi.png width=15 height=15></br></br>");
                    }
                    out.print("<hr style=\"width:40%\">");
                    out.print("<br/></br>");

                    Query qtrans = session.createQuery("FROM Transport WHERE destinacija.idDestinacije = :id_destinacije");
                    qtrans.setParameter("id_destinacije", nekiHotel.getDestinacija().getIdDestinacije());
                    List<Transport> trans = qtrans.list();
                    for (Transport tr : trans) {
                        out.print("<b>*" + tr.getPrevoznoSredstvo().getNacinPrevoza() + " - " + Math.round(tr.getCenaTransporta()) + "</b><img src=images/icons/evro_plavi.png width=15 height=15></br></br>");
                    }
                    out.print("<br/>");

                    out.print("<form method=\"post\" action=\"book?id_hotela=" + nekiHotel.getIdHotela() + "\">");
                    out.print("<input type=\"submit\" value=\"Dalje\"/>");
                    out.print("</form>");
                    
                    out.print("<br />");

                    Query qslike = session.createQuery("FROM Slika WHERE hotel.idHotela= :id_hotela");
                    qslike.setParameter("id_hotela", nekiHotel.getIdHotela());
                    List<Slika> slike = qslike.list();
                    for (Slika sl : slike) {
                        out.print("<div class=\"img-position\"><img class=\"img-size\" src='" + sl.getPutanjaSlike() + "'></a><br/></div>");
                    }
                    
                    out.print("<br/>");

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
