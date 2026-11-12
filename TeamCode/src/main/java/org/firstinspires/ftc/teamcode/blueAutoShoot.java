package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class blueAutoShoot {
    TargetProfile Shooting = new TargetProfile(.55,.08,10,3, 5, 2);
    TargetProfile ShortDrive = new TargetProfile(.55,.08,10,3, 5, 3);
    TargetProfile LongDrive = new TargetProfile(.55,.08,10,3, 5, 7);
    Target FirstTarget = new Target( -10,  0, 0,Shooting);
    Target SecondTarget = new Target( -25,  0, 0,ShortDrive);
    Target ThirdTarget = new Target( 0, 94.5, 0,LongDrive);
    Target FourthTarget = new Target( -203, 0, 0,LongDrive);
    Target FifthTarget = new Target( 0, 38, 0,ShortDrive);



}

    //Go backward 4 Inches
    //Release the active intake
    //shoot all balls at speed 0.55
    //go backward 10 inches
    // go right 35 inches
    // go back 80 inches
    // go right 15 inches
    // Wheal speed 0.45
