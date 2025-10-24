package com.example.recruitmentsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User applicant;

    private String resumeFileAddress;
    private String skills;
    private String education;
    private String experience;
    private String name;
    private String email;
    private String phone;

    // Constructors
    public Profile() {}

    public Profile(User applicant, String resumeFileAddress, String skills, String education, String experience, String name, String email, String phone) {
        this.applicant = applicant;
        this.resumeFileAddress = resumeFileAddress;
        this.skills = skills;
        this.education = education;
        this.experience = experience;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getApplicant() { return applicant; }
    public void setApplicant(User applicant) { this.applicant = applicant; }

    public String getResumeFileAddress() { return resumeFileAddress; }
    public void setResumeFileAddress(String resumeFileAddress) { this.resumeFileAddress = resumeFileAddress; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
