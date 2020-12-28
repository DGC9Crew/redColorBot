package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.ui.Picture;
import java.util.Random;
import java.util.Scanner;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    Random rand = new Random();
     float lr = 1;
     float bias = 1;
     float weights[] = {rand.nextFloat(),rand.nextFloat(),rand.nextFloat(), rand.nextFloat()};
     int lessers;
     int greaters;
     
     float totalAsked;
     float totalCorrect;
     float trueornot;
     
     float r = 1;
     float g = 0;
     float b = 0;
     Geometry past;
     Geometry geom;
     Material mat1 ;
     Material mat;
     BitmapText whatYouSaid;
     BitmapText whatBotSaid;
     BitmapText accuracy;
     BitmapText totalAsk;
     BitmapText totalRight;
     BitmapText weight1;
     BitmapText weight2;
     BitmapText weight3;
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    
    public void Perceptron(float input1,float input2,float input3, float output) {
        float outputP = input1 * weights[0] +input2 * weights[1]+input3 * weights[2]+ bias * weights[3];
        totalAsked += 1;
        if (outputP > 0) {
            outputP = 1;

        } else {
            outputP = 0;
        }
        if(outputP == output) {
             totalCorrect += 1;
        }
        System.out.println(outputP);
        float error = output - outputP;

        weights[0] += error * input1 * lr;
        weights[1] += error * input2 * lr;
        weights[2] += error * input3 * lr;
        weights[3] += error * bias * lr;
        mat1.setColor("Color", new ColorRGBA(r,g,b, 1));
        if(rand.nextFloat() > .5f) {
            r = rand.nextFloat()*2;
            g = rand.nextFloat()/8;
            b = rand.nextFloat()/8;
            mat.setColor("Color", new ColorRGBA(r, g, b, 1));
            
        } else {
            r = rand.nextFloat();
            g = rand.nextFloat();
            b = rand.nextFloat();
            mat.setColor("Color", new ColorRGBA(r, g, b, 1));
        }
        
        
                
        if(output == 1) {
            whatYouSaid.setText("You said: YES");
        } else {
            whatYouSaid.setText("You said: NO");
        }
        if (outputP == 1) {
            whatBotSaid.setText("COLORBOT said: YES");
        } else {
            whatBotSaid.setText("COLORBOT said: NO");
        }
        accuracy.setText("Accuracy: " + (totalCorrect / totalAsked)*100 + "%");
        
        Picture pic = new Picture("HUD Picture");
        pic.setImage(assetManager, "Textures/red.png", true);
        pic.setWidth(1);
        pic.setHeight(1);
        pic.setPosition(totalAsked, (totalCorrect / totalAsked)*settings.getHeight() );
        guiNode.attachChild(pic);
        
        System.out.println("---------");
        
    }
    private void initKeys() {
        // You can map one or several inputs to one named action
        inputManager.addMapping("Yes", new KeyTrigger(KeyInput.KEY_Y));
        inputManager.addMapping("No", new KeyTrigger(KeyInput.KEY_N));
       
        
        // Add the names to the action listener.
        inputManager.addListener(actionListener, "Yes","No");


    }
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Yes") && !keyPressed) { 
                Perceptron(r,g,b,1);
            }
            if (name.equals("No") && !keyPressed) {
                Perceptron(r,g,b,0);
            }
        }
    };
    @Override
    public void simpleInitApp() {
        setDisplayFps(false);
        setDisplayStatView(false);
        flyCam.setEnabled(false);
        whatYouSaid = new BitmapText(guiFont, false);
        whatYouSaid.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        whatYouSaid.setColor(ColorRGBA.Red);                             // font color
        whatYouSaid.setText("You said: ???");             // the text
        whatYouSaid.setLocalTranslation(0, 300, 0); // position
        guiNode.attachChild(whatYouSaid);
        
        whatBotSaid = new BitmapText(guiFont, false);
        whatBotSaid.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        whatBotSaid.setColor(ColorRGBA.Red);                             // font color
        whatBotSaid.setText("COLORBOT said: ???");             // the text
        whatBotSaid.setLocalTranslation(0, 200, 0); // position
        guiNode.attachChild(whatBotSaid);
        
        accuracy = new BitmapText(guiFont, false);
        accuracy.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        accuracy.setColor(ColorRGBA.Red);                             // font color
        accuracy.setText("Accuracy: ???");             // the text
        accuracy.setLocalTranslation(0, accuracy.getLineHeight(), 0); // position
        guiNode.attachChild(accuracy);
        
        weight1 = new BitmapText(guiFont, false);
        weight1.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        weight1.setColor(ColorRGBA.Red);                             // font color
        weight1.setText("Weight 1: " + weights[0]);             // the text
        weight1.setLocalTranslation(0, settings.getHeight(), 0); // position
        guiNode.attachChild(weight1);
        
        weight2 = new BitmapText(guiFont, false);
        weight2.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        weight2.setColor(ColorRGBA.Red);                             // font color
        weight2.setText("Weight 2: " + weights[1]);             // the text
        weight2.setLocalTranslation(200, settings.getHeight(), 0); // position
        guiNode.attachChild(weight2);
        
        weight3 = new BitmapText(guiFont, false);
        weight3.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        weight3.setColor(ColorRGBA.Red);                             // font color
        weight3.setText("Weight 3: " + weights[2]);             // the text
        weight3.setLocalTranslation(400, settings.getHeight(), 0); // position
        guiNode.attachChild(weight3);
        
        totalAsk = new BitmapText(guiFont, false);
        totalAsk.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        totalAsk.setColor(ColorRGBA.Red);                             // font color
        totalAsk.setText("Total Asked: ???");             // the text
        totalAsk.setLocalTranslation(200, totalAsk.getLineHeight(), 0); // position
        guiNode.attachChild(totalAsk);
        
        totalRight = new BitmapText(guiFont, false);
        totalRight.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        totalRight.setColor(ColorRGBA.Red);                             // font color
        totalRight.setText("Total Right: ???");             // the text
        totalRight.setLocalTranslation(400, totalRight.getLineHeight(), 0); // position
        guiNode.attachChild(totalRight);
      
        
        Box box = new Box(1, 1, 1);
        geom = new Geometry("Box", box);

        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", new ColorRGBA(r,g,b,1));
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        flyCam.setMoveSpeed(20);
        initKeys();
        
        Box box1 = new Box(.5f, .5f, .5f);
        past = new Geometry("Box", box1);

         mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", new ColorRGBA(0,0,0,1));
        past.setMaterial(mat1);
        past.setLocalTranslation(-4,0,0);
        rootNode.attachChild(past);
        flyCam.setMoveSpeed(20);
        initKeys();
    }
    
  
    
    @Override
    public void simpleUpdate(float tpf) {
        totalAsk.setText("Total Asked: " + totalAsked);
        totalRight.setText("Total Correct: " + totalCorrect);
        weight1.setText("Weight 1: " + weights[0]);
        weight2.setText("Weight 2: " + weights[1]); 
        weight3.setText("Weight 3: " + weights[2]); 
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
