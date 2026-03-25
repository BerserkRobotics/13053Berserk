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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

// BASIC DRIVE

@TeleOp(name = "LimelightJoystick")
public class LimelightJoystick extends LinearOpMode {
    private DcMotor Left;
    private DcMotor Right;
    private DcMotor limelightMotor;
    private DigitalChannel limitSwitch;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");

        Left = hardwareMap.get(DcMotor.class, "Left");
        Right = hardwareMap.get(DcMotor.class, "Right");
        limelightMotor = hardwareMap.get(DcMotor.class, "limelightMotor");
        limitSwitch = hardwareMap.get(DigitalChannel.class, "limitSwitch");

        Right.setDirection(DcMotor.Direction.FORWARD);
        Left.setDirection(DcMotor.Direction.REVERSE);

        Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        limelightMotor.setDirection(DcMotor.Direction.REVERSE);
        limelightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        limitSwitch.setMode(DigitalChannel.Mode.INPUT);
        limelightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        limelightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double left_power = 0;
        double right_power = 0;
        double speedSetter = 1;

        double x = 0;
        double y = 0;

        double limelightRadian = 0;
        boolean limitswitchData = false;
        double limelightPosition = 0;
        limelightMotor.setPower(0.1);

        telemetry.addData("Status", "Initialized");
        waitForStart();

        while (opModeIsActive()) {
            right_power  = (-gamepad1.left_stick_y - gamepad1.right_stick_x) * speedSetter;
            left_power = (-gamepad1.left_stick_y + gamepad1.right_stick_x) * speedSetter;

            Right.setPower(right_power);
            Left.setPower(left_power);

            limitswitchData = limitSwitch.getState();
            x = gamepad2.right_stick_x;
            y = -gamepad2.right_stick_y;
            if ((x * x + y * y) > 0.9) {
                limelightRadian = Math.atan2(y, x);
                if (limelightRadian < 0) {
                    limelightRadian += 2 * Math.PI;
                }
                limelightPosition = limelightRadian * 384.5 / (2 * Math.PI);
            }
            limelightMotor.setTargetPosition((int) limelightPosition);
            limelightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            telemetry.addData("limit Status: ",!limitswitchData);
            telemetry.addData("Ticks: ", limelightPosition);
            telemetry.addData("Circle Number: ", null);
            telemetry.addData("Previous Radian: ", null);
            telemetry.addData("Current Radian: ", limelightRadian);
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}