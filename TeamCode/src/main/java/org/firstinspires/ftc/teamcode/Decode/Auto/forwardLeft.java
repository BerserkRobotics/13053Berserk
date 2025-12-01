package org.firstinspires.ftc.teamcode.Decode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "forwardLeft", group = "a", preselectTeleOp = "fullDrive")
public class forwardLeft extends LinearOpMode {

    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor BackLeft;
    private DcMotor BackRight;

    @Override
    public void runOpMode() {
        FrontRight = hardwareMap.get(DcMotor.class, "rightFront");
        BackRight = hardwareMap.get(DcMotor.class, "rightBack");
        FrontLeft = hardwareMap.get(DcMotor.class, "leftFront");
        BackLeft = hardwareMap.get(DcMotor.class, "leftBack");

        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);

        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BackLeft.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);

        waitForStart();
        if (opModeIsActive()) {

            BackLeft.setPower(-0.5);
            BackRight.setPower(-0.5);
            FrontLeft.setPower(-0.5);
            FrontRight.setPower(-0.5);
            sleep(1000);
            BackLeft.setPower(-0.5);
            BackRight.setPower(0.5);
            FrontLeft.setPower(0.5);
            FrontRight.setPower(-0.5);
            sleep(1000);

        }
    }
}