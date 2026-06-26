package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "*")
public class ProfileController {
    @Autowired
    private final ProfileService profileService;
    @Autowired
    private final UserService userService;

    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    // Get user profile by id
    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Profile> getProfileById(Principal principal) {
        String userName = principal.getName();
        // find database user by username
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        // use orElseGet to return not found http code if user id is not found
        // avoids throwing an exception in this case
        return profileService.getProfile(userId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    //update profile info
    @PutMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Profile> updateProfileById(Principal principal, @RequestBody Profile profile) {
        String userName = principal.getName();
        // find database user by username
        User user = userService.getByUserName(userName);
        int userId = user.getId();
        return ResponseEntity.ok().body(profileService.updateProfileById(userId, profile));


    }
}
