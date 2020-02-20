package com.limelight.test;

import com.limelight.server.SocialMediaHandle;
import com.limelight.server.User;
import org.junit.Before;
import org.junit.Test;

import java.util.EnumMap;

import static com.limelight.test.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class of tests that test User functionality.
 */
public class UserTest {

    private User user;

    @Before
    public void setup() {
        user = new User();
    }

    /**
     * Test that ensures that user's username can be set correctly.
     */
    @Test
    public void testUserName() {
        user.setUserName(TEST_USER_NAME);
        assertEquals(user.getUserName(), TEST_USER_NAME);
    }

    /**
     * Test that ensures that user's first name can be set correctly.
     */
    @Test
    public void testFirstName() {
        user.setFirstName(TEST_FIRST_NAME);
        assertEquals(user.getFirstName(), TEST_FIRST_NAME);
    }

    /**
     * Test that ensures that user's last name can be set correctly.
     */
    @Test
    public void testLastName() {
        user.setLastName(TEST_LAST_NAME);
        assertEquals(user.getLastName(), TEST_LAST_NAME);
    }

    /**
     * Test that ensures that user's email can be set correctly.
     */
    @Test
    public void testEmail() {
        user.setEmail(TEST_EMAIL);
        assertEquals(user.getEmail(), TEST_EMAIL);
    }

    /**
     * Test that ensures that user's password can be set and verified correctly.
     */
    @Test
    public void testPassword() {
        user.setPassword(TEST_PASSWORD);
        assertTrue(user.checkPassword(TEST_PASSWORD));
    }

    /**
     * Test that ensures that user's social media handles can be set correctly.
     */
    @Test
    public void testSocialMediaHandle() {
        user.setSocialMediaHandle(SocialMediaHandle.FACEBOOK, TEST_SOCIAL_MEDIA_HANDLE);
        user.setSocialMediaHandle(SocialMediaHandle.INSTAGRAM, TEST_SOCIAL_MEDIA_HANDLE);
        user.setSocialMediaHandle(SocialMediaHandle.SNAPCHAT, TEST_SOCIAL_MEDIA_HANDLE);
        user.setSocialMediaHandle(SocialMediaHandle.YOUTUBE, TEST_SOCIAL_MEDIA_HANDLE);

        EnumMap<SocialMediaHandle, String> socialMediaHandles = user.getSocialMediaHandles();

        assertEquals(socialMediaHandles.get(SocialMediaHandle.FACEBOOK), TEST_SOCIAL_MEDIA_HANDLE);
        assertEquals(socialMediaHandles.get(SocialMediaHandle.INSTAGRAM), TEST_SOCIAL_MEDIA_HANDLE);
        assertEquals(socialMediaHandles.get(SocialMediaHandle.SNAPCHAT), TEST_SOCIAL_MEDIA_HANDLE);
        assertEquals(socialMediaHandles.get(SocialMediaHandle.YOUTUBE), TEST_SOCIAL_MEDIA_HANDLE);
    }
}
