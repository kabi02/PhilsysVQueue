package com.grp_one.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;

import com.grp_one.Main;

public class ChatBotController implements Initializable {

    private static final boolean TRACE_MODE = false;
    private String defaultResponse = "I'm sorry I coudln't understand.";
    private String botLastMsgStyle = "-fx-background-color: #bee0f0; -fx-background-radius: 0px 15px 15px 15px; -fx-border-radius: 0px 15px 15px 15px;-fx-padding: 5px;";
    private String botTopMsgStyle = "-fx-background-color: #bee0f0; -fx-background-radius: 15px 15px 15px 0px; -fx-border-radius: 15px 15px 15px 0px;-fx-padding: 5px;";
    private String botMiddleStyle = "-fx-background-color: #bee0f0; -fx-background-radius: 0px 15px 15px 0px; -fx-border-radius: 15px 15px 15px 15px;-fx-padding: 5px;";
    private String userStyle = "-fx-background-color: #bed0ef; -fx-background-radius: 15px 0px 15px 15px; -fx-border-radius: 15px 0px 15px 15px;-fx-padding: 5px 5px 5px 5px;";
    private Double initXpos;
    private Double offsetPos = 0.0;
    private Double chatHeight;
    private Double maxChatWidth = 250.0;
    private String resourcesPath;
    private String userMsg = "";
    private String botMsg = "";
    private Bot bot;
    private Chat chatSession;

    private Label message;
    private AnchorPane sessionPane = new AnchorPane();
    private ImageView botProfile;
    private Image botImage = new Image(getResourcesPath() + "/imgs/bot.png");
    private ArrayList<HBox> newResponse = new ArrayList<HBox>();
    private ArrayList<Label> action = new ArrayList<Label>();
    private ArrayList<String> chatHistory = new ArrayList<String>();

    private enum messagePosition {
        TOP,
        MIDDLE,
        BOTTOM
    }

    private messagePosition msgPos = messagePosition.BOTTOM;

    private enum Context {
        PROFANITY,
        HELP,
        DEFAULT
    }

    private Context context = Context.DEFAULT;

    @FXML
    private HBox suggestions;
    @FXML
    private AnchorPane messageContainer;
    @FXML
    private TextField chatbox;
    @FXML
    private ScrollPane session;
    @FXML
    private Button backTo;

