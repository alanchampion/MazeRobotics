public class SIM {
    public static void main ( String[] args )
    {
        if( args.length < 1 ) {
            System.out.println("Define the configuration file using a command line argument.");
            System.exit(1);
        }

        RoboticsSim sim = new RoboticsSim(args);
        String result = sim.run();
        System.out.println(result);

        /*for(int i = 0; i < 1000000; i++) {
            sim = new RoboticsSim(args);
            result = sim.run();
            // System.out.println(result);
            if(result.equals("No solution found"))
                System.exit(1);
        }*/
    }
}