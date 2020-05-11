#include <iostream>
#include <windows.h>

using namespace std;

static string device = " -s 000.000.000.000:0000";

void search(string phoneNumber) {
	string command;
	/*
	//force close
	command = "adb"+device+" shell am force-stop com.whatsapp";
	system(command.c_str());
	//Start WhatsApp
	command = "adb"+device+" shell am start -n com.whatsapp/com.whatsapp.Main";
	system(command.c_str());

	//Sleep(0000);

	//Tap Contact Icon
	command = "adb"+device+" shell input tap 000 0000";
	system(command.c_str());
	*/

	//Tap Add Friend Icon
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//open miniKeyboard
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//close miniKeyBoard
	command = "adb"+device+" shell input keyevent 0";
	system(command.c_str());
	//tap phone number
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//add phone number
	command = "adb"+device+" shell input text +00" + phoneNumber;
	system(command.c_str());
	//save
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//back
	command = "adb"+device+" shell input keyevent 0";
	system(command.c_str());
	//tap pic
	command = "adb"+device+" shell input tap 00 000";
	system(command.c_str());
	//tap info
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//back(Add New)
	command = "adb" + device + " shell input keyevent 0";
	system(command.c_str());
	//clear contacts(New Function)
	command = "adb" + device + " shell pm clear com.android.providers.contacts";
	system(command.c_str());

	/*
	//tap sangedian
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//view in address book
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//tap sangedian
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//tap delete
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//tap delete
	command = "adb"+device+" shell input tap 000 000";
	system(command.c_str());
	//back
	command = "adb"+device+" shell input keyevent 0";
	system(command.c_str());
	//back
	command = "adb"+device+" shell input keyevent 0";
	system(command.c_str());
	*/
}

void pullReslut() {
	string command;
	
	command = "adb"+device+" logcat -d crackWhatsApp:D *:S *:W *:E *:F> whatsapp.txt";
	system(command.c_str());
	command = "adb" + device + " logcat -c -b main -b events -b radio -b system";
	system(command.c_str());

	/*
	command = "adb"+device+" shell am force-stop com.whatsapp";
	system(command.c_str());
	command = "adb"+device+" shell pm clear com.android.providers.contacts";
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

