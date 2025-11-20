package org.firstinspires.ftc.teamcode.Decode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Robot;

@Config
@Autonomous(name = "roadrunnerTest2", group = "test", preselectTeleOp = "fullDrive")
public class farBlueRR extends LinearOpMode {

    private DcMotor IntakeRoller;
    private DcMotor ROuttakeSpinner;
    private DcMotor LOuttakeSpinner;
    private CRServo BSpinner;
    private CRServo TSpinner;

    @Override
    public void runOpMode() {
        IntakeRoller = hardwareMap.get(DcMotor.class, "IntakeRoller");
        ROuttakeSpinner = hardwareMap.get(DcMotor.class, "ROuttakeSpinner");
        LOuttakeSpinner = hardwareMap.get(DcMotor.class, "LOuttakeSpinner");

        BSpinner = hardwareMap.get(CRServo.class, "BSpinner");
        TSpinner = hardwareMap.get(CRServo.class, "TSpinner");

        IntakeRoller.setDirection(DcMotor.Direction.FORWARD);
        ROuttakeSpinner.setDirection(DcMotor.Direction.REVERSE);
        LOuttakeSpinner.setDirection(DcMotor.Direction.FORWARD);

        BSpinner.setDirection(CRServo.Direction.REVERSE);
        TSpinner.setDirection(CRServo.Direction.FORWARD);

        IntakeRoller.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ROuttakeSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LOuttakeSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IntakeRoller.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ROuttakeSpinner.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LOuttakeSpinner.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        IntakeRoller.setPower(0);
        LOuttakeSpinner.setPower(0);
        ROuttakeSpinner.setPower(0);
        BSpinner.setPower(0);
        TSpinner.setPower(0);

        //TODO: use meepmeep to determine initial position
        Pose2d initialPose = new Pose2d(11.8, 61.7, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);


        //TODO: this is just a sample; change name of tab, paths
        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .lineToYSplineHeading(33, Math.toRadians(0))
                .waitSeconds(2)
                .setTangent(Math.toRadians(90))
                .lineToY(48)
                .setTangent(Math.toRadians(0))
                .lineToX(32)
                .strafeTo(new Vector2d(44.5, 30))
                .turn(Math.toRadians(180))
                .lineToX(47.5)
                .waitSeconds(3);
    }
}