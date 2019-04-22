import java.util.Random;

public class Deck {
    private String[] deck = new String[52];
    private int num=0;
    public Deck() {
        int id=0;
        String[] suit = {"Clubs","Hearts","Spades","Diamonds"};
        String[] number = {"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};
        for(int i=0;i<4;i++) {
            for(int j=0;j<13;j++) {
                deck[id]=number[j]+" of "+suit[i];
                id++;
            }
        }
        shuffle();
    }

    public void shuffle() {
        Random rand = new Random();
        int rand1=0;
        int rand2=0;
        String temp="";
        num=0;
        for(int i = 0;i<20000000;i++) {
            rand1=rand.nextInt(52);
            rand2=rand.nextInt(52);
            temp=deck[rand1];
            deck[rand1]=deck[rand2];
            deck[rand2]=temp;
        }
    }

    public String drawCard() {
        if(num==51) {
            shuffle();
        }
        num++;
        return deck[num];
    }
}