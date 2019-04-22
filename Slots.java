import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class Slots {

    public Stage primaryStage;
    public Stage p2;
    public Label label = new Label ("Money: $"+MainMenu.money);
    private Button button = new Button("Back to Menu");
    private Button playButton = new Button("Spin");
    private boolean done = true;
    private TextField type = new TextField("Enter your bet");
    private VBox root;
    private double bet;

    public Slots(Stage p) {
        p2=p;
        Stage subStage = new Stage();
        primaryStage=subStage;
        button.setOnAction(this::press);
        playButton.setOnAction(this::pressPlay);
        type.setAlignment(Pos.CENTER);
        type.setMaxWidth(500);
        type.setMinWidth(100);
        root = new VBox(label,button,type,slotsSetup(),playButton);
        root.setPadding(new Insets(15,15,15,25));
        root.setSpacing(10);
        root.setStyle("-fx-background-color: pink");
        root.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(root,700,600);
        primaryStage.setTitle("Slots");
        primaryStage.setScene(scene);
        primaryStage.show();
        p2.close();
    }

    public void press(ActionEvent event) {
        primaryStage.close();
        p2.show();
        MainMenu.label2.setText("Money: $"+MainMenu.money);
    }

    public FlowPane slotsSetup() {
        Image seven = new Image("Imgs/Seven.jpg");
        ImageView sevenView = new ImageView(seven);
        Image Diamond = new Image("Imgs/Diamond.jpg");
        ImageView DiamondView = new ImageView(Diamond);
        Image Shoe = new Image("Imgs/Shoe.jpg");
        ImageView ShoeView = new ImageView(Shoe);
        FlowPane main = new FlowPane(sevenView,DiamondView,ShoeView);
        main.setAlignment(Pos.CENTER);
        main.setPadding(new Insets(120,0,0,0));
        return main;
    }

    public void pressPlay(ActionEvent event) {
        double value = -1;
        if(done) {
            if(type.getText().trim().equals("")) {
                type.setText("Enter a Value");
            } else if(!isDouble(type.getText())) {
                type.setText("Enter a Number");
            } else {
                value=Double.parseDouble(type.getText());
            }
            if(value>0) {
                if(value>MainMenu.money) {
                    type.setText("You Can Not Bet More Then You Have");
                } else {
                    MainMenu.money-=value;
                    label.setText(String.format("Money: $%1.2f",MainMenu.money));
                    root=new VBox(label,button,type,slotsSetup(),playButton);
                    updateScreen();
                    type.setText("Spinning Slots");
                    bet=value;
                    spinSlots();
                }
            } else {
                type.setText("You Can Not Bet 0 or Negative Numbers");
            }
        }
    }

    public void spinSlots() {
        done=false;
        Random rand = new Random();
        int img1 = rand.nextInt(9)+1;
        int img2 = rand.nextInt(9)+1;
        int img3 = rand.nextInt(9)+1;
        root=new VBox(label,button,type,createImgs(img1,img2,img3),playButton);
        updateScreen();
        checkWin(img1,img2,img3);
        done=true;
    }

    public void checkWin(int img1,int img2,int img3) {
        double value=0;
        if(img1==img2&&img1==img3) {
            switch (img1) {
                case 1: MainMenu.money+=bet*3;value+=bet*3; break;
                case 2: MainMenu.money+=bet*1.5;value+=bet*1.5; break;
                case 3: MainMenu.money+=bet*1.5;value+=bet*1.5; break;
                case 4: MainMenu.money+=bet*7;value+=bet*7; break;
                case 5: MainMenu.money+=bet*1.25;value+=bet*1.25; break;
                case 6: MainMenu.money+=bet*1;value+=bet*1; break;
                case 7: MainMenu.money+=bet*75;value+=bet*75; break;
                case 8: MainMenu.money+=100;value+=100; break;
                case 9: MainMenu.money+=bet*1;value+=bet*1; break;
            }
        } else if(img1==img2) {
            switch (img1) {
                case 1: MainMenu.money+=bet*1;value+=bet*1; break;
                case 2: MainMenu.money+=bet*.75;value+=bet*.75; break;
                case 3: MainMenu.money+=bet*.5;value+=bet*.5; break;
                case 4: MainMenu.money+=bet*1.5;value+=bet*1.5; break;
                case 5: MainMenu.money+=bet*1;value+=bet*1; break;
                case 6: MainMenu.money+=bet*.5;value+=bet*.5; break;
                case 7: MainMenu.money+=bet*2;value+=bet*2; break;
                case 8: MainMenu.money+=10;value+=10; break;
                case 9: MainMenu.money+=bet*.5;value+=bet*.5; break;
            }
            switch (img3) {
                case 1: MainMenu.money+=bet*.25;value+=bet*.25; break;
                case 2: MainMenu.money+=bet*0;value+=bet*0; break;
                case 3: MainMenu.money+=bet*0;value+=bet*0; break;
                case 4: MainMenu.money+=bet*.5;value+=bet*.5; break;
                case 5: MainMenu.money+=bet*.25;value+=bet*.25; break;
                case 6: MainMenu.money+=bet*0;value+=bet*0; break;
                case 7: MainMenu.money+=bet*0;value+=bet*0; break;
                case 8: MainMenu.money+=1;value+=1; break;
                case 9: MainMenu.money+=bet*.1;value+=bet*.1; break;
            }
        } else if(img1==img3) {
            switch (img1) {
                case 1: MainMenu.money+=bet*1;value+=bet*1; break;
                case 2: MainMenu.money+=bet*.75;value+=bet*.75; break;
                case 3: MainMenu.money+=bet*.5;value+=bet*.5; break;
                case 4: MainMenu.money+=bet*1.5;value+=bet*1.5; break;
                case 5: MainMenu.money+=bet*1;value+=bet*1; break;
                case 6: MainMenu.money+=bet*.5;value+=bet*.5; break;
                case 7: MainMenu.money+=bet*2;value+=bet*2; break;
                case 8: MainMenu.money+=10;value+=10; break;
                case 9: MainMenu.money+=bet*.5;value+=bet*.5; break;
            }
            switch (img2) {
                case 1: MainMenu.money+=bet*.25;value+=bet*.25; break;
                case 2: MainMenu.money+=bet*0;value+=bet*0; break;
                case 3: MainMenu.money+=bet*0;value+=bet*0; break;
                case 4: MainMenu.money+=bet*.5;value+=bet*.5; break;
                case 5: MainMenu.money+=bet*.25;value+=bet*.25; break;
                case 6: MainMenu.money+=bet*0;value+=bet*0; break;
                case 7: MainMenu.money+=bet*0;value+=bet*0; break;
                case 8: MainMenu.money+=1;value+=1; break;
                case 9: MainMenu.money+=bet*.1;value+=bet*.1; break;
            }
        } else if(img2==img3) {
            switch (img2) {
                case 1: MainMenu.money+=bet*1;value+=bet*1; break;
                case 2: MainMenu.money+=bet*.75;value+=bet*.75; break;
                case 3: MainMenu.money+=bet*.5;value+=bet*.5; break;
                case 4: MainMenu.money+=bet*1.5;value+=bet*1.5; break;
                case 5: MainMenu.money+=bet*1;value+=bet*1; break;
                case 6: MainMenu.money+=bet*.5;value+=bet*.5; break;
                case 7: MainMenu.money+=bet*2;value+=bet*2; break;
                case 8: MainMenu.money+=10;value+=10; break;
                case 9: MainMenu.money+=bet*.5;value+=bet*.5; break;
            }
            switch (img1) {
                case 1: MainMenu.money+=bet*.25;value+=bet*.25; break;
                case 2: MainMenu.money+=bet*0;value+=bet*0; break;
                case 3: MainMenu.money+=bet*0;value+=bet*0; break;
                case 4: MainMenu.money+=bet*.5;value+=bet*.5; break;
                case 5: MainMenu.money+=bet*.25;value+=bet*.25; break;
                case 6: MainMenu.money+=bet*0;value+=bet*0; break;
                case 7: MainMenu.money+=bet*0;value+=bet*0; break;
                case 8: MainMenu.money+=1;value+=1; break;
                case 9: MainMenu.money+=bet*.1;value+=bet*.1; break;
            }
        } else {
            switch (img1) {
                case 1: MainMenu.money+=bet*.25;value+=bet*.25; break;
                case 2: MainMenu.money+=bet*0;value+=bet*0; break;
                case 3: MainMenu.money+=bet*0;value+=bet*0; break;
                case 4: MainMenu.money+=bet*.5;value+=bet*.5; break;
                case 5: MainMenu.money+=bet*.25;value+=bet*.25; break;
                case 6: MainMenu.money+=bet*0;value+=bet*0; break;
                case 7: MainMenu.money+=bet*0;value+=bet*0; break;
                case 8: MainMenu.money+=1;value+=1; break;
                case 9: MainMenu.money+=bet*.1;value+=bet*.1; break;
            }
            switch (img2) {
                case 1: MainMenu.money+=bet*.25;value+=bet*.25; break;
                case 2: MainMenu.money+=bet*0;value+=bet*0; break;
                case 3: MainMenu.money+=bet*0;value+=bet*0; break;
                case 4: MainMenu.money+=bet*.5;value+=bet*.5; break;
                case 5: MainMenu.money+=bet*.25;value+=bet*.25; break;
                case 6: MainMenu.money+=bet*0;value+=bet*0; break;
                case 7: MainMenu.money+=bet*0;value+=bet*0; break;
                case 8: MainMenu.money+=1;value+=1; break;
                case 9: MainMenu.money+=bet*.1;value+=bet*.1; break;
            }
            switch (img3) {
                case 1: MainMenu.money+=bet*.25;value+=bet*.25; break;
                case 2: MainMenu.money+=bet*0;value+=bet*0; break;
                case 3: MainMenu.money+=bet*0;value+=bet*0; break;
                case 4: MainMenu.money+=bet*.5;value+=bet*.5; break;
                case 5: MainMenu.money+=bet*.25;value+=bet*.25; break;
                case 6: MainMenu.money+=bet*0;value+=bet*0; break;
                case 7: MainMenu.money+=bet*0;value+=bet*0; break;
                case 8: MainMenu.money+=1;value+=1; break;
                case 9: MainMenu.money+=bet*.1;value+=bet*.1; break;
            }
        }
        label.setText(String.format("Money: $%1.2f",MainMenu.money));
        type.setText(String.format("You made $%1.2f",value));
        root=new VBox(label,button,type,createImgs(img1,img2,img3),playButton);
        updateScreen();
    }

    public FlowPane createImgs(int img1, int img2, int img3) {
        ImageView image1 = new ImageView();
        switch (img1) {
            case 1: image1 = new ImageView(new Image("Imgs/Bar.jpg")); break;
            case 2: image1 = new ImageView(new Image("Imgs/Bell.jpg")); break;
            case 3: image1 = new ImageView(new Image("Imgs/Cherry.jpg")); break;
            case 4: image1 = new ImageView(new Image("Imgs/Diamond.jpg")); break;
            case 5: image1 = new ImageView(new Image("Imgs/Heart.jpg")); break;
            case 6: image1 = new ImageView(new Image("Imgs/Lemon.jpg")); break;
            case 7: image1 = new ImageView(new Image("Imgs/Seven.jpg")); break;
            case 8: image1 = new ImageView(new Image("Imgs/Shoe.jpg")); break;
            case 9: image1 = new ImageView(new Image("Imgs/Watermelon.jpg")); break;
            default: System.out.println("Error in rng"); break;
        }
        ImageView image2 = new ImageView();
        switch (img2) {
            case 1: image2 = new ImageView(new Image("Imgs/Bar.jpg")); break;
            case 2: image2 = new ImageView(new Image("Imgs/Bell.jpg")); break;
            case 3: image2 = new ImageView(new Image("Imgs/Cherry.jpg")); break;
            case 4: image2 = new ImageView(new Image("Imgs/Diamond.jpg")); break;
            case 5: image2 = new ImageView(new Image("Imgs/Heart.jpg")); break;
            case 6: image2 = new ImageView(new Image("Imgs/Lemon.jpg")); break;
            case 7: image2 = new ImageView(new Image("Imgs/Seven.jpg")); break;
            case 8: image2 = new ImageView(new Image("Imgs/Shoe.jpg")); break;
            case 9: image2 = new ImageView(new Image("Imgs/Watermelon.jpg")); break;
            default: System.out.println("Error in rng"); break;
        }
        ImageView image3 = new ImageView();
        switch (img3) {
            case 1: image3 = new ImageView(new Image("Imgs/Bar.jpg")); break;
            case 2: image3 = new ImageView(new Image("Imgs/Bell.jpg")); break;
            case 3: image3 = new ImageView(new Image("Imgs/Cherry.jpg")); break;
            case 4: image3 = new ImageView(new Image("Imgs/Diamond.jpg")); break;
            case 5: image3 = new ImageView(new Image("Imgs/Heart.jpg")); break;
            case 6: image3 = new ImageView(new Image("Imgs/Lemon.jpg")); break;
            case 7: image3 = new ImageView(new Image("Imgs/Seven.jpg")); break;
            case 8: image3 = new ImageView(new Image("Imgs/Shoe.jpg")); break;
            case 9: image3 = new ImageView(new Image("Imgs/Watermelon.jpg")); break;
            default: System.out.println("Error in rng"); break;
        }
        FlowPane main = new FlowPane(image1,image2,image3);
        main.setPadding(new Insets(120,0,0,0));
        main.setAlignment(Pos.CENTER);
        return main;
    }

    public void updateScreen() {
        root.setPadding(new Insets(15,15,15,25));
        root.setSpacing(10);
        root.setStyle("-fx-background-color: pink");
        root.setAlignment(Pos.TOP_CENTER);
        Scene scene= new Scene(root,700,600);
        primaryStage.setScene(scene);
    }

    public boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}