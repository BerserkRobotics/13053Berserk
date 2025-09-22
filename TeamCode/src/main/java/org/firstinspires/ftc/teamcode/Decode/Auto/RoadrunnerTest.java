package org.firstinspires.ftc.teamcode.Decode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "RoadrunnerTest", group = "Autonomous")
public class RoadrunnerTest extends LinearOpMode {

    @Override
    public void runOpMode() {

        // Starting pose
        Pose2d initialPose = new Pose2d(11.8, 61.7, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        // Build Road Runner Core action
        Action trajectory = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(-43.29, 41.16), Math.toRadians(177.70))
                .splineTo(new Vector2d(-53.71, -42.22), Math.toRadians(-88.19))
                .splineTo(new Vector2d(49.03, -40.52), Math.toRadians(0.95))
                .splineTo(new Vector2d(48.60, 10.32), Math.toRadians(90.48))
                .build();


        waitForStart();

        if (opModeIsActive()) {
            Actions.runBlocking(trajectory);
        }
    }
}
