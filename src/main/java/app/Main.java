package app;

import java.util.*;
import model.Agent;
import model.Parcel;
import service.ParcelService;
import service.BookingService;
import service.AgentService;
import service.TrackingService;
import util.IdGenerator;
import exceptions.InvalidParcelException;
import exceptions.NoAgentAvailableException;

// main application terminal entry point
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ParcelService ps = new ParcelService();
        BookingService bs = new BookingService(ps);
        AgentService as = new AgentService(ps);
        TrackingService ts = new TrackingService(ps);

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
            System.out.println("6. exit");
            System.out.print("enter choice: ");

            int ch = -1;
            ch = sc.nextInt();
            switch (ch) {
                case 1:
                    // handle parcel booking
                    System.out.print("enter sender name: ");
                    String snd = sc.nextLine().trim();
                    System.out.print("enter receiver name: ");
                    String rcv = sc.nextLine().trim();
                    double wt = -1;
                    while (wt <= 0) {
                        System.out.print("enter parcel weight in kg: ");
                        try {
                            wt = Double.parseDouble(sc.nextLine().trim());
                            if (wt <= 0) {
                                System.out.println("weight must be greater than zero");
                            }
                        } catch (Exception e) {
                            System.out.println("invalid number please enter positive weight");
                        }
                    }
                    Parcel p = bs.bookParcel(snd, rcv, wt);
                    System.out.println("parcel booked successfully with id: " + p.getId());
                    break;

                case 2:
                    // handle parcel cancellation
                    System.out.print("enter parcel id to cancel: ");
                    String cpid = sc.nextLine().trim();
                    try {
                        bs.cancelParcel(cpid);
                        System.out.println("parcel " + cpid + " cancelled successfully");
                    } catch (InvalidParcelException e) {
                        System.out.println("cancellation failed: " + e.getMessage());
                    }
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
                    Agent ag = as.assignParcelToAgent(apid);
                    System.out.println("assigned to agent: " + ag.getName() + " (" + ag.getId() + ")");
                    break;

                case 4:
                    System.out.println("status update selected");
                    break;

                case 5:
                    System.out.println("tracking history selected");
                    break;

                case 6:
                    System.out.println("exiting system goodbye");
                    run = false;
                    break;

                default:
                    System.out.println("invalid choice please select between 1 and 6");
                    break;
            }
        }
        sc.close();
    }
}
