package PSO;

import java.util.Arrays;
import java.util.Random;

import PSO.Utils.*;

import static PSO.Utils.*;

public class PSO {


    public static double evaluateFitness(double[] position) {
        // TODO: Implement your fitness function here
        // This function should evaluate the fitness of a given position
        // and return a fitness value.
        // The fitness value can be based on the objective function you are trying to optimize.
        // Lower fitness values indicate better solutions.

        // As an example, let's calculate the Euclidean distance between the position and the goal position
        double fitness = 0.0;
        for (int i = 0; i < DIMENSION; i++) {
            double diff = position[i] - positionGoal[i];
            fitness += diff * diff;
        }

        return fitness;
    }


    private static void updateGlobalBest() {
        for (Particle particle : swarm) {
            if (particle.personalBestFitness < globalBestFitness) {
                globalBestFitness = particle.personalBestFitness;
                globalBestPosition = particle.personalBestPosition;
            }
        }
    }

    private static void updateParticleVelocityAndPosition(Particle particle) {
        for (int i = 0; i < DIMENSION; i++) {
            double r1 = Math.random();
            double r2 = Math.random();

            double cognitiveComponent = C1 * r1 * (particle.personalBestPosition[i] - particle.position[i]);
            double socialComponent = C2 * r2 * (globalBestPosition[i] - particle.position[i]);

            particle.velocity[i] += cognitiveComponent + socialComponent;

            // Apply velocity limits
            if (particle.velocity[i] > VMAX) {
                particle.velocity[i] = VMAX;
            } else if (particle.velocity[i] < -VMAX) {
                particle.velocity[i] = -VMAX;
            }

            particle.position[i] += particle.velocity[i];

            // Apply position limits
            if (particle.position[i] > MAX_POSITION) {
                particle.position[i] = MAX_POSITION;
            } else if (particle.position[i] < MIN_POSITION) {
                particle.position[i] = MIN_POSITION;
            }
        }

        double fitness = evaluateFitness(particle.position);

        if (fitness < particle.personalBestFitness) {
            particle.personalBestFitness = fitness;
            System.arraycopy(particle.position, 0, particle.personalBestPosition, 0, DIMENSION);
        }
    }

    public static void main(String[] args) {

        System.out.println("Position Goal: " + Arrays.toString(positionGoal));
        swarm = new Particle[SWARM_SIZE];
        globalBestPosition = new double[DIMENSION];

        // Initialize the swarm
        for (int i = 0; i < SWARM_SIZE; i++) {
            swarm[i] = new Particle();
        }

        // Perform optimization iterations
        for (int iteration = 0; iteration < MAX_ITERATION; iteration++) {
            updateGlobalBest();

            // Print the positions of each particle in this iteration
            System.out.println("Iteration: " + (iteration + 1));
            for (int i = 0; i < SWARM_SIZE; i++) {
                System.out.println("PSO.Particle " + (i + 1) + " Position: " + Arrays.toString(swarm[i].position));
            }

            for (Particle particle : swarm) {
                updateParticleVelocityAndPosition(particle);
            }

            // Print the best fitness value in this iteration
            System.out.println("Iteration: " + (iteration + 1) + ", Best Fitness: " + globalBestFitness);
        }

        // Print the final best position and fitness
        System.out.println("Final Best Position: " + Arrays.toString(globalBestPosition));
        System.out.println("Final Best Fitness: " + globalBestFitness);
    }
}
