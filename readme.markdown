# What it is #

The Morse code input method editor (there's an acronym for that) is a soft keyboard for Android phones that replaces the default stock keyboard with one that only has two buttons -- a dot and a dash. Many thanks to Google for sharing this great idea as [Gmail Tap] on April Fool's day 2012.

  [Gmail Tap]:http://www.youtube.com/watch?v=1KhZKNZO8mQ

> You can tap it in the morning,  
> You can tap it at night,  
> You can tap in the bathroom,  
> It's a dot and a dash to have a conversation with the whole world.  
> -- Todd Smith, Gmail Tap Product Lead

This is super cool because, as mentioned in the Gmail Tap video, people (like myself) with larger fingers, can type quickly without error. Also, the fact that 26 keys have been reduced down to two impresses many.

![Screenshot](screenshot.png)

But while Google was joking, I am using this keyboard very happily; using International Morse code to input text,

|Character|Code|Character|Code|
|:-------:|:--:|:-------:|:--:|
|A|**&middot;&nbsp;-**|N|**--&nbsp;&middot;**|
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

Plus some extra codes for digits and common punctuation,

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
|;|**&nbsp;--&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;--**|-|**&nbsp;--&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;--**|
|_|**&nbsp;&middot;&nbsp;&middot;&nbsp;--&nbsp;--&nbsp;&middot;&nbsp;--**|\\|**&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;&middot;&nbsp;--&nbsp;&middot;**|
|$|**&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;&middot;&nbsp;--**|@|**&nbsp;&middot;&nbsp;--&nbsp;--&nbsp;&middot;&nbsp;--&nbsp;&middot;**|

And two [procedural signals][5],

|Code|Meaning|Code|Meaning|
|:--:|:-----:|:--:|:-----:|
|**&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;--**|Newline|**&nbsp;&middot;&nbsp;--&nbsp;&middot;&nbsp;--&nbsp;&middot;**|End-of-transmisson (EOT)|

# Building and installing #

To build from this source, Ant, the JDK, and the Android SDK are all required. (It's just like building any other Android app.) **I am assuming you have your phone connected via [ADB][3], and have the SDK preconfigured.**

1. Download a [tarball][2] or [zipball][1] of the source.
2. Unpack the archive to any location of your choosing, e.g. `/tmp/`.
3. Change into said directory via `cd`.
3. `android update project -p .` to create the local properties file.
4. Run `ant debug` to create the packages.
5. `adb -d install ./bin/MorseCodeKeyboard-debug.apk` will install to the connected physical device.

Once the `.apk` file has been installed, open *Settings > Language and Keyboard* on your device. Select the checkbox beside *Morse code* and accept the warning. Select the *Input Method* preference option and choose *Morse code* to change to the new keyboard. All done.

# Installing without building #

To install the Morse code keyboard using the included `.apk` file, first ensure that *Unknown Sources* is selected from the *Settings > Application* menu. This will allow you to install packages not from the Android Market (Google Play).

1. [Download the package][4] from your Android device.
2. Open the downloaded package, and choose install.

Once the `.apk` file has been installed, open *Settings > Language and Keyboard* on your device. Select the checkbox beside Morse code and accept the warning. Select the *Input Method* preference option and choose *Morse code* to change to the new keyboard. All done.

# The behaviour of the  delete (DEL) key #

The delete key behaviour may seem a bit weird at first. What it does is this: if you enter a sequence of dots and dashes and then press delete, it will empty the composing text (as in the dots/dashes), not just the last character. **It deletes the whole sequence that was entered, not just the last dot/dash and not the previous character.** In the event that no sequence was entered and you press the delete key, it will then delete the previous character. I feel that this becomes intuitive after a bit of use. You may disagree; I may add an alternative behaviour as preference in the future.

  [1]:https://github.com/whymarrh/mcime/zipball/master
  [2]:https://github.com/whymarrh/mcime/tarball/master
  [3]:http://developer.android.com/guide/developing/tools/adb.html
  [4]:https://raw.github.com/whymarrh/mcime/master/apk/MorseCodeKeyboard.apk
  [5]:http://en.wikipedia.org/wiki/Prosigns_for_Morse_code
