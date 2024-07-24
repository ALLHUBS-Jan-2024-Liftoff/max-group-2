package org.launchcode.threemix.controller;

import org.launchcode.threemix.model.BlockedArtist;
import org.launchcode.threemix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blockedArtists")
public class BlockedArtistController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<BlockedArtist> createBlockedArtist(@RequestBody BlockedArtist blockedArtist) {
        return ResponseEntity.ok(userService.createBlockedArtist(blockedArtist));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlockedArtist> getBlockedArtistById(@PathVariable Long id) {
        BlockedArtist blockedArtist = userService.getBlockedArtistById(id);
        return blockedArtist != null ? ResponseEntity.ok(blockedArtist) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlockedArtist> updateBlockedArtist(@PathVariable Long id, @RequestBody BlockedArtist blockedArtist) {
        blockedArtist.setId(id);
        return ResponseEntity.ok(userService.updateBlockedArtist(blockedArtist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlockedArtist(@PathVariable Long id) {
        userService.deleteBlockedArtist(id);
        return ResponseEntity.noContent().build();
    }
}