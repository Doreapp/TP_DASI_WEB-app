/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.controler;

import fr.insalyon.dasi.metier.modele.Conversation;
import fr.insalyon.dasi.metier.service.Service;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author antoi
 */
public class ActionGetConversationData extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        String param = request.getParameter("id");
        if (param == null) {
            return;
        }
        long id;
        try {
            id = Long.parseLong(param);
        } catch (NumberFormatException e) {
            return;
        }
        Service service = new Service();
        Conversation conv = service.getConversationParId(id);

        request.setAttribute("conversation", conv);
    }

}
