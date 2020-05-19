/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.controler;

import fr.insalyon.dasi.metier.modele.Utilisateur;
import fr.insalyon.dasi.metier.modele.Conversation;
import fr.insalyon.dasi.metier.modele.Employe;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author antoi
 */
public class ActionGetEmployeConversations extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) { //pas de session créée au préalable
            System.out.println("session null");
            return;
        }
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        if (user == null || !(user instanceof Employe)) {
            System.out.println("user null");
            return;
        }
        Service service = new Service();
        List<Conversation> conversations
                = service.rechercherConversationPourEmploye((Employe) user);

        request.setAttribute("conversations", conversations);

    }

}
