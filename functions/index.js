// import firebase functions modules
const functions = require("firebase-functions");
// import admin module
const admin = require("firebase-admin");
admin.initializeApp();

// NEW DIET
exports.dietCreated = functions.database.ref("/diets/{dietId}")
    .onCreate(async(snapshot, context) => {
      const payload = {
        notification: {
          title: "S'ha creat una nova dieta!",
          body: `Entra i observa la dieta \"${snapshot.val().name}\"!`,
          icon: "https://www.jefit.com/images/exercises/800_600/560.jpg",
          // https://firebasestorage.googleapis.com/v0/b/fitassistant-db0ef.appspot.com/o/notifications%2Froutine.png?alt=media&token=69cb94fc-ec8e-44b8-a545-d0c2045e2e9b
        }
      };
      // Get the list of device tokens.
      const getDeviceTokensPromise = admin.database().ref("/fcmTokens").once("value");
      const results = await Promise.all([getDeviceTokensPromise]);
      let tokensSnapshot;
      let tokens;
      tokensSnapshot = results[0];
      tokens = Object.keys(tokensSnapshot.val());
      // Send notifications to all tokens.
          const response = await admin.messaging().sendToDevice(tokens, payload);
          // For each message check if there was an error.
          const tokensToRemove = [];
          response.results.forEach((result, index) => {
            const error = result.error;
            if (error) {
              functions.logger.error(
                'Failure sending notification to',
                tokens[index],
                error
              );
              // Cleanup the tokens who are not registered anymore.
              if (error.code === 'messaging/invalid-registration-token' ||
                  error.code === 'messaging/registration-token-not-registered') {
                tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
              }
            }
          });
          return Promise.all(tokensToRemove);
});

// NEW EXERCISE
exports.exerciseCreated = functions.database.ref("/exercises/{exerciseId}/")
    .onCreate(async(snapshot, context) => {
      const payload = {
        notification: {
          title: "S'ha creat un nou exercici!",
          body: `Entra i observa el exercici \"${snapshot.val().name}\"!`,
          icon: "https://www.jefit.com/images/exercises/800_600/560.jpg",
          // https://firebasestorage.googleapis.com/v0/b/fitassistant-db0ef.appspot.com/o/notifications%2Froutine.png?alt=media&token=69cb94fc-ec8e-44b8-a545-d0c2045e2e9b
        }
      };
      // Get the list of device tokens.
      const getDeviceTokensPromise = admin.database().ref("/fcmTokens").once("value");
      const results = await Promise.all([getDeviceTokensPromise]);
      let tokensSnapshot;
      let tokens;
      tokensSnapshot = results[0];
      tokens = Object.keys(tokensSnapshot.val());
      // Send notifications to all tokens.
          const response = await admin.messaging().sendToDevice(tokens, payload);
          // For each message check if there was an error.
          const tokensToRemove = [];
          response.results.forEach((result, index) => {
            const error = result.error;
            if (error) {
              functions.logger.error(
                'Failure sending notification to',
                tokens[index],
                error
              );
              // Cleanup the tokens who are not registered anymore.
              if (error.code === 'messaging/invalid-registration-token' ||
                  error.code === 'messaging/registration-token-not-registered') {
                tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
              }
            }
          });
          return Promise.all(tokensToRemove);
});

