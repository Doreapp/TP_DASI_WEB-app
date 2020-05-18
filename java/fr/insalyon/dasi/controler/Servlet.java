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
import fr.insalyon.dasi.view.SerialisationConversation;
import fr.insalyon.dasi.view.SerialisationListConversations;
import fr.insalyon.dasi.view.SerialisationListMediums;
import fr.insalyon.dasi.view.SerialisationStatus;
import fr.insalyon.dasi.view.SerialisationUser;
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
        switch (action) {
            case "getmediums": //Acceuil : get medium list
                new ActionGetMediums().execute(request);
                new SerialisationListMediums().serialise(request, response);
                break;

            case "authentificate": //Connexion : connect (mail, password)
                new ActionAuthentificate().execute(request);
                new SerialisationUser().serialise(request, response);
                break;

            case "getClientData":
                new ActionGetClientData().execute(request);
                new SerialisationUser().serialise(request, response);
                break;

            case "subscribe":
                new ActionSubscribe().execute(request);
                new SerialisationUser().serialise(request, response);
                break;

            case "getHistoric":
                new ActionGetHistory().execute(request);
                new SerialisationListConversations().serialise(request, response);
                break;

            case "disconnect":
                new ActionDisconnect().execute(request);
                new SerialisationStatus().serialise(request, response);
                break;

            case "rechercherConversationPourEmploye":
                new ActionGetEmployeConversations().execute(request);
                new SerialisationListConversations().serialise(request, response);
                break;

            case "getConsultationData":
                new ActionGetConversationData().execute(request);
                new SerialisationConversation().serialise(request, response);
                break;

            case "getClientHistory":
                new ActionGetClientHistory().execute(request);
                new SerialisationListConversations().serialise(request, response);
                break;

            case "startConversation":
                new ActionStartConversation().execute(request);
                new SerialisationStatus().serialise(request, response);
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
