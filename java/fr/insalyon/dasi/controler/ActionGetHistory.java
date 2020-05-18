/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.controler;

import fr.insalyon.dasi.metier.modele.Utilisateur;
import fr.insalyon.dasi.metier.modele.Conversation;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author antoi
 */
public class ActionGetHistory extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) { //pas de session créée au préalable
            return;
        }
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        if (user == null || !(user instanceof Client)) {
            return;
        }

        Service service = new Service();

        List<Conversation> convs = service.historiqueClient((Client) user);

        request.setAttribute("conversations", convs);
    }

}
