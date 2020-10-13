/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aerolitos.prj44;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
import java.util.UUID;


/**
 *
 * @author pedro.firmino
 */
public class WorldMapScreenController extends BaseAppState implements ScreenController{
    private SimpleApplication app;
    private Nifty nifty;
    private Screen screen;
    private Node camNode;
    private BitmapText debugInfo;
    private ArrayList<String> selection = new ArrayList<String>();
    private ArrayList<String> characters = new ArrayList<String>();
    
    @Override
    public void initialize(Application app){
        setApp((SimpleApplication)app);
        //Load World Map
        Node scene = (Node) getApp().getAssetManager().loadModel("Scenes/stage1.j3o");
        getApp().getRootNode().attachChild(scene);
        
        //Load Characters
        this.characters.add("standardChar");
        this.characters.add("standardChar");

        for(int i = 0; i < this.characters.size(); i++){
            Spatial c = getApp().getAssetManager().loadModel("Models/" + this.characters.get(i) + ".j3o");
            c.setName(UUID.randomUUID().toString());
            c.setLocalTranslation((float)Math.random()*10, 0, 0);
            getApp().getRootNode().attachChild(c);
        }
        
        
        //Initialize Camera
        getApp().getFlyByCamera().setEnabled(false); //Disable default cam controls
        getApp().getInputManager().setCursorVisible(true); //Set Cursor to true
        getApp().getCamera().setFrustumPerspective(15, 1.778f, 0.1f, 100); //Field of View 15º
        Node camTarget = new Node();
        Node camPos = new Node();
        camPos.setLocalTranslation(new Vector3f(0,25,25));
        camPos.setName("camPos");
        camTarget.attachChild(camPos);
        setCamNode(camTarget);
        setCameraPosition(new Vector3f(0,0,0));

        //Setup Input
        getApp().getInputManager().addMapping("onTouch", new TouchTrigger(0), new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        getApp().getInputManager().addListener(touchListener, "onTouch");
        
        //Enable Debug Info
        enableDebugInfo();
    }
    @Override
    public void cleanup(Application app) {
    }
    @Override   
    protected void onEnable() {       
    }

    @Override   
    protected void onDisable() {       
    }       
    @Override
    public void bind(Nifty nifty, Screen screen) {
        //Set Interface 
        this.nifty = nifty;
        this.screen = screen;
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
    
    //Getters and Setters --------------------------------------------------//
    public SimpleApplication getApp() {
        return this.app;
    }    
    public void setApp(SimpleApplication app) {    
        this.app = app;
    }
    public Node getCamNode() {
        return camNode;
    }
    public void setCamNode(Node camNode) {
        this.camNode = camNode;
    }
    public void addSelection(String name){
        this.selection.add(name);
    }
    public void removeSelection(String name){
        this.selection.remove(name);
    }
    public ArrayList<String> getSelection(){
        return this.selection;
    }
    public int getSelectionLength(){
        return this.selection.size();
    }
    public String getSelectionValueByIndex(int index){
        return this.selection.get(index);
    }
    private void setCameraPosition(Vector3f position){
        this.camNode.setLocalTranslation(position);
        getApp().getCamera().setLocation(this.camNode.getChild("camPos").getWorldTranslation()); //Define camera initial position
        getApp().getCamera().lookAt(this.camNode.getLocalTranslation(), new Vector3f(0, 1, 0)); //Define look at direction 
    }
    
    //Getters and Setters END--------------------------------------------------//
    
    private void enableDebugInfo(){
        BitmapFont font = getApp().getAssetManager().loadFont("Interface/Fonts/Console.fnt");
        this.debugInfo = new BitmapText(font, false);
        this.debugInfo.setSize(font.getCharSet().getRenderedSize());      // font size
        this.debugInfo.setColor(ColorRGBA.White);// font color
        this.debugInfo.setText("empty");             // the text
        this.debugInfo.setLocalTranslation(300, this.debugInfo.getLineHeight(), 0); // position
        getApp().getGuiNode().attachChild(this.debugInfo); 
    }

    //Handle Touch Events -----------------------------------------------------------------------//
   
    private void displayEventInfo(TouchEvent event){
        String text = event.toString();
        this.debugInfo.setText(text);             // the text
    } 
    private final TouchListener touchListener = new TouchListener() {
        @Override
        public void onTouch(String name, TouchEvent event, float tpf) {
            if(name.equals("onTouch")){
                //TouchEvent(TouchEvent.Type type, float x, float y, float deltax, float deltay) 
                displayEventInfo(event);
                
                //Move Camera
                if(event.getType().equals(event.getType().MOVE) && event.getPointerId() == 1){
                    Vector3f v = getCamNode().getLocalTranslation();
                    v.x = v.x - event.getDeltaX()/70;
                    v.z = v.z + event.getDeltaY()/50;
                    setCameraPosition(v);
                }
                

                //Reset results list.
                CollisionResults results = new CollisionResults();

                // Convert screen click to 3d position
                Vector3f click3d = getApp().getCamera().getWorldCoordinates(new Vector2f(event.getX(), event.getY()), 0f).clone();
                Vector3f dir = getApp().getCamera().getWorldCoordinates(new Vector2f(event.getX(), event.getY()), 1f).subtractLocal(click3d).normalizeLocal();

                // Aim the ray from camera location in camera direction
                Ray ray = new Ray(click3d, dir);

                //Collect intersections between ray and all nodes in results list.
                getApp().getRootNode().collideWith(ray, results);


                if (results.size() > 0) {
                    //Get hlosest hit target
                    Geometry target = results.getClosestCollision().getGeometry();

                    //If the object is a Character 
                    if(target.getParent().getUserData("isCharacter") != null){
                        if(target.getParent().getUserData("isCharacter")){
                            //And If it's not already selected
                            if(!getSelection().contains(target.getParent().getName().toString())){
                                //Add selHilight Spatial
                                Node h = (Node) getApp().getAssetManager().loadModel("Models/selHighlight.j3o");
                                target.getParent().attachChild(h);
                                //Preload Texture in GPU to correct Alpha issues
                                getApp().getRenderManager().preloadScene(h);
                                //Add Selection to the selection list
                                addSelection(target.getParent().getName().toString());
                            } 
                        }
                    } //Else Remove Highlight Selection from the scene
                }
                if(event.getType().equals(event.getType().DOUBLETAP)){
                    for (int i = 0 ; i < getSelectionLength(); i++){
                        Node c = (Node) getApp().getRootNode().getChild(getSelectionValueByIndex(i));
                        c.detachChildNamed("selHighlight");
                        removeSelection(getSelectionValueByIndex(i));
                    }
                }

            }

        }

     };
     //Handle Touch Events END-----------------------------------------------------------------------//
}
