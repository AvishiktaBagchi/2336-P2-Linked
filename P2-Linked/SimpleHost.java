/* 
 * Author: Avi Bagchi
 */
public class SimpleHost extends Host
{
    Message hostMessage; // sending host's message
    Message response; // response of reciving host
    private int localInterval = -1; // local variable to keep track of interval
    private int destinationAdd = -1; // destination address

    public SimpleHost() // constructor for SimpleHost
    {
        super();
    }

    public void receive(Message msg)
    {
        if(msg.getMessage().equals("Host " + this.getHostAddress() + ": Sent ping to host " + destinationAdd))
        {
            System.out.println("[" + this.getCurrentTime() + " ts]" + " Host " + destinationAdd + 
                                ": Ping request from host " + this.getHostAddress());

            String responseMsg = "Host " + this.getHostAddress() + ": Ping response from host " + destinationAdd; 

            response = new Message(this, destinationAdd, responseMsg); // makes a new response message
            response.setInsertionTime(this.getCurrentTime()); // sets the insertion time for the message
            sendToNeighbor(response); // sends the message to the neighbor 
        }
        else
        {
            // Calculates the round trip time
            System.out.print("[" + this.getCurrentTime() + " ts] ");
            System.out.println("Host " + this.getHostAddress() + ": Ping response from host " + destinationAdd
                                + " (RTT = " + (2 * msg.getDistance()) + " ts)");
        }
        
    }

    /**
     * This is called after a Timer event expires, and enables you to write code to do something upon timer
     * expiry.  A timer expires when the duration set for the timer is reached.
     *
     * @param eventId the event id of the Timer event which expired
     */
    public void timerExpired(int eventId)
    {
        System.out.println("[" + this.getCurrentTime()  + " ts] " + hostMessage.getMessage());
        hostMessage.setInsertionTime(this.getCurrentTime()); // sets the insertion time
        sendToNeighbor(hostMessage); //send ping request since time has advanced by interval, message added to fel
        newTimer(localInterval); // makes a new interval timer
    }

    /**
     * This is called after a Timer event is cancelled, and enables you to write code to do something upon timer
     * cancellation.
     *
     * @param eventId the event id of the Timer event which was cancelled
     */
    public void timerCancelled(int eventId)
    {
        System.out.println("[" + this.getCurrentTime() + " ts] " + "Host " + this.getHostAddress() + ": Stopped sending pings");
    }

    /*
     * @return: id of the duration Timer
     */
    public int sendPings(int destAddress, int interval, int duration)
    {        
        Host local = this; // refers to this instance of SimpleHost
        newTimer(interval); // timer 1: interval of ping sending

        int durationID = newTimer(duration); // timer 2: total duration before stopping

        String stringMessage = "Host " + this.getHostAddress() + ": Sent ping to host " + destAddress;
        hostMessage = new Message(local, destAddress, stringMessage);
        setInterval(interval); // calls private method that sets interval
        setDestinationAdd(destAddress); // calls private methid that sets destination address
    
        return durationID;
    }

    private void setInterval(int localInterval) // sets interval for this class
    {
        this.localInterval = localInterval;
    }

    private void setDestinationAdd(int destinationAdd) // sets destination address for this class
    {
        this.destinationAdd = destinationAdd;
    }
}
