package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 *  This is basic auto using the pinpoint odometery from GoBilda
 *  @Autonomous(name = "RightBlueAuto")

    public class RightBlueAuto extends LinearOpMode {
 */

@Autonomous(name="Robot: Auto Odometery", group="Robot")
public class OdometeryTest extends LinearOpMode {

    RobotHardware robot       = new RobotHardware(this);
    // This is a list of targets
    TargetProfile way_point = new TargetProfile(.5,.08,10,3, 5, 3);

    // List of Targets
    Target firstTarget = new Target(30, 15, 30, way_point);
    Target secondTarget = new Target(20, -20, -45, way_point);


    // This is a list of Target Profiles that can be used to modify the way the robot
    // Approaches the target and determines how close it needs to be
    MoveCode moveCode = MoveCode.Done;

    @Override
    public void runOpMode() throws InterruptedException {
        DriveTrain driveTrain = new DriveTrain (robot, telemetry, this);
        robot.init();   // Initialize the various hardware components

        // Wait for the game to start (driver presses START)
        waitForStart();

        // Go thru a series of moves and actions based on a set target positions
        moveCode = driveTrain.moveTo(firstTarget, telemetry);
        //moveCode = driveTrain.moveTo(secondTarget);
        telemetry.addData("moveCode", moveCode);
        telemetry.update();

        Thread.sleep(30000);
        //moveCode = driveTrain.goTo(secondTarget);
    }
}
