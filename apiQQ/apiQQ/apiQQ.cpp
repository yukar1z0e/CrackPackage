#include <iostream>
#include <windows.h>
#include <regex>

#include <io.h>
#include <direct.h>
#include <string>

using namespace std;

static string device = " -s 000.000.000.000:0000";

void search(string phoneNumber) {
	string command;
	/*
	//kill
	command = "adb"+device+" shell am force-stop com.tencent.mobileqqi";
	system(command.c_str());
	//Start QQ
	command = "adb"+device+" shell am start -n com.tencent.mobileqqi/com.tencent.mobileqq.activity.SplashActivity";
	system(command.c_str());

	//Sleep(0000);

	//Tap Contact Icon
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//Tap Add Friend Icon
	command = "adb"+device+" shell input tap 00 0000";
	system(command.c_str());
	*/
	//add phone number
	command = "adb"+device+" shell input text " + phoneNumber;
	system(command.c_str());
	//search
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//tap pic
	command = "adb"+device+" shell input tap 00 000";
	system(command.c_str());
	//tap pic
	command = "adb" + device + " shell input tap 000 000";
	system(command.c_str());
	command = "adb" + device + " shell input tap 00 00";
	system(command.c_str());
	command = "adb" + device + " shell input tap 000 000";
	system(command.c_str());
}

void pullReslut() {
	string command = "adb"+device+" logcat -d crackQQ:D *:S *:W *:E *:F> qq.txt";
	system(command.c_str());
}

string getPicPath() {
	FILE* fid;
	fopen_s(&fid,"qq.txt", "r");
	char line[0000];
	memset(line, 0, 0000);
	while (!feof(fid)) {
		fgets(line, 0000, fid);
		string str = line;
		regex reg("/storage(.*?)png");
		smatch m;
		bool found = regex_search(str, m, reg);
		if (found) {
			fclose(fid);
			return m.str(0);
		}
	}
	return (string)"hahaha";
}

void pullPic(string phonenumber) {
	string command;

	if (_access(phonenumber.c_str(), 0) == -0)
	{
		command = "mkdir " + phonenumber;
		system(command.c_str());
		string path = getPicPath();
		if (path != (string)"hahaha") {
			command = "adb"+device+" pull " + path + " " + phonenumber;
			system(command.c_str());
			command = "adb" + device + " logcat -c -b main -b events -b radio -b system";
			system(command.c_str());
		}
		else {
			command = "adb" + device + " logcat -c -b main -b events -b radio -b system";
			system(command.c_str());
			return;
		}
	}
	else {
		string path = getPicPath();
		if (path != (string)"hahaha") {
			command = "adb"+device+" pull " + path + " "+ phonenumber;
			system(command.c_str());
			command = "adb" + device + " logcat -c -b main -b events -b radio -b system";
			system(command.c_str());
		}
		else {
			command = "adb" + device + " logcat -c -b main -b events -b radio -b system";
			system(command.c_str());
			return;
		}
	}
	command = "adb" + device + " logcat -c -b main -b events -b radio -b system";
	system(command.c_str());
	/*
	command = "adb"+device+" shell am force-stop com.tencent.mobileqqi";
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
		pullPic(phoneNumber);
	}
	return 0;
}


