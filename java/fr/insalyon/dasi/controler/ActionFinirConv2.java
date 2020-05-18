package fr.insalyon.dasi.controler;

import fr.insalyon.dasi.metier.modele.Conversation;
import fr.insalyon.dasi.metier.service.Service;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author antoi
 */
public class ActionFinirConv2 extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Service service = new Service();
        Conversation c = service.getConversationParId(Long.parseLong(request.getParameter("idConv")));
        if(c == null)
            return;
        request.setAttribute("conversation",c);
    }

}
