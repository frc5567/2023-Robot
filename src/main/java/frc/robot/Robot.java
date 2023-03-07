// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Claw.ClawState;
import frc.robot.CoDriveInput.ToggleInput;
import frc.robot.Shoulder.ShoulderState;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private String m_autonSelected;
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
  private UsbCamera m_camera;
  private boolean m_autoStepCompleted = false;

  private int m_delayCounter;
  private int m_outCounter;

  com.ctre.phoenix.sensors.Pigeon2 m_pigeon;


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    //Auton shuffleboard choices updating
    m_chooser.setDefaultOption(RobotMap.AutonConstants.SHORT_COMMUNITY, RobotMap.AutonConstants.SHORT_COMMUNITY);
    m_chooser.addOption(RobotMap.AutonConstants.SHORT_COMMUNITY, RobotMap.AutonConstants.SHORT_COMMUNITY);
    m_chooser.addOption(RobotMap.AutonConstants.LONG_COMMUNITY, RobotMap.AutonConstants.LONG_COMMUNITY);
    m_chooser.addOption(RobotMap.AutonConstants.MID_CUBE_SHORT_COMMUNITY, RobotMap.AutonConstants.MID_CUBE_SHORT_COMMUNITY);
    m_chooser.addOption(RobotMap.AutonConstants.MID_CONE_SHORT_COMMUNITY, RobotMap.AutonConstants.MID_CONE_SHORT_COMMUNITY);
    m_chooser.addOption(RobotMap.AutonConstants.MID_CUBE_LONG_COMMUNITY, RobotMap.AutonConstants.MID_CUBE_LONG_COMMUNITY);
    m_chooser.addOption(RobotMap.AutonConstants.MID_CONE_LONG_COMMUNITY, RobotMap.AutonConstants.MID_CONE_LONG_COMMUNITY);
    m_chooser.addOption(RobotMap.AutonConstants.HIGH_CUBE_SHORT_COMMUNITY, RobotMap.AutonConstants.HIGH_CUBE_SHORT_COMMUNITY);
    m_chooser.addOption(RobotMap.AutonConstants.HIGH_CONE_SHORT_COMMUNITY, RobotMap.AutonConstants.HIGH_CONE_SHORT_COMMUNITY);
    m_chooser.addOption(RobotMap.AutonConstants.HIGH_CUBE_LONG_COMMUNITY, RobotMap.AutonConstants.HIGH_CUBE_LONG_COMMUNITY);
    m_chooser.addOption(RobotMap.AutonConstants.HIGH_CONE_LONG_COMMUNITY, RobotMap.AutonConstants.HIGH_CONE_LONG_COMMUNITY);
    SmartDashboard.putData("Auton choices", m_chooser);
    m_autonSelected = m_chooser.getSelected();

    System.out.println("Selected:" + m_autonSelected);
    //Instantiation of needed classes and names assigned as appropriate
    m_pigeon = new Pigeon2(RobotMap.PIGEON_CAN_ID);
    m_vroomVroom = new Drivetrain(m_pigeon);    

    m_pilotControl = new PilotController();
    m_copilotControl = new CopilotController();

    m_shuffleName = new RobotShuffleboard();
    m_shuffleName.init();

    m_limelight = new Limelight();

    m_auton = new Auton();

    m_elevator = new Elevator();
    m_arm = new Arm();

    m_claw = new Claw();
    m_shoulder = new Shoulder();

    m_vroomVroom.initDrivetrain();

    m_delayCounter = 0;

    m_arm.init();
    m_arm.configPID();
    m_elevator.init();
    m_elevator.configPID();

    m_shoulder.init();
    m_claw.init();

    try {
      m_camera = CameraServer.startAutomaticCapture();

      m_camera.setResolution(160,120);
      m_camera.setFPS(10);

    } catch (Exception e){
      System.out.println("Camera failed to instantiate");
    }

    m_outCounter = 0;
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
    m_outCounter++;
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
    m_vroomVroom.initDrivetrain();

    m_autonSelected = m_chooser.getSelected();
    System.out.println("Auton selected: " + m_autonSelected);

    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);

    //m_shuffleName.setAutonPath();
    m_auton.init();
    m_auton.selectPath(m_autonSelected);
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


    AutonInput currentInput;
    currentInput = m_auton.periodic(m_autoStepCompleted);
    if (currentInput.m_autonComplete == false) {
      if (!Double.isNaN(currentInput.m_driveTarget)) {
        m_autoStepCompleted = m_vroomVroom.driveStraight(currentInput.m_driveTarget);
      }
      if (!Double.isNaN(currentInput.m_turnTarget)) {
        //TODO: change to turn to target instead of drive straight
        //m_autoStepCompleted = m_vroomVroom.driveStraight(currentInput.m_turnTarget);
      }
      if (currentInput.m_desiredState != RobotState.kUnknown) {
        m_autoStepCompleted = this.transitionToNewState(currentInput.m_desiredState);
      }
      if ((currentInput.m_clawState != ClawState.kUnknown) && (currentInput.m_clawState != m_claw.getClawState())){
        m_claw.toggleClawState();
        m_autoStepCompleted = true;
      }
      if (!Double.isNaN(currentInput.m_delay)) {
        Double cyclesToDelay = (currentInput.m_delay * 50);
        int intCyclesToDelay = cyclesToDelay.intValue();
        System.out.println("Cycles to delay: [" + cyclesToDelay + "] [" + intCyclesToDelay + "] [" + m_delayCounter + "]");
        if (m_delayCounter == intCyclesToDelay) {
          m_autoStepCompleted = true;
          m_delayCounter = 0;
        }
        else {
          m_delayCounter++;
          m_autoStepCompleted = false;
        }
      }
    }
    else {
      m_vroomVroom.arcadeDrive(0, 0);
      m_autoStepCompleted = false;
    }

    //autoLevel check and run
    if (m_auton.toRunAutoLevelOrNotToRun == true) {
      m_vroomVroom.autoLevel(curPitchAuton);
    }

    m_shuffleName.periodic(isBotLevelAuton, m_auton.isRunning(), m_autonSelected, m_auton.m_step, m_limelight.xOffset(), m_limelight.areaOfScreen());
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    m_vroomVroom.brakeMode();
    m_auton.m_autonStartOut = false;
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    DriveInput driverInput = m_pilotControl.getDriverInput();
    CoDriveInput coDriverInput = m_copilotControl.getCoDriveInput();
    double curPitch = m_pigeon.getPitch();

    m_limelight.periodic();
    
    //updated boolean for checking whether pitch is within "level" range, if/else statement for outputting into the console, initial value of false
    boolean isBotLevel = false;
    
    if (driverInput.m_isAutoLeveling) {

      isBotLevel = m_vroomVroom.autoLevel(curPitch);
      m_shuffleName.periodic(isBotLevel, m_auton.isRunning(), m_autonSelected, m_auton.m_step, m_limelight.xOffset(), m_limelight.areaOfScreen());

      //boolean isBotLevel = m_vroomVroom.isLevel(curPitch);
    }
    else {
      m_vroomVroom.arcadeDrive(driverInput);
      isBotLevel = m_vroomVroom.isLevel(curPitch);
      m_shuffleName.periodic(isBotLevel, m_auton.isRunning(), m_autonSelected, m_auton.m_step, m_limelight.xOffset(), m_limelight.areaOfScreen());
    }
    
    if (coDriverInput.m_desiredState != RobotState.kUnknown) {
      this.transitionToNewState(coDriverInput.m_desiredState);
    }
    else if (driverInput.m_desiredState != RobotState.kUnknown) {
      this.transitionToNewState(driverInput.m_desiredState);
    }
    else {
      /**
       * elevator: inputs the values from the controllers to the manual/PID methods.
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
       * arm: inputs the values from the controllers to the PID/set motor methods.
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
    }
    
    if (coDriverInput.m_clawPos == ToggleInput.kToggle) {
      m_claw.toggleClawState();
    }
    if (coDriverInput.m_shoulderPos == ToggleInput.kToggle) {
      m_shoulder.toggleShoulderState();
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    m_vroomVroom.coastMode();
    m_arm.coastMode();
    m_elevator.brakeMode();

    m_auton.m_autonStartOut = false;
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    m_vroomVroom.initDrivetrain();

    m_arm.init();
    m_arm.configPID();
    m_elevator.init();
    m_elevator.configPID();

    m_shoulder.init();
    m_claw.init();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}

  /**
   * Method to move to a specific "state" in a coordinated fashion such that our mechanisms move simultaneously and avoiding 
   * mechanical collisions by moving when there isn't clearance
   */
  private boolean transitionToNewState(RobotState targetState) {
    boolean movementCompleted = false;
    // we have to make sure the elevator is in a safe position to move the arm down to the floor, if the arm is currently above the elevator axis
    Double currentArmPosition = this.m_arm.getArmPosition();
    Double currentElevatorPosition = this.m_elevator.getElevatorPosition();
    ShoulderState currentShoulderState = this.m_shoulder.getShoulderState();

    if (((currentArmPosition > (targetState.getArmTarget() - RobotMap.ENC_DEADBAND)) && (currentArmPosition < (targetState.getArmTarget() + RobotMap.ENC_DEADBAND))) && 
        ((currentElevatorPosition > (targetState.getElevatorTarget() - RobotMap.ENC_DEADBAND)) && (currentElevatorPosition < (targetState.getElevatorTarget() + RobotMap.ENC_DEADBAND))) &&
        (currentShoulderState == targetState.getShoulderState())) {
          movementCompleted = true;
          System.out.println("Transition completed!!!!");
    }
    
    switch(targetState){
      case kTravel:
      {
        if (currentArmPosition >= (RobotMap.ArmConstants.ARM_HIGH_POS + RobotMap.ENC_DEADBAND) && currentElevatorPosition < RobotMap.ElevatorConstants.ELEVATOR_MID_POS) {
          m_arm.armPID(RobotMap.ArmConstants.ARM_HIGH_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else if (currentArmPosition >= (RobotMap.ArmConstants.ARM_APPROACH_POS - RobotMap.ENC_DEADBAND) && currentElevatorPosition <= (RobotMap.ElevatorConstants.ELEVATOR_MID_POS - RobotMap.ENC_DEADBAND)) {
          m_arm.armPID(RobotMap.ArmConstants.ARM_START_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else {
          // Move the elevator and arm simultaneously to target positions
          m_arm.armPID(targetState.getArmTarget());
          m_elevator.drivePID(targetState.getElevatorTarget());
          m_shoulder.setShoulderState(targetState.getShoulderState());
        }
        // need to break out of case so we don't execute the next
        break;
      }
      case kFloorPickup:
      {
        // If the encoder value is between start and approach positions, we're above the elevator and need to be careful
        m_shoulder.setShoulderState(targetState.getShoulderState());
        if (currentArmPosition <= (RobotMap.ArmConstants.ARM_APPROACH_POS - RobotMap.ENC_DEADBAND) && currentElevatorPosition < RobotMap.ElevatorConstants.ELEVATOR_MID_POS){
          // make the elevator move to a safe position
          m_arm.armPID(RobotMap.ArmConstants.ARM_APPROACH_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else if ((currentArmPosition <= (RobotMap.ArmConstants.ARM_HIGH_POS + RobotMap.ENC_DEADBAND)) && currentElevatorPosition <= (RobotMap.ElevatorConstants.ELEVATOR_MID_POS - RobotMap.ENC_DEADBAND)) {
          m_arm.armPID(RobotMap.ArmConstants.ARM_FLOOR_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
          if ( (m_outCounter % 100) == 0 ){
            System.out.println("Arm[" + currentArmPosition + "] Ele[" + currentElevatorPosition + "]");
          }
        }
        else {
          // Move the elevator and arm simultaneously to target positions
          m_arm.armPID(targetState.getArmTarget());
          m_elevator.drivePID(targetState.getElevatorTarget());
        }

        // need to break out of case so we don't execute the next
        break;
      }
      case kShelfPickup:
      {
        m_shoulder.setShoulderState(targetState.getShoulderState());
        if (currentArmPosition >= (RobotMap.ArmConstants.ARM_HIGH_POS + RobotMap.ENC_DEADBAND) && currentElevatorPosition < RobotMap.ElevatorConstants.ELEVATOR_MID_POS) {
          m_arm.armPID(RobotMap.ArmConstants.ARM_HIGH_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else if  (currentArmPosition >= (RobotMap.ArmConstants.ARM_APPROACH_POS - RobotMap.ENC_DEADBAND) && currentElevatorPosition <= (RobotMap.ElevatorConstants.ELEVATOR_MID_POS - RobotMap.ENC_DEADBAND)) {
          m_arm.armPID(RobotMap.ArmConstants.ARM_SHELF_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else {
          // Move the elevator and arm simultaneously to target positions
          m_arm.armPID(targetState.getArmTarget());
          m_elevator.drivePID(targetState.getElevatorTarget()); 
        }
        // need to break out of case so we don't execute the next
        break;
      }
      case kFloorPiece:
      {
        m_shoulder.setShoulderState(targetState.getShoulderState());
      
        if(currentArmPosition <= (RobotMap.ArmConstants.ARM_APPROACH_POS - RobotMap.ENC_DEADBAND) && currentElevatorPosition < RobotMap.ElevatorConstants.ELEVATOR_MID_POS){
          m_arm.armPID(RobotMap.ArmConstants.ARM_APPROACH_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else if ((currentArmPosition <= (RobotMap.ArmConstants.ARM_HIGH_POS + RobotMap.ENC_DEADBAND)) && currentElevatorPosition <= (RobotMap.ElevatorConstants.ELEVATOR_MID_POS - RobotMap.ENC_DEADBAND)) {
          m_arm.armPID(RobotMap.ArmConstants.FLOOR_PLACE_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else {
          // Move the elevator and arm simultaneously to target positions
          m_arm.armPID(targetState.getArmTarget());
          m_elevator.drivePID(targetState.getElevatorTarget()); 
        }
        // need to break out of case so we don't execute the next
        break;
      }
      case kMidPiece:
      {
        m_shoulder.setShoulderState(targetState.getShoulderState());
        if(currentArmPosition <= (RobotMap.ArmConstants.ARM_APPROACH_POS - RobotMap.ENC_DEADBAND) && currentElevatorPosition < RobotMap.ElevatorConstants.ELEVATOR_MID_POS){
          m_arm.armPID(RobotMap.ArmConstants.ARM_APPROACH_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else if ((currentArmPosition <= (RobotMap.ArmConstants.ARM_HIGH_POS - RobotMap.ENC_DEADBAND)) && currentElevatorPosition <= (RobotMap.ElevatorConstants.ELEVATOR_MID_POS - RobotMap.ENC_DEADBAND)) {
          m_arm.armPID(RobotMap.ArmConstants.ARM_MID_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else {
         // Move the elevator and arm simultaneously to target positions
         m_arm.armPID(targetState.getArmTarget());
         m_elevator.drivePID(targetState.getElevatorTarget()); 
        }
       
        // need to break out of case so we don't execute the next
       break;
      }
      case kHighCone:
      {
        m_shoulder.setShoulderState(targetState.getShoulderState());
        if (currentArmPosition <= (RobotMap.ArmConstants.ARM_APPROACH_POS - RobotMap.ENC_DEADBAND) && currentElevatorPosition < RobotMap.ElevatorConstants.ELEVATOR_MID_POS) {
          m_arm.armPID(RobotMap.ArmConstants.ARM_APPROACH_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else if (currentArmPosition <= (RobotMap.ArmConstants.ARM_HIGH_POS + RobotMap.ENC_DEADBAND) && currentElevatorPosition <= (RobotMap.ElevatorConstants.ELEVATOR_MID_POS - RobotMap.ENC_DEADBAND)){
          m_arm.armPID(RobotMap.ArmConstants.ARM_HIGH_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else {
          m_arm.armPID(targetState.getArmTarget());
          m_elevator.drivePID(targetState.getElevatorTarget());
        }
      // need to break out of case so we don't execute the next
      break;
      }
      case kHighCube:
      {
        m_shoulder.setShoulderState(targetState.getShoulderState());
        if (currentArmPosition <= (RobotMap.ArmConstants.ARM_APPROACH_POS - RobotMap.ENC_DEADBAND) && currentElevatorPosition < RobotMap.ElevatorConstants.ELEVATOR_MID_POS) {
          m_arm.armPID(RobotMap.ArmConstants.ARM_APPROACH_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else if ( currentArmPosition <= (RobotMap.ArmConstants.ARM_HIGH_POS + RobotMap.ENC_DEADBAND) && currentElevatorPosition <=(RobotMap.ElevatorConstants.ELEVATOR_MID_POS - RobotMap.ENC_DEADBAND)) {
          m_arm.armPID(RobotMap.ArmConstants.ARM_MID_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else{
          m_arm.armPID(targetState.getArmTarget());
          m_elevator.drivePID(targetState.getElevatorTarget());
        }
        // need to break out of case so we don't execute the next
        break;
      }
      case kApproachMid:
      {
        m_shoulder.setShoulderState(targetState.getShoulderState());
        if (currentArmPosition >= (RobotMap.ArmConstants.ARM_HIGH_POS + RobotMap.ENC_DEADBAND) && currentElevatorPosition < RobotMap.ElevatorConstants.ELEVATOR_MID_POS) {
          m_arm.armPID(RobotMap.ArmConstants.ARM_HIGH_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else if (currentArmPosition >= (RobotMap.ArmConstants.ARM_APPROACH_POS - RobotMap.ENC_DEADBAND) && currentElevatorPosition <= (RobotMap.ElevatorConstants.ELEVATOR_MID_POS - RobotMap.ENC_DEADBAND)) {
          m_arm.armPID(RobotMap.ArmConstants.ARM_APPROACH_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else {
          // Move the elevator and arm simultaneously to target positions
          m_arm.armPID(targetState.getArmTarget());
          m_elevator.drivePID(targetState.getElevatorTarget());
        }
        // need to break out of case so we don't execute the next
        break;
      }
      case kApproachHigh:
      {
        m_shoulder.setShoulderState(targetState.getShoulderState());
        if (currentArmPosition >= (RobotMap.ArmConstants.ARM_HIGH_POS + RobotMap.ENC_DEADBAND) && currentElevatorPosition < RobotMap.ElevatorConstants.ELEVATOR_MID_POS) {
          m_arm.armPID(RobotMap.ArmConstants.ARM_HIGH_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else if (currentArmPosition >= (RobotMap.ArmConstants.ARM_APPROACH_POS - RobotMap.ENC_DEADBAND) && currentElevatorPosition <= (RobotMap.ElevatorConstants.ELEVATOR_MID_POS - RobotMap.ENC_DEADBAND)) {
          m_arm.armPID(RobotMap.ArmConstants.ARM_APPROACH_POS);
          m_elevator.drivePID(RobotMap.ElevatorConstants.ELEVATOR_MID_POS);
        }
        else {
          // Move the elevator and arm simultaneously to target positions
          m_arm.armPID(targetState.getArmTarget());
          m_elevator.drivePID(targetState.getElevatorTarget());
        }
        // need to break out of case so we don't execute the next
        break;
      }
      default:
        break;
    }

    return movementCompleted;
  }
}
