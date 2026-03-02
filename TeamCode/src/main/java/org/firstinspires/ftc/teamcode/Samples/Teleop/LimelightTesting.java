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
import com.qualcomm.robotcore.hardware.DcMotorSimple;

// BASIC DRIVE

@TeleOp(name = "LimelightTesting")
public class LimelightTesting extends LinearOpMode {
    private DcMotor Left;
    private DcMotor Right;
    private DcMotor limelightMotor;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");

        Left = hardwareMap.get(DcMotor.class, "Left");
        Right = hardwareMap.get(DcMotor.class, "Right");
        limelightMotor = hardwareMap.get(DcMotor.class, "limelightMotor");

        Right.setDirection(DcMotor.Direction.FORWARD);
        Left.setDirection(DcMotor.Direction.FORWARD);
        limelightMotor.setDirection(DcMotor.Direction.FORWARD);

        Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        limelightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        double left_power = 0;
        double right_power = 0;
        limelightMotor.setPower(0);

        telemetry.addData("Status", "Initialized");


        waitForStart();

        while (opModeIsActive()) {

            double moveSpeed   = -gamepad1.left_stick_y;
            double strafeSpeed = gamepad1.left_stick_x;
            double speedSetter = 1;

            right_power  = (moveSpeed - gamepad1.right_stick_x - strafeSpeed) * speedSetter;
            left_power = (moveSpeed - gamepad1.right_stick_x + strafeSpeed) * speedSetter;

            Right.setPower(right_power);
            Left.setPower(left_power);

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}