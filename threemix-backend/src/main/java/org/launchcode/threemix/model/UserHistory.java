package org.launchcode.threemix.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "spotify_id", referencedColumnName = "spotifyId")
    private User user;

    private String entityType;
    private String entityId;
    private String action;
    private LocalDateTime timestamp;

    // Default constructor
    public UserHistory() {}

    // Constructor to initialize all fields except id (which is auto-generated)
    public UserHistory(User user, String entityType, String entityId, String action) {
        this.user = user;
        this.entityType = entityType;
        this.entityId = entityId;
        this.action = action;
        this.timestamp = LocalDateTime.now();
    }

    // Constructor used in your service (for simpler logging without entityType and entityId)
    public UserHistory(User user, String action, LocalDateTime now) {
        this.user = user;
        this.action = action;
        this.timestamp = now;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}