# YoutubeApiJava

## About the `.unsupported.chat` package.

YouTube's API quota for live chat is too unreasonably low. 
For a single user, with a cost of 5 points per query, you can only make 2000 requests per day. 
At a rate of ~1.5s (as suggested by the api) you can only get about 22 minutes worth of chat per day, which is unusable. 
As a compromise, we've implemented a wrapper around the [youtube-chat](https://www.npmjs.com/package/youtube-chat) npm package in Java.

### If you work at YouTube and don't like this

Either lower the quota costs to <1 point or remove the cost from the list endpoint entirely.
