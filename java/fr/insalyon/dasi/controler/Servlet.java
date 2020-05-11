package fr.insalyon.dasi.controler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import fr.insalyon.dasi.view.JsonSerialisation;
import fr.insalyon.dasi.dao.JpaUtil;
import fr.insalyon.dasi.metier.modele.*;
import fr.insalyon.dasi.metier.service.Service;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;	
import java.util.Date;

/**
 *
 * @author antoi
 */
@WebServlet(name = "Servlet", urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        JpaUtil.destroy();
        super.destroy();
    }

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
        String action = request.getParameter("todo");

        if (action == null) {
            return;
        }

        /*
        Nom de requete :
            * getmediums.
                Pas de paramètre
            * authentificate.
                "mail" --> email entrée
                "password" --> mot de passe
            * getClientData
        
        Session :
            
        
         */
        Service service = new Service();
        JsonSerialisation jsonSerialisation = new JsonSerialisation();
        switch (action) {
            case "getmediums": //Acceuil : get medium list
                jsonSerialisation.serialise(service.getMediums(), response);
                break;
            case "authentificate": //Connexion : connect (mail, password)
                String mail = request.getParameter("mail");
                String password = request.getParameter("password");
                if (mail == null || password == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    break;
                }
                Utilisateur user = service.authentifierUtilisateur(mail, password);
                if (user == null) {
                    jsonSerialisation.result(false, response);
                } else {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                    jsonSerialisation.result(true, response);
                }
                break;
            case "getClientData":
                HttpSession session = request.getSession(false);
                if (session == null) { //pas de session créée au préalable
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    break;
                }
                user = (Utilisateur) session.getAttribute("user");
                if (user == null || !(user instanceof Client)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    break;
                }
                jsonSerialisation.serialiseClientData((Client) user, response);
                break;
                case "subscribe":
                String name = request.getParameter("nom");
                String firstname = request.getParameter("prenom");
                String date = request.getParameter("date");
                String numeroDeTelephone = request.getParameter("numeroDeTelephone");
                String adresse = request.getParameter("adresse");
                String email = request.getParameter("email");
                String mdp = request.getParameter("password");
                
                Date d = new Date();
                try{
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    d = simpleDateFormat.parse(date);
                }catch(Exception e) {
                    System.err.println(e);
                }
                
                Client client = new Client(email,name,firstname,numeroDeTelephone,mdp,d,adresse);
                Long idClient = service.inscrireClient(client);
                if(idClient == null){
                    jsonSerialisation.result(false, response);
                } else {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user",client);
                    jsonSerialisation.result(true, response);
                }
                
                break;
            case "getHistoric":
                HttpSession session = request.getSession(false);
                if (session == null) { //pas de session créée au préalable
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    break;
                }
                user = (Utilisateur) session.getAttribute("user");
                if (user == null || !(user instanceof Client)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    break;
                }
                System.out.println("******Client :"+user.toString());
                jsonSerialisation.serialiseHistoric(service.historiqueClient((Client) user), response);
                break;
            case "disconnect":
                session = request.getSession(false);
                if(session != null){
                    session.invalidate();
                }
                break;
                
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
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
