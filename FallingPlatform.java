import java.util.Timer;
import java.util.TimerTask;

public class FallingPlatform {
    public Platform platform;
    public long fallingTime;
    public Timer t = new Timer();

    public FallingPlatform(Platform platform, long fallingTime) {
        this.platform = platform;
        this.fallingTime = fallingTime;
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                MovingPlatform pl = new MovingPlatform(platform, (int) platform.getPlatformX(), (int) platform.getPlatformX(), 0, true, (int) platform.getPlatformY(), (int) platform.getPlatformY() + 5000, 5, true);
            }
        }, fallingTime);
    }

    public void fall() {

    }
}

