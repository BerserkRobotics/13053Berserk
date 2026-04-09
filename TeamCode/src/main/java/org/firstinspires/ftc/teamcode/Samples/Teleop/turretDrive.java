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

// BASIC DRIVE W/ INTAKE & OUTTAKE

@TeleOp(name = "turretDrive")
public class turretDrive extends LinearOpMode {

    DcMotor frontLeft, frontRight, backLeft, backRight;

    @Override
    public void runOpMode() {

        frontLeft = hardwareMap.get(DcMotor.class, "fl");
        frontRight = hardwareMap.get(DcMotor.class, "fr");
        backLeft = hardwareMap.get(DcMotor.class, "bl");
        backRight = hardwareMap.get(DcMotor.class, "br");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        DcMotor armMotor = hardwareMap.get(DcMotor.class, "arm");
        DcMotor shooterMotor = hardwareMap.get(DcMotor.class, "shooter");

        CRServo spinner = hardwareMap.get(CRServo.class, "servo");
        DcMotor turret = hardwareMap.get(DcMotor.class, "turret");

        DcMotor leftOdo = hardwareMap.get(DcMotor.class, "leftOdo");
        DcMotor rightOdo = hardwareMap.get(DcMotor.class, "rightOdo");
        DcMotor perpOdo = hardwareMap.get(DcMotor.class, "perpOdo");

        double xPos = 0;
        double yPos = 0;
        double heading = 0;

        int lastLeft = 0;
        int lastRight = 0;
        int lastPerp = 0;

        boolean autoAim = true;
        boolean lastB = false;

        // Auto shooter toggle
        boolean shooterEnabled = false;
        boolean lastY = false;

        // ✅ NEW: manual shooter state
        double manualShooterPower = 0;

        waitForStart();

        while (opModeIsActive()) {

            // =============================
            // 🚗 DRIVER CONTROL
            // =============================
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            frontLeft.setPower((y + x + rx) / denominator);
            backLeft.setPower((y - x + rx) / denominator);
            frontRight.setPower((y - x - rx) / denominator);
            backRight.setPower((y + x - rx) / denominator);

            // =============================
            // 🎮 OPERATOR CONTROL
            // =============================

            // Arm
            armMotor.setPower(-gamepad2.left_stick_y);

            // Toggle auto/manual
            if (gamepad2.b && !lastB) autoAim = !autoAim;
            lastB = gamepad2.b;

            // =============================
            // 🎯 SHOOTER CONTROL
            // =============================

            if (autoAim) {

                // Y toggles ON/OFF
                if (gamepad2.y && !lastY) {
                    shooterEnabled = !shooterEnabled;
                }
                lastY = gamepad2.y;

            } else {

                // ✅ SET speeds (no holding)
                if (gamepad2.a) {
                    manualShooterPower = 0.7;
                }
                if (gamepad2.x) {
                    manualShooterPower = 0.9;
                }
                if (gamepad2.y) {
                    manualShooterPower = 1.0;
                }

                // ✅ D-pad down turns OFF
                if (gamepad2.dpad_down) {
                    manualShooterPower = 0;
                }

                shooterMotor.setPower(manualShooterPower);

                // Reset auto toggle
                shooterEnabled = false;
                lastY = gamepad2.y;
            }

            // =============================
            // SERVO (BUMPERS)
            // =============================
            double servoPower = 0;
            if (gamepad2.left_bumper) servoPower = 1;
            else if (gamepad2.right_bumper) servoPower = -1;
            spinner.setPower(servoPower);

            // =============================
            // 📍 ODOMETRY
            // =============================
            int left = leftOdo.getCurrentPosition();
            int right = rightOdo.getCurrentPosition();
            int perp = perpOdo.getCurrentPosition();

            int dLeft = left - lastLeft;
            int dRight = right - lastRight;
            int dPerp = perp - lastPerp;

            lastLeft = left;
            lastRight = right;
            lastPerp = perp;

            double trackWidth = 14.0;

            double dHeading = (dRight - dLeft) / trackWidth;
            heading += dHeading;

            double forward = (dLeft + dRight) / 2.0;
            double strafe = dPerp;

            xPos += forward * Math.cos(heading) - strafe * Math.sin(heading);
            yPos += forward * Math.sin(heading) + strafe * Math.cos(heading);

            // =============================
            // 🎯 TURRET CONTROL
            // =============================

            if (autoAim) {

                double targetX = 0;
                double targetY = 100;

                double dx = targetX - xPos;
                double dy = targetY - yPos;

                double distance = Math.sqrt(dx * dx + dy * dy);

                double minDist = 20;
                double maxDist = 120;

                double minPower = 0.6;
                double maxPower = 1.0;

                double shooterPower = minPower +
                        (distance - minDist) * (maxPower - minPower) / (maxDist - minDist);

                shooterPower = Math.max(minPower, Math.min(maxPower, shooterPower));

                // Only run if enabled
                if (shooterEnabled) {
                    shooterMotor.setPower(shooterPower);
                } else {
                    shooterMotor.setPower(0);
                }

                double targetAngle = Math.atan2(dy, dx);
                double error = targetAngle - heading;

                while (error > Math.PI) error -= 2 * Math.PI;
                while (error < -Math.PI) error += 2 * Math.PI;

                if (Math.abs(error) < 0.02) {
                    turret.setPower(0);
                } else {
                    double turretPower = error * 0.5;
                    turretPower = Math.max(-0.5, Math.min(0.5, turretPower));
                    turret.setPower(turretPower);
                }

            } else {

                double manualPower = gamepad2.right_trigger - gamepad2.left_trigger;
                turret.setPower(manualPower * 0.5);

                int turretPos = turret.getCurrentPosition();
                int maxTicks = 1000;
                int minTicks = -1000;

                if (turretPos > maxTicks && manualPower > 0) turret.setPower(0);
                if (turretPos < minTicks && manualPower < 0) turret.setPower(0);
            }
        }
    }
}
