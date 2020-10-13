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
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;

/**
 *
 * @author pedro.santos
 */
public class LoadingScreenController extends BaseAppState implements ScreenController{
    private Nifty nifty;
    private Screen screen;
    private Application app;
    //--------------------------------------------LoadingScreenVariables------------------------------------------------------//
    
    private Element progressBarElement;
    private boolean load = false;
    private TextRenderer textRenderer;
    private float frameCount = 0;
    private String resolution;
    private String nextScreen;
    
    //-------------------------------------------LoadingScreenVariables END---------------------------------------------------//
    
    @Override
    protected void initialize(Application app) {
        /*WorldMapScreenController worldMapScreenController = new WorldMapScreenController();
        app.getStateManager().attach(worldMapScreenController);
        nifty.fromXml("Interface/Screens/WorldMapScreen.xml", "worldMapScreen",worldMapScreenController);*/
        this.app = app;
        this.load = true;
        System.out.println(load);
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
        this.resolution = nifty.getGlobalProperties().getProperty("RESOLUTION");
        progressBarElement = nifty.getScreen("loadingScreen-"+resolution).findElementById("progressbar");
     }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }
    
    @Override
    public void update(float tpf) {
        if(nifty != null){
            if (load) { //loading is done over many frames
                  if (frameCount == 15) {
                      Element element = nifty.getScreen("loadingScreen-"+resolution).findElementById("loadingtext");
                      textRenderer = element.getRenderer(TextRenderer.class);
                      setProgress(0.2f, "Loading Main Screen...");
                  }
                  else if (frameCount == 30) {
                      setProgress(1f, "wait...");
                  }
                  else if (frameCount == 40) {
                      setProgress(1f, "Completed!");
                  }
                  else if (frameCount == 50) {
                      next();
                  }
              }
              frameCount++;
              System.out.println(frameCount);
        }
    }
    public void setProgress(final float progress, String loadingText) {
        int MIN_WIDTH = 16;
        
        if(resolution.equals("wqhd")){
            MIN_WIDTH = 64;
        }
        if(resolution.equals("fhd")){
            MIN_WIDTH = 32;
        }
        if(resolution.equals("hd")){
            MIN_WIDTH = 32;
        }
        if(resolution.equals("low")){
            MIN_WIDTH = 16;
        }
        
        int pixelWidth = (int) (MIN_WIDTH + (progressBarElement.getParent().
                getWidth() - MIN_WIDTH) * progress);
        progressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
        progressBarElement.getParent().layoutElements();

        textRenderer.setText(loadingText);
    }
    
    public void next(){
        if(nifty.getGlobalProperties().getProperty("NEXTSCREEN") == "mainScreen"){
            MainScreenController mainScreenController = new MainScreenController();
            nifty.fromXml("Interface/Screens/MainScreen.xml", "mainScreen", mainScreenController);
            app.getStateManager().attach(mainScreenController);
        }
        cleanup(app);
    }
}