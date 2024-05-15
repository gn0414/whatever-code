package cn.simon.basic;

import java.util.*;

public class Person {

    private User user;

    private String id;

    private String name;

    private String[] emails;

    private Set<String> tels;

    private List<String> address;

    private Map<String,String> qqs;

    private Properties p;

    public void setP(Properties p) {
        this.p = p;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Properties getP() {
        return p;
    }

    public void setQqs(Map<String, String> qqs) {
        this.qqs = qqs;
    }

    public Map<String, String> getQqs() {
        return qqs;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public List<String> getAddress() {
        return address;
    }


    public void setTels(Set<String> tels) {
        this.tels = tels;
    }


    public Set<String> getTels() {
        return tels;
    }


    public void setEmails(String[] emails) {
        this.emails = emails;
    }

    public String[] getEmails() {
        return emails;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public Person(){
        System.out.println("Person.Person");
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "user=" + user +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", emails=" + Arrays.toString(emails) +
                ", tels=" + tels +
                ", address=" + address +
                ", qqs=" + qqs +
                ", p=" + p +
                '}';
    }

    public String getId() {
        return id;
    }
}
