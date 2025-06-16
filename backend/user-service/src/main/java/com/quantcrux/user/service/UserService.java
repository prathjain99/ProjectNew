package com.quantcrux.user.service;

import com.quantcrux.user.entity.UserProfile;
import com.quantcrux.user.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserProfileRepository userProfileRepository;
    
    public UserProfile getUserProfile(String userId) {
        return userProfileRepository.findByUserId(userId)
                .orElse(new UserProfile(userId, "", ""));
    }
    
    public UserProfile saveUserProfile(UserProfile profile) {
        return userProfileRepository.save(profile);
    }
}