package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CenaSobe;
import model.Drzava;
import model.Hotel;
import model.Slika;
import model.Soba;
import model.TipSobe;
import model.Transport;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import util.HibernateUtil;

@WebServlet(name = "book", urlPatterns = {"/book"})
public class book extends HttpServlet {

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
                    out.print("</br>");

                    Query q = session.createQuery("FROM Slika WHERE hotel.idHotela= :id_hotela");
                    q.setParameter("id_hotela", nekiHotel.getIdHotela());
                    List<Slika> slike = q.list();
                    Slika prvaSlika = slike.get(0);
                    out.println("<img width=350 height=200 src='" + prvaSlika.getPutanjaSlike() + "'>");
                    out.print("</br></br>");

                    out.print("<form method=\"post\" action=\"payment?id_hotela=" + nekiHotel.getIdHotela() + "\">");

                    String startDate = null;
                    String endDate = null;
                    out.print("<h3>*Izaberite datume dolaska i odlaska: </h3>");
                    out.println("<input placeholder=\"datum dolaska\" class=\"textbox-n\" type=\"text\" onfocus=\"(this.type='date')\" onblur=\"(this.type='text')\" name=\"datumdolaska\">&nbsp&nbsp");
                    out.println("<input placeholder=\"datum odlaska\" class=\"textbox-n\" type=\"text\" onfocus=\"(this.type='date')\" onblur=\"(this.type='text')\" name=\"datumodlaska\">");
                    out.print("<br/><br/><br/>");

                    Query qtip = session.createQuery("FROM TipSobe");
                    List<TipSobe> tipovi = qtip.list();
                    out.print("<b>*Broj osoba: <select name=izabran_tip>");
                    out.print("<option disabled selected>*izaberite*</option>");
                    for(int i=1;i<=tipovi.size();i++){
                        out.print("<option value='" + i + "'>" + i + "</option>");
                    }
                    out.print("</select></h3></b><br /><br />");

                    Query qprevoz = session.createQuery("FROM Transport WHERE destinacija.idDestinacije = :id_destinacije");
                    qprevoz.setParameter("id_destinacije", nekiHotel.getDestinacija().getIdDestinacije());
                    List<Transport> trans = qprevoz.list();
                    out.print("<b>*Prevoz: <select name=izabran_prevoz>");
                    out.print("<option disabled selected>*izaberite*</option>");
                    for (Transport t : trans) {
                        out.print("<option value='" + t.getPrevoznoSredstvo().getIdPrevoza() + "'>" + t.getPrevoznoSredstvo().getNacinPrevoza() + "</option>");
                    }
                    out.print("</select></b><br />");

                    out.print("<input type=\"submit\" value=\"Rezervisi\"/>");
                    out.print("</form>");
                    out.print("<br/>");

                } else {
                    response.getWriter().write("<h1>Trenutno nemamo u ponudi.</h1>");
                    return;
                }

            } catch (Exception ex) {
                out.print("<h1>Oops! Something went wrong.</h1>");
                out.print(ex.getMessage());
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
