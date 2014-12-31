package com.example.cityrally.app.engine.model;

import com.example.cityrally.app.engine.controller.location.SimpleGeofence;
import com.example.cityrally.app.engine.controller.Controller;
import com.example.cityrally.app.engine.model.Game;



public class Challenge{
    private boolean inPosition;
    private boolean unlocked;
    private boolean solved;
    private String id;
    private int image;
    private int title;
    private int text;
    private String geofenceId;
    private String gameId;


    //unlocked => the card is visible
    //inPosition => ready to be solved
    // solved => the game has been succeeded
    public Challenge(String geofenceId, String gameId, boolean unlocked,boolean inPosition, boolean solved) {
        this.unlocked = unlocked;
        this.inPosition = inPosition;
        this.solved = solved;
        this.id = geofenceId;
        this.geofenceId = geofenceId;
        this.gameId = gameId;

    }


    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public boolean isInPosition() {
        return inPosition;
    }

    public void setInPosition(boolean inPosition) {
        this.inPosition = inPosition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public String getGeofenceId() {
        return geofenceId;
    }

    public void setGeofenceId(String geofenceId) {
        this.geofenceId = geofenceId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }



    public SimpleGeofence getGeofence() {
        return Controller.getGame().getGeofenceWithId(this.geofenceId);
    }

    public Game getGame() {
        return Controller.getGame().getGameWithId(this.gameId);
    }

    public void setResources(int image, int title, int text) {
        this.image = image;
        this.title = title;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "id='" + id  +
                ", image=" + image +
                ", title=" + title +
                ", text=" + text +
                ", geofenceId='" + geofenceId + '\'' +
                ", gameId='" + gameId + '\'' +
                ", unlocked=" + unlocked +
                ", inPostion=" + inPosition +
                ", solved=" + solved +
                '}';
    }
}