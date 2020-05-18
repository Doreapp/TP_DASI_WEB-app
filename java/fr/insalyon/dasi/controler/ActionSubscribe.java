/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.controler;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.service.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author antoi
 */
public class ActionSubscribe extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        String name = request.getParameter("nom");
        String firstname = request.getParameter("prenom");
        String date = request.getParameter("date");
        String numeroDeTelephone = request.getParameter("numeroDeTelephone");
        String adresse = request.getParameter("adresse");
        String email = request.getParameter("email");
        String mdp = request.getParameter("password");

        Date d = new Date();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            d = simpleDateFormat.parse(date);
        } catch (Exception e) {
            System.err.println(e);
        }
        
        Service service = new Service();

        Client client = new Client(email, name, firstname, numeroDeTelephone, mdp, d, adresse);
        Long idClient = service.inscrireClient(client);
        if (idClient == null) {
            return;
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", client);
            request.setAttribute("user", client);
        }
    }

}
