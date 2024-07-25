package org.launchcode.threemix.domain;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class BlockedArtist {

    private Integer artistId;
    private String userId;
    private String artistName;
}
