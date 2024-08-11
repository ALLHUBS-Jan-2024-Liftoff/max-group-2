package org.launchcode.threemix.service;

import org.launchcode.threemix.model.BlockedArtist;
import org.launchcode.threemix.model.BlockedSong;
import org.launchcode.threemix.model.User;
import org.launchcode.threemix.repository.BlockedArtistRepository;
import org.launchcode.threemix.repository.BlockedSongRepository;
import org.launchcode.threemix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlockedArtistRepository blockedArtistRepository;

    @Autowired
    private BlockedSongRepository blockedSongRepository;

    // User related methods
    public User findUserBySpotifyId(String spotifyId) {
        return userRepository.findBySpotifyId(spotifyId);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // BlockedArtist related methods
    public List<BlockedArtist> findBlockedArtistByUser(User user) {
        return blockedArtistRepository.findBlockedArtistByUser(user);
    }

    public BlockedArtist saveBlockedArtist(BlockedArtist blockedArtist) {
        return blockedArtistRepository.save(blockedArtist);
    }

    public void deleteBlockedArtistById(Long id) {
        blockedArtistRepository.deleteById(id);
    }

    // BlockedSong related methods
    public List<BlockedSong> findBlockedSongsByUser(User user) {
        return blockedSongRepository.findBlockedSongsByUser(user);
    }

    public BlockedSong saveBlockedSong(BlockedSong blockedSong) {
        return blockedSongRepository.save(blockedSong);
    }

    public void deleteBlockedSongById(Long id) {
        blockedSongRepository.deleteById(id);
    }
}