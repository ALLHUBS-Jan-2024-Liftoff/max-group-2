package org.launchcode.threemix.controller;

import org.launchcode.threemix.model.BlockedArtist;
import org.launchcode.threemix.model.BlockedSong;
import org.launchcode.threemix.model.User;
import org.launchcode.threemix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class PlaylistExportController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger(PlaylistExportController.class.getName());

    @GetMapping(value = "/generateTrackList", produces = "application/json")
    public ResponseEntity<Map<String, Object>> generateTrackList(@CookieValue("accessToken") String accessToken,
                                                                 @RequestParam List<String> chosenGenres,
                                                                 @RequestParam Long userId) {
        logger.info("Received request to generate track list for user ID: " + userId);

        User user = userService.getUserById(userId);
        if (user == null) {
            logger.severe("User not found: " + userId);
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }

        Map<String, List<String>> blockedList = getBlockedList(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        Map<String, Object> trackRecommendations;
        try {
            trackRecommendations = getRecommendations(chosenGenres, entity);
            if (trackRecommendations == null || !trackRecommendations.containsKey("tracks")) {
                logger.severe("No recommendations found from Spotify API.");
                return ResponseEntity.status(502).body(Map.of("error", "No recommendations found from Spotify API."));
            }
        } catch (RestClientException e) {
            logger.severe("Failed to fetch recommendations from Spotify: " + e.getMessage());
            return ResponseEntity.status(502).body(Map.of("error", "Failed to fetch recommendations from Spotify"));
        }

        filterRecommendations(trackRecommendations, blockedList);

        return ResponseEntity.ok(trackRecommendations);
    }

    private Map<String, List<String>> getBlockedList(User user) {
        List<String> blockedArtists = userService.getBlockedArtists(user).stream()
                .map(BlockedArtist::getName)
                .collect(Collectors.toList());
        List<String> blockedSongs = userService.getBlockedSongs(user).stream()
                .map(BlockedSong::getTitle)
                .collect(Collectors.toList());

        Map<String, List<String>> blockedList = new HashMap<>();
        blockedList.put("artists", blockedArtists);
        blockedList.put("songs", blockedSongs);

        return blockedList;
    }

    private Map<String, Object> getRecommendations(List<String> chosenGenres, HttpEntity<String> entity) {
        String url = buildSpotifyRecommendationUrl(chosenGenres);

        logger.info("Fetching recommendations from Spotify API: " + url);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        logger.info("Spotify API response status: " + responseEntity.getStatusCode());

        return responseEntity.getBody();
    }

    private String buildSpotifyRecommendationUrl(List<String> chosenGenres) {
        String genres = String.join(",", chosenGenres);
        return "https://api.spotify.com/v1/recommendations?seed_genres=" + genres;
    }

    private void filterRecommendations(Map<String, Object> recommendations, Map<String, List<String>> blockedList) {
        List<Map<String, Object>> tracks = (List<Map<String, Object>>) recommendations.get("tracks");

        List<String> blockedArtists = blockedList.get("artists");
        List<String> blockedSongs = blockedList.get("songs");

        List<Map<String, Object>> filteredTracks = tracks.stream()
                .filter(track -> {
                    Map<String, Object> trackDetails = (Map<String, Object>) track;
                    List<Map<String, Object>> artists = (List<Map<String, Object>>) trackDetails.get("artists");
                    String trackId = (String) trackDetails.get("id");

                    boolean isBlockedArtist = artists.stream()
                            .map(artist -> (String) artist.get("id"))
                            .anyMatch(blockedArtists::contains);

                    boolean isBlockedSong = blockedSongs.contains(trackId);

                    return !isBlockedArtist && !isBlockedSong;
                })
                .collect(Collectors.toList());

        recommendations.put("tracks", filteredTracks);
    }
}