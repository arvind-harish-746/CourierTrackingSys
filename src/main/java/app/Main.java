package app;

import java.util.*;
import model.Agent;
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
                    System.out.println("parcel booking selected");
                    break;
                case 2:
                    System.out.println("parcel cancellation selected");
                    break;
                case 3:
                    System.out.println("agent assignment selected");
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
