package com.example.cityrally.app;

import android.app.Application;
import android.content.*;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.test.ApplicationTestCase;
import android.view.Display;
import com.example.cityrally.app.engine.controller.ChallengesController;
import com.example.cityrally.app.engine.controller.GameController;
import com.example.cityrally.app.engine.model.Challenge;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private HashMap<String, Challenge> mChallenges;
    public ApplicationTest() {
        super(Application.class);
    }

    public void testUnlock(){
        //creations des challenges
        GameController gc = new GameController();
        gc.initChallenges();
        mChallenges = gc.getChallengesMap();
        //modifications des états
        gc.onUnlock(mChallenges.get("1"));
        gc.unlockNextChallenge(1);
        //verifications qu'ils sont corrects
        if(mChallenges.get("2").isUnlocked() && mChallenges.get("1").isSolved()){
            System.out.println("ok");
        }
        else
            System.out.println("ko");

    }

    public void testOnPosition(){
        //creations des challenges
        GameController gc = new GameController();
        gc.initChallenges();
        mChallenges = gc.getChallengesMap();
        //modifications des états
        gc.onPosition(mChallenges.get("1"));
        //verifications qu'ils sont corrects
        if( mChallenges.get("1").isInPosition()){
            System.out.println("ok");
        }
        else
            System.out.println("ko");

    }
}