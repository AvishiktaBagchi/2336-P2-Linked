/* 
 * Author: Avi Bagchi
 */
public class Message extends Event
{
    private Host host; // source host
    private Host destinationHost; // destination host
    private int destAddress; // destination address
    private int distanceBtwnHosts = 0; // distance between source and destination hosts
    private String stringMessage; // string message 

    public Message(Host host, int destAddress, String stringMessage)
    {
        this.host = host; // sender host
        this.destAddress = destAddress; // destination host's address 
        this.stringMessage = stringMessage; // string message 
    }
    /**
     * Sets the insertion time and arrival time for this Event.
     * <br>
     * It is assumed that any information needed to compute the arrival time from the insertion time is passed into
     * the Event's constructor (for example a duration).  This method should be called from within the FutureEventList's
     * insert method.
     * @param currentTime the current simulation time
     */
    public void setInsertionTime(int currentTime)
    {
        this.insertionTime = currentTime; // current simulation time
        this.arrivalTime = this.insertionTime + distanceBtwnHosts; // 1 distance = 1 simulation time
    }

    @Override
    public void cancel()
    {
        //leave empty, messages cannot be cancelled 
        System.out.println("ERROR: Cannot cancel Message event");
    }

    @Override
    public void handle()
    {
        this.host.receive(this); // calls the recieve method of host
    }

    /* 
     * Returns string representing the network message
    */
    public String getMessage()
    {
        //could be either ping request or ping response
        return stringMessage;
    }

    /*
     * Returns int representing sender host of the message
     */
    public int getSrcAddress()
    {
        return this.host.getHostAddress(); // gets the address of host
    }

    /*
     * Returns int representing the recieving host of the message
     */
    public int getDestAddress()
    {
        return destAddress;
    }

    /*
     * Setter method that sets the destination host (recieving host)
     * and distance between source and destination host
     */
    public void setNextHop(Host destinationHost, int distance)
    {
         this.destinationHost = destinationHost;
         distanceBtwnHosts = distance;
    }

    // returns host destination host
    public Host getDestinationHost()
    {
        return destinationHost;
    }

    // returs distance between the two hosts
    public int getDistance()
    {
        return distanceBtwnHosts;
    }
}
