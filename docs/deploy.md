#Deployment Notes Monitorizare-vot-android
##In order to have Crash Reports:
###Add Firebase in the App
1. Create new Firebase Console App [here](https://console.firebase.google.com/). You can use the google account from which you published the app.
2. Link the Android App in firebase Console from Project Settings(In the top left corner near the Project Overview is an gear icon). In project settings -> Add App, add the package name followed by the environment suffix( ex for dev: ro.code4.monitorizarevot.dev), make this step for every environment.
3. Download the google-services.json and add it the app folder and Sync Gradle Project. 
4. Wait and you should be able to see the crash reports under the Crashlytics tab in Firebase. By default you will receive emails for every crash occurred. 