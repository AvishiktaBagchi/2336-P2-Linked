/* Driver for the simulation
 * Author: Avi Bagchi
 */

import java.util.Scanner;
import java.io.*;
import java.util.Map;
import java.util.HashMap;

public class Main 
{
    public static void main(String[] args) throws Exception
    {
        String filename; // name of the file to be read
        Scanner scnr = new Scanner(System.in); //Scanner for user input

        System.out.print("\nEnter filename: ");
        filename = scnr.next(); 
        System.out.println(); // empty line after entering filename

        File openFile = new File(filename); // opening the file
        Scanner filescan = new Scanner(openFile); //connecting the scanner and the file

        FutureEventList fel = new LinkedEventList(); // future event list created
        Map<Integer, SimpleHost> localHosts = new HashMap<>();
        Map<Integer, SimpleHost> sendersMap = new HashMap<>();

        int centralHostAddress = filescan.nextInt(); // gets the address of the central host
        SimpleHost central = new SimpleHost();
        central.setHostParameters(centralHostAddress, fel);
        central.addNeighbor(central, 0); // adds a host as its own neighbout with 0 distance
        localHosts.put(centralHostAddress, central); //local map to keep track of hosts
        
        SimpleHost neighbor; // local variable to create network of hosts
        int distance = -1; // local variable to keep trasck of distance
        int token = 0; // local variable to keep track of filescan
        int durationTimerID = -1; // id of duration timer, needed to stop the simulation
        SimpleHost sender = null; // local variable for sender host
        int localDurationTimer = -1; 
        
        while(filescan.hasNext()) // creates network of hosts by adding neighbors
        {
            token = filescan.nextInt();

            if(token != -1)
            {
                neighbor = new SimpleHost(); // new SimpleHost created
                neighbor.setHostParameters(token, fel); // sets the parameters for the new neighbours 
                distance = filescan.nextInt(); // grabs the distance 

                central.addNeighbor(neighbor, distance); // adds the neighbor to the central host
                neighbor.addNeighbor(central, distance); // adds the distance from the central host
                localHosts.put(token, neighbor); //local map to keep track of hosts
            }
            else
            {
                break;
            }
        }
        

        while(filescan.hasNext()) // bootstrapping the simulation
        {
            int srcHost = filescan.nextInt(); // sender or source host
            int destHost = filescan.nextInt(); // destination or recieving host
            int interval = filescan.nextInt(); // interval of sending messages
            int duration = filescan.nextInt(); // full duration of sending messages
            
            sender = localHosts.get(srcHost); // assigns sender based on srcHost address
            durationTimerID = sender.sendPings(destHost, interval, duration); // called when simulation time = 0 
            sendersMap.put(durationTimerID, sender); // puts the durationTimeID into a local hashmap for senders
        }
        
        /* loop through fel
        // remove event with smallest arrival time
        // call handle method of removed event
        // keep looping until fel is empty
        */

         while(fel.size() != 1)
        {
            Event e = fel.removeFirst(); // removes the first Event in the fel
            localDurationTimer = -1;
            for(Map.Entry<Integer, SimpleHost> entry : sendersMap.entrySet())
            {
                localDurationTimer = entry.getKey();
                if((e.getId() == (localDurationTimer))) // if the duration timer is reached in the fel
                {
                    System.out.println("[" + fel.getSimulationTime() + " ts] " + "Host " + sender.getHostAddress() + ": Stopped sending pings");
                }
                else
                {
                    e.handle(); // handles all Events
                }
            }

        }// end while

        filescan.close();
        scnr.close();
    }
}

