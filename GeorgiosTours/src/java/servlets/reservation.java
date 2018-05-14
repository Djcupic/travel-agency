package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Aranzman;
import model.Hotel;
import model.Soba;
import model.KreditnaKartica;
import model.Putnik;
import model.RezervisanaSoba;
import model.RezervisanaSobaId;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

@WebServlet(name = "reservation", urlPatterns = {"/reservation"})
public class reservation extends HttpServlet {

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

            if (request.getParameter("ime_i_prezime").isEmpty() || request.getParameter("broj_pasosa").isEmpty() || request.getParameter("telefon").isEmpty() || request.getParameter("email").isEmpty() || request.getParameter("tip_kartice") == null || request.getParameter("broj_kartice").isEmpty() || request.getParameter("ime_i_prezime_na_kartici").isEmpty() || request.getParameter("datum_isteka").isEmpty() || request.getParameter("cvc").isEmpty()) {
                out.print("<h3 style=color:red>Sva polja su obavezna, molimo popunite opet!</h3>");
                return;
            } else {

                int idHotela = Integer.parseInt(request.getParameter("id_hotela"));
                Hotel nekiHotel = (Hotel) session.get(Hotel.class, idHotela);
                int idTipaSobe = Integer.parseInt(request.getParameter("tip"));

                String pocetakRezervacije = request.getParameter("date1");
                String krajRezervacije = request.getParameter("date2");
                double cenaAranzmana = Double.parseDouble(request.getParameter("cena"));

                try {
                    double stanjeNaKartici = (double) (Math.random() * 2000);       // simulacija iznosa novca na kartici
                    if (stanjeNaKartici < cenaAranzmana) {
                        out.print("<h3 style=color:red>Nemate dovoljno novca na kartici, molimo izaberite jeftiniji aranzman <br /><br /> ili dopunite svoju karticu!</h3>");
                        return;
                    } else {

                        stanjeNaKartici -= cenaAranzmana;

                        Query qsoba = session.createQuery("FROM Soba WHERE hotel.idHotela =" + nekiHotel.getIdHotela() + " AND tipSobe.idTipaSobe =" + idTipaSobe + "");
                        List<Soba> sobe = qsoba.list();
                        Soba soba = sobe.get((int)(Math.random()*sobe.size()));     // simulacija dodeljivanja sobe
                                                

                        String sifraRezervacije = "SR0" + ((int) (((Math.random()) * 20000) + 1000));  // simulacija dodeljivanja sifre rezervacije

                        KreditnaKartica k = new KreditnaKartica();
                        k.setBrojKartice(request.getParameter("broj_kartice"));
                        k.setTipKartice(request.getParameter("tip_kartice"));
                        k.setImeIPrezimeNaKartici(request.getParameter("ime_i_prezime_na_kartici"));
                        k.setDatumIsteka(request.getParameter("datum_isteka"));
                        k.setCvc(request.getParameter("cvc"));
                        k.setStanjeNaKartici(stanjeNaKartici);

                        Putnik p = new Putnik();
                        p.setBrojPasosa(request.getParameter("broj_pasosa"));
                        p.setImeIPrezimePutnika(request.getParameter("ime_i_prezime"));
                        p.setKontaktTelefon(request.getParameter("telefon"));
                        p.setEmail(request.getParameter("email"));
                        p.setKreditnaKartica(k);

                        Aranzman a = new Aranzman();
                        a.setSifraRezervacije(sifraRezervacije);
                        a.setPutnik(p);
                        a.setCenaAranzmana(cenaAranzmana);  
                                                
                        session.save(k);
                        session.save(p);                        
                        session.save(a); 
                        
                        RezervisanaSobaId rsid = new RezervisanaSobaId(); 
                        rsid.setIdRezervacije(a.getIdRezervacije());
                        rsid.setIdSobe(soba.getIdSobe());
                        
                        RezervisanaSoba rs = new RezervisanaSoba();                                                 
                        rs.setId(rsid);                        
                        rs.setPocetakRezervacije(pocetakRezervacije);
                        rs.setKrajRezervacije(krajRezervacije);
                                                    
                        session.save(rs);
                        
                        out.print("<b>POŠTOVANI/POŠTOVANA</b>,<br /><br /><br /> Uspešno ste rezervisali sobu u hotelu: &nbsp<b>" + nekiHotel.getImeHotela() + " (<i>" + nekiHotel.getDestinacija().getImeDestinacije() + "," + nekiHotel.getDestinacija().getDrzava().getImeDrzave() + "</i>)</b><br/>");
                        out.print("<br/>");
                        out.print("Broj sobe: &nbsp<b>" + soba.getBrojSobe() + "</b><br/>");
                        out.print("<br/>");
                        out.print("Rezervacija na ime(i prezime): &nbsp<b>" + p.getImeIPrezimePutnika() + "</b><br/>");
                        out.print("<br/>");
                        out.print("Datum rezervacije: &nbsp<b>" + pocetakRezervacije + "</b> do <b>" + krajRezervacije + "</b><br/>");
                        out.print("<br/>");
                        out.print("Tip sobe: &nbsp<b>" + soba.getTipSobe().getTipSobe() + "</b><br/>");
                        out.print("<br/>");
                        out.print("<h3><u>Šifra rezervacije</u>: &nbsp</br>" + "</br></br><div style=color:green><b>" + a.getSifraRezervacije() + "</b></div></h3><br/>");
                    }

                } catch (Exception ex) {
                    out.print("<h1>Oops! Something went wrong.</h1>");
                    out.print(ex.getMessage());
                }

            }

            out.println("</body>");
            out.println("</html>");

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
