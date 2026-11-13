package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


public class DriveTrain {
    RobotHardware robot;
    Telemetry telemetry;
    LinearOpMode opMode;

    double lfVectorSpeed;
    double lrVectorSpeed;
    double rfVectorSpeed;
    double rrVectorSpeed;

    double lfTurnSpeed;
    double lrTurnSpeed;
    double rfTurnSpeed;
    double rrTurnSpeed;

    double lfSpeed;
    double lrSpeed;
    double rfSpeed;
    double rrSpeed;

    double speedFactor;

    double diff_x;
    double diff_y;
    double diff_h;
    double distanceToTarget;

    double x2;
    double y2;


    public DriveTrain(RobotHardware robot,
                      Telemetry telemetry,
                      LinearOpMode opMode)  {
        this.robot = robot;
        this.telemetry = telemetry;
        this.opMode = opMode;
    }

    public void stop()
    {
        robot.LeftFrontMotor.setPower(0);
        robot.RightFrontMotor.setPower(0);
        robot.LeftRearMotor.setPower(0);
        robot.RightRearMotor.setPower(0);
    }

    public void turnMotorsOn()
    {
        robot.LeftFrontMotor.setPower(lfSpeed);
        robot.RightFrontMotor.setPower(rfSpeed);
        robot.LeftRearMotor.setPower(lrSpeed);
        robot.RightRearMotor.setPower(rrSpeed);
    }

    // This method determines the distance to the target and also
    // sets diff_x and diff_y values
    public void getDistanceToTarget(Pose2D pos, Target target)
    {
        diff_x = target.x - pos.getX(DistanceUnit.CM);
        diff_y = target.y - pos.getY(DistanceUnit.CM);
        distanceToTarget = Math.sqrt(diff_x * diff_x + diff_y * diff_y);
    }

    //
    // This method is used to determine the vector speed of the drive motors based on the
    // current position, the target, and the target profile.  It ignores the power
    // adjustments for correcting the robot angle.  When done the motor speed variables
    // are set to drive the robot to the target, but ignores the angle it's facing
    // when it gets there
    //
    public void findMotorVectorSpeed (Target target, Pose2D pos) {
        double sin;
        double cos;
        double denominator;

        getDistanceToTarget(pos, target);
        telemetry.addData("distance to target", distanceToTarget);

        sin = Math.sin(pos.getHeading(AngleUnit.RADIANS));
        cos = Math.cos(pos.getHeading(AngleUnit.RADIANS));
        //x2=cosβx1−sinβy1
        //y2=sinβx1+cosβy1
        x2 = (cos * diff_x) - (sin * diff_y);
        y2 = (sin * diff_x) + (cos * diff_y);

        //denominator = Math.max(Math.abs(y2) + Math.abs(x2), 1);
        denominator = Math.max((x2-y2), (x2+y2));

        lfVectorSpeed = (x2 - y2 ) / denominator;
        rfVectorSpeed = (x2 + y2 ) / denominator;
        lrVectorSpeed = (x2 + y2 ) / denominator;
        rrVectorSpeed = (x2 - y2 ) / denominator;

        if (distanceToTarget > target.tp.StartToSlowAt )
            speedFactor = target.tp.maxSpeed;
        else
            speedFactor = (distanceToTarget / target.tp.StartToSlowAt) * target.tp.maxSpeed;

        lfVectorSpeed = lfVectorSpeed * speedFactor;
        rfVectorSpeed = rfVectorSpeed * speedFactor;
        lrVectorSpeed = lrVectorSpeed * speedFactor;
        rrVectorSpeed = rrVectorSpeed * speedFactor;
    }

