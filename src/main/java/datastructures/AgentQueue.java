package datastructures;

import java.util.*;
import model.Agent;
import exceptions.NoAgentAvailableException;

// custom singly linked list queue for fifo agent assignment
public class AgentQueue {

    // internal node for linked list structure
    private class Node {
        Agent data;
        Node next;

        Node(Agent a) {
            this.data = a;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public AgentQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // adds an agent to the back of the queue
    public void enqueue(Agent a) {
        Node n = new Node(a);
        if (isEmpty()) {
            head = n;
            tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        size++;
    }

    // removes and returns the agent at front of queue
    public Agent dequeue() throws NoAgentAvailableException {
        if (isEmpty()) {
            throw new NoAgentAvailableException("no agents currently available in queue");
        }
        Agent a = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return a;
    }

    // inspects the front agent without removing
    public Agent peek() {
        if (isEmpty()) {
            return null;
        }
        return head.data;
    }

    // checks if queue has no elements
    public boolean isEmpty() {
        return head == null;
    }

    // returns the count of agents in queue
    public int getSize() {
        return size;
    }
}
