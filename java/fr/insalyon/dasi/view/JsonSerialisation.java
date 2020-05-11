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
import fr.insalyon.dasi.metier.modele.Medium;
import fr.insalyon.dasi.metier.modele.Conversation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author antoi
 */
public class JsonSerialisation  {
    
    public void serialise(List<Medium> mediums, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        
        JsonArray mediumsJson = new JsonArray();
        
        for(Medium medium : mediums){
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

    public void serialiseHistoric(List<Conversation> conversations, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        
        JsonArray conversationsJson = new JsonArray();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        System.out.println("=========================Historique");
        for(Conversation conv : conversations){
            JsonObject conversationJson = new JsonObject();
           
            System.out.println("=========================Historique1");

            conversationJson.addProperty("id", conv.getMedium().getId());
            conversationJson.addProperty("date", simpleDateFormat.format(conv.getDateConsultation()));
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
}
