import org.jcsp.lang.*;

public class Fork implements  CSProcess {

    private int ID;
    protected AltingChannelInput InLeft, InRight;

    public Fork(int id, AltingChannelInput InLeft, AltingChannelInput InRight){
        this.ID = id;
        this.InLeft = InLeft;
        this.InRight = InRight;
    }


    public void run(){
        Alternative alt = new Alternative (new Guard[] {InLeft, InRight});
        final int LEFT = 0;
        final int RIGHT = 1;

        while(true){
            switch (alt.fairSelect ()) {
                case LEFT:
                    this.InLeft.read();
                    this.InLeft.read();
                    break;
                case RIGHT:
                    this.InRight.read();
                    this.InRight.read();
                    break;
              }
        }
    }
    
}
