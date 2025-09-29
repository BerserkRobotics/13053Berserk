package org.firstinspires.ftc.teamcode.Decode.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;

@Config
@Autonomous(name = "RoadrunnerTest", group = "Autonomous")
public class RoadrunnerTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            //MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
            // Starting pose
            Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(0));
            MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

            waitForStart();

            if (opModeIsActive()) {
                Actions.runBlocking(
                        drive.actionBuilder(initialPose)
                                .lineToX(600)
                                .lineToX(0)
                                .build());
            }

            /* Build Road Runner Core action
            Action trajectory = drive.actionBuilder(initialPose)
                    .lineToX(50000)
                    .build();


            waitForStart();

            Actions.runBlocking(trajectory);
             */

        }
    }
}