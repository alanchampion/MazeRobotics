class RoboticsSim {
    private long steps, startingCount, width, scanAmount;
    private String fileName;

    RoboticsSim(String[] args) {
        width = 10;
        scanAmount = 10;
        fileName = args[0];

        try {
            if(args.length >= 2) {
                steps = Long.parseLong(args[1]);
                if(args.length > 2) width = Long.parseLong(args[2]);
                if(args.length > 3) scanAmount = Long.parseLong(args[3]);
            } else {
                steps = -1;
            }

            startingCount = steps;

        } catch(NumberFormatException e) {
            System.out.println("Please input the step count as a number.");
            System.exit(1);
        }
    }

    String run() {
        System.out.println("Running robotics simulation\n");

        MapLayout layout = new MapLayout(fileName, width, scanAmount);

        while(true) {
            if(layout.step()) break;
            /*if((startingCount - steps) % 1000000 == 0) {
                System.out.println("Step: " + (startingCount - steps));
            }*/
            steps--;
            if(steps == 0) break;
        }
        System.out.println("\nFinished simulation\n");
        System.out.println("Time: " + (startingCount - steps) + "\n");
        return(layout.getSolution());
    }
}
