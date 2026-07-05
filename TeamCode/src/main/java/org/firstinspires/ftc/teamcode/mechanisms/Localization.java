package org.firstinspires.ftc.teamcode.mechanisms;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.ArrayList;
import java.util.List;

public class Localization {

    // add as many tags as you want here
    private List<FieldTag> knownTags = new ArrayList<>();

    private double robotX = 0;
    private double robotY = 0;
    private double robotHeading = 0;
    private boolean hasPosition = false;

    public Localization() {
        // tag 23 is on the left wall (X=0) at the top of the field (Y=144)
        // facing inward means it faces 0 degrees (toward positive X)
        knownTags.add(new FieldTag(23, 0, 144, 0));

        // add more tags here as you get them, for example:
        // knownTags.add(new FieldTag(20, 144, 0, 180)); // right wall, bottom corner, facing left
    }

    public void update(AprilTagWebcam webcam) {
        for (FieldTag knownTag : knownTags) {
            AprilTagDetection detection = webcam.getTagBySpecificId(knownTag.id);

            if (detection != null && detection.ftcPose != null) {
                // The camera measures where the tag is compared to us, in inches.
                //   forward = how far the tag is straight ahead of the camera
                //   side    = how far the tag is to the RIGHT (left is a negative number)
                //   yaw     = how twisted the tag looks, in degrees
                // We ignore the up/down number, so the camera height does not matter here.
                double forward = detection.ftcPose.y;
                double side = detection.ftcPose.x;
                double yaw = detection.ftcPose.yaw;

                // Figure out which way the robot is pointing on the field, in degrees.
                // The tag faces knownTag.heading. When we stare straight at it we point
                // the opposite way (that is the + 180), minus how twisted the tag looks.
                double robotAngle = knownTag.heading + 180 - yaw;

                // Change the angle to radians so Math.cos and Math.sin can use it.
                double robotAngleRad = Math.toRadians(robotAngle);

                // Start at the tag's known field spot, then walk back to where the robot is.
                robotX = knownTag.fieldX - forward * Math.cos(robotAngleRad) - side * Math.sin(robotAngleRad);
                robotY = knownTag.fieldY - forward * Math.sin(robotAngleRad) + side * Math.cos(robotAngleRad);
                robotHeading = robotAngle;

                hasPosition = true;
                return; // got a fix, no need to check other tags
            }
        }

        // no tags visible
        hasPosition = false;
    }

    public double getRobotX() { return robotX; }
    public double getRobotY() { return robotY; }
    public double getRobotHeading() { return robotHeading; }
    public boolean hasPosition() { return hasPosition; }
}