
//

//TEACHER NOTE: I RAN OUT OF MEMORY AND DIDN'T GET HELP FROM PIAZZA SO I STILL HAVE NO IDEA WHAT TO DO. PLEASE PASTE THE CODE IN YOUR VM WITH THE SEPERATE CLASS FILES I HAVE AND RUN IT. I AM CONFIDENT MY MAIN BRANCH OUTPUTS SOMEHTING YOU CAN GRADE. I WILL SUBMIT A VERSION 2 WHICH I THINK COMPILES (can't check because reached out of memory error), IF IT DOES PLEASE GRADE THAT (newer version by time stamp)















package cs1302.api;

import java.net.http.HttpClient;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import java.util.Iterator;
import javafx.scene.control.Label;

import javafx.application.Application;

import javafx.application.Platform;

import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.stage.Stage;

import javafx.scene.image.ImageView;

import javafx.scene.control.Button;

import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;

import javafx.scene.image.Image;

import javafx.scene.control.TextField;

import javafx.scene.control.TextArea;

import javafx.scene.text.Text;

import javafx.scene.control.ComboBox;

import javafx.scene.layout.GridPane;

import javafx.scene.control.ProgressBar;

import javafx.scene.layout.Priority;

import cs1302.api.MealsResponse;

import cs1302.api.MealsResult;

import cs1302.api.IngredientsResponse;

import cs1302.api.IngredientsResults;

import javafx.scene.control.Separator;

import javafx.geometry.Orientation;

import java.net.http.HttpResponse;

import java.net.http.HttpRequest;

import java.nio.charset.StandardCharsets;

import java.io.IOException;

import java.io.InputStream;

import java.net.URI;

import java.net.URL;

import java.net.URLEncoder;

import java.net.http.HttpResponse.BodyHandlers;

import java.lang.Math;

//import cs1302.api.Meals;

import com.google.gson.Gson;
import cs1302.api.ItunesResponse;
//import cs1302.api.Recipe;
import com.google.gson.GsonBuilder;

import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Alert;

import java.io.IOException;

import java.lang.IllegalArgumentException;

import javafx.event.EventHandler;

import javafx.event.ActionEvent;


/**

 * The user will type in a cuisine and the result will be the top dish with the recipe provided.

 */


public class ApiApp extends Application {



    /** HTTP client. */

    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()

        .version(HttpClient.Version.HTTP_2)           // uses HTTP protocol version 2 where possible

        .followRedirects(HttpClient.Redirect.NORMAL)  // always redirects, except from HTTPS to HTTP

        .build();                                     // builds and returns a HttpClient object



    /** Google {@code Gson} object for parsing JSON-formatted strings. */

    public static Gson GSON = new GsonBuilder()

        .setPrettyPrinting()                          // enable nice output when printing

        .create();                                    // builds and returns a Gson object



    Stage stage;

    Scene scene;

    VBox root;

    Text ingredients;

    String mealdb = "";

    Text urlLink;

    TextField txtField;
Button getImage;
    HBox topLayer;

    HBox layer1;

    HBox layer2;

    HBox layer3;

    Label recipe1 = new Label("Recipe 1");

    Text recipe1Text = new Text("Recipe");

    Label recipe2 = new Label("Recipe 2");

    Text recipe2Text = new Text("Recipe");

    Label recipe3 = new Label("Recipe 3");

    Text recipe3Text = new Text("Recipe");

   // ItunesResponse itunesResponse;

    ComboBox<String> comboBox;

    String[] resultsArr;

    String edamam = "";

//    MealsResponse meal;

//    MealsResult meal;



/**

 * Constructs an {@code ApiApp} object. This default (i.e., no argument)

 * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.

 */



    public ApiApp() {



        root = new VBox();

        getImage = new Button("Get Recipes!");

        ingredients = new Text("Select Cuisine: ");

        txtField = new TextField("American");

        topLayer = new HBox(8);

        layer1 = new HBox(8);

        layer2 = new HBox(8);

        layer3 = new HBox(8);

       //itunesResponse = new ItunesResponse();

        resultsArr = new String[20];

        comboBox = new ComboBox<>();

        System.out.println("mealdb:"+ mealdb);

    } // ApiApp



    /** {@inheritDoc} */

    @Override

    public void init() {

        System.out.println("init() called");

        System.out.println("mealdb" + mealdb);

        comboBox.getItems().addAll("American", "British", "Canadian", "Chinese", "Croatian",

        "French", "Greek", "Indian", "Irish", "Italian", "Jamaican",

        "Japanese", "Mexican", "Portuguese",

        "Spanish","Turkish", "Vietnamese");

        topLayer.getChildren().addAll(ingredients, comboBox, getImage);

        layer1.getChildren().addAll(recipe1, recipe1Text);

        layer2.getChildren().addAll(recipe2, recipe2Text);

        layer3.getChildren().addAll(recipe3, recipe3Text);

        root.getChildren().addAll(topLayer, layer1, layer2, layer3);


        startLoad();


    }



    private void startLoad() {

        Runnable action = (() -> {

            comboBox.setValue("American");

            comboBox.setValue(comboBox.getValue());

            EventHandler<ActionEvent> mouseClickHandler = (ActionEvent e) -> {

           mealdb = "https://www.themealdb.com/api/json/v1/1/filter.php?a="+comboBox.getValue();

    


           this.callAPI();
           
        };
        
        getImage.setOnAction(mouseClickHandler);
        
        edamam = "https://api.edamam.com/api/recipes/v2?type=public&q="+
        
        recipe1.getText() + "&app_id=8d178979&app_key=ddce67b46dc440c7257e5674b23fa743";


        
        });

         Platform.runLater(action);

    }





