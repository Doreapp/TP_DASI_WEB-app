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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author antoi
 */
public class SerialisationListMediums extends Serialisation {

    @Override
    public void serialise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        JsonArray mediumsJson = new JsonArray();

        List<Medium> mediums = (List<Medium>) request.getAttribute("mediums");
        
        for (Medium medium : mediums) {
            JsonObject mediumJson = new JsonObject();

            mediumJson.addProperty("id", medium.getId());
            mediumJson.addProperty("nom", medium.getNom());
            mediumJson.addProperty("genre", medium.getGenre());
            mediumJson.addProperty("presentation", medium.getPresentation());

            mediumsJson.add(mediumJson);
        }

        container.add("mediums", mediumsJson);

        
        PrintWriter out = getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
    
}
