/* 
 * Author: Avi Bagchi
 */
public class LinkedEventList implements FutureEventList
{
    private Node head;
    private static int simulationTime = 0;
    
    public LinkedEventList()
    {
        this.head = null;
    }
    
    /*
     * Removes the first Event in the linked list
     * Sets the next and previous values
     * Updates simulation time
     * Returns the Event that got removed
     */
    public Event removeFirst()
    {
        if(head == null)
        {
            return null;
        }

        Node temp = head;
        head = head.next;
        head.prev = null;
        
        simulationTime = temp.getEvent().getArrivalTime();
        return temp.getEvent();
    }

    /*
     * Removes Event e
     * Sets next and prev accordingly
     * Updates simulation time
     * Returns true if Event e exists, false otherwise
     */
    public boolean remove(Event e)
    {
        Node currNode = head;

        if(head == null) //if list is empty
        {
            return false;
        }

        if(head.getEvent().equals(e)) //if Event e is the head node
        {
            head = head.next; // sets the next element as head
            head.prev = null; // sets the new head elements prev to null
            simulationTime = head.getEvent().getArrivalTime();
            return true;
        }
        
        while(currNode != null && !currNode.getEvent().equals(e)) 
        {
            if(currNode.next == null && currNode.getEvent().equals(e)) // if Event e is the last element
            { 
                simulationTime = currNode.getEvent().getArrivalTime();
                currNode.getEvent().handle(); // handles the Event that will be removed
                currNode.prev.next = null; // sets the next pointer of the previous element = null
                return true;
            }

            if(currNode.getEvent().equals(e))
            {
                simulationTime = currNode.getEvent().getArrivalTime();
                currNode.getEvent().handle(); // handles the Event that will be removed
                currNode.prev.next = currNode.next; // sets preceding elements pointer to next Node
                currNode.next.prev = currNode.prev; // sets succeeding elements pointer to previous Node
                return true;
            }

            currNode = currNode.next;
        }

        return false;
    }

    /*
    ** Inserts events into the linked list
    ** Maintains order based on arrival time
    ** Sets insertion time == simulation time
    */
    public void insert(Event e)
    {
        Node temp = new Node(e); //Stores event e in a temporary Node
        e.setInsertionTime(simulationTime);
        if(head == null) // if linked list is empty
        {
            head = temp;
            // head.getEvent().setInsertionTime(simulationTime);
        }
        // if e.arrivalTime < head.arrivalTime
        else if(e.getArrivalTime() < head.getEvent().getArrivalTime())
        {
            temp.next = head;
            head.prev = temp;
            head = temp;
            // head.getEvent().setInsertionTime(simulationTime);
        }
        else 
        {
            Node currNode = head; //temporary node for traversal
            
            //inserting betweeen two nodes
            while(currNode.next != null)
            {
                if(e.getArrivalTime() >= currNode.getEvent().getArrivalTime() && 
                    e.getArrivalTime() < currNode.next.getEvent().getArrivalTime())
                {
                    temp.next = currNode.next;
                    currNode.next = temp;
                    temp.prev = currNode;
                    temp.next.prev = temp;
                    break;
                }
                currNode = currNode.next;
            }// end while   
            
            //inserting at the end
            if(currNode.next == null)
            {
                currNode.next = temp;
                temp.prev = currNode;
            }
        }
    }

    //Returns the current size of the linked list
    public int size()
    {
        Node temp = head;
        int size = 0;

        if(head == null) // if the list is empty
        {
            return -1;
        }

        while(temp != null) //iterates through the linked list
        {
            size++;
            temp = temp.next;
        }
        return size;
    }

    //Returns the current size of the linked list
    public int capacity()
    {
        Node temp = head;
        int cap = 0;

        if(head == null) // if the list is empty
        {
            return -1;
        }

        while(temp != null)
        {
            cap++;
            temp = temp.next;
        }
        return cap;
    }

    // Returns the current simulation time
    public int getSimulationTime()
    {
        return simulationTime;
    }

}