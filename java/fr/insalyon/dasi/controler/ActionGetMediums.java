/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.controler;

import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.service.Service;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author antoi
 */
public class ActionGetMediums extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Service service = new Service();
        List<Medium> mediums = service.getMediums();
        
        request.setAttribute("mediums", mediums);
    }
    
}
