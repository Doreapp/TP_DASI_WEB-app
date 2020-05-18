/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.controler;

import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author antoi
 */
public class ActionGetHelp extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        String loveRange, healthRange, workRange;
        loveRange = request.getParameter("love");
        healthRange = request.getParameter("health");
        workRange = request.getParameter("work");
        String clientId = request.getParameter("clientId");

        if (loveRange == null || healthRange == null || workRange == null) {
            return;
        }

        int love = 1, health = 1, work = 1;
        long id;
        try {
            love = Integer.parseInt(loveRange);
            health = Integer.parseInt(healthRange);
            work = Integer.parseInt(workRange);
            id = Long.parseLong(clientId);
        } catch (NumberFormatException e) {
            return;
        }

        Service service = new Service();

        Client c = service.getClientParId(id);

        List<String> predictions = service.genererPredictions(c.getCouleurBonheur(), c.getAnimalTotem(),
                love, health, work);

        request.setAttribute("predictions", predictions);
    }

}
