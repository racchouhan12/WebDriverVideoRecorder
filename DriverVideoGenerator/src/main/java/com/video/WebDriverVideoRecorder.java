package com.varian.utilities;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

public class WebDriverVideoRecorder implements Runnable {
    private WebDriver driver;
    private boolean isProcessing;
    private Thread thread;
    private String screenShotsPath = null;
    private String tmpScreenshot = null;
    private double frameRate = 2;
    private int videoBitRate = 9000;
    private static int count = 1;
    private String videoName = "video";

    private ThreadLocal<WebDriverVideoRecorder> threadedRecorder = new ThreadLocal<>();

    public WebDriverVideoRecorder(WebDriver driver) {
        this.driver = driver;
    }

    private void setTrue() {
        isProcessing = true;
    }

    public void setFrameRate(double frameRate) {
        frameRate = frameRate;
    }

    public void setBitRate(int bitRate) {
        videoBitRate = bitRate;
    }

    public WebDriverVideoRecorder getRecorder() {
        return threadedRecorder.get();
    }

    public void setRecorder(WebDriverVideoRecorder videoRecorder) {
        threadedRecorder.set(videoRecorder);
    }

    public void startRecording(String videoPath, String videoName) {

        getRecorder().screenShotsPath = videoPath;
        getRecorder().tmpScreenshot = screenShotsPath + "/tmp_" + getRandomAlphaStringOfLen(10) + "/";
        getRecorder().videoName = videoName;
        System.out.println("Screen shot path : " + tmpScreenshot);
        createFolder(screenShotsPath);
        createFolder(tmpScreenshot);
        thread = new Thread(getRecorder(), "Image Capturing");
        System.out.println("Thread count :" + Thread.currentThread().getName());
        setTrue();
        thread.start();
    }

    public boolean createFolder(String nameOfFolder) {
        File file = new File(nameOfFolder);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println(nameOfFolder + "created");
                return true;
            } else {
                System.out.println("Fail to create " + nameOfFolder);

                return false;
            }
        }
        return false;
    }

    private void convertJpgToMovie(String imgPath, String destPathOfVideo) {
        File f = new File(imgPath);
        File[] f2 = f.listFiles();
        LinkedList<String> listOfFiles = new LinkedList<String>();
        Arrays.asList(f2).forEach(e -> listOfFiles.add(e.getAbsolutePath()));
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(destPathOfVideo, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        try {
            recorder.setFrameRate(frameRate);
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
            recorder.setVideoBitrate(videoBitRate);
            recorder.setFormat("mp4");
            recorder.setVideoQuality(0.0); // maximum quality
            recorder.start();
            for (int i = 0; i < listOfFiles.size(); i++) {
                recorder.record(grabberConverter.convert(cvLoadImage(listOfFiles.get(i))));
            }
            recorder.stop();
        } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
            e.printStackTrace();
        }
    }

    private void takeSnapShot(String fileWithPath) throws IOException {
        long currentMilliseconds = System.currentTimeMillis();
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File destFile = new File(fileWithPath + "." + currentMilliseconds + ".jpg");
        FileUtils.copyFile(srcFile, destFile);
    }

    public void stopRecording() throws InterruptedException {
        isProcessing = false;
        thread.join();
        convertJpgToMovie(tmpScreenshot, screenShotsPath + "/" + videoName + "_" + count + ".mp4");
        count = count + 1;
        deleteFiles();
    }

    private void deleteFiles() {
        File folder = new File(tmpScreenshot);
        File folder_2 = new File(screenShotsPath);
        File[] fileList = folder.listFiles();
        for (File f : fileList) {
            f.delete();
        }
        File[] fileList_2 = folder_2.listFiles();
        for (File f : fileList_2) {
            if (f.isDirectory()) {
                f.delete();
            }
        }

    }

    public static synchronized String getRandomAlphaStringOfLen(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    @Override
    public void run() {
        while (isProcessing) {
            try {
                takeSnapShot(tmpScreenshot);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
