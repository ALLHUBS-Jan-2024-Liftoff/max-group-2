package org.launchcode.threemix.controller;

import org.launchcode.threemix.model.BlockedArtist;
import org.launchcode.threemix.model.BlockedSong;
import org.launchcode.threemix.model.User;
import org.launchcode.threemix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class BlockedContentController {

    @Autowired
    private UserService userService;

    // Blocking a list of artists
    @PostMapping("/blockedArtist")
    public void blockArtists(@RequestParam List<String> artistIds, HttpSession session) {
        String spotifyId = userService.getUserId(session);
        User user = userService.findUserBySpotifyId(spotifyId);
        for (String artistId : artistIds) {
            if (user.getBlockedArtists().stream().map(BlockedArtist::getArtistId).noneMatch(id -> id.equals(artistId))) {
                BlockedArtist blockedArtist = new BlockedArtist();
                blockedArtist.setArtistId(artistId);
                blockedArtist.setUser(user);
                user.getBlockedArtists().add(blockedArtist);
            }
        }
        userService.saveUser(user);  // Save user only once after all artists are added
    }

    // Retrieving blocked artists
    @GetMapping("/blockedArtist")
    public List<BlockedArtist> getBlockedArtists(HttpSession session) {
        String spotifyId = (String) session.getAttribute("spotifyId");
        User user = userService.findUserBySpotifyId(spotifyId);
        return userService.findBlockedArtistByUser(user);
    }

    // Unblocking a list of artists
    @DeleteMapping("/blockedArtist")
    public void unblockArtists(@RequestParam List<Long> ids) {
        for (Long id : ids) {
            userService.deleteBlockedArtistById(id);
        }
    }

    // Blocking a list of songs
    @PostMapping("/blockedSong")
    public void blockSongs(@RequestParam List<String> songIds, HttpSession session) {
        String spotifyId = userService.getUserId(session);
        User user = userService.findUserBySpotifyId(spotifyId);
        for (String songId : songIds) {
            if (user.getBlockedSongs().stream().map(BlockedSong::getSongId).noneMatch(id -> id.equals(songId))) {
                BlockedSong blockedSong = new BlockedSong();
                blockedSong.setSongId(songId);
                blockedSong.setUser(user);
                user.getBlockedSongs().add(blockedSong);
            }
        }
        userService.saveUser(user);  // Save user only once after all songs are added
    }

    // Retrieving blocked songs
    @GetMapping("/blockedSong")
    public List<BlockedSong> getBlockedSongs(HttpSession session) {
        String spotifyId = (String) session.getAttribute("spotifyId");
        User user = userService.findUserBySpotifyId(spotifyId);
        return userService.findBlockedSongsByUser(user);
    }

    // Unblocking a list of songs
    @DeleteMapping("/blockedSong")
    public void unblockSongs(@RequestParam List<Long> ids) {
        for (Long id : ids) {
            userService.deleteBlockedSongById(id);
        }
    }
}