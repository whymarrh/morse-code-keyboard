#!/bin/bash

./uninstall.sh
ant debug
adb -d install ./bin/MorseCodeKeyboard-debug.apk
mv ./bin/MorseCodeKeyboard-debug.apk ./apk/MorseCodeKeyboard.apk
rm -rf bin
rm -rf gen
rm -rf libs
