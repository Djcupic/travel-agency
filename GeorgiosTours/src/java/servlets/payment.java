package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CenaSobe;
import model.Soba;
import model.CenaSobeId;
import model.Hotel;
import model.RezervisanaSoba;
import model.Transport;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import util.HibernateUtil;

@WebServlet(name = "payment", urlPatterns = {"/payment"})
public class payment extends HttpServlet {

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
                if (!(request.getParameter("datumdolaska") == null) && !(request.getParameter("datumodlaska") == null) && !(request.getParameter("datumdolaska").isEmpty()) && !(request.getParameter("datumodlaska").isEmpty()) && !(request.getParameter("izabran_tip") == null) && !(request.getParameter("izabran_tip").isEmpty()) && !(request.getParameter("izabran_prevoz") == null) && !(request.getParameter("izabran_prevoz").isEmpty())) {
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

                        String startDate = request.getParameter("datumdolaska");
                        String endDate = request.getParameter("datumodlaska");

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date pocetakRezervacije = sdf.parse(startDate);
                        Date krajRezervacije = sdf.parse(endDate);

                        String date1 = sdf.format(pocetakRezervacije);
                        String date2 = sdf.format(krajRezervacije);

                        Date currentDate = new Date();
                        LocalDateTime localDateTime = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault());
                        Date sadasnji = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

                        if (krajRezervacije.after(pocetakRezervacije) && pocetakRezervacije.after(sadasnji)) {
                            out.print("Datum dolaska:  " + date1);
                            out.print("<br /><br />");
                            out.print("Datum odlaska:  " + date2);
                            out.print("<br /><br />");
                        } else {
                            out.print("<h3 style=color:red>Izabran datum dolaska mora biti nakon danasnjeg dana, a pre dana odlaska,<br /><br /> molimo birajte ponovo datume!</h3>");
                            out.println("<br />\n"
                                    + "  </div><div id=\"footer\"> Copyright &copy; 2017 Georgios Tours - Design: Djordjije Cupic</div>\n"
                                    + "</div>\n"
                                    + "</body>\n"
                                    + "</html>");
                            return;
                        }
                        out.print("<br/>");
                        
                        
                        
                        int idTipaSobe = Integer.parseInt(request.getParameter("izabran_tip"));                        
                        Query qsoba = session.createQuery("FROM Soba WHERE hotel.idHotela =" + nekiHotel.getIdHotela() + " AND tipSobe.idTipaSobe =" + idTipaSobe + "");
                        List<Soba> sobe = qsoba.list();
                        if (sobe.isEmpty()) {
                            out.print("<h3 style=color:red>Sve sobe koje su za " + idTipaSobe + " osobu/e u nasem hotelu su za izabrani datum pune,<br /><br /> molimo probajte u drugom terminu!</h3>");
                            return;
                        }
                        out.print("Soba: ");
                        switch (idTipaSobe) {
                            case 1: {
                                out.print("jednokrevetna");
                                break;
                            }
                            case 2: {
                                out.print("dvokrevetna");
                                break;
                            }
                            case 3: {
                                out.print("trokrevetna");
                                break;
                            }
                            case 4: {
                                out.print("cetvorekrevetna");
                                break;
                            }
                            case 5: {
                                out.print("petokrevetna");
                                break;
                            }
                        }
                        out.print("<br/><br/>");

                        int idPrevoza = Integer.parseInt(request.getParameter("izabran_prevoz"));
                        out.print("Prevoz: ");
                        switch (idPrevoza) {
                            case 1: {
                                out.print(" sopstvena rezija");
                                break;
                            }
                            case 2: {
                                out.print(" autobus");
                                break;
                            }
                            case 3: {
                                out.print(" avion");
                                break;
                            }
                        }
                        out.print("<br/><br/>");

                        Query qcena = session.createQuery("FROM CenaSobe WHERE hotel.idHotela =" + trazeniIdHotela + " AND tipSobe.idTipaSobe=" + idTipaSobe + "");
                        List<CenaSobe> cene = qcena.list();
                        double cenaPoNocenju = cene.get(0).getCenaPoNocenju();
                        //  out.print((int)((krajRezervacije.getTime()-pocetakRezervacije.getTime())/(1000*60*60*24)));  zanimljivo rešenje
                        DateTime pocetakJoda = new DateTime(pocetakRezervacije);
                        DateTime krajJoda = new DateTime(krajRezervacije);

