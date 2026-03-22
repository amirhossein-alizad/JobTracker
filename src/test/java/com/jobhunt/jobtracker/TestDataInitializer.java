package com.jobhunt.jobtracker;

import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.Note;
import com.jobhunt.jobtracker.domain.Status;
import com.jobhunt.jobtracker.domain.User;
import com.jobhunt.jobtracker.repository.ApplicationRepository;
import com.jobhunt.jobtracker.repository.NoteRepository;
import com.jobhunt.jobtracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class TestDataInitializer {

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final NoteRepository noteRepository;

    public void populate() {
        // users
        User amir = new User();
        amir.setUsername("amir");

        User alice = new User();
        alice.setUsername("alice");

        userRepository.saveAll(List.of(amir, alice));

        // applications for amir
        Application nutanix = new Application();
        nutanix.setUser(amir);
        nutanix.setCompany("Nutanix");
        nutanix.setRoleTitle("Software Engineer");
        nutanix.setLocation("Vancouver, BC");
        nutanix.setStatus(Status.APPLIED);
        nutanix.setSource("LinkedIn");
        nutanix.setAppliedDate(LocalDate.of(2026, 3, 4));
        nutanix.setJobUrl("https://example.com/nutanix");
        nutanix.setSalaryMin(90000);
        nutanix.setSalaryMax(120000);

        Application bmo = new Application();
        bmo.setUser(amir);
        bmo.setCompany("BMO");
        bmo.setRoleTitle("Backend Developer");
        bmo.setLocation("Toronto, ON");
        bmo.setStatus(Status.INTERVIEW);
        bmo.setSource("Referral");
        bmo.setAppliedDate(LocalDate.of(2026, 3, 1));
        bmo.setJobUrl("https://example.com/bmo");
        bmo.setSalaryMin(95000);
        bmo.setSalaryMax(125000);

        // applications for alice
        Application shopify = new Application();
        shopify.setUser(alice);
        shopify.setCompany("Shopify");
        shopify.setRoleTitle("Software Engineer");
        shopify.setLocation("Remote");
        shopify.setStatus(Status.SCREENING);
        shopify.setSource("Company Website");
        shopify.setAppliedDate(LocalDate.of(2026, 2, 25));
        shopify.setJobUrl("https://example.com/shopify");
        shopify.setSalaryMin(100000);
        shopify.setSalaryMax(135000);

        applicationRepository.saveAll(List.of(nutanix, bmo, shopify));

        // notes
        Note note1 = new Note();
        note1.setApplication(nutanix);
        note1.setText("Applied through LinkedIn. Need to follow up in one week.");

        Note note2 = new Note();
        note2.setApplication(bmo);
        note2.setText("Referral submitted. Recruiter screen completed.");

        Note note3 = new Note();
        note3.setApplication(bmo);
        note3.setText("Prepare for system design and Spring Boot questions.");

        Note note4 = new Note();
        note4.setApplication(shopify);
        note4.setText("Screening stage. Waiting for recruiter response.");

        noteRepository.saveAll(List.of(note1, note2, note3, note4));
    }

    public void clear() {
        noteRepository.deleteAll();
        applicationRepository.deleteAll();
        userRepository.deleteAll();
    }

    public void reset() {
        clear();
        populate();
    }
}