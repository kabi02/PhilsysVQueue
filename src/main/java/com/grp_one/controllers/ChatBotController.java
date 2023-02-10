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
    private String defaultStyle = " -fx-background-radius: 25px;" + "-fx-border-radius: 25px;" + "-fx-padding: 5px;";
    private Double initXpos;
    private Double offsetPos = 0.0;
    private Double chatHeight;
    private String resourcesPath;
    private Bot bot;
    private Chat chatSession;

    private Label responseChat;
    private AnchorPane sessionPane;
    private ArrayList<Label> sessionBotChat;
    private ArrayList<Label> sessionUserChat;

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
        String chat = "";
        if (event.getCode() == KeyCode.ENTER) {
            chat = chatbox.getText();
            chatbox.clear();
            if ((chat == null) || (chat.length() < 1))
                return;
            String request = chat;
            if (MagicBooleans.trace_mode)
                System.out.println(
                        "STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
                                + ":TOPIC=" + chatSession.predicates.get("topic"));
            String response = chatSession.multisentenceRespond(request);
            while (response.contains("&lt;"))
                response = response.replace("&lt;", "<");
            while (response.contains("&gt;"))
                response = response.replace("&gt;", ">");
            // System.out.println("Robot : " + response);
            addUserChat(chat);
            addBotChat(response);
            sessionPane.setMinHeight(chatHeight);

        }
    }

    private void addUserChat(String chat) {
        Label previous = sessionBotChat.get(sessionBotChat.size() - 1);
        responseChat = new Label(chat);
        responseChat.setMaxWidth(200);
        responseChat.setWrapText(true);
        responseChat.setTranslateY((previous.getTranslateY() + previous.getHeight()) + 12);
        responseChat.setStyle(" -fx-background-color: #bed0ef;" + defaultStyle);
        responseChat.setVisible(false);
        sessionUserChat.add(responseChat);
        sessionPane.getChildren().add(sessionUserChat.get(sessionUserChat.size() - 1));
        sessionPane.applyCss();
        sessionPane.layout();
        sessionPane.getChildren().get(sessionPane.getChildren().size() - 1)
                .setTranslateX(Main.getStage().getWidth() - responseChat.getWidth() - 35);
        sessionPane.getChildren().get(sessionPane.getChildren().size() - 1).setVisible(true);
        System.out.println(Main.getStage().getWidth() + " " + responseChat.getWidth() + " "
                + (Main.getStage().getWidth() - responseChat.getWidth()));
    }

    private void addBotChat(String chat) {
        Label previous = sessionUserChat.get(sessionUserChat.size() - 1);
        responseChat = new Label(chat);
        responseChat.setMaxWidth(200);
        responseChat.setWrapText(true);
        responseChat.setTranslateY((previous.getTranslateY() + previous.getHeight()) + 12);
        responseChat.setStyle(" -fx-background-color: #bee0f0;" + defaultStyle);
        sessionBotChat.add(responseChat);
        sessionPane.getChildren().add(sessionBotChat.get(sessionBotChat.size() - 1));
        sessionPane.applyCss();
        sessionPane.layout();
        session.vvalueProperty().bind(sessionPane.heightProperty());
        chatHeight = responseChat.getHeight() + responseChat.getTranslateY() + 20;
        System.out.println(chatHeight);
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

        sessionBotChat = new ArrayList<Label>();
        sessionUserChat = new ArrayList<Label>();

        sessionUserChat.add(new Label(""));

        responseChat = new Label("Hello! How May I help you!");
        responseChat.setTranslateY(5);
        responseChat.setMaxWidth(200);
        responseChat.setWrapText(true);
        responseChat.setStyle(" -fx-background-color: #bee0f0;" + defaultStyle);
        sessionBotChat.add(responseChat);
        sessionPane.getChildren().add(sessionBotChat.get(sessionBotChat.size() - 1));
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
