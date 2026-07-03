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
                // camera gives us position of tag RELATIVE to robot
                // x = how far left/right, z = how far forward
                double camX = detection.ftcPose.x;
                double camZ = detection.ftcPose.z;
                double camYaw = detection.ftcPose.yaw;

                // convert tag-relative position into field position
                // robot is "behind" the tag by however far the camera sees it
                double tagFacingRad = Math.toRadians(knownTag.heading);

                robotX = knownTag.fieldX + camZ * Math.cos(tagFacingRad) - camX * Math.sin(tagFacingRad);
                robotY = knownTag.fieldY - camZ * Math.sin(tagFacingRad) - camX * Math.cos(tagFacingRad);
                robotHeading = knownTag.heading - camYaw;

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