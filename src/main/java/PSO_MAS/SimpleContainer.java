package PSO_MAS;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import static PSO_MAS.PSOUtils.ISLAND_NUMBER;

public class SimpleContainer {

    public static void main(String[] args) throws StaleProxyException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST,"localhost");
        AgentContainer agentContainer=runtime.createAgentContainer(profile);
        AgentController masteragent = agentContainer.createNewAgent("masteragent", MasterAgent.class.getName(), new Object[]{});
        masteragent.start();
        for (int i = 0; i < ISLAND_NUMBER; i++) {
            AgentController islandAgent = agentContainer.createNewAgent("IslandAgent"+i,IslandAgent.class.getName(),new Object[]{});
            islandAgent.start();
        }


    }
}
