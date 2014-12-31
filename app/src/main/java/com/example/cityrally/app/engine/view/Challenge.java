package com.example.cityrally.app.engine.view;

import android.util.Log;
import com.example.cityrally.app.engine.games.Game;
import com.example.cityrally.app.engine.manager.location.SimpleGeofence;
import com.example.cityrally.app.engine.manager.Manager;
import com.google.android.gms.location.Geofence;

import java.io.Serializable;
import java.util.HashMap;

public class Challenge implements Serializable {
    private static final long serialVersionUID = -29238982928391L;

    private String id;
    private int image;
    private int title;
    private int text;
    private int challenge;
    private String geofenceId;
    private String gameId;
    private boolean inPosition;
    private boolean unlocked;
    private boolean solved;

    //unlocked => the card is visible
    //inPosition => ready to be solved
    // solved => the game has been succeeded
    public Challenge(String geofenceId, String gameId, boolean unlocked,boolean inPosition, boolean solved) {
        this.id = geofenceId;
        this.geofenceId = geofenceId;
        this.gameId = gameId;
        this.unlocked = unlocked;
        this.inPosition = inPosition;
        this.solved = solved;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public int getChallenge() {
        return challenge;
    }

    public void setChallenge(int challenge) {
        this.challenge = challenge;
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

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean inPosition) {
        this.solved = solved;
    }

    public boolean isInPosition() {
        return inPosition;
    }

    public void setInPosition(boolean inPosition) {
        this.inPosition = inPosition;
    }

    public SimpleGeofence getGeofence() {
        return Manager.game().getGeofenceWithId(this.geofenceId);
    }

    public Game getGame() {
        return Manager.game().getGameWithId(this.gameId);
    }

    public void setResources(int image, int title, int text, int challenge) {
        this.image = image;
        this.title = title;
        this.text = text;
        this.challenge = challenge;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "id='" + id + '\'' +
                ", image=" + image +
                ", title=" + title +
                ", text=" + text +
                ", challenge=" + challenge +
                ", geofenceId='" + geofenceId + '\'' +
                ", gameId='" + gameId + '\'' +
                ", unlocked=" + unlocked +
                ", inPostion=" + inPosition +
                ", solved=" + solved +
                '}';
    }
}