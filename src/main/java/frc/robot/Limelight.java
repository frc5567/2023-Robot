package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * 
 */
public class Limelight {

    /**
     * Delcares the Pipeline object for the limelight Class
     */

     public Pipeline m_pipeline;
     public Pipeline m_AptagPipe;
     public Pipeline m_RetroTapePipe; 

     
    public enum Pipeline{
        kRetroTapePipeID(0),
        kApTagPipeID(1);
        
        

        int m_pipelineID;
        //Intakes the pipelineID and sets it to the member variable 
        private Pipeline(int pipelineID){
            m_pipelineID = pipelineID;
        }

        //returns the member varuble for the Pipeline. 
        public int getID(){
            return m_pipelineID;
        }

    }
 

   NetworkTable m_limelightTable; 

   //Sets the x angle offset so we can calculate the distance from the limelight to the reflective tape.  
   double m_xAngleOffset;

    // Percent of the reflective tape the screen takes up so we can calculate the distance. 
   double m_areaOfScreen;

   // Gets the values of the x angle offset and the target area. (Add Shuffleboard updates later)

   public Limelight() {
        m_xAngleOffset = m_limelightTable.getEntry("tx").getDouble(0.0);
        m_areaOfScreen = m_limelightTable.getEntry("ta").getDouble(0.0);
   }



   //Disables the LEDs and sets the default pipeline to the RetroTape pipeline
   public void init() {
    setPipeline(Pipeline.kRetroTapePipeID);

   }

   public void periodic() {
    m_xAngleOffset = m_limelightTable.getEntry("tx").getDouble(0.0);
    m_areaOfScreen = m_limelightTable.getEntry("ta").getDouble(0.0);

    //TODO: After shuffleboard is finished, uncomment this code.
    /**
     * Puts the x angle offset on the shuffleboard.
     * SmartDashboard.putNumber("LimelightX Offset", m_xAngleOffset);
     * Puts the percentage of screen on the shuffleboard.
     * SmartDashboard.putNumber("LimelightArea Percentage", m_areaOfScreen);
     */
   }

   //Switches the pipelines in the Network Table. 
   public static void setPipeline(Pipeline pipeline){

    NetworkTableInstance.getDefault().getEntry("pipeline").setNumber(pipeline.getID());
    }

}


