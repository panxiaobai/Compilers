#include<iostream>
#include<string>
using namespace std;

void main() {
	string str = "int23+fwo 23.53 + 434 && /* fwong   */ jfwoe ";
	cout << str<<endl;
	int i=0;

start:
	if (i >= str.length()) {
		goto end;
	}
	else if (str[i] == ' ') {
		i++;
		goto start;
	}
	else if (str[i] >= 'a'&&str[i] <= 'z'|| str[i] >= 'A'&&str[i] <= 'Z') {
		i++;
		goto state_identifier;
	}
	else if (str[i] >= '0'&&str[i] <= '9') {
		i++;
		goto state_number;
	}
	else if (str[i] == '+' || str[i] == '-' || str[i] == '=' || str[i] == '*'
		|| str[i] == '&' || str[i] == '|') {
		i++;
		goto state_operator;
	}
	else if (str[i] == '/') {
		i++;
		goto state_annotate;
	}

state_identifier:
	if (str[i] == ' ') {
		cout << "±êÊ¶·û ";
		i++;
		goto start;
	}
	else if (str[i] >= 'a'&&str[i] <= 'z' || str[i] >= 'A'&&str[i] <= 'Z'|| str[i] >= '0'&&str[i] <= '9') {
		i++;
		goto state_identifier;
	}
	else {
		goto state_error;
	}

state_number:
	if (str[i] == ' ') {
		cout << "Êý×Ö ";
		i++;
		goto start;
	}
	else if (str[i] >= '0'&&str[i] <= '9'||str[i]=='.') {
		i++;
		goto state_number;
	}
	else {
		goto state_error;
	}


state_operator:
	if (str[i] == ' ') {
		cout << "ÔËËã·û ";
		i++;
		goto start;
	}
	else if (str[i] == '+' || str[i] == '-' || str[i] == '=' || str[i] == '*' 
		|| str[i] == '&' || str[i] == '|') {
		i++;
		goto state_operator;
	}
	else {
		goto state_error;
	}

state_annotate:
	if (str[i] == '/') {
		cout << "×¢ÊÍ ";
		goto end;
	}
	else if (str[i] == '*') {
		i++;
		while (str[i] != '*'&&i<str.length()) {
			i++;
		}
		if (i >= str.length()) {
			goto state_error;
		}
		else {
			i++;
			if (str[i] == '/') {
				cout << "×¢ÊÍ ";
				i++;
				goto start;
			}
			else {
				goto state_error;
			}
		}
	}
	if (str[i] == ' ') {
		cout << "ÔËËã·û ";
		i++;
		goto start;
	}
	else {
		goto state_error;
	}

state_error:
	cout << "´íÎó ";
	while (str[i] != ' ') {
		i++;
	}
	goto start;

end:
	cout << "½áÊø" << endl;
}