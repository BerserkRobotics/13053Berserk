//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//public class Robot {
//
//    //intake roller
//    public static class IntakeRoll {
//        private DcMotor IntakeRoller;
//
//        public IntakeRoll(HardwareMap hardwareMap) {
//            IntakeRoller = hardwareMap.get(DcMotor.class, "IntakeRoller");
//            IntakeRoller.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//            IntakeRoller.setDirection(DcMotor.Direction.FORWARD);
//        }
//    }
//
//    //outtake
//    public static class Outtake {
//        private DcMotor ROuttakeSpinner;
//        private DcMotor LOuttakeSpinner;
//
//        public Outtake(HardwareMap hardwareMap) {
//            ROuttakeSpinner = hardwareMap.get(DcMotor.class, "ROuttakeSpinner");
//            LOuttakeSpinner = hardwareMap.get(DcMotor.class, "LOuttakeSpinner");
//            ROuttakeSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//            LOuttakeSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//            ROuttakeSpinner.setDirection(DcMotor.Direction.REVERSE);
//            LOuttakeSpinner.setDirection(DcMotor.Direction.FORWARD);
//        }
//    }
//
//    //intake spin
//    public static class IntakeSpin {
//        private CRServo BSpinner;
//        private CRServo TSpinner;
//
//        public IntakeSpin(HardwareMap hardwareMap) {
//            BSpinner = hardwareMap.get(CRServo.class, "BSpinner");
//            TSpinner = hardwareMap.get(CRServo.class, "TSpinner");
//            BSpinner.setDirection(CRServo.Direction.REVERSE);
//            TSpinner.setDirection(CRServo.Direction.FORWARD);
//        }
//    }
//
//}
