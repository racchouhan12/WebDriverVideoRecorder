# WebDriverVideoRecorder
Now we can record the execution of selenium webdriver without worrying about minimized screen.

To use this for now just include WebDriverVideoRecorder and dependencies in POM into your project.

In your test

1. Create instance of WebDriverVideoRecorder  
  WebDriverVideoRecorder videoRecorder = new WebDriverVideoRecorder(driver)  
  
2. Call StartRecording method   
videoRecorder.startRecording("D:/Project_name/testVideoRecorder", "video") //arg1 : path of the video, arg2 : name of the video  
  
3. To stop recording  
  videoRecorder.stopRecording();
