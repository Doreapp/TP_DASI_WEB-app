/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.controler;

import fr.insalyon.dasi.metier.modele.Utilisateur;
import fr.insalyon.dasi.metier.service.Service;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author antoi
 */
public class ActionAuthentificate extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        String mail = request.getParameter("mail");
        String password = request.getParameter("password");
        Utilisateur user = null;
        
        if (mail != null && password != null) {
            Service service = new Service();
            user = service.authentifierUtilisateur(mail, password);
        }
        
        request.setAttribute("user", user);
    }

}
