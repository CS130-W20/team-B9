package com.limelight.test;

import com.limelight.server.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.limelight.test.TestConstants.TEST_USER_NAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class of tests that test StreamController functionality.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class StreamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testJoinStreamQueue() throws Exception {
        mockMvc.perform(get("/stream/joinStreamQueue")
                .param("userName", TEST_USER_NAME)
                .param("key", String.valueOf(TEST_USER_NAME.hashCode()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stream/joinStreamQueue")
                .param("userName", TEST_USER_NAME)
                .param("key", String.valueOf(TEST_USER_NAME.hashCode()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLeaveStreamQueue() throws Exception {
        mockMvc.perform(get("/stream/joinStreamQueue")
                .param("userName", TEST_USER_NAME)
                .param("key", String.valueOf(TEST_USER_NAME.hashCode()))
                .accept(MediaType.APPLICATION_JSON))
                //.andExpect(content().string("true"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stream/leaveStreamQueue")
                .param("userName", TEST_USER_NAME)
                .param("key", String.valueOf(TEST_USER_NAME.hashCode()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stream/leaveStreamQueue")
                .param("userName", TEST_USER_NAME)
                .param("key", String.valueOf(TEST_USER_NAME.hashCode()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("false"))
                .andExpect(status().isOk());
    }
}
