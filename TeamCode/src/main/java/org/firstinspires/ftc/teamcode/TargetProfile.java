package org.firstinspires.ftc.teamcode;

//
// This class holds the variables that determine a targets profile.  Things like how
// fast to move, how close to get before slowing down, and time allowed for the trip in seconds.
//
public class TargetProfile {
        double maxSpeed;
        double minSpeed;
        double distanceVarianceAllowed;
        double angleVarianceAllowed;
        double StartToSlowAt;
        double timeAllowed;

        public TargetProfile(double maxSpeed,
                             double minSpeed,
                             double maxDistance,
                             double angleVarianceAllowed,
                             double startSlowDown,
                             double timeAllowed){
            this.maxSpeed = maxSpeed;
            this.minSpeed = minSpeed;
            this.distanceVarianceAllowed = maxDistance;
            this.angleVarianceAllowed = angleVarianceAllowed;
            this.StartToSlowAt = startSlowDown;
            this.timeAllowed = timeAllowed;
        }
    }
