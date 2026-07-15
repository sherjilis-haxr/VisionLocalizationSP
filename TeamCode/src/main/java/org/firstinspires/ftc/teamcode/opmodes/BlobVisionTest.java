package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.mechanisms.BlobVision;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp(name = "Vision: Blob Test", group = "Test")

public class BlobVisionTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        BlobVision blobVision = new BlobVision();
        VisionPortal camera = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1" ))
                .addProcessor(blobVision)
                .build();

        waitForStart();

        while (opModeIsActive()) {

            sleep(20);
        }

    }


}
