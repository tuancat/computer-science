import sys
from PyQt5.QtWidgets import QApplication, QWidget, QPushButton, QMessageBox, QDialog, QVBoxLayout, QGroupBox, \
    QFormLayout, QLabel, QLineEdit, QComboBox, QSpinBox, QDialogButtonBox
from PyQt5.QtGui import QIcon
from PyQt5.QtCore import pyqtSlot


class InputDialog(QDialog):
    so_cu_tri = 0;
    so_PS = 0;
    so_ung_vien = 0;

    def __init__(self):
        super().__init__();

        self.formGroupInputBox = QGroupBox("Form nhập liệu");
        self.setWindowTitle("Định nghĩa dữ liệu");

        self.buttonBox = QDialogButtonBox(QDialogButtonBox.Ok | QDialogButtonBox.Cancel)

        self.createInputForm();

        mainLayout = QVBoxLayout();
        mainLayout.addWidget(self.formGroupInputBox)
        mainLayout.addWidget(self.buttonBox)

        self.setLayout(mainLayout);
        self.setGeometry(11, 20, 320, 200)

        self.show();

    def createInputForm(self):
        layout = QFormLayout()

        self.soCutriSpinBox = QSpinBox()
        self.soCutriSpinBox.setMinimum(0);

        self.soUngVienSpinBox = QSpinBox()
        self.soUngVienSpinBox.setMinimum(0);
        self.soUngVienSpinBox.setMaximum(10);

        self.soPSSpinBox = QSpinBox()
        self.soPSSpinBox.setMinimum(0);
        self.soPSSpinBox.setMaximum(5);

        layout.addRow(QLabel("Số cử tri:"), self.soCutriSpinBox)
        layout.addRow(QLabel("số ứng viên:"), self.soUngVienSpinBox)
        layout.addRow(QLabel("số PS:"), self.soPSSpinBox)
        self.formGroupInputBox.setLayout(layout)


if __name__ == '__main__':
    app = QApplication(sys.argv)
    dialog = InputDialog()
    sys.exit(dialog.exec_())
