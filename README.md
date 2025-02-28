
<h1 align="center">
  <br>
  <img src="https://i.imgur.com/3uKca4r_d.webp?maxwidth=640&shape=thumb&fidelity=medium" alt="WebDriverVideoRecorder" width="200">
  <br>
  WebDriverVideoRecorder
  <br>
</h1>

<h4 align="center">A library to record Selenium Webdriver test execution as video.</h4>


<p align="center">
  <a href="#how-to-use">How To Use</a> •
  <a href="#Maven">Maven</a> •
  <a href="#credits">Credits</a> •
  <a href="#license">License</a>
</p>

## An idea to create test execution recorder

The idea to create this project came when there was a failure in a huge transaction E2E scenario and to debug the test script, a visual representation of whole process was needed.
No doubt there are ways we can achieve this but the problem with all those ways is that one has to keep his/her execution window in focus and cannot use the system.
This library helps you use your system while executing tests and recording the same.

## Note 

This library might not give you perfect results for some websites or application under test. There is still scope for improvement.
Please contact the developers for any feedback.

## How To Use

To use this for now just include WebDriverVideoRecorder and dependencies in POM into your project.

Do the following in your test -
```java
//Create instance of WebDriverVideoRecorder by passing WebDriver instance  
WebDriverVideoRecorder videoRecorder = new WebDriverVideoRecorder(driver);
 
//Set Recorder method   
videoRecorder.setRecorder(videoRecorder);
 
//Start recording
//arg1 : path of the video, arg2 : name of the video 
videoRecorder.getRecorder().startRecording(oneInstance.getAsString(KEYS.PROJECT_PATH.name()) + "/verifyBasicSearch", "basicSearch");
  
//To stop recording  
videoRecorder.getRecorder().stopRecording();
```


## Maven

Add this library as a maven dependency.
TODO

## Credits

This library uses the following open source packages:

- [JavaCV](https://github.com/bytedeco/javacv)
- [Selenium WebDriver](https://www.selenium.dev/)


## License

MIT

---

> Rakesh's Github [@racchouhan12](https://github.com/racchouhan12) &nbsp;&middot;&nbsp;
> Pankaj's Github [@PankajRPandey](https://github.com/PankajRPandey)
