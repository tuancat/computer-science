import sys
from PyQt5.QtWidgets import QApplication, QWidget, QPushButton, QMessageBox, QDialog, QVBoxLayout, QGroupBox, \
    QFormLayout, QLabel, QLineEdit, QComboBox, QSpinBox
from PyQt5.QtGui import QIcon
from PyQt5.QtCore import pyqtSlot


class InputDialog(QDialog):
    def __init__(self):
        super().__init__();

        self.formGroupInputBox = QGroupBox("Input form");
        self.setWindowTitle("Input data");

        self.createInputForm();

        mainLayout = QVBoxLayout();
        mainLayout.addWidget(self.formGroupInputBox)

        self.setLayout(mainLayout);

        self.show();

    def createInputForm(self):
        layout = QFormLayout()
        layout.addRow(QLabel("Số:"), QSpinBox())
        layout.addRow(QLabel("Country:"), QComboBox())
        layout.addRow(QLabel("Age:"), QSpinBox())
        self.formGroupInputBox.setLayout(layout)


if __name__ == '__main__':
    app = QApplication(sys.argv)
    dialog = InputDialog()
    sys.exit(dialog.exec_())