# WebDriverVideoRecorder
Now we can record the execution of selenium webdriver without worrying about minimized screen.

To use this for now just include WebDriverVideoRecorder and dependencies in POM into your project.

In your test

1. Create instance of WebDriverVideoRecorder  
  WebDriverVideoRecorder videoRecorder = new WebDriverVideoRecorder(getDriver())  
  
2. Set Recorder method   
videoRecorder.setRecorder(videoRecorder); set the recorder
 
3. Start recording
videoRecorder.getRecorder().startRecording(oneInstance.getAsString(KEYS.PROJECT_PATH.name()) + "/verifyBasicSearch", "basicSearch");

//arg1 : path of the video, arg2 : name of the video  
  
4. To stop recording  
  videoRecorder.stopRecording();
