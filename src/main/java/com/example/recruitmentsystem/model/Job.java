package com.example.recruitmentsystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime postedOn;
    private int totalApplications;
    private String companyName;

    @ManyToOne
    @JoinColumn(name = "posted_by")
    private User postedBy;

    @ManyToMany
    @JoinTable(
        name = "job_applications",
        joinColumns = @JoinColumn(name = "job_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> applicants;

    // Constructors
    public Job() {}

    public Job(String title, String description, String companyName, User postedBy) {
        this.title = title;
        this.description = description;
        this.companyName = companyName;
        this.postedBy = postedBy;
        this.postedOn = LocalDateTime.now();
        this.totalApplications = 0;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getPostedOn() { return postedOn; }
    public void setPostedOn(LocalDateTime postedOn) { this.postedOn = postedOn; }

    public int getTotalApplications() { return totalApplications; }
    public void setTotalApplications(int totalApplications) { this.totalApplications = totalApplications; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public User getPostedBy() { return postedBy; }
    public void setPostedBy(User postedBy) { this.postedBy = postedBy; }

    public List<User> getApplicants() { return applicants; }
    public void setApplicants(List<User> applicants) { this.applicants = applicants; }
}
