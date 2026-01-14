package org.firstinspires.ftc.teamcode.Decode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "oneBallAutoRight", group = "blue", preselectTeleOp = "fullDrive")
public class oneBallAutoRight extends LinearOpMode {

    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor BackLeft;
    private DcMotor BackRight;

    private DcMotor ROuttakeSpinner;
    private DcMotor LOuttakeSpinner;

    private CRServo middle;

    @Override
    public void runOpMode() {
        FrontRight = hardwareMap.get(DcMotor.class, "rightFront");
        BackRight = hardwareMap.get(DcMotor.class, "rightBack");
        FrontLeft = hardwareMap.get(DcMotor.class, "leftFront");
        BackLeft = hardwareMap.get(DcMotor.class, "leftBack");

        ROuttakeSpinner = hardwareMap.get(DcMotor.class, "ROuttakeSpinner");
        LOuttakeSpinner = hardwareMap.get(DcMotor.class, "LOuttakeSpinner");

        middle = hardwareMap.get(CRServo.class, "middle");


        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.FORWARD);
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);

        ROuttakeSpinner.setDirection(DcMotor.Direction.FORWARD);
        LOuttakeSpinner.setDirection(DcMotor.Direction.REVERSE);

        middle.setDirection(CRServo.Direction.FORWARD);


        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        ROuttakeSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LOuttakeSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
            sleep(250);

            BackLeft.setPower(0.5);
            BackRight.setPower(-0.5);
            FrontLeft.setPower(0.5);
            FrontRight.setPower(-0.5);
            sleep(200);

            BackLeft.setPower(0);
            BackRight.setPower(0);
            FrontLeft.setPower(0);
            FrontRight.setPower(0);
            ROuttakeSpinner.setPower(0.7);
            LOuttakeSpinner.setPower(0.7);
            sleep(3000);

            middle.setPower(-1);
            sleep(2000);

            ROuttakeSpinner.setPower(0);
            LOuttakeSpinner.setPower(0);
            middle.setPower(0);
            BackLeft.setPower(0.5);
            BackRight.setPower(-0.5);
            FrontLeft.setPower(-0.5);
            FrontRight.setPower(0.5);
            sleep(2000);

            BackLeft.setPower(0.5);
            BackRight.setPower(0.5);
            FrontLeft.setPower(0.5);
            FrontRight.setPower(0.5);
            sleep(1000);

        }
    }
}