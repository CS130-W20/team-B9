package com.limelight.server;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.EnumMap;

/**
 * Class that contains user attributes.
 */
@Entity // tells Hibernate to make a table out of this class
public class User {
    @Id
    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String otherInfo;

    EnumMap<SocialMediaHandle, String> socialMediaHandles = new EnumMap<SocialMediaHandle, String>(SocialMediaHandle.class);

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EnumMap<SocialMediaHandle, String> getSocialMediaHandles() {
        return socialMediaHandles;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public void setSocialMediaHandle(SocialMediaHandle socialMediaHandle, String userName) {
        socialMediaHandles.put(socialMediaHandle, userName);
        System.out.println(socialMediaHandle);
    }
}
