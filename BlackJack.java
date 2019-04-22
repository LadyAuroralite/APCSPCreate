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
import java.util.ArrayList;
import java.util.List;

public class BlackJack {

    private Stage primaryStage;
    private Stage p2;
    private Label label = new Label ("Money: $"+MainMenu.money);
    private Button button = new Button("Back to Menu");
    private Button playButton = new Button("Draw Cards");
    private Button hitButton = new Button("Hit");
    private Button stayButton = new Button("Stay");
    private Button convertButton = new Button("Convert Aces");
    private boolean done = true;
    private TextField type = new TextField("Enter Your Bet");
    private VBox root;
    private double bet;
    private Deck deck = new Deck();
    private int playerTotal=0;
    private int AITotal=0;
    private int aceC=0,aceD=0,aceH=0,aceS=0;
    private List<Image> imgList = new ArrayList<>();
    private List<Image> AIImgList = new ArrayList<>();
    private FlowPane pane = new FlowPane();
    private FlowPane AIPane = new FlowPane();
    private FlowPane buttons = new FlowPane(hitButton,stayButton,convertButton);
    private boolean first = true;
    private boolean win = false;

    public BlackJack(Stage p) {
        p2=p;
        Stage subStage = new Stage();
        primaryStage=subStage;
        button.setOnAction(this::press);
        playButton.setOnAction(this::pressPlay);
        hitButton.setOnAction(this::hit);
        stayButton.setOnAction(this::stay);
        convertButton.setOnAction(this::convertAce);
        type.setAlignment(Pos.CENTER);
        type.setMaxWidth(500);
        type.setMinWidth(100);
        root = new VBox(label,button,type,playButton);
        root.setPadding(new Insets(15,15,15,25));
        root.setSpacing(10);
        root.setStyle("-fx-background-color: #800000");
        root.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(root,700,600);
        buttons.setAlignment(Pos.CENTER);
        buttons.setHgap(10);
        primaryStage.setTitle("Black Jack");
        primaryStage.setScene(scene);
        primaryStage.show();
        p2.close();
    }

