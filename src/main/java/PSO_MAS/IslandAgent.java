package PSO_MAS;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;


import static PSO_MAS.PSOUtils.*;


public class IslandAgent extends Agent {


    @Override
    protected void setup() {

        SequentialBehaviour sequentialBehaviour = new SequentialBehaviour();
//        sequentialBehaviour.addSubBehaviour(new OneShotBehaviour() {
//            @Override
//            public void action() {
//                initialize();
//            }
//        });
        sequentialBehaviour.addSubBehaviour(new Behaviour() {
            int iter=0;
            @Override
            public void action() {
                if(swarm==null){
                    initialize();
                }
                optimize(iter);
                iter++;

            }

            @Override
            public boolean done() {
                return iter >= MAX_ITERATION || globalBestFitness <= 0.0001 ;
            }
        });

        sequentialBehaviour.addSubBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                DFAgentDescription dfAgentDescription=new DFAgentDescription();
                ServiceDescription serviceDescription=new ServiceDescription();
                serviceDescription.setType("pso");
                dfAgentDescription.addServices(serviceDescription);
                DFAgentDescription[] dfAgentDescriptions ;
                try {
                    dfAgentDescriptions= DFService.search(getAgent(), dfAgentDescription);
                } catch (FIPAException e) {
                    throw new RuntimeException(e);
                }
                ACLMessage aclMessage=new ACLMessage(ACLMessage.INFORM);
                aclMessage.addReceiver(dfAgentDescriptions[0].getName());
                aclMessage.setContent("Global Best Position: " + globalBestPosition[0] + ", " + globalBestPosition[1]+ " Global Best Fitness: " + globalBestFitness);
                send(aclMessage);

            }
        });
        addBehaviour(sequentialBehaviour);

    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }

    }

    private void initialize(){
        swarm = new Particle[SWARM_SIZE];
        globalBestPosition = new double[DIMENSION];

        // Initialize the swarm
        for (int i = 0; i < SWARM_SIZE; i++) {
            swarm[i] = new Particle();
        }
    }

    public static double evaluateFitness(double[] position) {
        double fitness = 0.0;
        for (int i = 0; i < DIMENSION; i++) {
            double diff = position[i] - positionGoal[i];
            fitness += diff * diff;
        }

        return fitness;
    }


    private static void updateGlobalBest() {
        for (Particle particle : swarm) {
            if (particle != null && particle.personalBestFitness < globalBestFitness) {
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

    private void optimize(int iteration){
        updateGlobalBest();

        // Print the positions of each particle in this iteration
//        System.out.println("Iteration: " + (iteration + 1));
//        for (int i = 0; i < SWARM_SIZE; i++) {
//            System.out.println("PSO.Particle " + (i + 1) + " Position: " + Arrays.toString(swarm[i].position));
//        }

        for (Particle particle : swarm) {
            updateParticleVelocityAndPosition(particle);
        }
    }

}
