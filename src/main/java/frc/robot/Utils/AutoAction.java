package frc.robot.Utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class AutoAction {

    public enum kActions {

        ConePickup,
        ConeScore,

        CubePickup,
        CubeScore,

        Balence

    }

    private final Pose2d m_pose;
    private final kActions m_action;

    public AutoAction(Pose2d pose, kActions action) {
        m_pose = new Pose2d(pose.getTranslation(), new Rotation2d());
        m_action = action;
    }

    public Pose2d getPose() {
        return m_pose;
    }

    public kActions getAction() {
        return m_action;
    }
}
