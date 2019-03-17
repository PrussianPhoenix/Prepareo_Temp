package com.example.Prepareo_Owl_to_Mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.BulkWriteOptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;



public class JSON_TO_MONGO_MOBILE
{
    public static void main(String[] args)
    {

        try
        {
            MongoClient mongoInstance = new MongoClient("localhost", 27017);
            MongoDatabase db = mongoInstance.getDatabase("test");
            MongoCollection<Document> collection = db.getCollection("mobileCol3");

            // convert JSON to DBObject directly

            BufferedReader reader;

            reader = new BufferedReader(new FileReader("C:/Users/c/IdeaProjects/Prepareo/Resources/convert.json-ld"));
            String fileLine = reader.readLine();
            // String jsonString = "";
            StringBuilder jsonString = new StringBuilder();
            while (fileLine != null)
            {
                // read next line
                //    jsonString += fileLine;
                jsonString.append(fileLine);
                fileLine = reader.readLine();
            }
            reader.close();
            //   jsonString = encode(jsonString);

            JSONParser parser = new JSONParser();
            // JSONArray jArray = (JSONArray) parser.parse(jsonString);
            JSONArray jArray = (JSONArray) parser.parse(encode(jsonString.toString()));
            System.out.println(jArray.size());

            int count = 0;
            int batch = 100;
            List<InsertOneModel<Document>> docs = new ArrayList<>();

            //for (int i=0; i<array.size(); i++)
            for (Object obj: jArray)
            {
                //JSONObject entry = (JSONObject) array.get(i);
                JSONObject entry = (JSONObject) obj;
                docs.add(new InsertOneModel<>(Document.parse(entry.toJSONString())));
                count++;
                if (count % batch == 0)
                {
                    collection.bulkWrite(docs, new BulkWriteOptions().ordered(false));
                    docs.clear();
                }
            }
            if (count % batch != 0)
            {
                collection.bulkWrite(docs, new BulkWriteOptions().ordered(false));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private static String encode(String encodee)
    {
        return encodee.replace(".", "~p")
                .replace("@","_");
    }
}