// NEW RECEIPT
exports.receiptCreated = functions.database.ref("/receipts/{receiptId}")
    .onCreate(async(snapshot, context) => {
      const payload = {
        notification: {
          title: "S'ha creat una nova recepta!",
          body: `Entra i observa la recepta \"${snapshot.val().name}\"!`,
          icon: "https://www.jefit.com/images/exercises/800_600/560.jpg",
          // https://firebasestorage.googleapis.com/v0/b/fitassistant-db0ef.appspot.com/o/notifications%2Froutine.png?alt=media&token=69cb94fc-ec8e-44b8-a545-d0c2045e2e9b
        }
      };
      // Get the list of device tokens.
      const getDeviceTokensPromise = admin.database().ref("/fcmTokens").once("value");
      const results = await Promise.all([getDeviceTokensPromise]);
      let tokensSnapshot;
      let tokens;
      tokensSnapshot = results[0];
      tokens = Object.keys(tokensSnapshot.val());
      // Send notifications to all tokens.
          const response = await admin.messaging().sendToDevice(tokens, payload);
          // For each message check if there was an error.
          const tokensToRemove = [];
          response.results.forEach((result, index) => {
            const error = result.error;
            if (error) {
              functions.logger.error(
                'Failure sending notification to',
                tokens[index],
                error
              );
              // Cleanup the tokens who are not registered anymore.
              if (error.code === 'messaging/invalid-registration-token' ||
                  error.code === 'messaging/registration-token-not-registered') {
                tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
              }
            }
          });
          return Promise.all(tokensToRemove);
});

// NEW WORKOUT
exports.workoutCreated = functions.database.ref("/workouts/{workoutId}")
    .onCreate(async(snapshot, context) => {
      const payload = {
        notification: {
          title: "S'ha creat un nou tipus de rutina!",
          body: `Entra i observa la rutina \"${snapshot.val().name}\"!`,
          icon: "https://www.jefit.com/images/exercises/800_600/560.jpg",
          // https://firebasestorage.googleapis.com/v0/b/fitassistant-db0ef.appspot.com/o/notifications%2Froutine.png?alt=media&token=69cb94fc-ec8e-44b8-a545-d0c2045e2e9b
        }
      };
      // Get the list of device tokens.
      const getDeviceTokensPromise = admin.database().ref("/fcmTokens").once("value");
      const results = await Promise.all([getDeviceTokensPromise]);
      let tokensSnapshot;
      let tokens;
      tokensSnapshot = results[0];
      tokens = Object.keys(tokensSnapshot.val());
      // Send notifications to all tokens.
          const response = await admin.messaging().sendToDevice(tokens, payload);
          // For each message check if there was an error.
          const tokensToRemove = [];
          response.results.forEach((result, index) => {
            const error = result.error;
            if (error) {
              functions.logger.error(
                'Failure sending notification to',
                tokens[index],
                error
              );
              // Cleanup the tokens who are not registered anymore.
              if (error.code === 'messaging/invalid-registration-token' ||
                  error.code === 'messaging/registration-token-not-registered') {
                tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
              }
            }
          });
          return Promise.all(tokensToRemove);
});

// MESSAGE RECEIVED
exports.messageReceived = functions.database.ref("/messages")
    .onCreate(async(snapshot, context) => {
      const payload = {
        notification: {
          title: "Has rebut un nou missatge",
          body: `Nou missatge de ${snapshot.val().fromUser}`
        }
      };
      // Get the list of device tokens.
      const getDeviceTokensPromise = admin.database().ref("/fcmTokens").once("value");
      const query = admin.database().ref("/userTokens").where("userId", "==", snapshot.val(fromUserId)).once("value");
      const results = Promise.all([getDeviceTokensPromise]);
      let tokensSnapshot;
      let tokens;
      tokensSnapshot = results;
      tokens = Object.keys(tokensSnapshot);
      let toSend = tokens.find();
      return admin.messaging().sendToDevice(tokens, payload);
});

// CLEAN TOKENS THAT ARE NO LONGER VALID
function cleanupTokens(response, tokens) {
    // For each notification we check if there was an error.
    const tokensDelete = [];
    response.results.forEach((result, index) => {
    const error = result.error;
    if (error) {
         console.error("Failure sending notification to", tokens[index], error);
         // Cleanup the tokens who are not registered anymore.
         if (error.code === "messaging/invalid-registration-token" ||
             error.code === "messaging/registration-token-not-registered") {
           const deleteTask = admin.firestore().collection("fcmTokens").doc(tokens[index]).delete();
           tokensDelete.push(deleteTask);
         }
    }
    });
    return Promise.all(tokensDelete);
};
