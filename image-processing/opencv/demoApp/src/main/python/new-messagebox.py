import sys
from PyQt5.QtWidgets import QApplication, QWidget, QPushButton, QMessageBox, QDialog
from PyQt5.QtGui import QIcon
from PyQt5.QtCore import pyqtSlot


class NewMessageBox(QWidget):
    def __init__(self):
        super().__init__();
        self.title = "Message box"
        self.left = 10;
        self.top = 10;
        self.width = 320;
        self.height = 200;
    def initMessageBox(self):
        self.setWindowTitle(self.title);
        self.setGeometry(self.left, self.top, self.width, self.height);

        messageBox = QMessageBox.


