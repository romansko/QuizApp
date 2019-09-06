

# QuizApp - Android


An Android trivia application that fetch data (Questions and answers) from Firebase and display them to the user allowing him to play the Trivia.

## Development Setup

#### Connecting Android app to FireBase
After downloading this repo, you will want to connect your App to your firebase database:
1. Log into firebase console and choose your project (or create new if you didn't already).
2. Go to "Project Overview" and then click "Add app" and choose Android app. Follow google's setup which includes the following steps:
	i. Register your app.
	ii. Add provided `google-services.json` to `QuizApp-Android\app\`
	iii. Adding FireBase SDK via the gradle is already reflected in this project. You might want to upgrade Firebase version in the future though.
	iv. Run the app via Android Studio in order to check connection with google's setup.
3. If your realtime database root name differs from "rootName". You must change the variable ROOT_NAME in the file `FireBaseHandler.java` to your root name.
3. That's it. The Android app is connected to Firebase.

#### Connecting FCM to your app
The android app can notify about database questions update through FCM (Firebase Cloud Messaging). In order for this mechanism to work, you will have to connect the FCM of your Firebase project to the android app.
Follow these steps:
1. Set up FCM project by navigating with CMD or PowerShell inside QuizApp project to QuizApp-Android\FireBase\
2. Run the following command: `npm install -g firebase-tools` in order to install Firebase tools. [npm](https://www.npmjs.com/get-npm) must be installed if you don't have it.
3. Run in cmd / powershell: `firebase login --interactive`and login with your firebase user. 
4. Run in cmd / powershell: `firebase init`
This will result a setup:
i. Choose `Functions: Configure and deploy Cloud Functions`
ii. Choose your firebase project.
iii. Choose Javascript.
iv. No need to use ESLint.
v. agree to install dependecies.
6. Replace`QuizApp-Android\FireBase\functions\index.js` with `QuizApp-Android\FireBase\index.js`
7.  Run in cmd / powershell: `firebase deploy`

*Note: All the commands above need to be run from terminal pointing at the folder "QuizApp-Android\FireBase\".*

##### *Android QuizApp was developed with android studio 3.4.1.*

## Plugins used

- [Firebase](https://firebase.google.com/) - Question's database.

- [Zoomage](https://github.com/jsibbold/zoomage) - A simple pinch-to-zoom ImageView library for Android with an emphasis on a smooth and natural feel.

- [Android Universal Image Loader](https://github.com/nostra13/Android-Universal-Image-Loader) 


