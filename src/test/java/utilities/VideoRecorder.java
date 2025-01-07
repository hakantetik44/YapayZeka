package utilities;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.EncoderException;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.VideoSize;
import java.awt.*;
import java.io.File;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class VideoRecorder {
    private static ScreenRecorder screenRecorder;
    private static String lastRecordingPath;
    private static String lastMp4Path;
    private static final String VIDEOS_DIR = "target/allure-results";

    public static void startRecording(String scenarioName) {
        try {
            File videosDir = new File(VIDEOS_DIR);
            if (!videosDir.exists()) {
                videosDir.mkdirs();
            }

            String timestamp = String.valueOf(System.currentTimeMillis());
            lastRecordingPath = VIDEOS_DIR + "/" + scenarioName + "_" + timestamp + ".avi";
            lastMp4Path = VIDEOS_DIR + "/" + scenarioName + "_" + timestamp + ".mp4";

            // Browser penceresinin konumunu ve boyutunu al
            WebDriver driver = Driver.getDriver();
            Point browserPosition = driver.manage().window().getPosition();
            Dimension browserSize = driver.manage().window().getSize();
            
            // Browser'ın bulunduğu ekranı bul
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] screens = ge.getScreenDevices();
            GraphicsDevice browserScreen = null;
            Rectangle browserBounds = new Rectangle(
                browserPosition.getX(),
                browserPosition.getY(),
                browserSize.getWidth(),
                browserSize.getHeight()
            );
            
            for (GraphicsDevice screen : screens) {
                Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();
                if (screenBounds.contains(browserPosition.getX(), browserPosition.getY())) {
                    browserScreen = screen;
                    break;
                }
            }
            
            // Eğer browser ekranı bulunamadıysa varsayılan ekranı kullan
            if (browserScreen == null) {
                browserScreen = ge.getDefaultScreenDevice();
            }
            
            // Browser penceresinin ekran üzerindeki göreceli konumunu hesapla
            Rectangle screenBounds = browserScreen.getDefaultConfiguration().getBounds();
            Rectangle captureSize = new Rectangle(
                browserPosition.getX() - screenBounds.x,
                browserPosition.getY() - screenBounds.y,
                browserSize.getWidth(),
                browserSize.getHeight()
            );

            screenRecorder = new ScreenRecorder(
                    browserScreen.getDefaultConfiguration(),
                    captureSize,
                    new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                            QualityKey, 1.0f,
                            KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                            FrameRateKey, Rational.valueOf(30)),
                    null,
                    new File(VIDEOS_DIR));

            screenRecorder.start();
            System.out.println("Video kaydı başlatıldı: " + lastRecordingPath);
            System.out.println("Browser konumu: x=" + browserPosition.getX() + ", y=" + browserPosition.getY());
            System.out.println("Browser boyutu: width=" + browserSize.getWidth() + ", height=" + browserSize.getHeight());

        } catch (Exception e) {
            System.err.println("Video kaydı başlatılamadı: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void stopRecording() {
        try {
            if (screenRecorder != null) {
                screenRecorder.stop();
                File videoFile = screenRecorder.getCreatedMovieFiles().get(0);
                File targetFile = new File(lastRecordingPath);
                if (videoFile.exists()) {
                    if (videoFile.renameTo(targetFile)) {
                        System.out.println("AVI video kaydedildi: " + targetFile.getAbsolutePath());
                        convertToMp4(targetFile, new File(lastMp4Path));
                    } else {
                        System.err.println("Video dosyası yeniden adlandırılamadı");
                    }
                } else {
                    System.err.println("Video dosyası oluşturulamadı");
                }
            }
        } catch (Exception e) {
            System.err.println("Video kaydı durdurulamadı: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void convertToMp4(File source, File target) {
        try {
            // Browser boyutlarını al
            WebDriver driver = Driver.getDriver();
            Dimension browserSize = driver.manage().window().getSize();

            VideoAttributes video = new VideoAttributes();
            video.setCodec("h264");
            video.setSize(new VideoSize(browserSize.getWidth(), browserSize.getHeight()));

            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setVideoAttributes(video);

            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);
            
            System.out.println("MP4 video oluşturuldu: " + target.getAbsolutePath());
            
            // AVI dosyasını sil
            if (source.delete()) {
                System.out.println("AVI dosyası silindi: " + source.getAbsolutePath());
            }
        } catch (EncoderException e) {
            System.err.println("Video dönüştürme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getLastRecordingPath() {
        return lastMp4Path;
    }
}