    @FXML
    private void backToPressed(final ActionEvent event) throws Exception {
        System.out.println("adadwa");
        bot.writeAIMLFiles();
    }

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
            // System.out.println("Offset: " + offsetPos);
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
        // Resets User Message
        userMsg = "";
        // Process Chat if Enter is pressed
        if (event.getCode() == KeyCode.ENTER) {
            userMsg = chatbox.getText();
            userMsg = userMsg.trim().replaceAll(" +", " ");
            chatbox.clear();
            // If no input when enter was pressed, return
            if ((userMsg == null) || (userMsg.length() < 1))
                return;

            botReplyProcess();
            getChatContext();
            interceptMessages();
            sessionPane.setMinHeight(chatHeight);
            // if (true)
            // System.out.println(
            // "STATE=" + userMsg + ":THAT=" +
            // (chatSession.thatHistory.get(0)).get(0).toString()
            // + ":TOPIC=" + chatSession.predicates.get("topic"));

            // while (response.contains("&lt;"))
            // response = response.replace("&lt;", "<");
            // while (response.contains("&gt;"))
            // response = response.replace("&gt;", ">");
            // System.out.println("Robot : " + response);

            // System.out.println((chatSession.thatHistory.get(0)).get(0).toString());

        }
    }

    private void botReplyProcess() {
        botMsg = chatSession.multisentenceRespond(userMsg);
        if ((chatSession.thatHistory.get(0)).get(0).toString().equalsIgnoreCase("unknown")) {
            botMsg = defaultResponse;
        }
    }

    private void getChatContext() {
        if (botMsg.compareTo("PROFANITY") == 0)
            context = Context.PROFANITY;
        else if (botMsg.compareTo("HELP") == 0)
            context = Context.HELP;
        else
            context = Context.DEFAULT;
    }

    private void interceptMessages() {
        switch (context) {
            case PROFANITY:
                userMsg = ChatContextProvider.USERPROFANITY;
                botMsg = chatSession.multisentenceRespond(ChatContextProvider.PROFANITYDISPATCH);
                displayResponses();
                return;
            case HELP:
                displayUserInquiry();
                botMsg = chatSession.multisentenceRespond(ChatContextProvider.HELPDISPATCH);
                displayMultiResponse(
                        chatSession.multisentenceRespond(ChatContextProvider.HELPDISPATCHSTART),
                        chatSession.multisentenceRespond(ChatContextProvider.HELPDISPATCHLIST),
                        chatSession.multisentenceRespond(ChatContextProvider.HELPDISPATCHEND));
                return;
            default:
                displayResponses();
                return;
        }

    }

    private void displayResponses() {
        instanceBotProfile();
        displayUserInquiry();
        displayBotResponse(action.get(action.size() - 1));
    }

    private void displayMultiResponse(String... responses) {
        instanceBotProfile();
        for (int i = 0; i < responses.length; i++) {
            botMsg = responses[i];
            if (i == 0) {
                msgPos = messagePosition.TOP;
                displayBotResponse(action.get(action.size() - 1));
            } else if (i < responses.length - 1) {
                msgPos = messagePosition.MIDDLE;
                displayBotResponse(newResponse.get(newResponse.size() - 1));
            } else {
                msgPos = messagePosition.BOTTOM;
                displayBotResponse(newResponse.get(newResponse.size() - 1));
            }
        }
    }

    private void displayUserInquiry() {
        HBox previous = newResponse.get(newResponse.size() - 1);
        // System.out.println(previous.getTranslateY());
        message = initMessage(userMsg);
        message.setTranslateY((previous.getTranslateY() + previous.getHeight()) + 7);
        message.setStyle(userStyle);
        message.setVisible(false);
        action.add(message);
        sessionPane.getChildren().add(action.get(action.size() - 1));
        sessionPane.applyCss();
        sessionPane.layout();
        sessionPane.getChildren().get(sessionPane.getChildren().size() - 1)
                .setTranslateX(Main.getStage().getWidth() - message.getWidth() - 35);
        sessionPane.getChildren().get(sessionPane.getChildren().size() - 1).setVisible(true);
        chatHistory.add(userMsg);
        // System.out.println(Main.getStage().getWidth() + " " + message.getWidth()
        // + " "
        // + (Main.getStage().getWidth() - message.getWidth()));
    }

    private void displayBotResponse(Region prevMsg) {
        HBox botMsgContainer = initBotMessage(prevMsg);
        newResponse.add(botMsgContainer);
        sessionPane.getChildren().add(newResponse.get(newResponse.size() - 1));
        session.vvalueProperty().bind(sessionPane.heightProperty());
        chatHistory.add(botMsg);
        msgPos = messagePosition.BOTTOM;
        // chatSession.thatHistory.printHistory();
    }

    private HBox initBotMessage(Region prevMsg) {

        message = initMessage(botMsg);
        message.setStyle(botLastMsgStyle);
        message.setTranslateY(6);
        if (msgPos == messagePosition.MIDDLE) {
            message.setStyle(botMiddleStyle);
            message.setTranslateX(30);
        } else if (msgPos == messagePosition.TOP) {
            message.setStyle(botTopMsgStyle);
            message.setTranslateX(30);
        }
        HBox botMsgContainer = new HBox();
        botMsgContainer.getChildren().add(botProfile);
        botMsgContainer.getChildren().add(message);
        botMsgContainer.setTranslateX(5);
        sessionPane.applyCss();
        sessionPane.layout();
        botMsgContainer.setTranslateY((prevMsg.getTranslateY() + prevMsg.getHeight()) + 2);
        chatHeight = botMsgContainer.getHeight() + botMsgContainer.getTranslateY() + 40;
        return botMsgContainer;
    }

    private Label initMessage(String chat) {
        Label newMessage = new Label(chat);
        newMessage.setMaxWidth(maxChatWidth);
        newMessage.setWrapText(true);
        return newMessage;
    }

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        // System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }

    private void instanceBotProfile() {
        botProfile = new ImageView(botImage);
        botProfile.setFitWidth(30);
        botProfile.setFitHeight(30);
        botProfile.setStyle("-fx-margin: 0 20 0 0;");
    }

    private void botInit() {
        resourcesPath = getResourcesPath();
        MagicStrings.default_bot_response = defaultResponse;
        MagicBooleans.trace_mode = TRACE_MODE;
        bot = new Bot("super", resourcesPath);
        chatSession = new Chat(bot);
        chatSession.customerId = "guest";
        bot.brain.nodeStats();
    }

    private void chatInit() {
        instanceBotProfile();
        session.setContent(sessionPane);

        action.add(new Label(""));

        botMsg = chatSession.multisentenceRespond(ChatContextProvider.BOTINTRODISPATCH);
        displayBotResponse(action.get(action.size() - 1));
        session.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        suggestions.setSpacing(15.0);
        suggestions.setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resourcesPath = getResourcesPath();
        MagicStrings.default_bot_response = defaultResponse;
        MagicBooleans.trace_mode = TRACE_MODE;
        bot = new Bot("super", resourcesPath);
        chatSession = new Chat(bot);
        chatSession.customerId = "guest";
        bot.writeAIMLFiles();
        bot.brain.nodeStats();
        chatInit();
        //

    }

}
