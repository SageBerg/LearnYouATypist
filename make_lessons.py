# -*- coding: utf-8 -*-
import sys

def make_lesson():
    arg = sys.argv[1]
    f = open(arg)
    text = ""
    count = 0
    for line in f:
        text += line
        count += 1
        if count > 9:
            count = 0
            text = clean(text)
            print text + "\n"
            text = ""
    print text

def clean(text):
    text = text.replace("’", "\'")
    text = text.replace("“", "\"")
    text = text.replace("”", "\"")
    text = text.replace("–", "-")
    text = text.replace("—", "-")
    return text

make_lesson()
