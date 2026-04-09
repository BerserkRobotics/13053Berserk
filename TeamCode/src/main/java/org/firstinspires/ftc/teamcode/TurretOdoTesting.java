package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TurretOdoTesting")
public class TurretOdoTesting extends LinearOpMode {

    DcMotor turret;
    DcMotor leftOdo, rightOdo, perpOdo;

    Odometry odo = new Odometry();
    PID turretPID = new PID(1.2, 0.0, 0.1);

    // TODO: CHANGE THESE FOR YOUR ROBOT
    double TICKS_TO_RAD = 2 * Math.PI / 8192.0;
    double GEAR_RATIO = 3.0;

    double MAX_TURRET = Math.toRadians(180);
    double MIN_TURRET = Math.toRadians(-180);

    double targetX = 72;
    double targetY = 36;

    @Override
    public void runOpMode() {

        turret = hardwareMap.get(DcMotor.class, "turret");

        leftOdo = hardwareMap.get(DcMotor.class, "leftOdo");
        rightOdo = hardwareMap.get(DcMotor.class, "rightOdo");
        perpOdo = hardwareMap.get(DcMotor.class, "perpOdo");

        // Reset encoders
        leftOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        perpOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftOdo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightOdo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        perpOdo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {

            // Update odometry
            odo.update(
                    leftOdo.getCurrentPosition(),
                    rightOdo.getCurrentPosition(),
                    perpOdo.getCurrentPosition()
            );

            double manual = gamepad2.right_stick_x;

            if (Math.abs(manual) > 0.05) {
                turret.setPower(manual * 0.6);
            } else {

                // Prediction
                double lookahead = 0.25;
                double predictedX = odo.x + odo.vx * lookahead;
                double predictedY = odo.y + odo.vy * lookahead;

                double dx = targetX - predictedX;
                double dy = targetY - predictedY;

                double angleToTarget = Math.atan2(dy, dx);
                double turretTarget = normalize(angleToTarget - odo.heading);

                turretTarget = Math.max(MIN_TURRET, Math.min(MAX_TURRET, turretTarget));

                double turretAngle = turret.getCurrentPosition()
                        * TICKS_TO_RAD / GEAR_RATIO;

                double error = normalize(turretTarget - turretAngle);

                double power = turretPID.update(error);
                power += Math.signum(error) * 0.05;

                power = Math.max(-1, Math.min(1, power));

                turret.setPower(power);
            }

            telemetry.addData("X", odo.x);
            telemetry.addData("Y", odo.y);
            telemetry.addData("Heading", odo.heading);
            telemetry.update();
        }
    }

    double normalize(double angle) {
        while (angle > Math.PI) angle -= 2 * Math.PI;
        while (angle < -Math.PI) angle += 2 * Math.PI;
        return angle;
    }
}

// ================= ODOMETRY =================

class Odometry {

    double x = 0, y = 0, heading = 0;

    int lastL = 0, lastR = 0, lastP = 0;

    double vx = 0, vy = 0;
    double lastX = 0, lastY = 0;
    long lastTime = System.nanoTime();

    double TICKS_PER_INCH = 8192 / (Math.PI * 2.0);
    double TRACK_WIDTH = 14.5;
    double PERP_OFFSET = 7.0;

    public void update(int L, int R, int P) {

        int dL = L - lastL;
        int dR = R - lastR;
        int dP = P - lastP;

        lastL = L;
        lastR = R;
        lastP = P;

        double left = dL / TICKS_PER_INCH;
        double right = dR / TICKS_PER_INCH;
        double perp = dP / TICKS_PER_INCH;

        double dTheta = (right - left) / TRACK_WIDTH;

        double dx = (left + right) / 2.0;
        double dy = perp - (PERP_OFFSET * dTheta);

        double mid = heading + dTheta / 2.0;

        x += dx * Math.cos(mid) - dy * Math.sin(mid);
        y += dx * Math.sin(mid) + dy * Math.cos(mid);

        heading += dTheta;

        // velocity
        long now = System.nanoTime();
        double dt = (now - lastTime) / 1e9;
        lastTime = now;

        if (dt > 0) {
            vx = (x - lastX) / dt;
            vy = (y - lastY) / dt;
        }

        lastX = x;
        lastY = y;
    }
}

// ================= PID =================

class PID {

    double kP, kI, kD;
    double integral = 0;
    double lastError = 0;
    long lastTime = System.nanoTime();

    public PID(double p, double i, double d) {
        kP = p; kI = i; kD = d;
    }

    public double update(double error) {

        long now = System.nanoTime();
        double dt = (now - lastTime) / 1e9;
        lastTime = now;

        if (dt <= 0) dt = 0.001;

        integral += error * dt;
        double derivative = (error - lastError) / dt;

        lastError = error;

        return kP * error + kI * integral + kD * derivative;
    }
}

@TeleOp(name="MainTeleOp")
public class MainTeleOp extends LinearOpMode {

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
