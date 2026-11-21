package app;

import java.util.*;
import model.Agent;
import model.Parcel;
import model.StatusUpdate;
import service.ParcelService;
import service.BookingService;
import service.AgentService;
import service.TrackingService;
import util.IdGenerator;

// main application terminal entry point
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ParcelService ps = new ParcelService();
        TrackingService ts = new TrackingService(ps);
        BookingService bs = new BookingService(ps, ts);
        AgentService as = new AgentService(ps);

        // seed initial delivery agents
        as.registerAgent(new Agent(IdGenerator.generateAgentId(), "John Doe", "9876543210"));
        as.registerAgent(new Agent(IdGenerator.generateAgentId(), "Alice Smith", "9123456780"));
        as.registerAgent(new Agent(IdGenerator.generateAgentId(), "Bob Wilson", "9988776655"));

        boolean run = true;

        while (run) {
            System.out.println("\n=== courier tracking system ===");
            System.out.println("1. book parcel");
            System.out.println("2. cancel parcel");
            System.out.println("3. assign delivery agent");
            System.out.println("4. update parcel status");
            System.out.println("5. view tracking history");
            System.out.println("6. view all parcels");
            System.out.println("7. view all agents");
            System.out.println("8. exit");
            System.out.print("enter choice: ");

            String line = sc.nextLine().trim();
            if (!line.matches("\\d+")) {
                System.out.println("invalid choice please enter a number between 1 and 8");
                continue;
            }

            int ch = Integer.parseInt(line);

            switch (ch) {
                case 1:
                    // handle parcel booking
                    System.out.print("enter sender name: ");
                    String snd = sc.nextLine().trim();
                    if (snd.isEmpty()) {
                        System.out.println("sender name cannot be empty");
                        break;
                    }
                    System.out.print("enter receiver name: ");
                    String rcv = sc.nextLine().trim();
                    if (rcv.isEmpty()) {
                        System.out.println("receiver name cannot be empty");
                        break;
                    }
                    System.out.print("enter parcel weight in kg: ");
                    String wtStr = sc.nextLine().trim();
                    if (!wtStr.matches("\\d+(\\.\\d+)?") || Double.parseDouble(wtStr) <= 0) {
                        System.out.println("invalid weight: must be a positive number");
                        break;
                    }
                    double wt = Double.parseDouble(wtStr);
                    Parcel p = bs.bookParcel(snd, rcv, wt);
                    System.out.println("parcel booked successfully with id: " + p.getId());
                    break;

                case 2:
                    // handle parcel cancellation
                    System.out.print("enter parcel id to cancel: ");
                    String cpid = sc.nextLine().trim();
                    bs.cancelParcel(cpid);
                    break;

                case 3:
                    // handle agent assignment using fifo queue
                    System.out.print("enter parcel id to assign: ");
                    String apid = sc.nextLine().trim();
                    Parcel target = ps.getParcel(apid);
                    if (target == null) {
                        System.out.println("parcel not found with id: " + apid);
                        break;
                    }
                    if (target.getAssignedAgentId() != null) {
                        System.out.println("parcel " + apid + " is already assigned to agent: " + target.getAssignedAgentId());
                        break;
                    }
                    Agent ag = as.assignParcelToAgent(apid);
                    if (ag == null) {
                        System.out.println("assignment failed: no delivery agents available in queue");
                    } else {
                        System.out.println("assigned to agent: " + ag.getName() + " (" + ag.getId() + ")");
                    }
                    break;

                case 4:
                    // handle parcel status update
                    System.out.print("enter parcel id to update: ");
                    String upid = sc.nextLine().trim();
                    Parcel upParcel = ps.getParcel(upid);
                    if (upParcel == null) {
                        System.out.println("parcel not found with id: " + upid);
                        break;
                    }
                    System.out.println("current status: " + upParcel.getStatus());
                    System.out.println("lifecycle: Booked -> Picked Up -> In Transit -> Out for Delivery -> Delivered");
                    System.out.print("enter target status: ");
                    String nstat = sc.nextLine().trim();
                    ts.updateStatus(upid, nstat);
                    break;

                case 5:
                    // handle tracking history viewing
                    System.out.print("enter parcel id to view history: ");
                    String hpid = sc.nextLine().trim();
                    Parcel hParcel = ps.getParcel(hpid);
                    if (hParcel == null) {
                        System.out.println("parcel not found with id: " + hpid);
                        break;
                    }
                    System.out.println("\n--- parcel details ---");
                    System.out.println("id: " + hParcel.getId());
                    System.out.println("sender: " + hParcel.getSender() + " | receiver: " + hParcel.getReceiver());
                    System.out.println("weight: " + hParcel.getWeight() + " kg | status: " + hParcel.getStatus());
                    String aid = hParcel.getAssignedAgentId();
                    System.out.println("assigned agent: " + (aid != null ? aid : "unassigned"));
                    System.out.println("\n--- tracking audit history ---");
                    List<StatusUpdate> history = ts.getHistory(hpid);
                    if (history.isEmpty()) {
                        System.out.println("no tracking updates found");
                    } else {
                        for (int i = 0; i < history.size(); i++) {
                            StatusUpdate su = history.get(i);
                            System.out.println((i + 1) + ". [" + su.getTimestamp() + "] " + su.getStatus());
                        }
                    }
                    break;

                case 6:
                    // handle view all parcels
                    List<Parcel> allParcels = ps.getAllParcels();
                    if (allParcels.isEmpty()) {
                        System.out.println("no parcels booked yet");
                    } else {
                        System.out.println("\n--- all parcels ---");
                        for (int i = 0; i < allParcels.size(); i++) {
                            Parcel pr = allParcels.get(i);
                            String aidStr = pr.getAssignedAgentId();
                            System.out.println((i + 1) + ". id: " + pr.getId() + " | sender: " + pr.getSender() + " | receiver: " + pr.getReceiver() + " | weight: " + pr.getWeight() + " kg | status: " + pr.getStatus() + " | agent: " + (aidStr != null ? aidStr : "unassigned"));
                        }
                    }
                    break;

                case 7:
                    // handle view all delivery agents
                    List<Agent> allAgents = as.getAllAgents();
                    if (allAgents.isEmpty()) {
                        System.out.println("no delivery agents registered");
                    } else {
                        System.out.println("\n--- all delivery agents ---");
                        for (int i = 0; i < allAgents.size(); i++) {
                            Agent a = allAgents.get(i);
                            System.out.println((i + 1) + ". " + a.displayInfo());
                        }
                    }
                    break;

                case 8:
                    // exit application
                    System.out.println("exiting system, goodbye.");
                    run = false;
                    break;

                default:
                    System.out.println("invalid choice please select between 1 and 8");
                    break;
            }
        }
        sc.close();
    }
}
