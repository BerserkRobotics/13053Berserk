package org.firstinspires.ftc.teamcode.Decode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.Robot;

//https://rr.brott.dev/docs/v1-0/guides/centerstage-auto/

@Config
@Autonomous(name = "farBlueBasic", group = "blue", preselectTeleOp = "fullDrive")
public class farBlueBasic extends LinearOpMode {

    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor BackLeft;
    private DcMotor BackRight;

    private DcMotor IntakeRoller;
    private DcMotor ROuttakeSpinner;
    private DcMotor LOuttakeSpinner;
    /*
    private CRServo BSpinner;
    private CRServo TSpinner;
     */

    @Override
    public void runOpMode() {
        FrontRight = hardwareMap.get(DcMotor.class, "rightFront");
        BackRight = hardwareMap.get(DcMotor.class, "rightBack");
        FrontLeft = hardwareMap.get(DcMotor.class, "leftFront");
        BackLeft = hardwareMap.get(DcMotor.class, "leftBack");

        IntakeRoller = hardwareMap.get(DcMotor.class, "IntakeRoller");
        ROuttakeSpinner = hardwareMap.get(DcMotor.class, "ROuttakeSpinner");
        LOuttakeSpinner = hardwareMap.get(DcMotor.class, "LOuttakeSpinner");

        /*
        BSpinner = hardwareMap.get(CRServo.class, "BSpinner");
        TSpinner = hardwareMap.get(CRServo.class, "TSpinner");
         */

        FrontRight.setDirection(DcMotor.Direction.FORWARD);
        BackRight.setDirection(DcMotor.Direction.REVERSE);
        FrontLeft.setDirection(DcMotor.Direction.FORWARD);
        BackLeft.setDirection(DcMotor.Direction.REVERSE);

        IntakeRoller.setDirection(DcMotor.Direction.FORWARD);
        ROuttakeSpinner.setDirection(DcMotor.Direction.REVERSE);
        LOuttakeSpinner.setDirection(DcMotor.Direction.FORWARD);

        /*
        BSpinner.setDirection(CRServo.Direction.REVERSE);
        TSpinner.setDirection(CRServo.Direction.FORWARD);
         */

        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IntakeRoller.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ROuttakeSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LOuttakeSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IntakeRoller.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ROuttakeSpinner.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LOuttakeSpinner.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        BackLeft.setPower(0);
        BackRight.setPower(0);
        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        IntakeRoller.setPower(0);
        LOuttakeSpinner.setPower(0);
        ROuttakeSpinner.setPower(0);
        /*
        BSpinner.setPower(0);
        TSpinner.setPower(0);
         */

        // instantiate your MecanumDrive at a particular pose.

        waitForStart();
        if (opModeIsActive()) {

            // move off wall slightly
            // turn left slightly
            // engage outtake

            BackLeft.setPower(-0.5);
            BackRight.setPower(-0.5);
            FrontLeft.setPower(-0.5);
            FrontRight.setPower(-0.5);
            sleep(200);

            BackLeft.setPower(0.5);
            BackRight.setPower(0);
            FrontLeft.setPower(0);
            FrontRight.setPower(0.5);
            sleep(500);

            /*
            TSpinner.setPower(1);
            BSpinner.setPower(1);
            sleep(1000);
             */

            ROuttakeSpinner.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LOuttakeSpinner.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ROuttakeSpinner.setPower(.5);
            LOuttakeSpinner.setPower(.5);
            sleep(1000);

            IntakeRoller.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            IntakeRoller.setPower(.5);
            sleep(200);

            ROuttakeSpinner.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LOuttakeSpinner.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ROuttakeSpinner.setPower(.5);
            LOuttakeSpinner.setPower(.5);
            sleep(1000);




        }
    }
}