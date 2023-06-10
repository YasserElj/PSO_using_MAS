# Particle Swarm Optimization (PSO) Multi-Agent System (MAS)
This repository contains an implementation of a Particle Swarm Optimization (PSO) Multi-Agent System (MAS). The PSO algorithm is a population-based optimization algorithm inspired by the social behavior of bird flocking or fish schooling. In this MAS implementation, multiple agents, called particles, collaborate to find the optimal solution to a given problem.

#System Architecture
The PSO MAS system follows a distributed architecture, where each island agent operates independently. The system consists of the following components:

##Island Agent: 
Each island agent is an instance of the IslandAgent class. It coordinates the optimization process for its local swarm of particles. The island agent initializes the swarm, updates the particles' positions and velocities, and determines the global best fitness and position.
##Particle: 
Each particle is an instance of the Particle class. It represents an individual in the swarm and maintains its position, velocity, personal best position, and personal best fitness.

# Results
We used PSO to find our position goal
```java
public static double[] positionGoal = {1.0, 2.0};
```
# The normal implementation
![image](https://github.com/YasserElj/PSO_using_MAS/assets/61060853/7fe8de86-1051-48f9-8ad6-295c71624b3a)

# The MAS implementation
![image](https://github.com/YasserElj/PSO_using_MAS/assets/61060853/7c78a8ca-6f47-46b8-9711-2d381378db10)


![image](https://github.com/YasserElj/PSO_using_MAS/assets/61060853/24d4aef9-8547-4c1f-835a-7fe2b6f9f5a0)


# Dependencies
The PSO MAS implementation has the following dependencies:

JADE (Java Agent Development Framework)
