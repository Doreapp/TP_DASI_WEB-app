/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;

/**
 *
 * @author antoi
 */
public class JsonSerialisation {

    public void serialise(List<Medium> mediums, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        JsonArray mediumsJson = new JsonArray();

        for (Medium medium : mediums) {
            JsonObject mediumJson = new JsonObject();

            mediumJson.addProperty("id", medium.getId());
            mediumJson.addProperty("nom", medium.getNom());
            mediumJson.addProperty("genre", medium.getGenre());
            mediumJson.addProperty("presentation", medium.getPresentation());

            mediumsJson.add(mediumJson);
        }

        container.add("mediums", mediumsJson);

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

    public void result(boolean done, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        int status = done ? 0 : 1;
        container.addProperty("status", status);

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

    public void serialiseClientData(Client client, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        /*
        
                session.setAttribute("name", client.getNom());
                session.setAttribute("firstname", client.getPrenom());
                session.setAttribute("zodiaque_sign", client.getSigneZodiaque());
                session.setAttribute("chinese_sign", client.getSigneChinois());
                session.setAttribute("totem_animal", client.getAnimalTotem());
                session.setAttribute("lucky_color", client.getCouleurBonheur());
         */
        container.addProperty("name", client.getNom());
        container.addProperty("firstname", client.getPrenom());
        container.addProperty("zodiaque_sign", client.getSigneZodiaque());
        container.addProperty("chinese_sign", client.getSigneChinois());
        container.addProperty("totem_animal", client.getAnimalTotem());
        container.addProperty("lucky_color", client.getCouleurBonheur());

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

    public void serialiseHistoric(List<Conversation> conversations, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        JsonArray conversationsJson = new JsonArray();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (Conversation conv : conversations) {
            JsonObject conversationJson = new JsonObject();

            conversationJson.addProperty("idMedium", conv.getMedium().getId());
            conversationJson.addProperty("date", simpleDateFormat.format(conv.getDateConsultation()));
            conversationJson.addProperty("medium", (String) conv.getMedium().getNom());
            conversationJson.addProperty("comment", (String) conv.getCommentaire());

            conversationsJson.add(conversationJson);
        }

        container.add("conversations", conversationsJson);

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
    
    public void serialiseConversationPourEmploye(List<Conversation> conversations, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        
        JsonArray conversationsJson = new JsonArray();
        for(Conversation conv : conversations){
            JsonObject conversationJson = new JsonObject();

            conversationJson.addProperty("id", conv.getId());
            conversationJson.addProperty("nomClient", (String)conv.getClinet().getNom());
            conversationJson.addProperty("prenomClient", (String)conv.getClinet().getPrenom());
            conversationJson.addProperty("medium", (String)conv.getMedium().getNom());
            
            conversationsJson.add(conversationJson);
        }
        
        container.add("conversations", conversationsJson);
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

    public void serialiseConversation(Conversation conversation, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

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
        
        container.add("medium",medium);
        container.add("client",client);

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
}
