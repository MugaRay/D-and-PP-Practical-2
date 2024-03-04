import org.jcsp.lang.*;

public class Room implements CSProcess {
    
    public void run(){
        int nPhilosophers = 5;
        final One2OneChannel[] left = Channel.one2oneArray (nPhilosophers);
        final One2OneChannel[] right = Channel.one2oneArray(nPhilosophers);

        final Any2OneChannel down = Channel.any2one ();
        final Any2OneChannel up = Channel.any2one ();

        
        // creating the fork classes and list
        final Fork[] fork = new Fork[nPhilosophers];
        for (int i = 0; i < nPhilosophers; i++) { // change once you implement rooms 
            fork[i] = new Fork (i,
                                left[i].in (), right[(i + 1)%nPhilosophers].in() );
          }

        // creating the philosper class
        final Phil[] Philosophers = new Phil[nPhilosophers];
        for (int i = 0; i < nPhilosophers; i++) {
            Philosophers[i] = new Phil(i, left[i].out(),  right[i].out(),  down.out(), up.out());
        }

        new Parallel (
            new CSProcess[] {
                new Parallel (Philosophers),
                new Parallel (fork),
                new DiningRoom(nPhilosophers-1, down.in(), up.in())
            }
    ).run ();

    }

    public static void main(String[] args) {
        new Parallel (
            new CSProcess[] {
                new Room()
            }).run ();
}

}
