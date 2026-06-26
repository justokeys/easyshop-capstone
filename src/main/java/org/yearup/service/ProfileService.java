package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Profile;
import org.yearup.repository.ProfileRepository;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Optional<Profile> getProfile(int userId) {
        return profileRepository.findById(userId);
    }

    public Profile updateProfileById(int userId, Profile profile) {
        Profile existing = profileRepository.findById(userId).orElseThrow();
        existing.setAddress(profile.getAddress());
        existing.setCity(profile.getCity());
        existing.setEmail(profile.getEmail());
        existing.setFirstName(profile.getFirstName());
        existing.setLastName(profile.getLastName());
        existing.setState(profile.getState());
        existing.setPhone(profile.getPhone());
        existing.setZip(profile.getZip());

        return profileRepository.save(existing);

    }

    public Profile create(Profile profile) {
        return profileRepository.save(profile);
    }
}
