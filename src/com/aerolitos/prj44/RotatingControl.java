package com.aerolitos.prj44;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 *
 * @author pedro.firmino
 */
public class RotatingControl extends AbstractControl{
    private float speed = -4f;
    @Override
    protected void controlUpdate(float tpf) {
        spatial.rotate(0,tpf * speed, 0);
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public Control controlForSpatial(Spatial spatial){
        RotatingControl control = new RotatingControl();
        control.setSpeed(speed);
        control.setSpatial(spatial);
        return control;
    }
    
    public float getSpeed(){
        return speed;
    }
    
    public void setSpeed(float speed){
        this.speed = speed;
    }
}
