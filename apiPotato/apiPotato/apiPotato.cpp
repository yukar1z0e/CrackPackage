#include <iostream>
#include <windows.h>

using namespace std;

static string device = " -s 000.000.000.000:0000";

void search(string phoneNumber) {
	string command;
	/*
	//kill process
	command = "adb"+device+" shell am force-stop org.potato.messenger";
	system(command.c_str());
	//Start Telegram
	command = "adb"+device+" shell am start -n org.potato.messenger/org.potato.ui.LaunchActivity";
	system(command.c_str());
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	*/
	command = "adb" + device + " logcat -c -b main -b events -b radio -b system";
	system(command.c_str());
	command = "adb"+device+" shell input text " + phoneNumber;
	system(command.c_str());
	command = "adb"+device+" shell input tap 000 0000";
	system(command.c_str());
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
}

void pullReslut() {
	string command = "adb"+device+" logcat -d crackPotato:D *:S *:W *:E *:F> potato.txt";
	system(command.c_str());
	command = "adb" + device + " logcat -c -b main -b events -b radio -b system";
	system(command.c_str());
	/*
	command = "adb"+device+" shell am force-stop org.potato.messenger";
	system(command.c_str());
	*/
}

int main(int argc, char* argv[])
{
	if (argc != 0) {
		cout << "Please Only Enter Phone Number" << endl;
		return 0;
	}
	else {
		string phoneNumber = argv[0];
		search(phoneNumber);
		pullReslut();
	}
	return 0;
}


