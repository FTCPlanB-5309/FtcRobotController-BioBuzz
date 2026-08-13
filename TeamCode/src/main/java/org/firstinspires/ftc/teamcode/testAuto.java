package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 *  This is basic auto using the pinpoint odometery from GoBilda
 */

@Autonomous(name="Robot: Auto Drive By Encoder", group="Robot")
public class testAuto extends LinearOpMode {

    //Move move = new Move()
    RobotHardware robot       = new RobotHardware(this);
    DriveTrain driveTrain = new DriveTrain (robot, telemetry, this);

    // This is a list of Target Profiles that can be used to modify the way the robot
    // Approaches the target and determines how close it needs to be
    TargetProfile way_point = new TargetProfile(.8,.08,3,3, 5, 3);

    // This is a list of targets
    Target firstTarget = new Target(15, 15, 45, way_point);
    Target secondTarget = new Target(20, -20, -45, way_point);

    MoveCodes moveCode = MoveCodes.Done;


    @Override
    public void runOpMode() {
        robot.init();   // Initialize the various hardware components

        // Wait for the game to start (driver presses START)
        waitForStart();

        // Go thru a series of moves and actions based on a set target positions
        moveCode = driveTrain.goTo(firstTarget);
        moveCode = driveTrain.goTo(secondTarget);
    }
}
