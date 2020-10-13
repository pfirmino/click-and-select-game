package com.aerolitos.prj44;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import java.util.Properties;


/**
 * @author pedro.firmino
 */
public class Main extends SimpleApplication {
    
    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(30);
        app.setSettings(settings);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        setDisplayFps(false); //false to hide FPS
        setDisplayStatView(false); //false to hide StatView
        flyCam.setEnabled(false);
        //Start Interface
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();
        Properties properties = new Properties();
        
        //Compensate Fullscreen Images Aspect

        //Set resolution
        if(getViewPort().getCamera().getWidth()<=720){
            properties.setProperty("RESOLUTION", "low");
        }
        else if(getViewPort().getCamera().getWidth()<=1280){
            properties.setProperty("RESOLUTION", "hd");
        }
        else if(getViewPort().getCamera().getWidth()<=1920){
            properties.setProperty("RESOLUTION", "fhd");
        }
        else {
            properties.setProperty("RESOLUTION", "wqhd");
        }
        
        properties.setProperty("NEXTSCREEN", "bootScreen");
        properties.setProperty("PREVIOUSSCREEN", "none");
        
        System.out.println(properties.getProperty("RESOLUTION"));
        
        nifty.setGlobalProperties(properties);
        BootScreenController bootScrenController = new BootScreenController();
        stateManager.attach(bootScrenController);
        nifty.fromXml("Interface/Screens/BootScreen.xml", "bootScreen-" + properties.getProperty("RESOLUTION"), bootScrenController);
        //nifty.setDebugOptionPanelColors(true); //comment this line to set Nifty colors off
        guiViewPort.addProcessor(niftyDisplay);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
