import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInterface {
    Stage window;
    Scene menuScene;
    DBInitialise conn;
    final int width = 1920;
    final int height = 1080;

    public UserInterface(Stage w){
        window = w;
        window.setWidth(1280);
        window.setHeight(720);
        constructMainInterface();
    }

    public void constructMainInterface(){
        //Main Menu elements
        StackPane mainMenu = new StackPane();
        Button[] buttons =  new Button[2];
        buttons[0] = new Button("Start WoW\n5.4.8 Client");
        buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Runtime.getRuntime().exec("D:\\Users\\chand\\Documents\\World of Warcraft Mists of Pandaria\\Wow-64.exe", null, new File("D:\\Users\\chand\\Documents\\World of Warcraft Mists of Pandaria\\"));
                } catch(IOException ex){
                    System.out.println("Executable could not be started.");
                }
            }
        });
        buttons[1] = new Button("Edit WoW\n5.4.8 Database");
        buttons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                conn = new DBInitialise();
                constructDBInterface();
            }
        });
        for(int i = 0; i < buttons.length; i++){
            buttons[i].setTextAlignment(TextAlignment.CENTER);
            buttons[i].setId("button");
            buttons[i].setPrefSize(350, 150);
            buttons[i].setTranslateX(590 + i * (buttons[i].getPrefWidth()+50));
            buttons[i].setTranslateY(height/2-(buttons[i].getPrefHeight()/2));
        }
        mainMenu.getChildren().addAll(buttons);
        mainMenu.setAlignment(Pos.TOP_LEFT);
        mainMenu.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        menuScene = new Scene(mainMenu);
        //Background
        Image backImg = new Image("CSS/Assets/WoWMap.jpg");
        ImagePattern pattern = new ImagePattern(backImg);
        menuScene.setFill(pattern);
        //Scale to window handler
        mainMenu.getTransforms().add(new Scale(height/height, width/width, 0, 0));
        menuScene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                Scale scale = new Scale((double)newSceneWidth/width, window.getHeight()/height, 0, 0);
                mainMenu.getTransforms().setAll(scale);
            }
        });
        menuScene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                Scale scale = new Scale(window.getWidth()/width, (double)newSceneHeight/height, 0, 0);
                mainMenu.getTransforms().setAll(scale);
            }
        });
        //Create Window
        window.setScene(menuScene);
        window.getScene().getStylesheets().add("css/main.css");
        window.setTitle("TrinityDB Tool");
        window.show();
    }

    private void constructDBInterface(){
        StackPane mainMenu = new StackPane();
        mainMenu.setAlignment(Pos.TOP_LEFT);
        menuScene = new Scene(mainMenu);
        //BackButton
        Button back = new Button("Main Menu");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                conn.close();
                constructMainInterface();
            }
        });
        back.setId("button");
        back.setPrefSize(200, 50);
        back.setTextAlignment(TextAlignment.CENTER);
        mainMenu.getChildren().add(back);
        //Background
        Rectangle box = new Rectangle();
        box.setTranslateX((width-1600)/2);
        box.setTranslateY((height-900)/2);
        box.setId("box");
        box.setWidth(1600);
        box.setHeight(900);
        mainMenu.getChildren().add(box);
        //Text box
        String name = "";
        String statement = "SELECT * FROM creature_template WHERE entry = 197";
        ResultSet rows = conn.processQuery(statement);
        try {
            while (rows.next()) {
                name = rows.getString("name");
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        Label npcName = new Label("NPC Name: ");
        TextField entry = new TextField(name);
        npcName.setId("label");
        entry.setTranslateY(100);
        npcName.setTranslateY(100);
        entry.setTranslateX(100);
        entry.setMaxWidth(entry.getText().length()*10);
        mainMenu.getChildren().addAll(entry, npcName);
        //Background
        mainMenu.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        Image backImg = new Image("CSS/Assets/WoWPortal.jpg");
        ImagePattern pattern = new ImagePattern(backImg);
        menuScene.setFill(pattern);
        //Scale to window handler
        mainMenu.getTransforms().add(new Scale(height/height, width/width, 0, 0));
        menuScene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                Scale scale = new Scale((double)newSceneWidth/width, window.getHeight()/height, 0, 0);
                mainMenu.getTransforms().setAll(scale);
            }
        });
        menuScene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                Scale scale = new Scale(window.getWidth()/width, (double)newSceneHeight/height, 0, 0);
                mainMenu.getTransforms().setAll(scale);
            }
        });
        //Create Window
        window.setScene(menuScene);
        window.getScene().getStylesheets().add("css/main.css");
        window.show();
    }

    public void closeConnection(){
        conn.close();
    }

}