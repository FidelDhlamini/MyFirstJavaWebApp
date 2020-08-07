package net.webapp;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;


public class App {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {

        port(getHerokuAssignedPort());

        Map<String, Object> map = new HashMap<>();
        ArrayList <String> namesGreeted = new ArrayList <String>();

        get("/hello", (req, res) -> new ModelAndView(map, "hello.handlebars"), new HandlebarsTemplateEngine());



        post("/hello", (req, res) -> {


            // create the greeting message
            String name =  req.queryParams("username");
            String language = req.queryParams("lang");
            namesGreeted.add(name);




            if(!language.equals("")&& !name.equals("")){
                String greet = "";
                if(language.equals("zulu")){
                    greet = "Sawubona "+ name;


                }
                if(language.equals("english")){
                    greet = "Hello " + name;

                }
                if(language.equals("xhosa")){
                    greet = "Molo " + name;

                }

                
                map.put("greeting", greet);
//                map.put("username", name);
                map.put("lang", language);
                map.put("greetedNames",namesGreeted);
            }



            // put it in the map which is passed to the template - the value will be merged into the template
//            map.put("greeting", greet);
//            map.put("username", name);
//            map.put("lang", language);


            return new ModelAndView(map, "hello.handlebars");


        }, new HandlebarsTemplateEngine());

    }

    }

