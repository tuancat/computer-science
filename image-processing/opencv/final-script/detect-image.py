from PIL import Image
import pytesseract
pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'


# print("hello")

im = Image.open(r'C:\Users\SamFisher\Downloads\output-image\2.JPG');
config = ("-l eng --oem 1 --psm 7");
text = pytesseract.image_to_string(im, config= config)
print(text);