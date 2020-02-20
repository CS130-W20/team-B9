package com.limelight.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/app", method = RequestMethod.GET)
public class MainController {
    @Autowired
    private UserRepository userRepository;
    private StreamQueue queue = StreamQueue.getInstance();

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
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(password);
        userRepository.save(newUser);

        return userName.hashCode();
    }

    @PostMapping(path = "/login")
    public @ResponseBody
    Integer logIn(@RequestParam String userName,
                  @RequestParam String password) {
        Optional<User> user = getUser(userName);

        if (!user.isPresent()) {
            return null;
        }

        if (user.get().checkPassword(password)) {
            return userName.hashCode();
        }

        return null;
    }

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
        }

        // important: commit changes to database
        userRepository.save(user);

        return true;
    }

    /**
     * add user to stream queue
     * @param userName userName to add to stream queue
     * @param key user's key to ensure it is authorized user
     * @return true if user was added to stream queue, false otherwise
     */
    @PostMapping(path = "/joinStreamQueue")
    public @ResponseBody
    boolean joinStreamQueue(@RequestParam String userName,
                            @RequestParam Integer key) {
        Optional<User> userOptional = getUser(userName);
        if (userName.hashCode() != key || !userOptional.isPresent()) return false;
        User user = userOptional.get();
        return queue.addStreamer(user);
    }

    /**
     * remove user from stream queue
     * @param userName userName to remove from stream queue
     * @param key user's key to ensure it is authorized user
     * @return ture if user was removed from stream queue, false otherwise
     */
    @PostMapping(path = "/leaveStreamQueue")
    public @ResponseBody
    boolean leaveStreamQueue(@RequestParam String userName,
                            @RequestParam Integer key) {
        Optional<User> userOptional = getUser(userName);
        if (userName.hashCode() != key || !userOptional.isPresent()) return false;
        User user = userOptional.get();
        return queue.removeStreamer(user);
    }

    @GetMapping(path = "/getAllUsers")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        // returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping(path = "/getUser")
    public @ResponseBody
    Optional<User> getUser(@RequestParam String userName) {
        return userRepository.findById(userName);
    }
}
