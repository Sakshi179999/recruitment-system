package com.example.recruitmentsystem.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.recruitmentsystem.model.Profile;
import com.example.recruitmentsystem.model.User;
import com.example.recruitmentsystem.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ResumeService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ResumeService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public void uploadResume(MultipartFile file, String email) throws IOException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"Applicant".equals(user.getUserType())) {
            throw new RuntimeException("Only applicants can upload resumes");
        }

        // Save file locally
        String fileName = user.getId() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/" + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        // Parse resume using third-party API
        JsonNode parsedData = parseResume(file);

        // Update profile
        Profile profile = user.getProfile();
        if (profile == null) {
            profile = new Profile();
            profile.setApplicant(user);
            user.setProfile(profile);
        }
        profile.setResumeFileAddress(path.toString());
        profile.setSkills(extractSkills(parsedData));
        profile.setEducation(extractEducation(parsedData));
        profile.setExperience(extractExperience(parsedData));
        profile.setName(parsedData.get("name").asText());
        profile.setEmail(parsedData.get("email").asText());
        profile.setPhone(parsedData.get("phone").asText());

        userRepository.save(user);
    }

    private JsonNode parseResume(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set("apikey", "0bWeisRWoLj3UdXt3MXMSMWptYFIpQfS");

        HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.apilayer.com/resume_parser/upload",
                HttpMethod.POST,
                entity,
                String.class
        );

        return objectMapper.readTree(response.getBody());
    }

    private String extractSkills(JsonNode data) {
        List<String> skills = new ArrayList<>();
        if (data.has("skills")) {
            for (JsonNode skill : data.get("skills")) {
                skills.add(skill.asText());
            }
        }
        return String.join(", ", skills);
    }

    private String extractEducation(JsonNode data) {
        List<String> educations = new ArrayList<>();
        if (data.has("education")) {
            for (JsonNode edu : data.get("education")) {
                educations.add(edu.get("name").asText());
            }
        }
        return String.join(", ", educations);
    }

    private String extractExperience(JsonNode data) {
        List<String> experiences = new ArrayList<>();
        if (data.has("experience")) {
            for (JsonNode exp : data.get("experience")) {
                experiences.add(exp.get("name").asText() + " (" + exp.get("dates").asText() + ")");
            }
        }
        return String.join(", ", experiences);
    }
}
