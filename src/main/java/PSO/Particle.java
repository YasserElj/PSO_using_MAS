package PSO;

import java.util.Random;

import static PSO.PSO.evaluateFitness;
import static PSO.Utils.*;

public class Particle {
    double[] position;
    double[] velocity;
    double[] personalBestPosition;
    double personalBestFitness;

    Particle() {
        position = new double[DIMENSION];
        velocity = new double[DIMENSION];
        personalBestPosition = new double[DIMENSION];

        for (int i = 0; i < DIMENSION; i++) {
            position[i] = getRandomInRange(MIN_POSITION, MAX_POSITION);
            velocity[i] = getRandomInRange(-VMAX, VMAX);
            personalBestPosition[i] = position[i];
        }


        personalBestFitness = evaluateFitness(position);
    }
    private static double getRandomInRange(double min, double max) {
        Random rand = new Random();
        return min + (max - min) * rand.nextDouble();
    }

}

