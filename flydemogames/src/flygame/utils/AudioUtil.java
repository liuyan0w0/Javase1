package flygame.utils;
//音乐工具类

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class AudioUtil {

    public static final String HIT="music/5528.wav";

    public static List<AudioClip> getAudios(){
        List<AudioClip> audios = new ArrayList<>();
        try {

            audios.add(Applet.newAudioClip(new File(AudioUtil.HIT).toURL()));

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //audios.add(Applet.newAudioClip(AudioUtil.class.getResource(AudioUtil.BGM)));
        return audios;
    }
}
