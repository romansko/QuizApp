

# QuizApp

An application that fetch data (Questions and answers) from Firebase and display them to the user allowing him to play Trivia.

## Project Components

1. [**FireBase**](https://firebase.google.com/) - RealTime Database that stores the questions in JSON Format.
2. [**QuestionGenerator**](https://github.com/Romansko/QuizApp/tree/master/QuestionGenerator) - .NET application which reads a specific format written by the user and generates a JSON format suitable for importing to Firebase.
3. [**QuizApp-WebApp**](https://github.com/Romansko/QuizApp/tree/master/QuizApp-WebApp) - Web Application written with HTML + JavaScript that displays the questions from Firebase allowing the user to play.
4. [**QuizApp-Android**](https://github.com/Romansko/QuizApp/tree/master/QuizApp-Android) - Android Application written in Java that displays the questions from Firebase allowing the user to play.



## FireBase Setup

In order to make everything to work, you will need to setup your own database in firebase:
1. Go to [Firebase Console](https://console.firebase.google.com).
2. Create new project.
3. Navigate to Develop -> Database.
4. Create Real Time Database. (not cloud firestore).
5. While you're at Develop->Database page, make sure you're pointing to "RealTime Database".
6. Click on "Rules" tab and edit the `read` property to be `true`. This is mandatory step because questions will not be loaded if this set to `false`.
7. Click the `Data` tab. To import JSON format which contains your questions, click on the 3 dots menu at the right side and choose "import JSON".
8. The default root name that contains all the questions is `rootName`. If you decide to change it, it must be the same in all the components: Andrid app, WebApp, JSON root name.


*This Project is inspired by https://github.com/jrue/JavaScript-Quiz by Jeremy Rue. The QuizApp-WebApp is based on Jeremy's project.*


## Example
Example for usage, can be found in the following links. The example is for Human Computer Interface course.
1. Web App:     https://github.com/Romansko/HCI
2. Andorid App: https://play.google.com/store/apps/details?id=com.rkcodesolution.hci

