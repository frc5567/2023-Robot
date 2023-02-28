// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private Drivetrain m_vroomVroom;
  private Limelight m_limelight;
  private PilotController m_pilotControl;
  private RobotShuffleboard m_shuffleName;
  private Auton m_auton;
  private Elevator m_elevator;
  private Arm m_arm;
  private CopilotController m_copilotControl;
  private Claw m_claw;
  private Shoulder m_shoulder;

  com.ctre.phoenix.sensors.Pigeon2 m_pigeon;


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    //Auton shuffleboard choices updating
    m_chooser.setDefaultOption("0 Object", kDefaultAuto);
    m_chooser.addOption("1 Object", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    //Instantiation of needed classes and names assigned as appropriate
    String drivetrainName = "VroomVroom";
    m_vroomVroom = new Drivetrain(drivetrainName);
    

    m_pilotControl = new PilotController();
    m_copilotControl = new CopilotController();

    m_shuffleName = new RobotShuffleboard();
    m_shuffleName.init();

    m_limelight = new Limelight();

    m_auton = new Auton(m_shuffleName);

    m_pigeon = new Pigeon2(RobotMap.PIGEON_CAN_ID);

    m_elevator = new Elevator();
    m_arm = new Arm();
    m_claw = new Claw();
    m_shoulder = new Shoulder();

    m_arm.init();
    m_arm.configPID();
    m_elevator.init();
    m_elevator.configPID();

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_vroomVroom.brakeMode();
    m_vroomVroom.zeroEncoders();
    m_vroomVroom.initDrivetrain();

    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

    //m_shuffleName.setAutonPath();
    m_auton.init();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    //publisher widget method to push boolean value of autonRunning status (SHOULD, here, always be TRUE)
    //m_shuffleName.setWhetherAutonRunning(m_auton.isRunning());

    //isLevel variable sets for Auton, much like TeleOp
    boolean isBotLevelAuton = false;
    double curPitchAuton = m_pigeon.getPitch();
    isBotLevelAuton = m_vroomVroom.isLevel(curPitchAuton);

    DriveEncoderPos drivePos = m_vroomVroom.getEncoderPositions();
    //run periodic method of Auton class
    DriveInput driveInput = m_auton.periodic(drivePos, isBotLevelAuton);
    m_vroomVroom.arcadeDrive(driveInput.m_speed, driveInput.m_turnSpeed);

    //autoLevel check and run
    if (m_auton.toRunAutoLevelOrNotToRun == true) {
      m_vroomVroom.autoLevel(curPitchAuton);
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    m_vroomVroom.initDrivetrain();
    m_vroomVroom.brakeMode();

    m_arm.init();
    m_arm.configPID();

    m_elevator.init();
    m_elevator.configPID();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    DriveInput driverInput = m_pilotControl.getDriverInput();
    // TODO: Test these elements now that they exist.
    CoDriveInput coDriverInput = m_copilotControl.getCoDriveInput();
    double curPitch = m_pigeon.getPitch();

    m_limelight.periodic();
    
    //updated boolean for checking whether pitch is within "level" range, if/else statement for outputting into the console, initial value of false
    boolean isBotLevel = false;
    
    if (driverInput.m_isAutoLeveling) {

      isBotLevel = m_vroomVroom.autoLevel(curPitch);
      m_shuffleName.periodic(isBotLevel, m_limelight.xOffset(), m_limelight.areaOfScreen());

      //boolean isBotLevel = m_vroomVroom.isLevel(curPitch);
    }
    else {
      m_vroomVroom.arcadeDrive(driverInput);
      isBotLevel = m_vroomVroom.isLevel(curPitch);
      m_shuffleName.periodic(isBotLevel, m_limelight.xOffset(), m_limelight.areaOfScreen());
    }
    
    /**
     * elevator: inputs the values from the controllers to the PID/set state methods.
     **/
    if (coDriverInput.m_manualElevator != 0) {
      m_elevator.drive(coDriverInput.m_manualElevator);
    }
    else if (!Double.isNaN(coDriverInput.m_elevatorPos)) {
      m_elevator.drivePID(coDriverInput.m_elevatorPos);
    }
    else {
      m_elevator.drive(0.0);
    }

    /**
     * elevator: inputs the values from the controllers to the PID/set state methods.
     **/
    if (driverInput.m_manualArm != 0) {
      m_arm.driveArm(driverInput.m_manualArm);
    }
    else if (!Double.isNaN(driverInput.m_armPosition)) {
      m_arm.armPID(driverInput.m_armPosition);
    } 
    else {
      // No Input case
      m_arm.driveArm(0.0);
    }
    
    //m_claw.setClawState(coDriverInput.m_clawPos);
    //m_shoulder.setShoulderState(coDriverInput.m_shoulderPos);

    //publisher widget method to push boolean value of current pitch and "level" status
    //m_shuffleName.setWhetherBotIsLevel(m_vroomVroom.isLevel(curPitch));
    //publisher widget method to push boolean value of autonRunning status (SHOULD, here, always be FALSE)
    //m_shuffleName.setWhetherAutonRunning(m_auton.isRunning());
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    m_vroomVroom.coastMode();
    m_arm.coastMode();
    m_elevator.coastMode();
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
