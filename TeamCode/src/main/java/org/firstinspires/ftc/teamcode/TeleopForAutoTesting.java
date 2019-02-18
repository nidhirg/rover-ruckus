package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.spa.ftclib.internal.state.ToggleBoolean;

/**
 *
 */

@TeleOp(name = "TeleopForAutoTesting", group = "prototype")

public class TeleopForAutoTesting extends OpMode {

    private OmegaBot robot;

    private ToggleBoolean gamepad2LeftBumper = new ToggleBoolean();
    private ToggleBoolean gamepad2RightBumper = new ToggleBoolean();
    private ToggleBoolean gamepad2LeftTrigger = new ToggleBoolean();
    private ToggleBoolean gamepad2RightTrigger = new ToggleBoolean();

    private double speedDamper = 0.4;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        robot = new OmegaBot(telemetry, hardwareMap);
        robot.drivetrain.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.extension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * In teleop mode, the robot is continually checking for input from the user.
     */
    @Override
    public void loop() {
        if(gamepad1.a) {
            robot.move(12, 0.2);
        } else if (gamepad1.b) {
            robot.move(-12, 0.2);
        } else if (gamepad1.x) {
            robot.movePID(12, 0.5);
        } else if (gamepad1.y) {
            robot.movePID(-12, 0.5);
        }
    }

    private double absMax(double a, double b) { //Returns the argument whose absolute value is greater (similar to Math.max() but compares absolute values)
        return (Math.abs(a) > Math.abs(b)) ? a : b;
    }

    /**
     * Tells the thread to wait for a number of seconds. Exception-protected
     *
     * @param sec number of sec to wait for
     */
    private void waitFor(double sec) {
        try {
            Thread.sleep((long) (sec * 1000));
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}

