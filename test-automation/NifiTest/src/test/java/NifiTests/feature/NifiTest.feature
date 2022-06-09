@nifi
Feature: Nifi Test

Background: Define Url
    * def dbHandler = Java.type('helpers.MongoDbHandler')
    * def dataGenerator = Java.type('helpers.DataGenerator')
    * def rabbitmqHandler = Java.type('helpers.RabbitMQHandler')
    * def nifiRequestBody = read('classpath:NifiTests/data/nifiRequest.json')
    * def sleep = function(ms){ java.lang.Thread.sleep(ms) }
    Given url apiUrl

  @nifi1
  Scenario: Nifi Test - Check With Code from db
    Given def code = dataGenerator.getRandomCode()
    Given set nifiRequestBody.code =  code
    Given path 'order'
    And request nifiRequestBody
    When method Post
    Then status 200
    * eval sleep(10000)
    * def mongoDbResponse =  karate.toString(karate.toJson(dbHandler.findKeyValue(mongodbInfo, "code",code)))
    * print mongoDbResponse
    Then match mongoDbResponse contains code

  @nifi3
  Scenario: Nifi Test - Update Document
    * def mongoDbResponse =  dbHandler.updateDocument(mongodbInfo, "code","test-99","test-99-update")

    #bu test için nifi den "ConsumeAMQP" prosesoru disable etmek gerekli
  @nifi4
  Scenario: Nifi Test - RabbitMQ Message Control
    Given def code = dataGenerator.getRandomCode()
    Given set nifiRequestBody.code =  code
    Given path 'order'
    And request nifiRequestBody
    When method Post
    Then status 200
    * eval sleep(5000)
    #Then rabbitmqHandler.test()
    * def rabbitMqMessage =  karate.toJson(rabbitmqHandler.test(rabbitMqInfo))
    * print rabbitMqMessage
    Then match rabbitMqMessage contains code


