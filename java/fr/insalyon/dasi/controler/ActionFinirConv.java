package fr.insalyon.dasi.controler;

import fr.insalyon.dasi.metier.modele.Conversation;
import fr.insalyon.dasi.metier.service.Service;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author antoi
 */
public class ActionFinirConv extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Service service = new Service();
        Conversation c = service.getConversationParId(Long.parseLong(request.getParameter("idConv")));
        if(c == null)
            return;
        service.finirConversation(c,request.getParameter("commentaire"));
    }
}
