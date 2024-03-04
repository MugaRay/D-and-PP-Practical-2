import java.util.Random;
import org.jcsp.lang.*;
import org.jcsp.plugNplay.ProcessWrite;

public class Phil implements  CSProcess {
     
    private int ID;
    protected  ChannelOutput OutLeft, OutRight;
    ChannelOutput down, up;
    private Random rand = new Random();

    public Phil(int id, 
                ChannelOutput OutLeft,  ChannelOutput OutRight,
                ChannelOutput down, ChannelOutput up){
        this.ID = id;
        this.OutLeft = OutLeft;
        this.OutRight = OutRight;
        this.down = down;
        this.up = up; 
    }

    public void run(){
        
        final ProcessWrite signalLeft = new ProcessWrite(OutLeft);
        signalLeft.value = this.ID;
        
        final ProcessWrite signalRight = new ProcessWrite(OutRight);
        signalRight.value = this.ID;

        final CSProcess signalForks = new Parallel (new CSProcess[] {signalLeft, signalRight});

        while(true){
            try{
            System.out.println("Phil: " + this.ID + " is thinking");
            Thread.sleep(rand.nextInt(1, 100));
            this.down.write(this.ID); // sitting down in the dining room
            System.out.println("Phil: " + this.ID + " entered the room and is sitting down"); 
            signalForks.run ();  // grab both forks simultaneously
            System.out.println("Phil: " + this.ID + " is eating");
            Thread.sleep(rand.nextInt(1, 100));
            signalForks.run ();  // give up the forks
            System.out.println("Phil: " + this.ID + " is standing up and leaving the room");
            this.up.write(this.ID);
        }
        catch(InterruptedException e)
        {
            System.out.println(e);
        } 
    }
}
}
