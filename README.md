## Test out the app with login:
Username: pizzaguy22

Password: 4523039


Photos:

https://imgur.com/a/1NCgDfK

Clone from github or try this APK

https://www.dropbox.com/s/h2f40eylq3w6ce9/app-debug.apk?dl=0
## Known issues
API calls to hoteloffers gives a network exception error on devices with API 19 and lower.

Points of interest sometimes gives out of sandbox coordinates even using the provided coordinates in the datacollection
https://github.com/amadeus4dev/data-collection/blob/master/data/pois.md
Also tested using coordinates from google instead, still gives error.

Typing a nonsupported 3 letter citycode into HotelOffers causes an immediate crash due to another network exception Error

## Built With
amazon-web-services,
node.js,
postgresql,
java,
agora,
android-studio,
lambda,
rds,
apigateway,
s3,
rekognition,
cognito,
sns

## What it does
Travelor.io has many functions. The unique ones include helping find/book hotels. Provides optional live stream of the rooms available for those that are unsure which room they would like. Also provides live streams of places,  you would want to visit / or already plan on visiting, but at a later date so you have a feel of what it's going to be like. All you have to do is make a request to someone in that area you plan on visiting and they stream from their phone directly to yours. (This will provide an incentive if you have to pay the streamer, somewhat of an ecommerce model)

Travelor.io also provides a facial recognition check in at the hotel. Say if you lose your hotel key or have your wallet + identification stolen, you can use face recognition to regain access to your room.

## How I built it
For quick deployment I created an RDS instance and used API gateway/Lambda to perform CRUD requests. This way I could push out a base product more quickly and focus my time on UI/UX design.

## Challenges I ran into
Developing a fullstack application alone is not an easy task. I had to setup the backend, write the front end and also design the UI/UX (which I thought was the most difficult). I ended up having to hardcode some of the features instead of making it dynamic because I just didn't have enough time to do everything alone.

Also my real android device (Kitkat API 19) had some issues making network calls to the self service Amadeus API. I tried debugging for a while but to no avail and also couldn't find any documentation on why it wasn't working. The calls work fine on my API 27 emulator and API 28 real device, but in the end I couldn't get some of the them to work on my ancient 2013 phone.


## Accomplishments that I'm proud of
I'm actually very satisfied with the amount of effort I put in these past 5 days. I started extremely late but still managed to produce a solid foundation for a full application.


## What I learned
I added a few more tools to my android belt. I specifically used widgets that I was not familiar with in order to build this app so I could improve my technical skills a bit more. As for Machine Learning / Facial Recognition using AWS Rekognition I definitely learned a lot. Now I can say I have built a functional application using that service. Woohoo!

## What's next for travelor.io
I believe the live streaming component for users to view a place (tourist attractions, beaches, etc..) remotely before visiting and checking into hotels/boarding flights with facial recognition is what makes my app unique compared to the hundreds of other apps for booking hotels, checking flights. 

I would also tweak the UI a bit, add some sortby features, and create a machine learning model to recalculate prices based on user queries every 15 mins or so.
