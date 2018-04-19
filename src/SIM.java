public class SIM {
    public static void main ( String[] args )
    {
        if( args.length < 1 ) {
            System.out.println("Not enough command arguments!");
            System.exit(1);
        }

        RoboticsSim sim = new RoboticsSim(args);
        String result = sim.run();
        System.out.println(result);

        for(int i = 0; i < 10; i++) {
            sim = new RoboticsSim(args);
            result = sim.run();
            System.out.println(result);
        }
    }
}