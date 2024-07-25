package org.launchcode.threemix.service;

import org.launchcode.threemix.dao.UserRepository;
import org.launchcode.threemix.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserHistory(String userId) {
        return (User) userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found with id: "));
    }
}
