/* Copyright (c) 2022 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/*


 */

@TeleOp(name="TeleOpMotorTest", group="Robot")
public class TeleOpTest extends LinearOpMode {

    // Create a RobotHardware object to be used to access robot hardware.
    // Prefix any hardware functions with "robot." to access this class.

    @Override
    public void runOpMode() {


        // initialize all the hardware, using the hardware class. See how clean and simple this is?
        RobotHardware robot       = new RobotHardware(this);
        robot.init();

        // Send telemetry message to signify robot waiting;
        // Wait for the game to start (driver presses START)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            drive(-gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x,
                    robot);
            if (gamepad2.y)
                robot.IntakeMotor.setPower(0.7);
            if (gamepad2.y == false && gamepad2.x== false)
                robot.IntakeMotor.setPower(0);

            if (gamepad2.b) {
                robot.NectorMotor.setPower(0.52);
                robot.PollenMotor.setPower(0.60);
            }

            if (gamepad2.a) {
                robot.NectorMotor.setPower(0);
                robot.PollenMotor.setPower(0);
            }
            //this set of code allows us to open the arms causing balls to fall into firing chamber r
            if (gamepad2.right_trigger > 0.5)
                robot.NectorOpener.setPosition(robot.NECTOR_OPEN_VALUE);

            if (gamepad2.right_trigger == 0 )
                robot.NectorOpener.setPosition(robot.NECTOR_CLOSED_VALUE);

            if (gamepad2.left_trigger > 0.5)
                robot.PollenOpener.setPosition(robot.POLLEN_OPEN_VALUE);

            if (gamepad2.left_trigger == 0)
                robot.PollenOpener.setPosition(robot.POLLEN_CLOSED_VALUE);

            if (gamepad2.x)
                robot.IntakeMotor.setPower(-0.5);

            if (gamepad2.left_bumper)
                robot.IntakeRelease.setPosition(0.4);
//            if (gamepad1.x)
//                robot.LeftFrontMotor.setPower(.5);
//            else
//                robot.LeftFrontMotor.setPower(0);
//
//            if (gamepad1.y)
//                robot.RightFrontMotor.setPower(.5);
//            else
//                robot.RightFrontMotor.setPower(0);
//
//            if (gamepad1.b)
//                robot.RightRearMotor.setPower(.5);
//            else
//                robot.RightRearMotor.setPower(0);
//
//            if (gamepad1.a)
//                robot.LeftRearMotor.setPower(.5);
//            else
//                robot.LeftRearMotor.setPower(0);
//            sleep(50);
        }
    }
    public void drive(double forward, double right, double rotate, RobotHardware robot) {
        // This calculates the power needed for each wheel based on the amount of forward,
        // strafe right, and rotate
        double LeftFrontPower = forward + right + rotate;
        double RightFrontPower = forward - right - rotate;
        double RightRearPower = forward + right - rotate;
        double LeftRearPower = forward - right + rotate;

        double maxPower = 1.0;
        double maxSpeed = 1.0;  // make this slower for outreaches

        // This is needed to make sure we don't pass > 1.0 to any wheel
        // It allows us to keep all of the motors in proportion to what they should
        // be and not get clipped
        maxPower = Math.max(maxPower, Math.abs(LeftFrontPower));
        maxPower = Math.max(maxPower, Math.abs(RightFrontPower));
        maxPower = Math.max(maxPower, Math.abs(RightRearPower));
        maxPower = Math.max(maxPower, Math.abs(LeftRearPower));

        robot.LeftFrontMotor.setPower(LeftFrontPower);
        robot.RightFrontMotor.setPower(RightFrontPower);
        robot.RightRearMotor.setPower(RightRearPower);
        robot.LeftRearMotor.setPower(LeftRearPower);
    }
}
