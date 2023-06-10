package PSO;

public class Utils {

    public static final int SWARM_SIZE = 30;
    public static final int MAX_ITERATION = 100;
    public static final double C1 = 2.0; // Cognitive parameter
    public static final double C2 = 2.0; // Social parameter

    public static final int DIMENSION = 2; // Number of dimensions
    public static final double MIN_POSITION = -10.0; // Minimum position value
    public static final double MAX_POSITION = 10.0; // Maximum position value
    public static final double VMAX = 0.1 * (MAX_POSITION - MIN_POSITION); // Maximum velocity

    public static double[] positionGoal = {1.0, 2.0};

    public static double[] globalBestPosition;
    public static double globalBestFitness = Double.MAX_VALUE;

    public static Particle[] swarm;
}
