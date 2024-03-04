import org.jcsp.lang.*;

public class DiningRoom implements CSProcess {
    
    protected AltingChannelInput down, up;
    protected int maxSitting;

    public DiningRoom(int maxSitting, AltingChannelInput down, AltingChannelInput up){
        this.down = down;
        this.up = up;
        this.maxSitting = maxSitting;
    }

    public void run(){
        Alternative alt = new Alternative (new Guard[] {down, up});
        boolean[] precondition = {true, true};
        final int DOWN = 0;
        final int UP = 1;

        int nSitting = 0;

        while (true) {
            precondition[DOWN] = (nSitting < maxSitting);
            switch (alt.fairSelect (precondition)) {
                case DOWN:
                    down.read ();
                    nSitting++;
                    break;
                case UP:
                    up.read ();
                    nSitting--;
                    break;
                }
            }
        }
  
}
