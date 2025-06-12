package Models;

import java.io.Serializable;

/*hwnglesauveur*/
public class Node implements Serializable{
    
    Guest info;
    Node next;
    
    Node () {}
    Node (Guest info){
        this.info = info;
        this.next = null;
    }

}
