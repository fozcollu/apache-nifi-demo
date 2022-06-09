package helpers;


import com.github.javafaker.Faker;

import net.minidev.json.JSONObject;

public class DataGenerator {

    public static String getRandomEmail(){
        Faker faker = new Faker();
        String email = faker.name().firstName().toLowerCase() + faker.random().nextInt(0,100) + "@test.com";
        return email;
    }

    public static String getRandomUserName(){
        Faker faker = new Faker();
        String userName = faker.name().username();
        return userName;
    }

    public static String getRandomCode(){
        Faker faker = new Faker();
        String userName = "test-"+ faker.random().nextInt(0,100);

        return userName;
    }

    public static String getRandomTitle(){
        Faker faker = new Faker();
        String title = faker.name().title();
        return title;
    }

    public static JSONObject getRandomArticleValues(){
        Faker faker = new Faker();
        String title = faker.gameOfThrones().character();
        String decription = faker.gameOfThrones().city();
        String body = faker.gameOfThrones().quote();
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("description", decription);
        json.put("body", body);
        return json;

    }
    
}
