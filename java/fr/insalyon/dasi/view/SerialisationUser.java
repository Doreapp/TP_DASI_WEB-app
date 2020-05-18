/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Utilisateur;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Employe;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author antoi
 */
public class SerialisationUser extends Serialisation {

    @Override
    public void serialise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        Utilisateur user = (Utilisateur) request.getAttribute("user");
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        if (user instanceof Client) {
            Client client = (Client) user;
            container.addProperty("type", "client");
            container.addProperty("id", client.getId());
            container.addProperty("name", client.getNom());
            container.addProperty("firstname", client.getPrenom());
            container.addProperty("zodiaque_sign", client.getSigneZodiaque());
            container.addProperty("chinese_sign", client.getSigneChinois());
            container.addProperty("totem_animal", client.getAnimalTotem());
            container.addProperty("lucky_color", client.getCouleurBonheur());
            container.addProperty("adress", client.getAdressePostale());

        } else if (user instanceof Employe) {
            Employe employe = (Employe) user;
            container.addProperty("type", "employe");
            container.addProperty("id", employe.getId());
            container.addProperty("name", employe.getNom());
            container.addProperty("firstname", employe.getPrenom());
            container.addProperty("gender", employe.getGenre());
            container.addProperty("nb_consult", employe.getNbConsultation());
            container.addProperty("phone_number", employe.getNumeroDeTelephone());
        }

        PrintWriter out = getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

}
