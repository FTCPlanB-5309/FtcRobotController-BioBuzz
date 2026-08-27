package org.firstinspires.ftc.teamcode;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Target {
    double x;
    double y;
    double h;
    TargetProfile tp;

    //
    // Constructor for a taget that includes x,y, h and a target profile
    //
    public Target(double x, double y, double h, TargetProfile tp){
        this.x = x;
        this.y = y;
        this.h = h;
        this.tp = tp;
    }

    public double findDifferenceInAngle (Pose2D pos, Target target) {
        return Math.abs(pos.getHeading(AngleUnit.DEGREES) - target.h);
    }
}
