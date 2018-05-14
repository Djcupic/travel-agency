package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Aranzman;
import model.RezervisanaSoba;
import model.RezervisanaSobaId;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

@WebServlet(name = "check_in", urlPatterns = {"/check_in"})
public class check_in extends HttpServlet {

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
                    + "<div class=\"padding\"> <a href=../choose><u>Početna</u></a> | <a href=GeorgiosTours/choose/check_in_form><u>Čekiranje</u></a> | <a href=/GeorgiosTours/choose/kontakt.html><u>Kontakt</u></a> </div>"
                    + "</div>"
                    + "<div id=\"content\">");

            Session session = HibernateUtil.getSessionFactory().openSession();

            session.beginTransaction();

            try {
                
                String sifra_rez = request.getParameter("sifra_rezervacije");
                
                Query qar = session.createQuery("FROM Aranzman WHERE sifraRezervacije LIKE '" + sifra_rez + "' ");
                List<Aranzman> aranzmani = qar.list();               
                if (aranzmani.isEmpty() || aranzmani == null) {
                    out.print("<h3 style=color:red>Pogresno ste uneli sifru, molimo unesite ponovo!<h3/>");
                } else {
                    for (Aranzman ar : aranzmani) {
                        out.print("<h3><div style=color:green><b>" + sifra_rez + "</b></div></h3></br>");
                        out.print("Rezervacija na ime(i prezime): &nbsp<b>" + ar.getPutnik().getImeIPrezimePutnika() + "</b><br/><br/><br/>");
                        Query qrezsoba = session.createQuery("FROM RezervisanaSoba WHERE aranzman = "+ar.getIdRezervacije()+"");
                        List<RezervisanaSoba> rezsobe = qrezsoba.list();
                        for(RezervisanaSoba rs : rezsobe){
                            out.println("Hotel: &nbsp<b>" +  rs.getSoba().getHotel().getImeHotela().toUpperCase() + "</b>&nbsp");           
                        for (int i = 1; i <= 5; i++) {
                            if (i <= rs.getSoba().getHotel().getZvezdice()) {
                                out.print("<img src=images/icons/puna.png>");
                            } else {
                                out.print("<img src=images/icons/prazna.png>");
                            }
                        }
                        out.print("<br/><br/>");
                        out.print("<b><i>(" + rs.getSoba().getHotel().getDestinacija().getImeDestinacije() + ", " + rs.getSoba().getHotel().getDestinacija().getDrzava().getImeDrzave() + ")</i></b><br/><br/>");
                        out.print("Broj sobe: &nbsp<b>" + rs.getSoba().getBrojSobe() + "</b><br/><br/>");
                        out.print("Tip sobe: &nbsp<b>" + rs.getSoba().getTipSobe().getTipSobe() + "</b><br/><br/>");
                        out.print("Datum rezervacije: &nbsp<b>" + rs.getPocetakRezervacije() + "</b> do <b>" + rs.getKrajRezervacije() + "</b><br/><br/>");
                        }
                    }
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
