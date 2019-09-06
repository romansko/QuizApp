
// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp();

exports.dataBaseUpdateNotification = functions.database.ref('rootName').onWrite(event => {
    
    var payload = {
      notification: {
        title: "Database update",
        body: "Questions database has been updated"
      }
    };
    console.log("Sending update message..");
    var topic = "db_updates";
    admin.messaging().sendToTopic(topic, payload);
  });
