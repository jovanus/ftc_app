package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class TensorFlow {

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "Ac6tBZr/////AAABmd2+0ZS1DUaxvjeLOQXt6BocTj8MS8ZdGc3iaWgJcb4x+GTRiMydjRed7kvoAvq0x21glktV2ekv6Nq8WLNelf5Chl5vN4X9QjUKYvH1fgh72q2cY2w5lMO5tmoOAbyNlN4hSM+RdaWXC7MpY95EVbwz584eP2KUQ97DMCFYqGj6zaVTap2FQ/U2rK7XDNp+s0mdm1+2dvJh6bw0Xpp/DjkUG7RB3uLZe0niObsnONPJg29RCf2eOVY/NP7qjXZamhGLjR1Cpj+U2HGh5DIqCauT/lvn/PDfa+H8ErXG0grgeSqQUHGYlsnYiYrp7Q70RKeebAeOsMVVj6zNhjI6dGE06u3JZgT6aF5EMxnJyc2X";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    public void Initialize(int tfodID){
        initVuforia();
        initTfod(tfodID);
    }

    public BlockPosition FindBlock(){
        BlockPosition Location = BlockPosition.LEFT;
        if (tfod != null){
            List<Recognition> ObjectsSeen = tfod.getUpdatedRecognitions();
            if (ObjectsSeen != null && ObjectsSeen.size() >= 2){
                
            }
        }

        return Location;
    }












    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod(int tfodId) {
        int tfodMonitorViewId = tfodId;
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
