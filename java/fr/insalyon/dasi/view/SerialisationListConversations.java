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
import fr.insalyon.dasi.metier.modele.Conversation;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author antoi
 */
public class SerialisationListConversations extends Serialisation {

    @Override
    public void serialise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        JsonArray conversationsJson = new JsonArray();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<Conversation> conversations = (List<Conversation>) request.getAttribute("conversations");

        if (conversations == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        for (Conversation conv : conversations) {
            JsonObject conversationJson = new JsonObject();

            conversationJson.addProperty("idMedium", conv.getMedium().getId());
            conversationJson.addProperty("date", simpleDateFormat.format(conv.getDateConsultation()));
            conversationJson.addProperty("medium", (String) conv.getMedium().getNom());
            conversationJson.addProperty("comment", (String) conv.getCommentaire());
            conversationJson.addProperty("nomClient", (String) conv.getClinet().getNom());
            conversationJson.addProperty("prenomClient", (String) conv.getClinet().getPrenom());

            conversationsJson.add(conversationJson);
        }

        container.add("conversations", conversationsJson);

        PrintWriter out = getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }

}
