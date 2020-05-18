package fr.insalyon.dasi.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.insalyon.dasi.metier.modele.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javafx.util.Pair;

public class SerialisationStats extends Serialisation {

    @Override
    public void serialise(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();

        JsonArray mediumsJson = new JsonArray();

        List<Pair<Medium, Long>> nbConsultMed = (List<Pair<Medium, Long>>) request.getAttribute("nbConsultMed");
        
        for (Pair<Medium, Long> pair : nbConsultMed) {
            JsonObject mediumJson = new JsonObject();

            mediumJson.addProperty("id", pair.getKey().getId());
            mediumJson.addProperty("nom", pair.getKey().getNom());
            mediumJson.addProperty("type", pair.getKey().getClass().toString().substring(pair.getKey().getClass().toString().lastIndexOf(".")+1));
            mediumJson.addProperty("rang", pair.getValue());

            mediumsJson.add(mediumJson);
        }
        container.add("mediums", mediumsJson);

        JsonArray employesJson = new JsonArray();

        List<Pair<Employe, Long>> nbConsultEmp = (List<Pair<Employe, Long>>) request.getAttribute("nbConsultEmp");
        
        for (Pair<Employe, Long> pair : nbConsultEmp) {
            JsonObject employeJson = new JsonObject();

            employeJson.addProperty("id", pair.getKey().getId());
            employeJson.addProperty("nom", pair.getKey().getNom());
            employeJson.addProperty("prenom", pair.getKey().getPrenom());
            employeJson.addProperty("rang", pair.getValue());

            employesJson.add(employeJson);
        }
        container.add("employes", employesJson);
        
        PrintWriter out = getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
    
}