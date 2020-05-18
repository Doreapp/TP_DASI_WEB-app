/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.controler;

import fr.insalyon.dasi.metier.modele.Conversation;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author antoi
 */
public class ActionCreateConversation extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        System.out.println("crerr");
        HttpSession session = request.getSession(false);
        if (session == null) { //pas de session créée au préalable
            return;
        }
        Client cli = (Client) session.getAttribute("user");
        if (cli == null) {
            return;
        }
        String param = request.getParameter("id");
        if (param == null) {
            return;
        }
        
        Service service = new Service();
        
        Medium medium = service.getMediumParId(Long.parseLong(param));
        if (medium == null) {
            return;
        }
        System.out.println("crerr 2");
        Conversation c = service.creerConversation(cli, medium);

        System.out.println("crerr 3 : "+c);
        request.setAttribute("status", 0);
    }

}
