package cn.simon.entity;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;

    private String name;

    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
