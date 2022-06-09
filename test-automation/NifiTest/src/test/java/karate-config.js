function fn() {
  var env = karate.env; // get system property 'karate.env'
  karate.log('karate.env system property was:', env);
  if (!env) {
    env = 'dev';
  }
  
  const config = {
    apiUrl: '',
    rabbitMqInfo: {
      queueName: "",
      exchangeName: "",
      hostName: "",
      userName: "",
      password: "",
    },
    mongodbInfo: {
      connectionUrl: "",
      databaseName: "",
      collectionName: "",
    }
  }

  if (env == 'dev') {
    config.apiUrl =  'http://nifi:8050/'
    config.mongodbInfo = {
      connectionUrl: "mongodb://mongodb:27017",
      databaseName: "pd",
      collectionName: "order",
    };
    config.rabbitMqInfo = {
      queueName: "order_queue",
      exchangeName: "order",
      hostName: "localhost",
      userName: "guest",
      password: "guest",
    }
  } else if (env == 'e2e') {

  }

  var MyClass = Java.type("helpers.Utils");
  MyClass.MongoDbUtils(config.mongodbInfo);
  MyClass.RabbitMqUtils(config.rabbitMqInfo);

  return config;
}