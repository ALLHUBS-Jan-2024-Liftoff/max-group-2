package org.launchcode.threemix.controller;

import org.launchcode.threemix.model.BlockedSong;
import org.launchcode.threemix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blockedSongs")
public class BlockedSongController {

    @Autowired
    private UserService userService;

    // Create BlockedSong
    @PostMapping
    public ResponseEntity<BlockedSong> createBlockedSong(@RequestBody BlockedSong blockedSong) {
        return ResponseEntity.ok(userService.createBlockedSong(blockedSong));
    }

    // Read BlockedSong by ID
    @GetMapping("/{id}")
    public ResponseEntity<BlockedSong> getBlockedSongById(@PathVariable Long id) {
        BlockedSong blockedSong = userService.getBlockedSongById(id);
        return blockedSong != null ? ResponseEntity.ok(blockedSong) : ResponseEntity.notFound().build();
    }

    // Update BlockedSong
    @PutMapping("/{id}")
    public ResponseEntity<BlockedSong> updateBlockedSong(@PathVariable Long id, @RequestBody BlockedSong blockedSong) {
        blockedSong.setId(id);
        return ResponseEntity.ok(userService.updateBlockedSong(blockedSong));
    }

    // Delete BlockedSong
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlockedSong(@PathVariable Long id) {
        userService.deleteBlockedSong(id);
        return ResponseEntity.noContent().build();
    }
}