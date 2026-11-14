package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TelemeteryTest {
    RobotHardware robot;
    Telemetry telemetry;
    LinearOpMode opMode;

    public TelemeteryTest(RobotHardware robot,
                          Telemetry telemetry,
                          LinearOpMode opMode)  {
        this.robot = robot;
        this.telemetry = telemetry;
        this.opMode = opMode;
    }

    public void printSomething () {
        telemetry.addData("Printing Something", true);
        telemetry.update();
    }
}
