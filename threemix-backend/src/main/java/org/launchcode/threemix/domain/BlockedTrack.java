package org.launchcode.threemix.domain;


import jakarta.persistence.Entity;
import lombok.Data;

@Entity(name = "blocked_tracks")
@Data
public class BlockedTrack {
    private Integer trackId;
    private String userId;
    private String trackTitle;

}