    //  This method determines the speed of the motors for turning the robot
    // to the correct alignment.  These speeds are later combined to with the
    // vector speeds to determine actual motor speeds required.
    public void findMotorTurnSpeeds (Target target, Pose2D pos) {
        double speedFactor;   // between .07 and 1 as needed
        //diff_h = target.findDifferenceInAngle(pos, target);
        if (diff_h < target.tp.angleVarianceAllowed)
            speedFactor = 0;

        else {
            if (diff_h >= 100)
                speedFactor = 1;  //upper speed factor
            else
                speedFactor = diff_h / 100;
            if (speedFactor < .07)
                speedFactor = .07;  // lower speed factor
        }

        // Turn either right or left depending on the current and target headings
        if (target.h > pos.getHeading(AngleUnit.DEGREES) ) {//Turn Left
            lfTurnSpeed = -speedFactor;
            lrTurnSpeed = -speedFactor;
            rfTurnSpeed = speedFactor;
            rrTurnSpeed = speedFactor;
        }
        else {  // Turn Right
            lfTurnSpeed = speedFactor;
            lrTurnSpeed = speedFactor;
            rfTurnSpeed = -speedFactor;
            rrTurnSpeed = -speedFactor;
        }
    }

    // this method combines the vector and motor speeds.  For now it simply adds them together
    // and assures they fall within the range of the allowed motor speeds -1...1
    private void combineMotorSpeeds() {
        double lfDenominator = lfVectorSpeed + lfTurnSpeed;
        double lrDenominator = lrVectorSpeed + lrTurnSpeed;
        double rfDenominator = rfVectorSpeed + rfTurnSpeed;
        double rrDenominator = rrVectorSpeed + rrTurnSpeed;

        lfSpeed = lfVectorSpeed + lfTurnSpeed;
        lrSpeed = lrVectorSpeed + lrTurnSpeed;
        rfSpeed = rfVectorSpeed + rfTurnSpeed;
        rrSpeed = rrVectorSpeed + rrTurnSpeed;

//        lfSpeed = lfVectorSpeed/lfDenominator + lfTurnSpeed/lfDenominator;
//        lrSpeed = lrVectorSpeed/lrDenominator + lrTurnSpeed/lrDenominator;
//        rfSpeed = rfVectorSpeed/rfDenominator + rfTurnSpeed/rfDenominator;
//        rrSpeed = rrVectorSpeed/rrDenominator + rrTurnSpeed/rrDenominator;
    }

    // This method checks to status of the goTo command...Time, x, y, and angle
    // It returns the move code accordingly
    public MoveCode goToStatus(ElapsedTime runtime, Target target, Pose2D pos)
    {
        double diff_x;
        double diff_y;

        getDistanceToTarget(pos, target);
        if (runtime.seconds() >= target.tp.timeAllowed)
            return MoveCode.TimedOut;
        if (distanceToTarget > target.tp.distanceVarianceAllowed ||
            Math.abs(pos.getHeading(AngleUnit.DEGREES) - target.h) > target.tp.angleVarianceAllowed)
            return MoveCode.NotDone;
        return MoveCode.Done;
    }

    // This method gets the robot to move to the desired target
    public MoveCode moveTo(Target atarget)  {
        int loopCount = 0;
        MoveCode moveCode = MoveCode.NotDone;
        ElapsedTime runtime = new ElapsedTime();   // time the move
        runtime.reset();

        robot.pinpoint.update();   // Get the current position of the Robot
        Pose2D pos = robot.pinpoint.getPosition();

        while ( moveCode == MoveCode.NotDone && opMode.opModeIsActive()) {
            findMotorVectorSpeed(atarget, pos);  // straight line motor speeds
            findMotorTurnSpeeds(atarget, pos);   // find turn motor speeds
            combineMotorSpeeds();
            turnMotorsOn();
            robot.pinpoint.update();   // Get the current position of the Robot
            pos = robot.pinpoint.getPosition();

            // get the status of the move (position and time)
            moveCode = goToStatus(runtime, atarget, pos);
            loopCount++;
            if (loopCount > 300)
                moveCode = MoveCode.Diagnostic;
        }
        pos = robot.pinpoint.getPosition();

        telemetry.addData("pos: ","x: %.2f, y: %.2f, h: %.2f",
                pos.getX(DistanceUnit.CM),
                pos.getY(DistanceUnit.CM),
                pos.getHeading(AngleUnit.DEGREES));

        telemetry.update();
        stop();  // we ran out of time or we are at the desired location
        return moveCode;
    }
}
