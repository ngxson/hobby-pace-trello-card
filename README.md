# Trello Card for Pace/Verge/Stratos

This app allow you to sync a card on Trello to your watch.  
It uses [Amazfit Internet Companion](https://forum.xda-developers.com/t/app-amazfit-internet-companion-generic-internet-companion-app-for-pace-stratos.3779945/) lib, so you don't need to connect your watch to a wifi network. You only need [Amazmod](https://github.com/AmazMod/AmazMod) installed on your phone.

Download APK file from [release page](https://github.com/ngxson/hobby-pace-trello-card/releases)

# How to use

Notice: **Amazfit Internet Companion** support must be enabled on Amazfit. Open Amazmod app on your phone > Settings. Scroll to bottom and enable "Amazfit Internet Companion"

![image](https://user-images.githubusercontent.com/7702203/116853261-1191bb80-abf6-11eb-945b-c2531dd712f9.png)

### Get your Trello personal token

- Go to https://trello.com/app-key
- Save the "key" to somewhere (it will be used later)
- Click on "Token" to generate a new token
- Save the token to somewhere for using later

![image](https://user-images.githubusercontent.com/7702203/116852761-36396380-abf5-11eb-97e4-4971f949d3da.png)

### Get the card ID

For example, the card below has `SgKfsmht` as its ID

![image](https://user-images.githubusercontent.com/7702203/116852975-93351980-abf5-11eb-9889-878108ca76fa.png)

### Write these data to your watch

Create a file named `trello.txt`. The content of this file looks like this:

```
your-api-key
your-token
the-card-id
```

For example:

![image](https://user-images.githubusercontent.com/7702203/116853513-79e09d00-abf6-11eb-9b2b-46d12b83c2c1.png)

Copy and paste this file to the root directory of your watch (/sdcard/)

### Sync data

Simple open the app on your watch. Swipe down from the top.
