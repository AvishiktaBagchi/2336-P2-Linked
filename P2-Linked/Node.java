/* 
 * Author: Avi Bagchi
 */
public class Node
{
    private final Event event; // Event
    Node next; // pointer to the next node
    Node prev; // pointer to the previous node

    public Node(Event event)
    {
        this.event = event;
        this.next = null;
        this.prev = null;
    }

    public Event getEvent() // gets the event
    {
        return event; 
    }

    public Node getNext() // gets the next pointer
    {
        return next;
    }

    public Node getPrev() // gets the previous pointer
    {
        return prev;
    }
}