     private void endLoad() {

          Runnable action = (() -> {

              String recipeVal = recipe1.getText();

              recipeVal = URLEncoder.encode(recipeVal, StandardCharsets.UTF_8);

            //  edamam = "https://api.edamam.com/api/recipes/v2?type=public&q="+

              //    recipeVal + "&app_id=8d178979&app_key=ddce67b46dc440c7257e5674b23fa743";
              String app_id = URLEncoder.encode("8d178979", StandardCharsets.UTF_8);
              String app_key = URLEncoder.encode("ddce67b46dc440c7257e5674b23fa743", StandardCharsets.UTF_8);
              edamam = "https://api.edamam.com/api/recipes/v2?type=public&q=" + recipeVal + "&app_id=" + app_id + "&app_key=" + app_key;
//              this.secondAPI();

          });

           Platform.runLater(action);

     }



    /** {@inheritDoc} */

    @Override

    public void start(Stage stage) {



        this.stage = stage;

        scene = new Scene(root);

        // setup stage

        stage.setTitle("Recipe App!");

        stage.setScene(scene);

        stage.setOnCloseRequest(event -> Platform.exit());

        stage.sizeToScene();

        stage.show();



    } // start




    public void secondAPI() {

        try {

            endLoad();


        String query2 = String.format(edamam);

        String uri2 = edamam;


        // build request

        HttpRequest request2 = HttpRequest.newBuilder()

            .uri(URI.create(query2))

            .build();

        HttpResponse<String> response2 = HTTP_CLIENT

            .send(request2, BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {

            System.err.println(e);

            e.printStackTrace();

        }

    }
 private void getRecipeDetails(String strMeal, TextArea textArea) {
    try {
        // Encode the recipe name as a URL parameter
        String encodedRecipeName = URLEncoder.encode(strMeal, StandardCharsets.UTF_8);
String app_id = URLEncoder.encode("8d178979", StandardCharsets.UTF_8);
              String app_key = URLEncoder.encode("ddce67b46dc440c7257e5674b23fa743", StandardCharsets.UTF_8);
        // Send the API request
        // Build the API request URL
        String edamamUrl = "https://api.edamam.com/api/recipes/v2?type=public&q=" + encodedRecipeName +
                        "&app_id=" + app_id + "&app_key=" + app_key;

        URL url = new URL(edamamUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Read the response
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        String response = responseBuilder.toString().trim();
        textArea.setText(response);
        // Print the list of ingredients to the console
        /////////


        // Close the connections and streams
        reader.close();
        connection.disconnect();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }



    public void callAPI () {



        try {


            String query = String.format(mealdb);

            String uri = mealdb;

            // build request

            HttpRequest request = HttpRequest.newBuilder()

                .uri(URI.create(query))

                .build();

            // send request / receive response in the form of a String

            HttpResponse<String> response = HTTP_CLIENT

                .send(request, BodyHandlers.ofString());

            System.out.println();



            endLoad();


            String jsonString = response.body();

            //String jsonString2 = response2.body();

            System.out.println("********** RAW JSON STRING: **********");

            System.out.println(jsonString.trim());



            MealsResponse mealResponse = GSON

                .fromJson(jsonString, MealsResponse.class);

//            System.out.println(mealResponse);

            MealsResult result = mealResponse.meals[0];

            System.out.println(result.strMeal);

            System.out.println(mealResponse);

            recipe1.setText(result.strMeal);



            MealsResult result2 = mealResponse.meals[1];

            recipe2.setText(result2.strMeal);



            MealsResult result3 = mealResponse.meals[2];

            recipe3.setText(result3.strMeal);

            ImageView imageView;  
            ObservableList<Node> nodes = root.getChildren();



for (Iterator<Node> it = nodes.iterator(); it.hasNext(); ) {
    Node node = it.next();
    if (node instanceof ImageView) {
        it.remove(); // remove the ImageView from the root
    }
}     
for(int i=0; i< 3; i++)
{            TextArea ingredientsTextArea = new TextArea();

            
                 result = mealResponse.meals[i];
                
                Image image = new Image(result.strMealThumb);
               imageView = new ImageView(image);
                // create an ImageView
                
                // set the size of the ImageView
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                String recipeName = result.strMeal;

                
                // create a mouse event handler for the image view
  imageView.setOnMouseClicked(event -> {
            // Call the method to get the recipe details
            this.getRecipeDetails(recipeName,ingredientsTextArea);

        });
             
                root.getChildren().add(imageView);    
                root.getChildren().add(ingredientsTextArea);  
            }



//            String jsonString2 = response2.body();

            //        System.out.println(jsonString2.trim());


/* 
            IngredientsResponse ingResponse = GSON

            .fromJson(jsonString, IngredientsResponse.class);

         for (int i = 0; i < ingResponse.ingredientLines.length; i++) {

             IngredientsResults ingResult = ingResponse.ingredientLines[i];

           System.out.println(ingResult.ingredientLines[i]);
*/
                                                                                  





        } catch (IOException | InterruptedException e) {


            System.err.println(e);

            e.printStackTrace();

        } // try



    }


} // ApiApp