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

    // User CRUD operations
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public BlockedArtist createBlockedArtist(BlockedArtist blockedArtist) {
        return blockedArtistRepository.save(blockedArtist);
    }

    public BlockedArtist getBlockedArtistById(Long id) {
        return blockedArtistRepository.findById(id).orElse(null);
    }

    public BlockedArtist updateBlockedArtist(BlockedArtist blockedArtist) {
        return blockedArtistRepository.save(blockedArtist);
    }

    public void deleteBlockedArtist(Long id) {
        blockedArtistRepository.deleteById(id);
    }

    public BlockedSong createBlockedSong(BlockedSong blockedSong) {
        return blockedSongRepository.save(blockedSong);
    }

    public BlockedSong getBlockedSongById(Long id) {
        return blockedSongRepository.findById(id).orElse(null);
    }

    public BlockedSong updateBlockedSong(BlockedSong blockedSong) {
        return blockedSongRepository.save(blockedSong);
    }

    public void deleteBlockedSong(Long id) {
        blockedSongRepository.deleteById(id);
    }

    public List<BlockedArtist> getBlockedArtists(User user) {
        return blockedArtistRepository.findByUser(user);
    }

    public List<BlockedSong> getBlockedSongs(User user) {
        return blockedSongRepository.findByUser(user);
    }
}