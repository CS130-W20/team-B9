package com.limelight.test;

import com.limelight.server.Application;
import com.limelight.server.User;
import com.limelight.server.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testUserSave() {
        User user = new User();
        user.setUserName("test_user");
        userRepository.save(user);
        Assert.assertNotNull(userRepository.findById("test_user"));
    }
}
