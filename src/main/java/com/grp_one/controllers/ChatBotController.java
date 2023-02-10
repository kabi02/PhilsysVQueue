package com.grp_one.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;

import com.grp_one.Main;

public class ChatBotController implements Initializable {

    private static final boolean TRACE_MODE = false;
    private String defaultResponse = "Sensya na lods";
    private String defaultStyle = " -fx-background-radius: 15px;" + "-fx-border-radius: 15px;" + "-fx-padding: 5px;";
    private Double initXpos;
    private Double offsetPos = 0.0;
    private Double chatHeight;
    private String resourcesPath;
    private Bot bot;
    private Chat chatSession;

    private Label message;
    private AnchorPane sessionPane;
    private ArrayList<Label> response;
    private ArrayList<Label> action;
    private ArrayList<String> chatHistory = new ArrayList<String>();

    @FXML
    private AnchorPane suggestions;
    @FXML
    private TextField chatbox;
    @FXML
    private ScrollPane session;

    @FXML
    private void enterSuggestions(final MouseEvent event) throws Exception {
        Main.getStage().getScene().setCursor(Cursor.HAND);
    }

    @FXML
    private void exitSuggestions(final MouseEvent event) throws Exception {
        Main.getStage().getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void dragSuggestions(final MouseEvent event) throws Exception {
        suggestions.setOnMousePressed(mousedPressedEvent -> {
            initXpos = mousedPressedEvent.getSceneX();
        });
        suggestions.setOnMouseReleased(mousedPressedEvent -> {
            offsetPos = suggestions.getTranslateX();
            System.out.println("Offset: " + offsetPos);
        });
        if (initXpos == null)
            initXpos = event.getX();
        suggestions.setTranslateX(event.getSceneX() - initXpos + offsetPos);
        if (suggestions.getTranslateX() > 0) {
            suggestions.setTranslateX(0.0);
        } else if (suggestions.getTranslateX() < Main.getStage().getWidth() - suggestions.getWidth()) {
            suggestions.setTranslateX(Main.getStage().getWidth() - suggestions.getWidth());
        }
    }

    @FXML
    public void chatInteract(final KeyEvent event) throws Exception {
        String inquiry = "";
        if (event.getCode() == KeyCode.ENTER) {
            inquiry = chatbox.getText();
            chatbox.clear();
            if ((inquiry == null) || (inquiry.length() < 1))
                return;
            // if (true)
            // System.out.println(
            // "STATE=" + inquiry + ":THAT=" +
            // (chatSession.thatHistory.get(0)).get(0).toString()
            // + ":TOPIC=" + chatSession.predicates.get("topic"));
            String response = chatSession.multisentenceRespond(inquiry);
            // while (response.contains("&lt;"))
            // response = response.replace("&lt;", "<");
            // while (response.contains("&gt;"))
            // response = response.replace("&gt;", ">");
            // System.out.println("Robot : " + response);
            if ((chatSession.thatHistory.get(0)).get(0).toString().equalsIgnoreCase("unknown")) {
                response = "I'm sorry I coudln't understand.";
            }
            chatHistory.add(inquiry);
            chatHistory.add((chatSession.thatHistory.get(0)).get(0).toString());
            // System.out.println((chatSession.thatHistory.get(0)).get(0).toString());
            userInquiry(inquiry);
            botResponse(response);
            sessionPane.setMinHeight(chatHeight);

        }
    }

    private void userInquiry(String chat) {
        Label previous = response.get(response.size() - 1);
        message = initMessage(chat);
        message.setTranslateY((previous.getTranslateY() + previous.getHeight()) + 12);
        message.setStyle(" -fx-background-color: #bed0ef;" + defaultStyle);
        message.setVisible(false);
        action.add(message);
        sessionPane.getChildren().add(action.get(action.size() - 1));
        sessionPane.applyCss();
        sessionPane.layout();
        sessionPane.getChildren().get(sessionPane.getChildren().size() - 1)
                .setTranslateX(Main.getStage().getWidth() - message.getWidth() - 35);
        sessionPane.getChildren().get(sessionPane.getChildren().size() - 1).setVisible(true);
        // System.out.println(Main.getStage().getWidth() + " " + message.getWidth()
        // + " "
        // + (Main.getStage().getWidth() - message.getWidth()));
    }

    private void botResponse(String chat) {
        Label previous = action.get(action.size() - 1);
        message = initMessage(chat);
        message.setTranslateX(5);
        message.setTranslateY((previous.getTranslateY() + previous.getHeight()) + 12);
        message.setStyle(" -fx-background-color: #bee0f0;" + defaultStyle);
        response.add(message);
        sessionPane.getChildren().add(response.get(response.size() - 1));
        sessionPane.applyCss();
        sessionPane.layout();
        session.vvalueProperty().bind(sessionPane.heightProperty());
        chatHeight = message.getHeight() + message.getTranslateY() + 20;
        // chatSession.thatHistory.printHistory();
    }

    private Label initMessage(String chat) {
        Label newMessage = new Label(chat);
        newMessage.setMaxWidth(200);
        newMessage.setWrapText(true);
        return newMessage;
    }

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sessionPane = new AnchorPane();
        session.setContent(sessionPane);

        response = new ArrayList<Label>();
        action = new ArrayList<Label>();

        action.add(new Label(""));

        message = new Label("Hello! How May I help you!");
        message.setTranslateY(5);
        message.setTranslateX(5);
        message.setMaxWidth(200);
        message.setWrapText(true);
        message.setStyle(" -fx-background-color: #bee0f0;" + defaultStyle);
        response.add(message);
        sessionPane.getChildren().add(response.get(response.size() - 1));
        session.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        resourcesPath = getResourcesPath();
        MagicStrings.default_bot_response = defaultResponse;
        MagicBooleans.trace_mode = TRACE_MODE;
        bot = new Bot("super", resourcesPath);
        chatSession = new Chat(bot);
        chatSession.customerId = "guest";
        bot.writeAIMLFiles();
        bot.brain.nodeStats();
    }

}
