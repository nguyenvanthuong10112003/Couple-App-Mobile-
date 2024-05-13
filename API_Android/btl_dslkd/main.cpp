#include <bits/stdc++.h>
using namespace std;
const string fileDataNv = "data-nhanvien.txt";
const string fileDataSp = "data-sanpham.txt";
bool isNumber(string number) {
    if (number.length() == 0)
        return false;
    for (int i = 0; i < number.length(); i++)
        if (number[i] > '9' || number[i] < '0')
            return false;
    return true;
}
int string_to_int(string str) {
    int number = 0, i;
    bool isSoDuong = true;
    if (str[0] == '-')
        isSoDuong = false;
    if (isSoDuong)
        i = 0;
    else
        i = 1;
    for (; i < str.length(); i++)
        number = number*10 + (static_cast<int>(str[i]) - 48);
    return number;
}
class NhanVien {
	private:
		int ma;
		string hoVaTen;
		bool gioiTinh;
		string chucVu;
		string loaiHinhLamViec;
	public:
		NhanVien() {
		}
		NhanVien(int ma, string hoVaTen, bool gioiTinh,
			string chucVu, string loaiHinhLamViec) {
			this->ma = ma;
			this->hoVaTen = hoVaTen;
			this->gioiTinh = gioiTinh;
			this->chucVu = chucVu;
			this->loaiHinhLamViec = loaiHinhLamViec;
		}
		void setMa(int ma) {
			this->ma = ma;
		}
		void setHoVaTen(string hoVaTen) {
			this->hoVaTen = hoVaTen;
		}
		void setGioiTinh(bool gioiTinh) {
			this->gioiTinh = gioiTinh;
		}
		void setChucVu(string chucVu) {
			this->chucVu = chucVu;
		}
		void setLoaiHinhLamViec(string loaiHinhLamViec) {
			this->loaiHinhLamViec = loaiHinhLamViec;
		}
		int getMa() {
			return ma;
		}
		bool getGioiTinh() {
			return gioiTinh;
		}
		string getHoVaTen() {
			return hoVaTen;
		}
		string getChucVu() {
			return chucVu;
		}
		string getLoaiHinhLamViec() {
			return loaiHinhLamViec;
		}
        void display() {
            cout << "Ma: " << ma << ";Ho va ten: " << hoVaTen << ";Gioi tinh: " << (gioiTinh ? "Nam" : "Nu") << ";Chuc vu: " << chucVu << ";Loai hinh lam viec: " << loaiHinhLamViec << endl;
		}
};
class SanPham {
	private:
		int ma;
		string ten;
		double gia;
		string cachBaoQuan;
	public:
		SanPham() {
		}
		SanPham(int ma, string ten, double gia, string cachBaoQuan) {
			this->ma = ma;
			this->ten = ten;
			this->gia = gia;
			this->cachBaoQuan = cachBaoQuan;
		}
		void setMa(int ma) {
			this->ma = ma;
		}
		void setTen(string ten) {
			this->ten = ten;
		}
		void setGia(double gia) {
			this->gia = gia;
		}
		void setCachBaoQuan(string cachBaoQuan) {
			this->cachBaoQuan = cachBaoQuan;
		}
		int getMa() {
			return ma;
		}
		string getTen() {
			return ten;
		}
		double getGia() {
			return gia;
		}
		string getCachBaoQuan() {
			return cachBaoQuan;
		}
		void display() {
            cout << "Ma: " << ma << ";Ten: " << ten << ";Gia: " << gia << ";Cach bao quan: " << cachBaoQuan << endl;
		}
};
template <typename T>
class Node {
	protected:
		T data;
		Node<T>* next;
	public:
		Node() {
			next = NULL;
		}
		Node(T data) {
			this->data = data;
			this->next = NULL;
		}
		Node(T data, Node<T>* next) {
			this->data = data;
			this->next = next;
		}
		void setData(T data) {
			this->data = data;
		}
		void setNext(Node<T>* next) {
			this->next = next;
		}
		T getData() {
			return data;
		}
		Node<T>* getNext() {
			return next;
		}
};
template <typename T>
class LinkedList {
	public:
		Node<T>* head;
		Node<T>* tail;
		LinkedList() {
			head = NULL;
			tail = NULL;
		}
		void themVaoDau(Node<T>* node) {
			if (head == NULL) {
				head = node;
				tail = node;
				return;
			}
			node->setNext(head);
			head = node;
		}
		void themVaoCuoi(Node<T>* node) {
			if (head == NULL) {
				head = node;
				tail = node;
				return;
			}
			tail->setNext(node);
			tail = node;
		}
		void themVaoSauNodeX(Node<T>* them, Node<T>* x) {
			if (them == NULL)
				return;
			if (x == NULL)
				return;
			them->setNext(x->getNext());
			x->setNext(them);
			if (tail == x)
				tail = them;
		}
		void xoaPhanTuDau() {
			if (head == NULL)
				return;
			Node<T>* node = head;
			head = head->getNext();
			if (head == NULL)
				tail = NULL;
			delete node;
		}
		void xoaPhanTuCuoi() {
			if (head == NULL)
				return;
			if (head == tail) {
				xoaPhanTuDau();
				return;
			}
			Node<T>* node = head;
			while(node->getNext()->getNext() != NULL) {
				node = node->getNext();
			}
			delete node->getNext();
			node->setNext(NULL);
		}
		void xoaNodeX(Node<T>* x) {
			if (head == NULL)
				return;
			if (x == head) {
				xoaPhanTuDau();
				return;
			}
			if (x == tail) {
				xoaPhanTuCuoi();
				return;
			}
			Node<T>* node = head;
			while(node->getNext() != NULL) {
				if (node->getNext() == x) {
					node->setNext(x->getNext());
					x->setNext(NULL);
					delete x;
					return;
				}
				node = node->getNext();
			}
		}
};
class DSLKD_NhanVien : LinkedList<NhanVien> {
	public:
		Node<NhanVien>* timNhanVien(int ma) {
			Node<NhanVien>* node = head;
			while(node != NULL) {
				if (node->getData().getMa() == ma)
					return node;
				node = node->getNext();
			}
			return NULL;
		}
		void xoaNhanVien(int ma) {
			Node<NhanVien>* node = head;
			while(node != NULL) {
				if (node->getData().getMa() == ma) {
					xoaNodeX(node);
					cout << "Xoa thanh cong" << endl;
					return;
				}
				node = node->getNext();
			}
			cout << "Khong ton tai nhan vien co ma " << ma << endl;
		}
        void themVaoCuoi(Node<NhanVien>* node) {
            LinkedList<NhanVien>::themVaoCuoi(node);
        }
        void themVaoDau(Node<NhanVien>* node) {
            LinkedList<NhanVien>::themVaoDau(node);
		}
		void themVaoSauNodeX(Node<NhanVien>* them, Node<NhanVien>* x) {
            LinkedList<NhanVien>::themVaoSauNodeX(them, x);
		}
		void xoaNodeX(Node<NhanVien>* x) {
            LinkedList<NhanVien>::xoaNodeX(x);
		}
        int soNhanVienLamPartTime() {
            Node<NhanVien>* node = head;
            int dem = 0;
            while(node != NULL) {
                if (node->getData().getLoaiHinhLamViec() == "part time")
                    dem++;
                node = node->getNext();
            }
            return dem;
		}
		void xoaPhanTuCuoi() {
            LinkedList<NhanVien>::xoaPhanTuCuoi();
        }
		DSLKD_NhanVien getListNhanVienNu() {
            DSLKD_NhanVien ds = DSLKD_NhanVien();
            Node<NhanVien>* node = head;
            while(node != NULL) {
                if (!node->getData().getGioiTinh())
                {
                    Node<NhanVien>* newNode = new Node<NhanVien>(node->getData());
                    ds.themVaoCuoi(newNode);
                }
                node = node->getNext();
            }
            return ds;
		}
		static void inDanhSach(DSLKD_NhanVien dsnv) {
			if (dsnv.head == NULL) {
				cout << "Danh sach rong" << endl;
				return;
			}
			cout << "--------------------------------------------------------" << endl;
			cout << "Danh sach nhan vien" << endl;
			cout << "Ma\t|Ho va ten\t|Gioi tinh\t|Chuc vu\t|Loai hinh lam viec" << endl;
			Node<NhanVien>* node = dsnv.head;
			while(node != NULL) {
				NhanVien nv = node->getData();
				cout << nv.getMa() << "\t|";
				cout << nv.getHoVaTen() << "\t|";
				cout << (nv.getGioiTinh() ? "Nam" : "Nu") << "\t|";
				cout << nv.getChucVu() << "\t|";
				cout << nv.getLoaiHinhLamViec() << endl;
				node = node->getNext();
			}
			cout << "--------------------------------------------------------" << endl;
		}
        static void docFileNhanVien(DSLKD_NhanVien &dsnv) {
            dsnv = DSLKD_NhanVien();
            ifstream ifs(fileDataNv);
            if(!ifs){
                cerr << "Error: file not opened." << endl;
                return;
            }
            string line;
            while(getline(ifs, line)){
                string data[5];
                int index = 0;
                data[0] = "";
                for (int i = 0; i < line.length(); i++) {
                    if (line[i] == '|') {
                        index++;
                        data[index] = "";
                    }
                    else
                        data[index] += line[i];
                }
                NhanVien nv = NhanVien();
                nv.setMa(string_to_int(data[0]));
                nv.setHoVaTen(data[1]);
                nv.setGioiTinh(data[2] == "1");
                nv.setChucVu(data[3]);
                nv.setLoaiHinhLamViec(data[4]);
                Node<NhanVien>* node = new Node<NhanVien>(nv);
                dsnv.themVaoCuoi(node);
            }
            ifs.close();
        }
        static void ghiFileNhanVien(DSLKD_NhanVien dsnv) {
            ofstream outfile;
            outfile.open(fileDataNv);
            Node<NhanVien>* node = dsnv.head;
            while(node != NULL)
            {
                NhanVien nv = node->getData();
                outfile << nv.getMa() << "|";
                outfile << nv.getHoVaTen() << "|";
                outfile << (nv.getGioiTinh() ? 1 : 0) << "|";
                outfile << nv.getChucVu() << "|";
                outfile << nv.getLoaiHinhLamViec() << endl;
                node = node->getNext();
            }
            outfile.close();
        }
};
class DSLKD_SanPham : LinkedList<SanPham> {
	public:
		Node<SanPham>* timSanPham(int ma) {
			Node<SanPham>* node = head;
			while(node != NULL) {
				if (node->getData().getMa() == ma)
					return node;
				node = node->getNext();
			}
			return NULL;
		}
		void xoaSanPham(int ma) {
			Node<SanPham>* node = head;
			while(node != NULL) {
				if (node->getData().getMa() == ma) {
					xoaNodeX(node);
					cout << "Xoa thanh cong" << endl;
					return;
				}
				node = node->getNext();
			}
			cout << "Khong ton tai san pham co ma " << ma << endl;
		}
        void themVaoCuoi(Node<SanPham>* node) {
            LinkedList<SanPham>::themVaoCuoi(node);
        }
        void themVaoDau(Node<SanPham>* node) {
            LinkedList<SanPham>::themVaoDau(node);
		}
		void themVaoSauNodeX(Node<SanPham>* them, Node<SanPham>* x) {
            LinkedList<SanPham>::themVaoSauNodeX(them, x);
		}
		void xoaNodeX(Node<SanPham>* x) {
            LinkedList<SanPham>::xoaNodeX(x);
		}
		int demSanPhamCoGiaTren50000() {
            Node<SanPham>* node = head;
            int dem = 0;
			while(node != NULL) {
                if (node->getData().getGia() > 50000)
                    dem++;
                node = node->getNext();
			}
			return dem;
		}
		static void inDanhSach(DSLKD_SanPham dssp) {
			if (dssp.head == NULL) {
				cout << "Danh sach rong" << endl;
				return;
			}
			cout << "--------------------------------------------------------" << endl;
			cout << "Danh sach san pham" << endl;
			cout << "Ma\t|Ten san pham\t|Gia tien\t|Cach bao quan" << endl;
			Node<SanPham>* node = dssp.head;
			while(node != NULL) {
				SanPham sp = node->getData();
				cout << sp.getMa() << "\t|";
				cout << sp.getTen() << "\t|";
				cout << sp.getGia() << "\t|";
				cout << sp.getCachBaoQuan() << endl;
                node = node->getNext();
			}
			cout << "--------------------------------------------------------" << endl;
		}
		DSLKD_SanPham between(double x, double y) {
		    DSLKD_SanPham ds = DSLKD_SanPham();
            Node<SanPham>* node = head;
			while(node != NULL) {
                if (node->getData().getGia() >= x && node->getData().getGia() <= y)
                {
                    Node<SanPham>* newNode = new Node<SanPham>(node->getData());
                    ds.themVaoCuoi(newNode);
                }
                node = node->getNext();
			}
			return ds;
		}
        DSLKD_SanPham cachBaoQuanZ(string z) {
		    DSLKD_SanPham ds = DSLKD_SanPham();
            Node<SanPham>* node = head;
			while(node != NULL) {
                if (node->getData().getCachBaoQuan() == z)
                {
                    Node<SanPham>* newNode = new Node<SanPham>(node->getData());
                    ds.themVaoCuoi(newNode);
                }
                node = node->getNext();
			}
			return ds;
		}
		void sapXep() {
		    while(true) {
                bool check = true;
                Node<SanPham>* node = head;
                while(node->getNext() != NULL) {
                    if (node->getData().getGia() > node->getNext()->getData().getGia())
                    {
                        SanPham temp = node->getData();
                        node->setData(node->getNext()->getData());
                        node->getNext()->setData(temp);
                        check = false;
                    }
                    node = node->getNext();
                }
                if (check)
                    break;
		    }
		}
        static void docFileSanPham(DSLKD_SanPham &dssp) {
            dssp = DSLKD_SanPham();
            ifstream ifs(fileDataSp);
            if(!ifs){
                cerr << "Error: file not opened." << endl;
                return;
            }
            string line;
            while(getline(ifs, line)){
                string data[5];
                int index = 0;
                data[0] = "";
                for (int i = 0; i < line.length(); i++) {
                    if (line[i] == '|') {
                        index++;
                        data[index] = "";
                    }
                    else
                        data[index] += line[i];
                }
                SanPham sp = SanPham();
                sp.setMa(string_to_int(data[0]));
                sp.setTen(data[1]);
                sp.setGia(stod(data[2]));
                sp.setCachBaoQuan(data[3]);
                Node<SanPham>* node = new Node<SanPham>(sp);
                dssp.themVaoCuoi(node);
            }
            ifs.close();
        }
        static void ghiFileSanPham(DSLKD_SanPham dssp) {
            ofstream outfile;
            outfile.open(fileDataSp);
            Node<SanPham>* node = dssp.head;
            while(node != NULL)
            {
                SanPham nv = node->getData();
                outfile << nv.getMa() << "|";
                outfile << nv.getTen() << "|";
                outfile << nv.getGia() << "|";
                outfile << nv.getCachBaoQuan() << endl;
                node = node->getNext();
            }
            outfile.close();
        }
};
void displayListFunction() {
	cout << "CHUONG TRINH QUAN LY" << endl;
	cout << "1. Quan ly nhan vien" << endl;
	cout << "2. Quan ly san pham" << endl;
	cout << "0. Thoat chuong trinh" << endl;
	cout << "Lua chon cua ban: ";
}
void displayListFunctionNhanVien() {
	cout << "Quan Ly Nhan Vien" << endl;
	cout << "1. Nhap danh sach nhan vien" << endl;
	cout << "2. In danh sach nhan vien" << endl;
	cout << "3. Tim kiem nhan vien" << endl;
	cout << "4. Sua thong tin nhan vien" << endl;
	cout << "5. Them nhan vien vao dau danh sach" << endl;
	cout << "6. Xoa nhan vien vao cuoi danh sach" << endl;
	cout << "7. Dem nhan vien lam viec part time" << endl;
	cout << "8. In ra cac nhan vien nu" << endl;
	cout << "9. Doc file" << endl;
	cout << "10. Ghi file" << endl;
	cout << "0. Tro ve" << endl;
	cout << "Lua chon cua ban: ";
}
void displayListFunctionSanPham() {
	cout << "Quan Ly San Pham" << endl;
	cout << "1. Nhap danh sach san pham" << endl;
	cout << "2. In danh sach san pham" << endl;
	cout << "3. Sua thong tin san pham" << endl;
	cout << "4. Them san pham vao cuoi danh sach" << endl;
	cout << "5. Xoa san pham" << endl;
	cout << "6. So san pham co don gia tren 50000d" << endl;
	cout << "7. In ra san pham co gia trong khoang x den y" << endl;
	cout << "8. Sap xep danh sach tang dan theo don gia" << endl;
	cout << "9. Xuat thong tin san pham co cach bao quan Z" << endl;
	cout << "10. Doc file" << endl;
	cout << "11. Ghi file" << endl;
	cout << "0. Tro ve" << endl;
	cout << "Lua chon cua ban: ";
}
NhanVien nhapNhanVien(DSLKD_NhanVien dsnv) {
    int ma;
    string hoVaTen, chucVu, loaiHinhLamViec, gioiTinh;
    while(true) {
        cout << "	+ Nhap ma: ";
        cin >> ma;
        if (dsnv.timNhanVien(ma) == NULL)
            break;
        else
            cout << "	! Nhan vien co ma " << ma << " da ton tai" << endl;
    }
    cout << "	+ Nhap ho va ten: ";
    cin.ignore();
    getline(cin, hoVaTen);
    while(true) {
        cout << "	+ Nhap gioi tinh (nam|nu): ";
        getline(cin, gioiTinh);
        if (gioiTinh == "nam" || gioiTinh == "nu")
            break;
        else
            cout << "	! Gioi tinh khong hop le" << endl;
    }
    cout << "	+ Nhap chuc vu: ";
    getline(cin, chucVu);
    cout << "	+ Nhap loai hinh lam viec: ";
    getline(cin, loaiHinhLamViec);
    NhanVien nv = NhanVien();
    nv.setMa(ma);
    nv.setHoVaTen(hoVaTen);
    nv.setChucVu(chucVu);
    nv.setGioiTinh(gioiTinh == "nam" ? true : false);
    nv.setLoaiHinhLamViec(loaiHinhLamViec);
    return nv;
}
void nhapDanhSachNhanVien(DSLKD_NhanVien &dsnv) {
	dsnv = DSLKD_NhanVien();
	cout << "Nhap so luong nhan vien: ";
	int n;
	cin >> n;
	for (int i = 0; i < n; i++) {
        cout << "Nhan vien thu " << i + 1 << ":" << endl;
        NhanVien nv = nhapNhanVien(dsnv);
		Node<NhanVien>* node = new Node<NhanVien>(nv);
		dsnv.themVaoCuoi(node);
	}
}
SanPham nhapSanPham(DSLKD_SanPham dssp) {
    int ma;
    string ten, cachBaoQuan;
    string gia;
    while(true) {
        cout << "	+ Nhap ma: ";
        cin >> ma;
        if (dssp.timSanPham(ma) == NULL)
            break;
        else
            cout << "	! San pham co ma " << ma << " da ton tai" << endl;
    }
    cout << "	+ Nhap ten san pham: ";
    cin.ignore();
    getline(cin, ten);
    cout << "	+ Nhap gia tien: ";
    getline(cin, gia);
    cout << "	+ Nhap cach bao quan: ";
    getline(cin, cachBaoQuan);
    SanPham sp = SanPham();
    sp.setMa(ma);
    sp.setTen(ten);
    sp.setGia(stod(gia));
    sp.setCachBaoQuan(cachBaoQuan);
    return sp;
}
void nhapDanhSachSanPham(DSLKD_SanPham &dssp) {
	dssp = DSLKD_SanPham();
	cout << "Nhap so luong san pham: ";
	int n;
	cin >> n;
	for (int i = 0; i < n; i++) {
        cout << "San pham thu " << i + 1 << ":" << endl;
        SanPham sp = nhapSanPham(dssp);
		Node<SanPham>* node = new Node<SanPham>(sp);
		dssp.themVaoCuoi(node);
	}
}
void timKiemNhanVien(DSLKD_NhanVien dsnv) {
    cout << "Nhap ma nhan vien: ";
    int ma;
    cin >> ma;
    Node<NhanVien>* node = dsnv.timNhanVien(ma);
    if (node == NULL)
        cout << "Nhan vien co ma " << ma << " khong ton tai" << endl;
    else
        node->getData().display();
}
void suaThongTinNhanVien(DSLKD_NhanVien dsnv) {
    cout << "Nhap ma nhan vien: " << endl;
    int ma;
    cin >> ma;
    Node<NhanVien>* node = dsnv.timNhanVien(ma);
    if (node == NULL)
        cout << "Nhan vien co ma " << ma << " khong ton tai" << endl;
    else {
        cout << "Nhap thong tin sua: " << endl;
        cout << "   + Nhap ho va ten: ";
        cin.ignore();
        string hoVaTen, gioiTinh, chucVu, loaiHinhLamViec;
        getline(cin, hoVaTen);
        while(true) {
            cout << "   + Nhap gioi tinh (nam|nu): ";
            getline(cin, gioiTinh);
            if (gioiTinh == "nam" || gioiTinh == "nu")
                break;
            else
                cout << "   ! Gioi tinh khong hop le";
        }
        cout << "   + Nhap chuc vu: ";
        getline(cin, chucVu);
        cout << "   + Nhap loai hinh lam viec: ";
        getline(cin, loaiHinhLamViec);
        NhanVien nv = node->getData();
        nv.setHoVaTen(hoVaTen);
        nv.setGioiTinh(gioiTinh == "nam");
        nv.setChucVu(chucVu);
        nv.setLoaiHinhLamViec(loaiHinhLamViec);
        node->setData(nv);
    }
}
void themNhanVienVaoDauDS(DSLKD_NhanVien &dsnv) {
    NhanVien nv = nhapNhanVien(dsnv);
    Node<NhanVien>* newNode = new Node<NhanVien>(nv);
    dsnv.themVaoDau(newNode);
}
void suaThongTinSanPham(DSLKD_SanPham dssp) {
    cout << "Nhap ma san pham: ";
    int ma;
    cin >> ma;
    Node<SanPham>* node = dssp.timSanPham(ma);
    if (node == NULL)
        cout << "San pham co ma " << ma << " khong ton tai" << endl;
    else {
        cout << "Nhap thong tin sua: " << endl;
        cout << "   + Nhap ten san pham: ";
        cin.ignore();
        string ten, cachBaoQuan;
        string gia;
        getline(cin, ten);
        cout << "   + Nhap gia: ";
        getline(cin, gia);
        cout << "   + Nhap cach bao quan: ";
        getline(cin, cachBaoQuan);
        SanPham sp = node->getData();
        sp.setTen(ten);
        sp.setGia(stod(gia));
        sp.setCachBaoQuan(cachBaoQuan);
        node->setData(sp);
    }
}
void themSanPhamVaoCuoiDS(DSLKD_SanPham &dssp) {
    SanPham sp = nhapSanPham(dssp);
    Node<SanPham>* newNode = new Node<SanPham>(sp);
    dssp.themVaoCuoi(newNode);
}
void xoaSanPham(DSLKD_SanPham &dssp) {
    int ma;
    cout << "Nhap ma san pham: ";
    cin >> ma;
    Node<SanPham>* node = dssp.timSanPham(ma);
    if (node == NULL)
        cout << "San pham co ma " << ma << " khong ton tai" << endl;
    else
        dssp.xoaNodeX(node);
}
void cacSanPhamCoGiaKhoangXY(DSLKD_SanPham &dssp) {
    cout << "Nhap khoang gia x: ";
    double x,y;
    cin >> x;
    cout << "Nhap khoang gia y: ";
    cin >> y;
    if (x > y)
    {
        double temp = x;
        x = y;
        y = temp;
    }
    DSLKD_SanPham::inDanhSach(dssp.between(x, y));
}
void cacSanPhamCoCachBaoQuanZ(DSLKD_SanPham &dssp) {
    string z;
    cout << "Nhap cach bao quan: ";
    cin.ignore();
    getline(cin, z);
    DSLKD_SanPham::inDanhSach(dssp.cachBaoQuanZ(z));
}
int main() {
	DSLKD_NhanVien dsnv = DSLKD_NhanVien();
	DSLKD_SanPham dssp = DSLKD_SanPham();
	int option;
	while(true) {
		displayListFunction();
		cin >> option;
		switch(option) {
			case 0: {
				cout << "Chuong trinh ket thuc" << endl;
				return 0;
			}
			case 1: {
				while(option != 0) {
					displayListFunctionNhanVien();
					cin >> option;
					switch(option) {
                        case 1: nhapDanhSachNhanVien(dsnv);
                            break;
                        case 2: DSLKD_NhanVien::inDanhSach(dsnv);
                            break;
                        case 3: timKiemNhanVien(dsnv);
                            break;
                        case 4: suaThongTinNhanVien(dsnv);
                            break;
                        case 5: themNhanVienVaoDauDS(dsnv);
                            break;
                        case 6: dsnv.xoaPhanTuCuoi();
                            break;
                        case 7: cout << "Co " << dsnv.soNhanVienLamPartTime() << " nhan vien lam part time" << endl;
                            break;
                        case 8: DSLKD_NhanVien::inDanhSach(dsnv.getListNhanVienNu());
                            break;
                        case 9: DSLKD_NhanVien::docFileNhanVien(dsnv);
                            break;
                        case 10: DSLKD_NhanVien::ghiFileNhanVien(dsnv);
                            break;
					}
				}
				break;
			}
			case 2: {
				while(option != 0) {
					displayListFunctionSanPham();
					cin >> option;
					switch(option) {
                        case 1: nhapDanhSachSanPham(dssp);
                            break;
                        case 2: DSLKD_SanPham::inDanhSach(dssp);
                            break;
                        case 3: suaThongTinSanPham(dssp);
                            break;
                        case 4: themSanPhamVaoCuoiDS(dssp);
                            break;
                        case 5: xoaSanPham(dssp);
                            break;
                        case 6: cout << "Co " << dssp.demSanPhamCoGiaTren50000() << " san pham co gia tren 50000d" << endl;
                            break;
                        case 7: cacSanPhamCoGiaKhoangXY(dssp);
                            break;
                        case 8: dssp.sapXep();
                            break;
                        case 9: cacSanPhamCoCachBaoQuanZ(dssp);
                            break;
                        case 10: DSLKD_SanPham::docFileSanPham(dssp);
                            break;
                        case 11: DSLKD_SanPham::ghiFileSanPham(dssp);
                            break;
					}
				}
				break;
			}
			default:
				cout << "Lua chon khong hop le" << endl;
		}
	}
}
