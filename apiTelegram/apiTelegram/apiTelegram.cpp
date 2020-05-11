#include <iostream>
#include <windows.h>

using namespace std;

static string device = " -s 000.000.000.000:0000";
//static string device = " -s 000.000.000.000:0000";

void search(string phoneNumber) {
	string command;
	/*
	//kill process
	command = "adb"+device+" shell am force-stop org.telegram.messenger";
	system(command.c_str());
	//Start Telegram
	command = "adb"+device+" shell am start -n org.telegram.messenger/org.telegram.ui.LaunchActivity";
	system(command.c_str());
	
	//Sleep(0000);

	//Tap Write Icon
	command = "adb"+device+" shell input tap 000 0000";
	system(command.c_str());
	*/
	//Tap Add Friend Icon
	command = "adb"+device+" shell input tap 000 0000";
	system(command.c_str());
	//first name is single_search
	command = "adb"+device+" shell input text single_search";
	system(command.c_str());
	//tap country code
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//Del
	command = "adb"+device+" shell input keyevent 00";
	system(command.c_str());
	//input country code
	command = "adb"+device+" shell input text 00";
	system(command.c_str());
	//tab
	command = "adb"+device+" shell input keyevent 00";
	system(command.c_str());
	//input phone number
	command = "adb"+device+" shell input text " + phoneNumber;
	system(command.c_str());
	//tap add
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());

	//New Function
	command = "adb" + device + " shell input tap 000 000";
	system(command.c_str());
	command = "adb" + device + " shell input keyevent 0";
	system(command.c_str());
	command = "adb" + device + " shell input keyevent 0";
	system(command.c_str());

	/*
	//return and kill process
	command = "adb"+device+" shell am force-stop org.telegram.messenger";
	system(command.c_str());
	//restart
	command = "adb"+device+" shell am start -n org.telegram.messenger/org.telegram.ui.LaunchActivity";
	system(command.c_str());
	//Sleep(0000);
	//Tap Add Friend Icon
	command = "adb" + device + " shell input tap 000 0000";
	system(command.c_str());
	//
	command = "adb" + device + " shell input tap 000 000";
	system(command.c_str());
	//
	command = "adb" + device + " shell input tap 000 000";
	system(command.c_str());
	//after that show everything
	//del friend
	//tap 'more'(or search icon)
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//tap del(or blank)
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//tap confirm(or key 'o')
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	*/

}

void pullReslut() {
	string command = "adb"+device+" logcat -d crackTelegram:D *:S *:W *:E *:F> telegram.txt";
	system(command.c_str());
	/*
	command = "adb"+device+" shell am force-stop org.telegram.messenger";
	system(command.c_str());
	*/
	command = "adb"+device+" shell pm clear com.android.providers.contacts";
	system(command.c_str());
	command = "adb" + device + " logcat -c -b main -b events -b radio -b system";
	system(command.c_str());
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

