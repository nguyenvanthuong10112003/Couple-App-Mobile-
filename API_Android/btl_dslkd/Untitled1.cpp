#include <iostream>
using namespace std;
int main() {
	cout << "Nhap chuoi: ";
	string chuoi;
	getline(cin, chuoi);
	while(chuoi[0] == ' ' && chuoi.length() > 0)
		chuoi = chuoi.substr(1, chuoi.length());
	while(chuoi.length() > 0 && chuoi[chuoi.length() - 1] == ' ')
		chuoi = chuoi.substr(0, chuoi.length() - 1);
	int dem = 1;
	for (int i = 1; i < chuoi.length(); i++)
		if (chuoi[i] == ' ' && chuoi[i-1] != ' ')
			dem++;
	if (chuoi.length() == 0)
		dem = 0;
	cout << "Chuoi co " << dem << " phan tu";
	return 0;
}
