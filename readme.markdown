# What it is #

The **Morse Code Soft Keyboard** or **Morse Code Input Method Editor (MCIME)** is a soft keyboard for Android phones and tablets that replaces the default keyboard with one that only has two buttons -- a dot and a dash. Many thanks to Google for sharing [this great idea][Gmail Tap] on April Fool's day.

> You can tap it in the morning,  
> You can tap it at night,  
> You can tap in the bathroom,  
> It's a dot and a dash to have a conversation with the whole world.  
> -- Todd Smith, Gmail Tap Product Lead

This is cool because, as mentioned in the [Gmail Tap] video, people (like myself) with larger fingers, can type without error. As well, the fact that 26 keys have been reduced down to two impresses many.

But while Google was joking, I am using this keyboard very happily; using the International Morse Code to input text:

|Character|Code|Character|Code|
|:-------:|:--:|:-------:|:--:|
|A|**&middot;&nbsp;--**|N|**--&nbsp;&middot;**|
|B|**--&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;**|O|**--&nbsp;--&nbsp;--**|
|C|**--&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;**|P|**&nbsp;&middot;&nbsp;--&nbsp;--&nbsp;&middot;**|
|D|**--&nbsp;&middot;&nbsp;&middot;&nbsp;**|Q|**--&nbsp;--&nbsp;&middot;&nbsp;--**|
|E|**&middot;&nbsp;**|R|**&nbsp;&middot;&nbsp;--&nbsp;&middot;**|
|F|**&middot;&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;**|S|**&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;**|
|G|**--&nbsp;--&nbsp;&middot;&nbsp;**|T|**--**|
|H|**&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;**|U|**&nbsp;&middot;&nbsp;&middot;&nbsp;--**|
|I|**&nbsp;&middot;&nbsp;&middot;**|V|**&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;--**|
|J|**&nbsp;&middot;&nbsp;--&nbsp;--&nbsp;**|W|**&nbsp;&middot;&nbsp;--&nbsp;--**|
|K|**--&nbsp;&middot;&nbsp;--**|X|**--&nbsp;&middot;&nbsp;&middot;&nbsp;--**|
|L|**&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;&middot;**|Y|**--&nbsp;&middot;&nbsp;--&nbsp;--**|
|M|**--&nbsp;--**|Z|**--&nbsp;--&nbsp;&middot;&nbsp;&middot;**|

Plus some extra codes for digits and common punctuation:

|Character|Code|Character|Code|
|:-------:|:--:|:-------:|:--:|
|1|**&nbsp;&middot;&nbsp;--&nbsp;--&nbsp;--&nbsp;--**|.|**&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;--**|
|2|**&nbsp;&middot;&nbsp;&middot;&nbsp;--&nbsp;--&nbsp;--**|,|**&nbsp;--&nbsp;--&nbsp;&middot;&nbsp;&middot;&nbsp;--&nbsp;--**|
|3|**&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;--&nbsp;--**|?|**&nbsp;&middot;&nbsp;&middot;&nbsp;--&nbsp;--&nbsp;&middot;&nbsp;&middot;**|
|4|**&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;--**|'|**&nbsp;&middot;&nbsp;--&nbsp;--&nbsp;--&nbsp;--&nbsp;&middot;**|
|5|**&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;**|!|**&nbsp;--&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;--&nbsp;--**|
|6|**&nbsp;--&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;**|/|**&nbsp;--&nbsp;&middot;&nbsp;&middot;&nbsp;--&nbsp;&middot;**|
|7|**&nbsp;--&nbsp;--&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;**|(|**&nbsp;--&nbsp;&middot;&nbsp;--&nbsp;--&nbsp;&middot;**|
|8|**&nbsp;--&nbsp;--&nbsp;--&nbsp;&middot;&nbsp;&middot;**|)|**&nbsp;--&nbsp;&middot;&nbsp;--&nbsp;--&nbsp;&middot;&nbsp;--**|
|9|**&nbsp;--&nbsp;--&nbsp;--&nbsp;--&nbsp;&middot;**|&amp;|**&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;**|
|0|**&nbsp;--&nbsp;--&nbsp;--&nbsp;--&nbsp;--**|:|**&nbsp;--&nbsp;--&nbsp;--&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;**|
|;|**&nbsp;--&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;--**|=|**&nbsp;--&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;--**|
|+|**&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;--&nbsp;&middot;**|-|**&nbsp;--&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;--**|
|_|**&nbsp;&middot;&nbsp;&middot;&nbsp;--&nbsp;--&nbsp;&middot;&nbsp;--**|\\|**&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;&middot;&nbsp;--&nbsp;&middot;**|
|$|**&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;&middot;&nbsp;--**|@|**&nbsp;&middot;&nbsp;--&nbsp;--&nbsp;&middot;&nbsp;--&nbsp;&middot;**|

  [Gmail Tap]:http://www.youtube.com/watch?v=1KhZKNZO8mQ

# Building and installing #

To build from this source, Ant, the JDK, and the Android SDK are all required. (It's just like building any other Android app.) **I am assuming you have your phone connected via [ADB][3], and have the SDK preconfigured.**

1. Download a [tarball][2] or a [zipball][1] of the source.
2. Unpack the archive to any location of your choosing, e.g. `/tmp/`.
3. Change into said directory via `cd`.
3. `android update project -p .` to create the local properties file.
4. Run `ant debug > /dev/null` to create the packages.
5. `adb -d install ./bin/MorseCodeKeyboard-debug.apk` will install to the connected physical device.

Once the `.apk` file has been installed, open *Settings > Language and Keyboard* on your device. Select the checkbox beside Morse Code and accept the warning. Select the *Input Method* preference option and choose *Morse Code* to change to the new keyboard. All done. **It will take some getting used to, but Morse Code is super cool, trust me.**

# Installing without building #

To install the Morse Code Keyboard using the included `.apk` file, first ensure that *Unknown Sources* is selected from the *Settings > Application* menu. This will allow you to install packages not from the Android Market (Google Play).

1. [Download the package][4] from your device.
2. Open the downloaded package, and choose install.

Once the `.apk` file has been installed, open *Settings > Language and Keyboard* on your device. Select the checkbox beside Morse Code and accept the warning. Select the *Input Method* preference option and choose *Morse Code* to change to the new keyboard. All done. **It will take some getting used to, but Morse Code is super cool, trust me.**

  [1]:https://github.com/whymarrh/mcime/zipball/master
  [2]:https://github.com/whymarrh/mcime/tarball/master
  [3]:http://developer.android.com/guide/developing/tools/adb.html
  [4]:https://raw.github.com/whymarrh/mcime/master/apk/MorseCodeKeyboard.apk
