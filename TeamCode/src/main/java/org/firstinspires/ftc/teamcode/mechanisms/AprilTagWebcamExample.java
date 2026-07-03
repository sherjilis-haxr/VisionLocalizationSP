package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.mechanisms.Localization;

@Autonomous
public class AprilTagWebcamExample extends OpMode {

    AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();
    Localization localization = new Localization();

    @Override
    public void init() {
        aprilTagWebcam.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        aprilTagWebcam.update();
        localization.update(aprilTagWebcam);

        if (localization.hasPosition()) {
            telemetry.addData("Robot X", localization.getRobotX());
            telemetry.addData("Robot Y", localization.getRobotY());
            telemetry.addData("Heading", localization.getRobotHeading());
        } else {
            telemetry.addLine("No tag visible — position unknown");
        }

        telemetry.update();
    }
}