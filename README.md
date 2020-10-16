# Click (Touch) and Select Game
A simple click(touch) and select system within a 3D world map for games, developed using Java and JME plataform.

## Screenshots
![Screenshot](https://github.com/pfirmino/click-and-select-game/blob/master/screenshots/screenshots.jpg?raw=true)

## Objective
Implementing the Nifty GUI system to change between the main framework screens, in this case: Splash Screen, Loading Screen, Main Screen (Menu) and World Map Screen.
After that, a simple select and gesture system was implemented comprising 3 functionalities:
* Select any character in the 3D world map
* Deselect all of them by tapping twice anywhere on the floor
* Identify 2 fingers on the screen and if it's true, only if it's true, move the camera in the world.

## Where is the main source code?
You can find the files under the following package `com.aerolitos.prj44`, in the `./src` path.

## Where is test compiled APK?
The APG was compiled and tested in Galaxy S7, and everything worked as expected.
You can find these files under the `./dist` folder.

## How to compile it?
You'll need the following applications installed and set up to compile it yourself:
* Jmonkey Engine 3 SDK - Since we're using Nbandroid and Netbeans 8.2 is incompatible with it, you'll need JME SDK 3.1-beta2-b001-SNAPSHOT [Link](https://github.com/jMonkeyEngine/sdk/releases/tag/3.1-beta2-b001-SNAPSHOT)
* CodeWorks for Android 1R6 - I had problems using the newer versions, so this one would work for us [Link](https://developer.nvidia.com/gameworksdownload#?tx=$gameworks,developer_tools)

### Hints
* Use JME3 SDK and configure in Options the proper Android SDK path (In my case was `C:\NVPACK\android-sdk`)
* Open `project properties` and check the following config
* Pay atention to the JDK version set in `Sources section`, otherwise you'll get error stuff (I used JDK 7)
* In the `Compiling` section uncheck the `compile on save` option
* Check the `Enable Android Deployment` option in the `Mobile` section
* Still in `Mobile` section, specify the `Android Target` option (For my project I used Android 6.0)
* Enable the Developer Mode in your Android and the USB Debugging feature before running
* Clean and Build and the Run with your smartphone plugged in

For more information access [JME3 SDK Android section](https://wiki.jmonkeyengine.org/docs/3.3/sdk/android.html)
