package com.example.recruitmentsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.recruitmentsystem.service.ResumeService;

@RestController
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/uploadResume")
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file, Authentication authentication) {
        try {
            resumeService.uploadResume(file, authentication.getName());
            return ResponseEntity.ok("Resume uploaded and processed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error uploading resume: " + e.getMessage());
        }
    }
}
