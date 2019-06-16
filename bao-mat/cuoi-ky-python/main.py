
import sys
from PyQt5.QtWidgets import QApplication
from UI.input_dialog import InputDialog

if __name__ == '__main__':
    app = QApplication(sys.argv)
    dialog = InputDialog()

    print(dialog.so_cu_tri);
    sys.exit(dialog.exec_())