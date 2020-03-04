package com.limelight.test;

import com.limelight.server.Application;
import com.limelight.server.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.limelight.test.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class of tests that test MainController functionality.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    /**
     * Test to ensure that user sign up flow works as intended and that new user is persisted to database.
     *
     * @throws Exception if POST request fails
     */
    @Test
    public void testSignUp() throws Exception {
        mockMvc.perform(post("/app/signup")
                .param("userName", TEST_USER_NAME)
                .param("firstName", TEST_FIRST_NAME)
                .param("lastName", TEST_LAST_NAME)
                .param("email", TEST_EMAIL)
                .param("password", TEST_PASSWORD)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test to ensure that user login flow works as intended and that valid user key is returned.
     *
     * @throws Exception if POST request fails
     */
    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(post("/app/login")
                .param("userName", TEST_USER_NAME)
                .param("password", TEST_PASSWORD)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(TEST_USER_NAME.hashCode())))
                .andExpect(status().isOk());
    }

    /**
     * Test to ensure that user profile edit flow works as intended and that new user attribute is persisted to
     * database.
     *
     * @throws Exception if POST request fails
     */
    @Test
    public void testEditProfile() throws Exception {
        mockMvc.perform(post("/app/edit")
                .param("userName", TEST_USER_NAME)
                .param("key", String.valueOf(TEST_USER_NAME.hashCode()))
                .param("attribute", "EMAIL")
                .param("value", "new_email@ucla.edu")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"))
                .andExpect(status().isOk());
    }

    /**
     * Test to ensure that correct user is returned when calling the /getUser endpoint.
     *
     * @throws Exception if GET request fails
     */
    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(get("/app/getUser")
                .param("userName", TEST_USER_NAME)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(EXPECTED_USER_JSON))
                .andExpect(status().isOk());
    }

    // TODO: move these tests to StreamControllerTest when it is created
//    @Test
//    public void testJoinStreamQueue() throws Exception {
//        mockMvc.perform(get("/app/joinStreamQueue")
//                .param("userName", TEST_USER_NAME)
//                .param("key", String.valueOf(TEST_USER_NAME.hashCode()))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().string("true"))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/app/joinStreamQueue")
//                .param("userName", TEST_USER_NAME)
//                .param("key", String.valueOf(TEST_USER_NAME.hashCode()))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().string("false"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testLeaveStreamQueue() throws Exception {
//        mockMvc.perform(get("/app/joinStreamQueue")
//                .param("userName", TEST_USER_NAME)
//                .param("key", String.valueOf(TEST_USER_NAME.hashCode()))
//                .accept(MediaType.APPLICATION_JSON))
//                //.andExpect(content().string("true"))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/app/leaveStreamQueue")
//                .param("userName", TEST_USER_NAME)
//                .param("key", String.valueOf(TEST_USER_NAME.hashCode()))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().string("true"))
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/app/leaveStreamQueue")
//                .param("userName", TEST_USER_NAME)
//                .param("key", String.valueOf(TEST_USER_NAME.hashCode()))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().string("false"))
//                .andExpect(status().isOk());
//    }
}
