/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.controler;

import fr.insalyon.dasi.metier.modele.Conversation;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author antoi
 */
public class ActionStartConversation extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        String param = request.getParameter("id");
        if (param == null) {
            return;
        }
        long id = 0;
        try {
            id = Long.parseLong(param);
        } catch (NumberFormatException e) {
            return;
        }

        Service service = new Service();
        Conversation conv = service.getConversationParId(id);
        if (conv == null) {
            return;
        }

        service.commencerConversation(conv);

        request.setAttribute("status", 0);
    }

}
