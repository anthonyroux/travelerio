## Test out the app with login:
Username: pizzaguy22

Password: 4523039

https://vimeo.com/350896141


Photos:

https://imgur.com/a/1NCgDfK

Clone from github or try this APK

https://www.dropbox.com/s/67du1ynjruqde3i/travelorio.apk?dl=0
## Known issues
API calls to hoteloffers gives a network exception error on devices with API 19 and lower.

Points of interest sometimes gives out of sandbox coordinates even using the provided coordinates in the datacollection
https://github.com/amadeus4dev/data-collection/blob/master/data/pois.md
Also tested using coordinates from google instead, still gives error.

Typing a nonsupported 3 letter citycode into HotelOffers causes an immediate crash due to another network exception Error

Tested on devices: api 19, 27, 28

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

Libraries used:
Glide, Volley, ZXing, Agora.io (Java SDK)

## What it does
Travelor.io has many functions. The unique ones include helping find/book hotels. Provides optional live stream of the rooms available for those that are unsure which room they would like. Also provides live streams of places,  you would want to visit / or already plan on visiting at a later date. All you have to do is make a request to someone in that area you plan on visiting and they stream from their phone directly to yours

Travelor.io also provides a facial recognition check in at the hotel. Say if you lose your hotel key or have your wallet + identification stolen, you can use face recognition to regain access to your room.

## How I built it
For quick deployment I created an RDS instance and used API gateway/Lambda to perform CRUD requests. This way I could push out a base product more quickly and focus my time on UI/UX design.

## Challenges I ran into
Developing a fullstack application alone is not an easy task. I had to setup the backend, write the front end and also design the UI/UX (which I thought was the most difficult). I ended up having to hardcode some of the features instead of making it dynamic because I just didn't have enough time to do everything alone.

Also for one of my real android devices (Kitkat API 19), had some issues making network calls to the self service Amadeus API. I tried debugging for a while but couldn't figure out why it wouldn't work with a certain device. The calls work fine on my API 27 emulator and API 28 real device, but in the end I couldn't get some of the them to work on my 2013 phone.

## What I learned
I added a few more tools to my android belt. I specifically used widgets that I was not familiar with in order to build this app so I could improve my technical skills a bit more. As for Machine Learning / Facial Recognition using AWS Rekognition I definitely learned a lot. Now I can say I have built a functional application using that service. Woohoo!

## What's next for travelor.io
I believe the live streaming component for users to view a place (tourist attractions, beaches, etc..) remotely before visiting. And also checking into hotels/boarding flights with facial recognition is what makes my app unique compared to other apps for booking hotels, and checking out new places like yelp. 

Future features would be tweaking the UI a bit, add some sortby functions for hotel price, distance, has gym, pool, sauna, etc, and also create a machine learning model to recalculate prices based on user queries every 15 mins or so.
