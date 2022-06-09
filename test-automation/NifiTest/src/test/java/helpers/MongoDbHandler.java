package helpers;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Map;

public class MongoDbHandler {

    public static  String findKeyValue(Map<String, Object> mongodbInfo, String fieldName, String value){
        Document doc;
        String connectionUrl = (String) mongodbInfo.get("connectionUrl");
        String databaseName = (String) mongodbInfo.get("databaseName");
        String collectionName = (String) mongodbInfo.get("collectionName");

        try (MongoClient mongoClient = MongoClients.create(connectionUrl)) {
                MongoDatabase database = mongoClient.getDatabase(databaseName);
                MongoCollection<Document> collection = database.getCollection(collectionName);

                Bson projectionFields = Projections.fields(
                        Projections.include("code"),
                        Projections.excludeId());

                doc = collection.find(Filters.eq(fieldName,value))
                        .projection(projectionFields)
                        .first();
                if (doc == null) {
                    System.out.println("No results found.");
                } else {
                    System.out.println(doc.toJson());
                }
            }
           return doc.toJson();
    }


    public  static void updateDocument(Map<String, Object> mongodbInfo, String fieldName, String value,String updatedValue){
        String connectionUrl = (String) mongodbInfo.get("connectionUrl");
        String databaseName = (String) mongodbInfo.get("databaseName");
        String collectionName = (String) mongodbInfo.get("collectionName");

        try (MongoClient mongoClient = MongoClients.create(connectionUrl)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Document query = new Document().append(fieldName,  value);
            Bson updates = Updates.combine(
                    Updates.set("runtime", 99),
                    Updates.addToSet("products", "product1"),
                    Updates.set(fieldName,updatedValue),
                    Updates.currentTimestamp("lastUpdated"));
            UpdateOptions options = new UpdateOptions().upsert(true);
            try {
                UpdateResult result = collection.updateOne(query, updates, options);
                System.out.println("Modified document count: " + result.getModifiedCount());
                System.out.println("Upserted id: " + result.getUpsertedId()); // only contains a value when an upsert is performed
            } catch (MongoException me) {
                System.err.println("Unable to update due to an error: " + me);
            }
        }
    }

}
