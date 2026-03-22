package com.jobhunt.jobtracker.Controller;

import com.jobhunt.jobtracker.Service.ApplicationService;
import com.jobhunt.jobtracker.Service.UserService;
import com.jobhunt.jobtracker.TestDataInitializer;
import com.jobhunt.jobtracker.domain.Application;
import com.jobhunt.jobtracker.domain.Status;
import com.jobhunt.jobtracker.domain.User;
import com.jobhunt.jobtracker.exception.NotFoundException;
import com.jobhunt.jobtracker.repository.ApplicationRepository;
import com.jobhunt.jobtracker.repository.NoteRepository;
import com.jobhunt.jobtracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private TestDataInitializer testDataInitializer;

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    public void setup() {
        User amir = new User();
        amir.setUsername("amir");


        userRepository.save(amir);

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

        applicationRepository.saveAll(List.of(nutanix, bmo));
    }


    @Test
    public void testListApplications() throws Exception {
        mvc.perform(get("/applications"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].company").value("Nutanix"))
                .andExpect(jsonPath("$[1].company").value("BMO"));
    }

    @Test
    public void testGetApplicationById() throws Exception {
        Application application = applicationRepository.findAll().getFirst();
        mvc.perform(get("/applications/" + application.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company").value(application.getCompany()))
                .andExpect(jsonPath("$.roleTitle").value(application.getRoleTitle()));
    }

    @Test
    public void testGetApplicationById_NotFound() throws Exception {
        mvc.perform(get("/applications/9999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateApplication() throws Exception {
        String newAppJson = """
                {
                    "username": "amir",
                    "company": "Google",
                    "roleTitle": "Software Engineer",
                    "location": "Mountain View, CA",
                    "status": "APPLIED",
                    "source": "LinkedIn",
                    "appliedDate": "2026-04-01",
                    "jobUrl": "https://example.com/google",
                    "salaryMin": 100000,
                    "salaryMax": 150000
                }
                """;

        mvc.perform(
                        post("/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(newAppJson)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.company").value("Google"))
                .andExpect(jsonPath("$.roleTitle").value("Software Engineer"));
    }

    @Test
    public void testCreateApplicationWithInvalidUser() throws Exception {
        String newAppJson = """
                {
                    "username": "nonexistent_user",
                    "company": "Google",
                    "roleTitle": "Software Engineer",
                    "location": "Mountain View, CA",
                    "status": "APPLIED",
                    "source": "LinkedIn",
                    "appliedDate": "2026-04-01",
                    "jobUrl": "https://example.com/google",
                    "salaryMin": 100000,
                    "salaryMax": 150000
                }
                """;

        mvc.perform(
                        post("/applications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(newAppJson)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(NotFoundException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("User not found: nonexistent_user", result.getResolvedException().getMessage()));
    }
}
