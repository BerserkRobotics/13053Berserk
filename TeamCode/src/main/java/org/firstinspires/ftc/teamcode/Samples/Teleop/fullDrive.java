/* Copyright (c) 2017 FIRST. All rights reserved.
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

package org.firstinspires.ftc.teamcode.Samples.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

// BASIC DRIVE W/ INTAKE & OUTTAKE

@TeleOp(name = "fullDrive", group = "main")
public class fullDrive extends LinearOpMode {
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor BackLeft;
    private DcMotor BackRight;

    private DcMotor IntakeRoller;
    private DcMotor ROuttakeSpinner;
    private DcMotor LOuttakeSpinner;
    private CRServo BSpinner;
    private CRServo TSpinner;

    double OuttakeSpeed = 0.5;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        FrontRight = hardwareMap.get(DcMotor.class, "rightFront");
        BackRight = hardwareMap.get(DcMotor.class, "rightBack");
        FrontLeft = hardwareMap.get(DcMotor.class, "leftFront");
        BackLeft = hardwareMap.get(DcMotor.class, "leftBack");

        IntakeRoller = hardwareMap.get(DcMotor.class, "IntakeRoller");
        ROuttakeSpinner = hardwareMap.get(DcMotor.class, "ROuttakeSpinner");
        LOuttakeSpinner = hardwareMap.get(DcMotor.class, "LOuttakeSpinner");

        BSpinner = hardwareMap.get(CRServo.class, "BSpinner");
        TSpinner = hardwareMap.get(CRServo.class, "TSpinner");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.REVERSE);
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);

        //TODO: find motor directions
        IntakeRoller.setDirection(DcMotor.Direction.FORWARD);
        ROuttakeSpinner.setDirection(DcMotor.Direction.REVERSE);
        LOuttakeSpinner.setDirection(DcMotor.Direction.FORWARD);

        BSpinner.setDirection(CRServo.Direction.REVERSE);
        TSpinner.setDirection(CRServo.Direction.FORWARD);

        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IntakeRoller.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ROuttakeSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LOuttakeSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        double front_left_power  = 0;
        double front_right_power = 0;
        double back_left_power   = 0;
        double back_right_power  = 0;

        IntakeRoller.setPower(0);
        LOuttakeSpinner.setPower(0);
        ROuttakeSpinner.setPower(0);
        BSpinner.setPower(0);
        TSpinner.setPower(0);


        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");


        waitForStart();

        while (opModeIsActive()) {

            double moveSpeed   = -gamepad1.left_stick_y;
            double strafeSpeed = gamepad1.left_stick_x;
            double speedSetter = 1;

            front_right_power  = (moveSpeed - gamepad1.right_stick_x - strafeSpeed) * speedSetter;
            front_left_power   = (moveSpeed - gamepad1.right_stick_x + strafeSpeed) * speedSetter;
            back_left_power    = (moveSpeed + gamepad1.right_stick_x - strafeSpeed) * speedSetter;
            back_right_power   = (moveSpeed + gamepad1.right_stick_x + strafeSpeed) * speedSetter;

            FrontRight.setPower(front_right_power);
            FrontLeft.setPower(front_left_power);
            BackRight.setPower(back_right_power);
            BackLeft.setPower(back_left_power);

            OuttakeSpeed += 0.05*(gamepad2.right_stick_y);
            if (OuttakeSpeed > 1) {
                OuttakeSpeed = 1;
            } else if (OuttakeSpeed < 0.25) {
                OuttakeSpeed = 0.25;
            }

            if (gamepad2.y) {
                BSpinner.setPower(1);
                TSpinner.setPower(1);
            } else if (gamepad2.a) {
                BSpinner.setPower(-1);
                TSpinner.setPower(-1);
            } else {
                BSpinner.setPower(0);
                TSpinner.setPower(0);
            }

            if (gamepad2.dpad_up) {
                IntakeRoller.setPower(1);
            } else if (gamepad2.dpad_down) {
                IntakeRoller.setPower(-1);
            } else {
                IntakeRoller.setPower(0);
            }

            if (gamepad2.dpad_left) {
                ROuttakeSpinner.setPower(-OuttakeSpeed);
                LOuttakeSpinner.setPower(-OuttakeSpeed);
            } else if (gamepad2.dpad_right) {
                ROuttakeSpinner.setPower(OuttakeSpeed);
                LOuttakeSpinner.setPower(OuttakeSpeed);
            } else {
                ROuttakeSpinner.setPower(0);
                LOuttakeSpinner.setPower(0);
            }


            telemetry.addData("Status", "Running");
            telemetry.addData("OuttakeSpeed", OuttakeSpeed);
            telemetry.update();
        }
    }
}