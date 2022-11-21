#!/bin/bash
emulator_device_name="automotive"

detect_os() {
	case $(uname | tr '[:upper:]' '[:lower:]') in
	  darwin*)
	    export os=osx
		export path_android_sdk=${HOME}/Library/Android/sdk
		export path_cmd_tools=${path_android_sdk}/cmdline-tools
		export emulator=$path_android_sdk/emulator/emulator	
		export sdkmanager=${path_cmd_tools}/latest/bin/sdkmanager
		export avdmanager=${path_cmd_tools}/latest/bin/avdmanager		
	    ;;
	  msys*)
	    export os=windows
	    export user="$(cut -d "\\" -f2 <<< $(whoami))" 
		export JAVA_HOME=C:/Program\ Files/Android/Android\ Studio/jre
		export path_android_sdk=C:/Users/$user/AppData/Local/Android/sdk
		export ANDROID_HOME=$path_android_sdk
		export path_cmd_tools=${path_android_sdk}/cmdline-tools
		export emulator=$path_android_sdk/emulator/emulator.exe	
		export sdkmanager=${path_cmd_tools}/latest/bin/sdkmanager.bat
		export avdmanager=${path_cmd_tools}/latest/bin/avdmanager.bat		
	    ;;
	  *)
	    export os=notimplemented
	    ;;
	esac
	export adb=$path_android_sdk/platform-tools/adb		
}

confirm() {
	if [ "$override" == "true" ]; then
		true
	else
	    read -r -p "${1} Are you sure? [y/N]" response
	    case "$response" in
	        [yY][eE][sS]|[yY]) 
	            true
	            ;;
	        *)
	            false
	            ;;
	    esac
	fi
}

install_cmd_line_tools() {
	echo "install command line tools"
	if [ $os == "windows" ]; then
		curl https://dl.google.com/android/repository/commandlinetools-win-9123335_latest.zip -J -o tools.zip
	else
		curl https://dl.google.com/android/repository/commandlinetools-mac-9123335_latest.zip -J -o tools.zip
	fi
	unzip tools.zip
	mkdir -p $path_cmd_tools/latest/
	cp -r cmdline-tools/* $path_cmd_tools/latest/
	rm -rf cmdline-tools
	rm tools.zip
}

copy_repo_cfg() {
	echo "Copy custom url config into android folder"
	if [ "$os" == "windows" ]; then
		if [ ! -d "C:/Users/$user/.android/" ]; then
			mkdir C:/Users/$user/.android/
		fi
		cp setup_files/repositories.cfg C:/Users/$user/.android/
	else
		cp setup_files/repositories.cfg ${HOME}/.android/
	fi
}

install_automotive_system_image() {
	if [ $(uname -m) == "arm64" ]; then
		echo "using arm64 automotive image"
		export sys_image="system-images;android-32;android-automotive-playstore;arm64-v8a"
	else
		echo "setting up custom url for x86_64 polestar automotive image"
		confirm "This will overwrite all previously set up custom sdk urls!" && copy_repo_cfg
		echo "using x86_64 polestar system image"
		export sys_image="system-images;android-29;polestar_emulator;x86_64"
	fi
	$sdkmanager --install "platforms;android-32"
	$sdkmanager --install "build-tools;32.1.0-rc1"
	$sdkmanager --install "sources;android-32"
	$sdkmanager --install $sys_image
	$sdkmanager --install "extras;google;auto"
	$sdkmanager --install "extras;google;simulators"
	$sdkmanager --licenses
}

create_emulator_device() {
	echo "create virtual device"
	if [ $(uname -m) == "arm64" ]; then
		device="automotive_1024p_landscape"
	else
		device="polestar_2_playstore"
	fi
	$avdmanager create avd -n $emulator_device_name -k $sys_image -d $device --force
}

build_and_deploy() {
	echo "build apk"
	cd App/
	chmod +x gradlew
	./gradlew assembleDebug
	echo "start emulator"
	$emulator -avd $emulator_device_name  &
	echo "wait for device boot"
	$adb wait-for-device shell 'while [[ -z $(getprop sys.boot_completed) ]]; do sleep 1; done; input keyevent 82'
	echo "install apk"
	$adb install ./automotive/build/outputs/apk/debug/automotive-debug.apk
	echo "setup complete"
}

main() {
	detect_os
	if [ "$os" == "notimplemented" ]; then
		echo "Operating system $os not supported"
		exit 1
	fi
	if [ ! -d "$path_cmd_tools" ]; then
		echo "No command line tools found"
		confirm "Installing command line tools" && install_cmd_line_tools
	fi
	install_automotive_system_image
	create_emulator_device
	build_and_deploy
}

override="false"
if [ "$1" == "--full" ]; then
	echo "full setup enabled..."
	override="true"
fi
main