/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.Conversation;
import fr.insalyon.dasi.metier.modele.Client;
import fr.insalyon.dasi.metier.modele.Medium;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author antoi
 */
public class SerialisationConversation extends Serialisation {

    @Override
    public void serialise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        Conversation conversation = (Conversation) request.getAttribute("covnersation");
        System.out.println(conversation.toString());
        if (conversation == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        container.addProperty("date", simpleDateFormat.format(conversation.getDateConsultation()));

        //medium
        Medium m = conversation.getMedium();
        JsonObject medium = new JsonObject();
        medium.addProperty("id", m.getId());
        medium.addProperty("name", m.getNom());
        medium.addProperty("presentation", m.getPresentation());
        medium.addProperty("genre", m.getGenre());

        //client
        Client c = conversation.getClinet();
        JsonObject client = new JsonObject();
        client.addProperty("id", c.getId());
        client.addProperty("name", c.getNom());
        client.addProperty("firstname", c.getPrenom());
        client.addProperty("zodiaque_sign", c.getSigneZodiaque());
        client.addProperty("chinese_sign", c.getSigneChinois());
        client.addProperty("totem_animal", c.getAnimalTotem());
        client.addProperty("lucky_color", c.getCouleurBonheur());

        container.add("medium", medium);
        container.add("client", client);

        PrintWriter out = getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

}