    public void press(ActionEvent event) {
        primaryStage.close();
        p2.show();
        MainMenu.label2.setText("Money: $"+MainMenu.money);
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
                    root=new VBox(label,button,type,playButton);
                    updateScreen();
                    type.setText("Drawing Cards");
                    bet=value;
                    playGame();
                }
            } else {
                type.setText("You Can Not Bet 0 or Negative Numbers");
            }
        }
    }

    public void hit(ActionEvent event) {
        draw();
        checkWinHit();
    }

    public void stay(ActionEvent event) {
        addImgsAI();
        checkWinStay();
    }

    public void convertAce(ActionEvent event) {
        if(aceC==1) {
            playerTotal-=10;
            type.setText("You have converted the Ace of Clubs from 11 to 1, your total is now "+playerTotal);
        } else if(aceD==1) {
            playerTotal-=10;
            type.setText("You have converted the Ace of Diamonds from 11 to 1, your total is now "+playerTotal);
        } else if(aceH==1) {
            playerTotal-=10;
            type.setText("You have converted the Ace of Hearts from 11 to 1, your total is now "+playerTotal);
        } else if(aceS==1) {
            playerTotal-=10;
            type.setText("You have converted the Ace of Spades from 11 to 1, your total is now "+playerTotal);
        }
    }
    
    public void playGame() {
        clear();
        draw();
        draw();
        drawAI();
        checkWinFirst();
    }

    public void checkWinFirst() {
        if(playerTotal==21) {
            type.setText("Instant 21, You win 2.5X your bet");
            MainMenu.money+=bet*2.5;
            win = true;
            root=new VBox(label,button,type,pane,playButton,AIPane);
            updateScreen();
            addImgsAI();
        } else if(playerTotal<21) {
            type.setText("You can now either hit or stay");
        }
        label.setText("Money: $"+MainMenu.money);
    }

    public void checkWinHit() {
        if(playerTotal==21) {
            type.setText("You have 21, You win your bet 2X");
            win = true;
            MainMenu.money+=bet*2;
            root=new VBox(label,button,type,pane,playButton,AIPane);
            updateScreen();
            addImgsAI();
        } else if(playerTotal>21) {
            if(aceH==1||aceD==1||aceS==1||aceC==1) {
                if(aceC==1) {
                    playerTotal-=10;
                    type.setText("You have converted the Ace of Clubs from 11 to 1, your total is now "+playerTotal);
                } else if(aceD==1) {
                    playerTotal-=10;
                    type.setText("You have converted the Ace of Diamonds from 11 to 1, your total is now "+playerTotal);
                } else if(aceH==1) {
                    playerTotal-=10;
                    type.setText("You have converted the Ace of Hearts from 11 to 1, your total is now "+playerTotal);
                } else if(aceS==1) {
                    playerTotal-=10;
                    type.setText("You have converted the Ace of Spades from 11 to 1, your total is now "+playerTotal);
                }
            } else {
                type.setText("You bust");
                win = true;
                root=new VBox(label,button,type,pane,playButton,AIPane);
                updateScreen();
                addImgsAI();
            }
        } else if(playerTotal<21) {
            type.setText("You can now either hit or stay");
        }
        label.setText("Money: $"+MainMenu.money);
    }

    public void checkWinStay() {
        if(AITotal>21) {
            type.setText("You win");
            MainMenu.money+=bet*2;
            win = true;
            root=new VBox(label,button,type,pane,playButton,AIPane);
            updateScreen();
        } else {
            if(playerTotal>AITotal) {
                type.setText("You win");
                MainMenu.money+=bet*2;
                win = true;
                root=new VBox(label,button,type,pane,playButton,AIPane);
                updateScreen();
            } else if(playerTotal==AITotal) {
                type.setText("You Tie");
                win = true;
                MainMenu.money+=bet;
                root=new VBox(label,button,type,pane,playButton,AIPane);
                updateScreen();
            } else if(playerTotal<AITotal){
                type.setText("You lose");
                win = true;
                root=new VBox(label,button,type,pane,playButton,AIPane);
                updateScreen();
            } else {
                type.setText("error");
            }
        }
        label.setText("Money: $"+MainMenu.money);
        addImgsAI();
    }

    public void clear() {
        first=true;
        win=false;
        playerTotal=0;
        imgList.clear();
        AITotal=0;
        AIImgList.clear();
        addImgs();
    }

    public void draw() {
        imgList.add(createImgs());
        addImgs();
    }

    public void drawAI() {
        AIImgList.add(createImgsAI());
        addImgsAI();
    }

    public Image createImgs() {
        Image image = new Image("Imgs/CardImgs/"+convertCardPlayer());
        return image;
    }

    public Image createImgsAI() {
        Image image = new Image("Imgs/CardImgs/"+convertCardAI());
        return image;
    }

    public void addImgs() {
        Image img;
        ImageView[] imageV = new ImageView[11];
        for (int i=0;i<imageV.length;i++) {
            imageV[i]=new ImageView();
        }
        for (int i=0;i<imgList.size();i++) {
            img = imgList.get(i);
            imageV[i]=new ImageView(img);
        }
        pane = new FlowPane(imageV[0],imageV[1],imageV[2],imageV[3],imageV[4],imageV[5],imageV[6],imageV[7],imageV[8],imageV[9],imageV[10]);
        root=new VBox(label,button,type,pane,buttons,AIPane);
        updateScreen();
    }

    public void addImgsAI() {
        Image img;
        ImageView[] imageV = new ImageView[11];
        for (int i=0;i<imageV.length;i++) {
            imageV[i]=new ImageView();
        }
        for (int i=0;i<AIImgList.size();i++) {
            if(i==0&&first==true) {
                img = new Image("Imgs/CardImgs/row-5-col-3.jpg");
                imageV[i]=new ImageView(img);
            } else {
                img = AIImgList.get(i);
                imageV[i]=new ImageView(img);
            }
        }
        if(AITotal<=16) {
            drawAI();
            return;
        }
        first=false;
        AIPane = new FlowPane(imageV[0],imageV[1],imageV[2],imageV[3],imageV[4],imageV[5],imageV[6],imageV[7],imageV[8],imageV[9],imageV[10]);
        if(win==true) {
            root=new VBox(label,button,type,pane,playButton,AIPane);
            updateScreen();
        } else {
            root=new VBox(label,button,type,pane,buttons,AIPane);
            updateScreen();
        }
    }

    public String convertCardPlayer() {
        String card = deck.drawCard();
        String ret = "";
        switch (card) {
            case "Ace of Clubs": ret="row-1-col-1.jpg"; playerTotal+=11; aceC=1; break;
            case "2 of Clubs": ret="row-1-col-2.jpg"; playerTotal+=2; break;
            case "3 of Clubs": ret="row-1-col-3.jpg"; playerTotal+=3; break;
            case "4 of Clubs": ret="row-1-col-4.jpg"; playerTotal+=4; break;
            case "5 of Clubs": ret="row-1-col-5.jpg"; playerTotal+=5; break;
            case "6 of Clubs": ret="row-1-col-6.jpg"; playerTotal+=6; break;
            case "7 of Clubs": ret="row-1-col-7.jpg"; playerTotal+=7; break;
            case "8 of Clubs": ret="row-1-col-8.jpg"; playerTotal+=8; break;
            case "9 of Clubs": ret="row-1-col-9.jpg"; playerTotal+=9; break;
            case "10 of Clubs": ret="row-1-col-10.jpg"; playerTotal+=10; break;
            case "Jack of Clubs": ret="row-1-col-11.jpg"; playerTotal+=10; break;
            case "Queen of Clubs": ret="row-1-col-12.jpg"; playerTotal+=10; break;
            case "King of Clubs": ret="row-1-col-13.jpg"; playerTotal+=10; break;
            case "Ace of Diamonds": ret="row-2-col-1.jpg"; playerTotal+=11; aceD=1; break;
            case "2 of Diamonds": ret="row-2-col-2.jpg"; playerTotal+=2; break;
            case "3 of Diamonds": ret="row-2-col-3.jpg"; playerTotal+=3; break;
            case "4 of Diamonds": ret="row-2-col-4.jpg"; playerTotal+=4; break;
            case "5 of Diamonds": ret="row-2-col-5.jpg"; playerTotal+=5; break;
            case "6 of Diamonds": ret="row-2-col-6.jpg"; playerTotal+=6; break;
            case "7 of Diamonds": ret="row-2-col-7.jpg"; playerTotal+=7; break;
            case "8 of Diamonds": ret="row-2-col-8.jpg"; playerTotal+=8; break;
            case "9 of Diamonds": ret="row-2-col-9.jpg"; playerTotal+=9; break;
            case "10 of Diamonds": ret="row-2-col-10.jpg"; playerTotal+=10; break;
            case "Jack of Diamonds": ret="row-2-col-11.jpg"; playerTotal+=10; break;
            case "Queen of Diamonds": ret="row-2-col-12.jpg"; playerTotal+=10; break;
            case "King of Diamonds": ret="row-2-col-13.jpg"; playerTotal+=10; break;
            case "Ace of Hearts": ret="row-3-col-1.jpg"; playerTotal+=11; aceH=1; break;
            case "2 of Hearts": ret="row-3-col-2.jpg"; playerTotal+=2; break;
            case "3 of Hearts": ret="row-3-col-3.jpg"; playerTotal+=3; break;
            case "4 of Hearts": ret="row-3-col-4.jpg"; playerTotal+=4; break;
            case "5 of Hearts": ret="row-3-col-5.jpg"; playerTotal+=5; break;
            case "6 of Hearts": ret="row-3-col-6.jpg"; playerTotal+=6; break;
            case "7 of Hearts": ret="row-3-col-7.jpg"; playerTotal+=7; break;
            case "8 of Hearts": ret="row-3-col-8.jpg"; playerTotal+=8; break;
            case "9 of Hearts": ret="row-3-col-9.jpg"; playerTotal+=9; break;
            case "10 of Hearts": ret="row-3-col-10.jpg"; playerTotal+=10; break;
            case "Jack of Hearts": ret="row-3-col-11.jpg"; playerTotal+=10; break;
            case "Queen of Hearts": ret="row-3-col-12.jpg"; playerTotal+=10; break;
            case "King of Hearts": ret="row-3-col-13.jpg"; playerTotal+=10; break;
            case "Ace of Spades": ret="row-4-col-1.jpg"; playerTotal+=11; aceS=1; break;
            case "2 of Spades": ret="row-4-col-2.jpg"; playerTotal+=2; break;
            case "3 of Spades": ret="row-4-col-3.jpg"; playerTotal+=3; break;
            case "4 of Spades": ret="row-4-col-4.jpg"; playerTotal+=4; break;
            case "5 of Spades": ret="row-4-col-5.jpg"; playerTotal+=5; break;
            case "6 of Spades": ret="row-4-col-6.jpg"; playerTotal+=6; break;
            case "7 of Spades": ret="row-4-col-7.jpg"; playerTotal+=7; break;
            case "8 of Spades": ret="row-4-col-8.jpg"; playerTotal+=8; break;
            case "9 of Spades": ret="row-4-col-9.jpg"; playerTotal+=9; break;
            case "10 of Spades": ret="row-4-col-10.jpg"; playerTotal+=10; break;
            case "Jack of Spades": ret="row-4-col-11.jpg"; playerTotal+=10; break;
            case "Queen of Spades": ret="row-4-col-12.jpg"; playerTotal+=10; break;
            case "King of Spades": ret="row-4-col-13.jpg"; playerTotal+=10; break;
            default: System.out.println("Error in Converter"); break;
        }
        return ret;
    }

    public String convertCardAI() {
        String card = deck.drawCard();
        String ret = "";
        switch (card) {
            case "Ace of Clubs": ret="row-1-col-1.jpg"; AITotal+=11; break;
            case "2 of Clubs": ret="row-1-col-2.jpg"; AITotal+=2; break;
            case "3 of Clubs": ret="row-1-col-3.jpg"; AITotal+=3; break;
            case "4 of Clubs": ret="row-1-col-4.jpg"; AITotal+=4; break;
            case "5 of Clubs": ret="row-1-col-5.jpg"; AITotal+=5; break;
            case "6 of Clubs": ret="row-1-col-6.jpg"; AITotal+=6; break;
            case "7 of Clubs": ret="row-1-col-7.jpg"; AITotal+=7; break;
            case "8 of Clubs": ret="row-1-col-8.jpg"; AITotal+=8; break;
            case "9 of Clubs": ret="row-1-col-9.jpg"; AITotal+=9; break;
            case "10 of Clubs": ret="row-1-col-10.jpg"; AITotal+=10; break;
            case "Jack of Clubs": ret="row-1-col-11.jpg"; AITotal+=10; break;
            case "Queen of Clubs": ret="row-1-col-12.jpg"; AITotal+=10; break;
            case "King of Clubs": ret="row-1-col-13.jpg"; AITotal+=10; break;
            case "Ace of Diamonds": ret="row-2-col-1.jpg"; AITotal+=11; break;
            case "2 of Diamonds": ret="row-2-col-2.jpg"; AITotal+=2; break;
            case "3 of Diamonds": ret="row-2-col-3.jpg"; AITotal+=3; break;
            case "4 of Diamonds": ret="row-2-col-4.jpg"; AITotal+=4; break;
            case "5 of Diamonds": ret="row-2-col-5.jpg"; AITotal+=5; break;
            case "6 of Diamonds": ret="row-2-col-6.jpg"; AITotal+=6; break;
            case "7 of Diamonds": ret="row-2-col-7.jpg"; AITotal+=7; break;
            case "8 of Diamonds": ret="row-2-col-8.jpg"; AITotal+=8; break;
            case "9 of Diamonds": ret="row-2-col-9.jpg"; AITotal+=9; break;
            case "10 of Diamonds": ret="row-2-col-10.jpg"; AITotal+=10; break;
            case "Jack of Diamonds": ret="row-2-col-11.jpg"; AITotal+=10; break;
            case "Queen of Diamonds": ret="row-2-col-12.jpg"; AITotal+=10; break;
            case "King of Diamonds": ret="row-2-col-13.jpg"; AITotal+=10; break;
            case "Ace of Hearts": ret="row-3-col-1.jpg"; AITotal+=11; break;
            case "2 of Hearts": ret="row-3-col-2.jpg"; AITotal+=2; break;
            case "3 of Hearts": ret="row-3-col-3.jpg"; AITotal+=3; break;
            case "4 of Hearts": ret="row-3-col-4.jpg"; AITotal+=4; break;
            case "5 of Hearts": ret="row-3-col-5.jpg"; AITotal+=5; break;
            case "6 of Hearts": ret="row-3-col-6.jpg"; AITotal+=6; break;
            case "7 of Hearts": ret="row-3-col-7.jpg"; AITotal+=7; break;
            case "8 of Hearts": ret="row-3-col-8.jpg"; AITotal+=8; break;
            case "9 of Hearts": ret="row-3-col-9.jpg"; AITotal+=9; break;
            case "10 of Hearts": ret="row-3-col-10.jpg"; AITotal+=10; break;
            case "Jack of Hearts": ret="row-3-col-11.jpg"; AITotal+=10; break;
            case "Queen of Hearts": ret="row-3-col-12.jpg"; AITotal+=10; break;
            case "King of Hearts": ret="row-3-col-13.jpg"; AITotal+=10; break;
            case "Ace of Spades": ret="row-4-col-1.jpg"; AITotal+=11; break;
            case "2 of Spades": ret="row-4-col-2.jpg"; AITotal+=2; break;
            case "3 of Spades": ret="row-4-col-3.jpg"; AITotal+=3; break;
            case "4 of Spades": ret="row-4-col-4.jpg"; AITotal+=4; break;
            case "5 of Spades": ret="row-4-col-5.jpg"; AITotal+=5; break;
            case "6 of Spades": ret="row-4-col-6.jpg"; AITotal+=6; break;
            case "7 of Spades": ret="row-4-col-7.jpg"; AITotal+=7; break;
            case "8 of Spades": ret="row-4-col-8.jpg"; AITotal+=8; break;
            case "9 of Spades": ret="row-4-col-9.jpg"; AITotal+=9; break;
            case "10 of Spades": ret="row-4-col-10.jpg"; AITotal+=10; break;
            case "Jack of Spades": ret="row-4-col-11.jpg"; AITotal+=10; break;
            case "Queen of Spades": ret="row-4-col-12.jpg"; AITotal+=10; break;
            case "King of Spades": ret="row-4-col-13.jpg"; AITotal+=10; break;
            default: System.out.println("Error in Converter"); break;
        }
        return ret;
    }

    public void updateScreen() {
        root.setPadding(new Insets(15,15,15,25));
        root.setSpacing(10);
        root.setStyle("-fx-background-color: #800000");
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