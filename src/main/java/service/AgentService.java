package service;

import java.util.*;
import model.Agent;
import model.Parcel;
import datastructures.AgentQueue;
import exceptions.NoAgentAvailableException;

// service managing agent registration and fifo parcel assignment
public class AgentService {
    private AgentQueue agentQueue;
    private List<Agent> allAgents;
    private ParcelService parcelService;

    public AgentService(ParcelService ps) {
        this.agentQueue = new AgentQueue();
        this.allAgents = new ArrayList<>();
        this.parcelService = ps;
    }

    // registers new agent and adds to fifo queue
    public void registerAgent(Agent a) {
        allAgents.add(a);
        agentQueue.enqueue(a);
    }

    // assigns next available agent to parcel using fifo queue
    public Agent assignParcelToAgent(String pid) throws NoAgentAvailableException {
        if (agentQueue.isEmpty()) {
            throw new NoAgentAvailableException("no delivery agents available in queue");
        }
        Parcel p = parcelService.getParcel(pid);
        if (p == null) {
            return null;
        }
        Agent a = agentQueue.dequeue();
        p.setAssignedAgentId(a.getId());
        a.setCurrentLoad(a.getCurrentLoad() + 1);
        agentQueue.enqueue(a);
        return a;
    }

    // returns all registered agents
    public List<Agent> getAllAgents() {
        return allAgents;
    }

    // returns the custom queue
    public AgentQueue getAgentQueue() {
        return agentQueue;
    }
}
