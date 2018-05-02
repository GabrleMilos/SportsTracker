package cz.uhk.fim.sportstracker.Models;

public class User {
    private int id;
    private String login;
    private String password;
    private double weight;
    private double height;

    public User(int id, String login, String password, double weight, double height) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.weight = weight;
        this.height = height;
    }

    public User(String login, String password, double weight, double height) {
        this.login = login;
        this.password = password;
        this.weight = weight;
        this.height = height;
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
}
