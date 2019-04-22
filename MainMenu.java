import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {

    public static double money = 1000;
    private ChoiceBox<String> choice;
    private Button playButton;
    private Stage prim;
    public static Label label2 = new Label(String.format("Money: $%1.2f",MainMenu.money));

    public void start(Stage primaryStage) {
        prim = primaryStage;
        String[] games = {"Play Slots","Play Black Jack","Quit Game"};
        Label label = new Label("Select an Option:");
        choice = new ChoiceBox<String>();
        choice.getItems().addAll(games);
        choice.getSelectionModel().selectFirst();
        playButton = new Button("play");
        HBox buttons = new HBox(playButton);
        buttons.setPadding(new Insets(15,0,0,0));
        buttons.setAlignment(Pos.CENTER);
        playButton.setOnAction(this::processButtonPush);
        VBox root = new VBox(label2,label,choice,buttons);
        root.setPadding(new Insets(15,15,15,25));
        root.setSpacing(10);
        root.setStyle("-fx-background-color: skyblue");
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root,300,150);
        prim.setTitle("Main Menu");
        prim.setScene(scene);
        prim.show();
    }

    public void processButtonPush(ActionEvent event) {
        if(choice.getValue()=="Play Slots") {
            new Slots(prim);
        }
        else if(choice.getValue()=="Play Black Jack") {
            new BlackJack(prim);
        }
        else if(choice.getValue()=="Quit Game") {
            new EndClass(prim);
        }
    }
}