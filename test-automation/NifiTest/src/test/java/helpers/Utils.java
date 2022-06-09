package helpers;

import java.util.Map;

public class Utils {
    public static void MongoDbUtils(Map<String, Object> config) {
        String connectionUrl = (String) config.get("connectionUrl");
        String databaseName = (String) config.get("databaseName");
        String collection = (String) config.get("collection");
    }

    public static void RabbitMqUtils(Map<String, Object> config) {
        String orderQueueName = (String) config.get("orderQueueName");
        String exchangeName = (String) config.get("exchangeName");
        String mqHostName = (String) config.get("mqHostName");
        String mqUserName = (String) config.get("mqUserName");
    }
}
