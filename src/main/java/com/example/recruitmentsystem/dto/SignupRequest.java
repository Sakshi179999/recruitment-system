package com.example.recruitmentsystem.dto;

public class SignupRequest {
    private String name;
    private String email;
    private String address;
    private String userType;
    private String password;
    private String profileHeadline;

    // Constructors
    public SignupRequest() {}

    public SignupRequest(String name, String email, String address, String userType, String password, String profileHeadline) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.userType = userType;
        this.password = password;
        this.profileHeadline = profileHeadline;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getProfileHeadline() { return profileHeadline; }
    public void setProfileHeadline(String profileHeadline) { this.profileHeadline = profileHeadline; }
}