                        Days razlika = Days.daysBetween(pocetakJoda, krajJoda);
                        int razlika_u_danima = razlika.getDays();

                        Query qcenatr = session.createQuery("FROM Transport WHERE prevoznoSredstvo.idPrevoza=" + idPrevoza + " AND destinacija.idDestinacije = :id_destinacije");
                        qcenatr.setParameter("id_destinacije", nekiHotel.getDestinacija().getIdDestinacije());
                        List<Transport> trans = qcenatr.list();
                        double cenaTransporta = trans.get(0).getCenaTransporta();

                        out.print("<br /><b>*Ukupna cena aranzmana: ");
                        double cenaAranzmana = (cenaPoNocenju * razlika_u_danima) + (cenaTransporta * idTipaSobe);
                        out.print("<font color=green>"+Math.round(cenaAranzmana)+"</font>" + "</b><img src=images/icons/evro_zeleni.png width=15 height=15>");
                        out.print("<hr style=\"width:35%\">");                        
                        
                       
                        out.print("<div class=\"tabela\"><table align=\"center\">");
                        out.print("<form method=\"post\" action=\"reservation?id_hotela=" + nekiHotel.getIdHotela() + "&tip=" + idTipaSobe + "&date1=" + date1 + "&date2=" + date2 + "&cena=" + cenaAranzmana + "\">");
                        out.print("<tr><td colspan=\"2\" style=\"text-align:center\"><h2>Unesite podatke: </h2></td></tr>");
                        out.print("<tr><td>Ime i prezime :</td><td> <input type=\"text\" name=\"ime_i_prezime\"/></td></tr>");
                        out.print("<tr><td>Broj pasosa: </td><td><input type=\"text\" name=\"broj_pasosa\"/></td></tr>");
                        out.print("<tr><td>Kontakt telefon: </td><td><input type=\"text\" name=\"telefon\"/></td></tr>");
                        out.print("<tr><td>Email: </td><td><input type=\"text\" name=\"email\"/></td></tr>");

                        out.print("<tr><td colspan=\"2\" style=\"text-align:center\"><br/><h2>Placanje: </h2></td></tr>");
                        out.print("<tr><td><div>Izaberite karticu:</td>");
                        out.print("<td><input value=\"American Express\" type=\"radio\" name=\"tip_kartice\"/><img src=images/icons/americanexpress.png>&nbsp&nbsp&nbsp");  
                        out.print("<input value=\"Maestro\" type=\"radio\" name=\"tip_kartice\"/><img src=images/icons/maestro.png>&nbsp&nbsp&nbsp");
                        out.print("<input value=\"MasterCard\" type=\"radio\" name=\"tip_kartice\"/><img src=images/icons/mastercard.png>&nbsp&nbsp&nbsp");
                        out.print("<input value=\"Visa\" type=\"radio\" name=\"tip_kartice\"/><img src=images/icons/visa.png><br /></div>");
                        out.print("</td></tr></tr><tr><td>Broj kartice: </td><td><input type=\"password\" name=\"broj_kartice\"/></td></tr>");
                        out.print("<tr><td>Ime i prezime na kartici: </td><td><input type=\"text\" name=\"ime_i_prezime_na_kartici\"/></td></tr>");
                        out.print("<tr><td>Datum isteka kartice: </td><td><input type=\"text\" name=\"datum_isteka\"/></td></tr>");
                        out.print("<tr><td>CVC: </td><td><input type=\"text\" name=\"cvc\"/></td></tr>");
                        out.print("</table></div>");
                        out.print("<br/>");
                        out.print("<input type=\"submit\" value=\"Plati\"/>");
                        out.print("</form>");

                    } else {
                        response.getWriter().write("<h1>Trenutno nemamo u ponudi.</h1>");
                        return;
                    }

                } else {
                    out.print("<h3 style=color:red>Datumi dolaska i odlaska, broj osoba i nacin transporta moraju biti izabrani,<br /><br /> molimo vas izaberite sve ponovo!</h3>");

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
