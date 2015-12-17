# -*- coding: utf-8 -*-
import sys

def make_lesson():
    arg = sys.argv[1]
    name = sys.argv[2]
    f = open(arg)
    text = ""
    count = 0
    created_file_count = 0
    for line in f:
        text += line
        count += 1
        if count > 9:
            count = 0
            text = clean(text)
            write_lesson(text, name, created_file_count)
            created_file_count += 1
            text = ""
    # create a lesson out of the remaining text
    write_lesson(text, name, created_file_count)
    f.close()

def write_lesson(text, file_series, number):
    f = open(file_series + "_" + str(number) + ".txt", 'w')
    f.write(text)
    f.close()

def clean(text):
    text = text.replace("\n\n", "\n")
    text = text.replace("’", "\'")
    text = text.replace("“", "\"")
    text = text.replace("”", "\"")
    text = text.replace("–", "-") # en dash
    text = text.replace("—", "-") # em dash
    text = text.replace("à", "a")
    text = text.replace("À", "A")
    text = text.replace("é", "e")
    text = text.replace("É", "E")

    text = text.replace("í", "i")
    text = text.replace("Í", "I")
    text = text.replace("ó", "o")
    text = text.replace("Ó", "O")
    text = text.replace("ú", "u")
    text = text.replace("Ú", "U")
    return text

make_lesson()
