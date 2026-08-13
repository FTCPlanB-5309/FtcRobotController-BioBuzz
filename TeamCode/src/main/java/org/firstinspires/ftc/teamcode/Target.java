package org.firstinspires.ftc.teamcode;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Target {
    double x;
    double y;
    double h;
    TargetProfile tp;
    double distanceToRobot;
    double diff_x;
    double diff_y;
    double diff_h;

    //
    // Constructor for a taget that includes x,y, h and a target profile
    //
    public Target(double x, double y, double h, TargetProfile tp){
        this.x = x;
        this.y = y;
        this.h = h;
        this.tp = tp;
    }

    public void setDistanceFromRobot(Pose2D pos)
    {
        double diff_x = x - pos.getX(DistanceUnit.CM);
        double diff_y = y - pos.getY(DistanceUnit.CM);
        double diff_h = -(h - pos.getHeading(AngleUnit.DEGREES));
        distanceToRobot = Math.sqrt(diff_x * diff_x + diff_y * diff_y);
    }

    public void setDifferenceInAngle (Pose2D pos) {
        diff_h = Math.abs(pos.getHeading(AngleUnit.DEGREES) - this.h);
    }
}
