package cz.uhk.fim.sportstracker.Models;

import java.util.Date;

public class User {
    private int id;
    private String login;
    private String password;
    private double weight;
    private double height;
    private Date born;
    private String gender;

    public User(int id, String login, String password, double weight, double height, Date born, String gender) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.born = born;
        this.gender = gender;
    }

    public User(String login, String password, double weight, double height, Date born, String gender) {
        this.login = login;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.born = born;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
