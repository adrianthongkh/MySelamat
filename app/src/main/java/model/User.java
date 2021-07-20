package model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class User {

    private String username;
    private String password;
    private String email;
    private String phone;
    private List<Premises> premisesList;

    // ---------- Constructor, Getters & Setters ---------- //
    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // -------------------- End -------------------- //


    // --------------- Functions starts here --------------- //
    public void createAccount(String[] s) {
        // TODO: create account and add to database
    }

    public void resetEmail(String username, String newEmail) {
        // TODO: update Email
    }

    public void resetPass(String username, String newPass) {
        // TODO: update Password
    }

    public boolean login(String username, String password) {
        // TODO: perform login and validate from server
        return true;
    }

    public double riskPrediction(LatLng latLng) {
        // TODO: call Risk class and predict risk
        return 0.00;
    }

    public User findUser(String username) {
        // TODO: find User based on username
        return new User();
    }

    public List<Premises> getPremisesList() {
        // TODO: query database and return all premises (history)
        return premisesList;
    }
    // -------------------- End -------------------- //
}
