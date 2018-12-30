package org.firstinspires.ftc.teamcode;

/**
 * Created by tvt on 9/21/17.
 */

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Auto_Turn360", group = "Testers")
//@Disabled

public class Auto_Turn360 extends LinearOpMode {


    private int initialPos, finalPos;
    private GoldAlignDetector detector;
    private ElapsedTime runtime = new ElapsedTime();
    private OmegaBot robot;
    private double robotSpeed = 0.3;

    @Override
    public void runOpMode() {
        robot = new OmegaBot(telemetry, hardwareMap);
        robot.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.setDrivetrainToMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setDrivetrainToMode(DcMotor.RunMode.RUN_TO_POSITION);
        // Set up detector
        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        // Optional tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment

        detector.enable(); // Start the detector!

        telemetry.addData("IsAligned", detector.getAligned()); // Is the bot aligned with the gold mineral?
        telemetry.addData("X Pos", detector.getXPosition()); // Gold X position.
        telemetry.addData("Initialization", "Complete");
        sleep(100);
        //Determine location of gold cube (threshold radius of 20) and
        int x = (int) detector.getXPosition();
        detector.disable();
        waitForStart();
        runtime.reset();
        robot.turn(360,0.3);
    }

    //preset paths based on where the gold cube is located (left, center, right) based on approximate x values {null--none, 100, 315}
    public void goldLeft() {
        robot.turn(-28.561, robotSpeed);
        robot.move(Math.sqrt(1260), robotSpeed);
        robot.turn(70.746, robotSpeed);
        robot.move(Math.sqrt(1440), robotSpeed);
        robot.turn(-(180 - 18.435), robotSpeed);

    }

    public void goldCenter() {
        robot.move(7.5 * Math.sqrt(72), 1);
    }

    public void goldRight() {
        robot.turn(28.561, robotSpeed);
        robot.move(Math.sqrt(1260), robotSpeed);
        robot.turn(-70.746, robotSpeed);
        robot.move(Math.sqrt(1440), robotSpeed);
        robot.turn(-108.435, robotSpeed);
    }

    public void finishPath() {

    }
}