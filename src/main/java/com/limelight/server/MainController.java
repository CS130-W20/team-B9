package com.limelight.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * The main controller that processes all GET and POST requests pertaining to users and user attributes.
 */
@Controller
@RequestMapping(path = "/app", method = RequestMethod.GET)
public class MainController {
    @Autowired
    private UserRepository userRepository;
    private StreamQueue queue = StreamQueue.getInstance();

    /**
     * POST request that creates a user account and persists it to the database if the username is not already taken,
     * then return a user session key. Otherwise, do nothing and return null.
     *
     * @param userName  user's username
     * @param firstName user's first name
     * @param lastName  user's last name
     * @param email     user's email address
     * @param password  user's password
     * @return null if the username is already taken, or a user session key otherwise
     */
    @PostMapping(path = "/signup")
    public @ResponseBody
    Integer signUp(@RequestParam String userName,
                   @RequestParam String firstName,
                   @RequestParam String lastName,
                   @RequestParam String email,
                   @RequestParam String password) {

        // username is already taken
        if (getUser(userName).isPresent()) {
            return null;
        }

        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setFirstName("");
        newUser.setLastName("");
        newUser.setEmail("");
        newUser.setPassword(password);
        userRepository.save(newUser);
        
<<<<<<< HEAD
        System.out.println("user created" );
        System.out.println(userName);
=======
        //System.out.println("user created" );
        //System.out.println(userName);
>>>>>>> master
        return userName.hashCode();
    }

    /**
     * POST request that verifies the user's login credentials. If they are valid, a user session key is returned.
     * Otherwise, null is returned.
     *
     * @param userName user's username
     * @param password user's password
     * @return null if the credentials are invalid, or a user session key otherwise
     */
    @PostMapping(path = "/login")
    public @ResponseBody
    Integer logIn(@RequestParam String userName,
                  @RequestParam String password) {
        Optional<User> user = getUser(userName);

        if (!user.isPresent()) {
            return null;
        }

        if (user.get().checkPassword(password)) {
            System.out.println("login success" );
            System.out.println(userName);
            return userName.hashCode();
        }

        //System.out.println("login success" );
        //System.out.println(userName);

        return null;
    }

    /**
     * POST request that edits an attribute of the user profile. If the user's session key is invalid, does nothing
     * and returns false. Otherwise, the user's selected attribute is modified and persisted to the database, then
     * true is returned.
     *
     * @param userName  user's username
     * @param key       user's session key
     * @param attribute attribute user would like to edit (e.g. EMAIL)
     * @param platform  optional param specifying the social media platform to be modified; only relevant if
     *                  <code>attribute</code> is SOCIAL_MEDIA_HANDLE
     * @param value     updated value for the selected <code>attribute</code>
     * @return true if the user's session key and edit request are valid, false otherwise
     */
    @PostMapping(path = "/edit")
    public @ResponseBody
    boolean editProfile(@RequestParam String userName,
                        @RequestParam Integer key,
                        @RequestParam String attribute,
                        @RequestParam(required = false) String platform,
                        @RequestParam String value) {

        Optional<User> userOptional = getUser(userName);

        // user not authorized to edit profile or profile does not exist
        if (userName.hashCode() != key || !userOptional.isPresent()) {
            return false;
        }

        User user = userOptional.get();
        EditableUserAttribute editableUserAttribute = EditableUserAttribute.valueOf(attribute);

        switch (editableUserAttribute) {
            case FIRST_NAME:
                user.setFirstName(value);
                break;
            case LAST_NAME:
                user.setLastName(value);
                break;
            case EMAIL:
                user.setEmail(value);
                break;
            case PASSWORD:
                user.setPassword(value);
                break;
            case SOCIAL_MEDIA_HANDLE:
                user.setSocialMediaHandle(SocialMediaHandle.valueOf(platform), value);
            case OTHER_INFO:
                user.setOtherInfo(value);
        }

        // important: commit changes to database
        userRepository.save(user);

        System.out.println("profile changed" );
        System.out.println(userName);

        return true;
    }

    /**
     * GET request that returns a JSON list of all users in the database.
     *
     * @return a JSON list of all users in the database.
     */
    @GetMapping(path = "/getAllUsers")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        // returns a JSON or XML with the users
        return userRepository.findAll();
    }

    /**
     * GET request that returns a JSON representation of a selected user.
     *
     * @param userName username of selected username
     * @return JSON representation of the user with specified username
     */
    @GetMapping(path = "/getUser")
    public @ResponseBody
    Optional<User> getUser(@RequestParam String userName) {
        return userRepository.findById(userName);
    }
}
