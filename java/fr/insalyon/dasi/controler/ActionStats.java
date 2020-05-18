package fr.insalyon.dasi.controler;

import fr.insalyon.dasi.metier.service.Service;
import javax.servlet.http.HttpServletRequest;

public class ActionStats extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Service service = new Service();
        request.setAttribute("nbConsultMed",service.nbConsultationParMedium());
        request.setAttribute("nbConsultEmp",service.nbConsultationParEmploye());
    }

}
