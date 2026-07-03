package org.firstinspires.ftc.teamcode.mechanisms;

public class FieldTag {
    public int id;
    public double fieldX;   // tag's position on field in inches
    public double fieldY;
    public double heading;  // direction tag faces in degrees (0 = facing right/positive X)

    public FieldTag(int id, double fieldX, double fieldY, double heading) {
        this.id = id;
        this.fieldX = fieldX;
        this.fieldY = fieldY;
        this.heading = heading;
    }
}