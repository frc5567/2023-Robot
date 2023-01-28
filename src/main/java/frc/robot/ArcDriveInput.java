package frc.robot;

public class ArcDriveInput {
    public double m_speed;
    public double m_turnSpeed;

    public ArcDriveInput() {
        m_speed = 0;
        m_turnSpeed = 0;
    }

    public ArcDriveInput(double speed, double turn) {
        m_speed = speed;
        m_turnSpeed = turn;
    }
}
