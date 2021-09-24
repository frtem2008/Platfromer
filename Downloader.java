import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Downloader {
    
    public static File test = new File("test");
    public static File src;
    public static HashMap<String, String> images = new HashMap<>();
    public static HashMap<String, String> sounds = new HashMap<>();
    public static HashMap<String, String> learn = new HashMap<>();
    public static int m = 0;
    public static JFrame dialog = new JFrame();
    public static String sourceLink = "https://drive.google.com/drive/folders/19-gufTrbcgSTaZ4tQEZalKoKYLgZ-w5h?usp=sharing";

    public static void fileCheck() {
        try {
            test.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String testPath = test.getAbsolutePath().replaceAll("test", "");
        src = new File(testPath + "/src/resources");
        System.out.println(src.getAbsolutePath());
        dialog.setTitle("Скачивание файлов для платформера");
        dialog.setLayout(null);
        dialog.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dialog.setSize(600, 500);
        JTextField link = new JTextField("Здесь будет ссылка на скачивание файлов");
        link.setVisible(false);
        link.setBounds(50, 350, 500, 50);
        JButton doMyself = new JButton("Я скачаю файлы сам");
        doMyself.setBounds(350, 280, 200, 50);
        doMyself.addActionListener(e -> {
            link.setText(sourceLink);
            link.setVisible(true);
        });
        doMyself.setVisible(true);
        JButton doProgram = new JButton("Скачать текстуры и звуки");
        doProgram.setBounds(50, 50, 500, 50);
        doProgram.addActionListener(e -> {
            link.setText("Скачивание файлов");
            try {
                Thread.sleep(500);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            downloadFiles();
            link.setText("Файлы скачаны");
        });
        doProgram.setVisible(true);
        dialog.add(doMyself);
        dialog.add(doProgram);
        dialog.add(link);
        if (!src.exists()) {
            dialog.setVisible(true);
        } else {
            System.out.println("ban");
        }

    }

    public static void downloadFiles() {
        src.mkdir();
        File Images = new File("src/resources/Images");
        File Sounds = new File("src/resources/Sounds");
        File Levels = new File("src/resources/Levels");
        Images.mkdir();
        Sounds.mkdir();
        Levels.mkdir();
        File Learn = new File("src/resources/Levels/learn");
        Learn.mkdir();
        File restore = new File("src/resources/Levels/restore.txt");
        File name = new File("src/resources/Levels/name.txt");
        File levels = new File("src/resources/Levels.txt");

        try {
            restore.createNewFile();
            name.createNewFile();
            levels.createNewFile();
            if (restore.length() == 0) {
                appendStrToFile(restore.getPath(), "true");
            }
            appendStrToFile(name.getPath(), "learn");
            appendStrToFile(levels.getPath(), "learn\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        fillImages();
        fillSounds();
        fillLearn();
        downloadHashMap(images, "src/resources/Images/");
        downloadHashMap(sounds, "src/resources/Sounds/");
        downloadHashMap(learn, "src/resources/Levels/learn/");

    }

    public static void appendStrToFile(String fileName, String str) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
            out.write(str);
            out.close();
        } catch (IOException e) {
            System.out.println("exception occurred" + e);
        }
    }


    public static String convertLink(String link) {
        String first = link.replaceAll("https://drive.google.com/file/d/", "");
        int a = first.indexOf('?');
        String sec = first.substring(0, a - 5);
        sec = ("https://drive.google.com/uc?export=download&id=") + sec;
        return sec;

    }

    public static void downloadHashMap(HashMap<String, String> hash, String path) {
        List<String> keys = new ArrayList<>(hash.keySet());

        for (int i = 0; i < keys.size(); i++) {
            String name = keys.get(i);
            String url = hash.get(name);
            url = convertLink(url);
            long a = System.currentTimeMillis();
            System.out.println("downloading " + name);
            try {
                URL website = new URL(url);
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream(path + name);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                rbc.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(url + "       CRASH      total req " + m);
                deleteFile("src/resources");
                System.exit(200);
            }
            long b = System.currentTimeMillis();
            System.out.println("finished downloading " + name + " in " + (b - a) + " millis");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m++;
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.isFile()) {
            file.delete();
        }
        if (file.isDirectory()) {
            File[] contents = file.listFiles();
            if (contents != null) {
                for (File f : contents) {
                    if (!Files.isSymbolicLink(f.toPath())) {
                        deleteFile(f.getPath());
                    }
                }
            }
            file.delete();
        }
    }

    public static void fillImages() {
        images.put("день.jpg", "https://drive.google.com/file/d/1Gp6rEf0v2QF9B2GAeJkDg3i6oeNyeXjl/view?usp=sharing");
        images.put("закат.jpg", "https://drive.google.com/file/d/1lYI3NM3uSufjpeKWHE7MHr3tMzt3prwD/view?usp=sharing");
        images.put("ночь.png", "https://drive.google.com/file/d/17Ilg43aceyeaaAbRbophYsy1q40VUNLc/view?usp=sharing");
        images.put("рассвет.png", "https://drive.google.com/file/d/1CrTLEk1FASchajALPil_-rD1D30SgtsN/view?usp=sharing");
        images.put("Arrow.png", "https://drive.google.com/file/d/1Iukhnr9PIzRO9Bx-puVtcpbfq1KSlvNA/view?usp=sharing");
        images.put("bird left 1.png", "https://drive.google.com/file/d/1zqP84sfDbtLXjulThTDseGKN-WrsnePi/view?usp=sharing");
        images.put("bird left 2.png", "https://drive.google.com/file/d/1Ssq6MIQrDojeqluFhXVPkrb7dNKJfkBv/view?usp=sharing");
        images.put("bird left 3.png", "https://drive.google.com/file/d/1DBwLSztZ8oleWZQDaowbqErky0WHoxAj/view?usp=sharing");
        images.put("bird left 4.png", "https://drive.google.com/file/d/1BNULCdZo-3I_cu7eSu_2vuM_n0kD5zQd/view?usp=sharing");
        images.put("bird left 5.png", "https://drive.google.com/file/d/1n7mB7cBMIEZ9oh1e0NfKewxPyqYh0W69/view?usp=sharing");
        images.put("bird left 6.png", "https://drive.google.com/file/d/1NmQDWcELo_lunMK-DTTUrJbhUjUlBzPr/view?usp=sharing");
        images.put("bird left 7.png", "https://drive.google.com/file/d/1GSodLcMOOkiwiCEy5ldMNZpBXj_nxDMH/view?usp=sharing");
        images.put("bird left 8.png", "https://drive.google.com/file/d/1YBRQ5XgYwCN26AbgDwLD8ztCXJYGws3x/view?usp=sharing");
        images.put("bird left 9.png", "https://drive.google.com/file/d/1LoMUYhc2PZg9LFUagStfD5uQpY3csmIa/view?usp=sharing");
        images.put("bird left 10.png", "https://drive.google.com/file/d/1Ks6mfisvtZ6BHZlfoNfl7J5BKHeZkV3i/view?usp=sharing");
        images.put("Icon.png", "https://drive.google.com/file/d/1REekjzktbrJWkrylY5JLhBdK0Ap_os1R/view?usp=sharing");
        images.put("Platform 3.png", "https://drive.google.com/file/d/1MuzdqY04-Rl3SiSdQqDtrhm42vXFxnZv/view?usp=sharing");
        images.put("Platform 6.png", "https://drive.google.com/file/d/1KakbjjSxTgckuAMHwpNOyD0RYtFd2qPZ/view?usp=sharing");
        images.put("Platform 8.png", "https://drive.google.com/file/d/1hFu7ew5Ik3nWiR_f6Ui-ksmxk9H1273r/view?usp=sharing");
        images.put("Platform 32.png", "https://drive.google.com/file/d/128nh560uSg9ZAmyy4Pvq08dIvHVd-Q1n/view?usp=sharing");
        images.put("Platform 82.png", "https://drive.google.com/file/d/1dTZ_lXFybbmMK86ek9yy7hGzxOXbpM-D/view?usp=sharing");
        images.put("Platform5.png", "https://drive.google.com/file/d/1zL1EF2iB79fl6vrWUFWCmxPqH_XlwIuE/view?usp=sharing");
        images.put("Platform52.png", "https://drive.google.com/file/d/1gtD7_tTc5AhUNPG3mdww4oOJF4aF5wiP/view?usp=sharing");
        images.put("Platform62.png", "https://drive.google.com/file/d/1iYVErRh4Ci0E23GjsE1Hkqi9wMozSCrK/view?usp=sharing");
        images.put("player.png", "https://drive.google.com/file/d/1pzRZWeHAjmZgdoFAXwhk0QoqHtcb7l_H/view?usp=sharing");
        images.put("eye.png", "https://drive.google.com/file/d/1oEOgjJPa_0xtIMSs34VDysFSjsLvwuoV/view?usp=sharing");images.put("Platform7.png", "https://drive.google.com/file/d/1hT0R8dT22u1MAYhmI4mcsL2F-_TG3EGf/view?usp=sharing");
        images.put("закат 2.jpg", "https://drive.google.com/file/d/1hf29eongycDK9Cfzi3pzGT6A48DfHhDF/view?usp=sharing");

    }

    public static void fillSounds() {
        sounds.put("AirborneRobots.wav", "https://drive.google.com/file/d/1bDDkn0xw3yggLuusyqaiEPEnKz3E5Blm/view?usp=sharing");
        sounds.put("BackOnTrack.wav", "https://drive.google.com/file/d/1KbQZm0qPRJY-nrAKlSOYgSQjHuziTcJS/view?usp=sharing");
        sounds.put("BaseAfterBase.wav", "https://drive.google.com/file/d/1rTs4ZxeYuSXFI8mhitf6QKoA-cgqzJdv/view?usp=sharing");
        sounds.put("Clubstep.wav", "https://drive.google.com/file/d/1BCrDFpw1p4xxT5vMyvMFeh1o5DnVumsV/view?usp=sharing");
        sounds.put("Cycles.wav", "https://drive.google.com/file/d/1xnjYGPMfDc4tw5HP_QKtpCeHrp7-8Fk3/view?usp=sharing");
        sounds.put("Deadlocked.wav", "https://drive.google.com/file/d/1YQ78By9LRQDTLWMJaQarzGdzVlZn9fpH/view?usp=sharing");
        sounds.put("practice mode.wav", "https://drive.google.com/file/d/1xRgmU_PmQgVRQ7Kv8vHZ50Nc-VxVnUie/view?usp=sharing");
        sounds.put("The7seas.wav", "https://drive.google.com/file/d/1xpSq5wLdCMtzAdCuegcUmfXzdnqodRcC/view?usp=sharing");
    }

    public static void fillLearn() {
        learn.put("arrows.txt", "https://drive.google.com/file/d/1gdBv-7K5uS-idQaxGOkeF8nxb-i-iZqm/view?usp=sharing");
        learn.put("barriers.txt", "https://drive.google.com/file/d/1uQJW9KEyGla0nH0-GEEOzx_qtv9GNUfl/view?usp=sharing");
        learn.put("movingPlatforms.txt", "https://drive.google.com/file/d/1Ry8gaJzgXjo2ZoXTUV5q8FO8kQ3bSmyB/view?usp=sharing");
        learn.put("platforms.txt", "https://drive.google.com/file/d/1gnLeS-F4tIrzXMGjVdHkuut9nCjzH8u4/view?usp=sharing");
        learn.put("player data.txt", "https://drive.google.com/file/d/1YNUf3Aiw_5UwuKbw80Kq_UZIEUf-xV6v/view?usp=sharing");

    }


}




