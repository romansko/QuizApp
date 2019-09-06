## Connecting FCM to your app
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


