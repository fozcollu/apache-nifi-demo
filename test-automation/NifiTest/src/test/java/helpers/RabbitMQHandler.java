package helpers;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Map;

public class RabbitMQHandler {
    protected Connection connect(Map<String, Object> rabbitMqInfo) {
        String hostName = (String) rabbitMqInfo.get("hostName");
        String userName = (String) rabbitMqInfo.get("userName");
        String password = (String) rabbitMqInfo.get("password");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostName);
        factory.setPort(5672);
        factory.setUsername(userName);
        factory.setPassword(password);
        Connection connection = null;

        try {
            connection = factory.newConnection();
        } catch (Exception e){

        }
        return connection;
    }

    public String message = "";
    public String receive(Map<String, Object> rabbitMqInfo, final String type, final String name, final String bindingKey) {
        String exchangeName = (String) rabbitMqInfo.get("exchangeName");
        String queueName = (String) rabbitMqInfo.get("queueName");

        Connection connection = null;
         Channel channel = null;

        try {
            connection = connect(rabbitMqInfo);
            channel = connection.createChannel();
            channel.exchangeDeclare(exchangeName,BuiltinExchangeType.DIRECT,true);

            boolean durable = true;
            String qN = channel.queueDeclare(queueName, durable, false, false, null).getQueue();
            channel.queueBind(qN, exchangeName, bindingKey);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    message = new String(body, "UTF-8");
                    System.out.println(name + " Received " + envelope.getRoutingKey() + ": '" + message + "'");
                }
            };
            // auto acknowledgment is true
            // if false, will result in messages_unacknowledged
            // `rabbitmqctl list_queues name messages_ready messages_unacknowledged`
            channel.basicConsume(queueName, true, consumer);
        }
         catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                channel.close();
                connection.close();
            } catch (Exception e) {

            }
            System.out.println(name + " thread exists!");
            System.out.println("message: "+message);
            return message;
        }

    }

    public static String test(Map<String, Object> rabbitMqInfo) {
        String exchangeName = (String) rabbitMqInfo.get("exchangeName");

        String message = "";
        try{
            RabbitMQHandler q = new RabbitMQHandler();
             message = q.receive(rabbitMqInfo,"direct", exchangeName, "");
            System.out.println("all thread joined!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  message;
    }
}