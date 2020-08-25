package jpademo;

import javax.persistence.*;

@Entity(name="guestbook")
public class GuestBook {
    @Id
    public int id;
    public String title;
    public String contents;
    public String username;
    public int createtime;    
}
