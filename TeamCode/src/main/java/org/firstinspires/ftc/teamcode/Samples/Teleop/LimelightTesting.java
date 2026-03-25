package org.firstinspires.ftc.teamcode.Samples.Teleop;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "LimelightTesting")
public class LimelightTesting extends LinearOpMode {

    private DcMotor Left;
    private DcMotor Right;
    private DcMotor limelightMotor;
    private Limelight3A limelight;
    private DigitalChannel limitSwitch;

    @Override
    public void runOpMode() {

        // ======= HARDWARE SETUP =======
        Left = hardwareMap.get(DcMotor.class, "Left");
        Right = hardwareMap.get(DcMotor.class, "Right");
        limelightMotor = hardwareMap.get(DcMotor.class, "limelightMotor");
        limitSwitch = hardwareMap.get(DigitalChannel.class, "limitSwitch");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        Right.setDirection(DcMotor.Direction.FORWARD);
        Left.setDirection(DcMotor.Direction.REVERSE);
        Right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        limelightMotor.setDirection(DcMotor.Direction.FORWARD);
        limelightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // 🔥 Encoder setup
        limelightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        limelightMotor.setTargetPosition(0);
        limelightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        limitSwitch.setMode(DigitalChannel.Mode.INPUT);

        limelight.pipelineSwitch(0);
        limelight.start();

        waitForStart();

        // ======= CONTROL PARAMETERS =======
        double ticksPerDegree = 1;   // TODO: TUNE THIS
        int minPosition = -1000;      // safety bounds
        int maxPosition = 1000;
        double speed = 0.7;

        while (opModeIsActive()) {

            // ======= LIMIT SWITCH HOMING =======
            if (!limitSwitch.getState()) { // pressed
                limelightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                limelightMotor.setTargetPosition(0);
                limelightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            // ======= GET LIMELIGHT DATA =======
            LLResult result = limelight.getLatestResult();
            boolean hasTarget = result != null && result.isValid();
            double tx = hasTarget ? result.getTx() : 0;

            int deltaTicks = 0;
            if (hasTarget && Math.abs(tx) > 1) {

                int currentPos = limelightMotor.getCurrentPosition();

                // Convert angle error → ticks
                deltaTicks = (int) (tx * ticksPerDegree);

                int targetPos = currentPos + deltaTicks;

                // Clamp to safe range
                targetPos = Range.clip(targetPos, minPosition, maxPosition);

                limelightMotor.setTargetPosition(targetPos);
                limelightMotor.setPower(speed);

            } else {
                // Hold position when aligned
                limelightMotor.setPower(0.1); // small holding power
            }

            // ======= ROBOT DRIVE =======
            double leftPower = -gamepad1.left_stick_y;
            double rightPower = -gamepad1.right_stick_y;

            if (hasTarget) {
                leftPower *= 0.5;
                rightPower *= 0.5;
            }

            Left.setPower(leftPower);
            Right.setPower(rightPower);

            // ======= TELEMETRY =======
            telemetry.addData("Has Target", hasTarget);
            telemetry.addData("Tx", tx);
            telemetry.addData("Target Pos", limelightMotor.getTargetPosition());
            telemetry.addData("Current Pos", limelightMotor.getCurrentPosition());
            telemetry.addData("DeltaTicks", deltaTicks);
            telemetry.addData("Left Power", leftPower);
            telemetry.addData("Right Power", rightPower);
            telemetry.update();
        }
    }
}