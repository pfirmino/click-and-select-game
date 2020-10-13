/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aerolitos.prj44;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author pedro.santos
 */
public class MainScreenController extends BaseAppState implements ScreenController{
    private Nifty nifty;
    private Screen screen;
    private Application app;

    
    @Override
    protected void initialize(Application app) {
        this.app = app;
    }

    @Override
    protected void cleanup(Application app) {
        ((SimpleApplication)app).getRootNode().detachAllChildren();
        ((SimpleApplication)app).getStateManager().detach(this);
    }
    
    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        
        nifty.getGlobalProperties().setProperty("NEXTSCREEN", "worldMapScreen");
        nifty.getGlobalProperties().setProperty("PREVIOUSSCREEN", "bootScreen");
    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }
    
    @Override
    public void update(float tpf) {
    }
    
    public void next(){
        System.out.println("WorldMap Now!");
        WorldMapScreenController worldMapScreenController = new WorldMapScreenController();
        nifty.fromXml("Interface/Screens/WorldMapScreen.xml", "worldMapScreen", worldMapScreenController);
        app.getStateManager().attach(worldMapScreenController);
        cleanup(app);
    }

}
