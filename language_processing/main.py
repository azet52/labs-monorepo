#-*- coding: utf-8 -*-

import collections
import os.path
import re

def letterFrequency(filename):
    f = open(filename, 'r+')
    buffer = f.read()
    f.close()
    buffer = buffer.lower()
    frequencies = collections.Counter(buffer)
    letters = 0
    for key, value in frequencies.items():
        if re.match('[a-ząęćłńóśżź]', key):
            letters += value
    print ('Sumaric letters number: ' + str(letters))
    frequencies = collections.OrderedDict(sorted(frequencies.items(),
                                          key=lambda x: x[1], reverse=True))
    for key, value in frequencies.items():
        if re.match('[a-ząęćłńóśżź]', key):
            print (key + ': ' + str(format((value/letters)*100, '.5f')) + '%')

def wordsFrequency(filename):
    f = open(filename, 'r+')
    buffer = f.read()
    f.close()
    buffer = buffer.lower()
    # in order to split dash separated words
    # e.g. "Radziwił-sierota"
    buffer = buffer.replace('-', ' ')		
    wordsRaw = buffer.split()
    words = []
    for word in wordsRaw:
        for character in word:
            if not re.match('[a-ząęćłńóśżź]', character):
                word = word.replace(character, '')
        if re.match('[a-ząęćłńóśżź]+', word):
             words.append(word)
    frequencies = collections.Counter(words)
    frequencies = collections.OrderedDict(sorted(frequencies.items(),
                                          key=lambda x: x[1], reverse=True))
    print('different words number:{}'.format(len(frequencies)))
    
    for element in list(frequencies.items())[:10]:
        print(element)
    wordsLength(frequencies)

def wordsLength(frequencies):
    lengths = {}
    for key, value in frequencies.items():
        length = len(key)
        if length == 16:
            print(key)
        if not length in lengths:
            lengths[length] = value
        else:
            lengths[length] = lengths[length] + value
    print(lengths)

def split():
    chapters = []
    f = open('Pan Tadeusz/raw', 'r+')
    chapter = None
    for line in f:
        m = re.search('Księga [a-ząęćłńóśżź]+\n', line)
        if m != None:
            filename = m.group(0).replace('Księga ', '')
            chapters.append(filename)
            if chapter != None:
                chapter.close()
            if (not os.path.exists('Pan Tadeusz/' + filename)):
                chapter = open('Pan Tadeusz/' + filename, 'a+')
        if chapter != None:
            chapter.write(line)
    f.close()
    return chapters

def main():
    chapters = split()
    print('Split succesful')
    letterFrequency('Pan Tadeusz/raw')
    for chapter in chapters:
        letterFrequency('Pan Tadeusz/' + chapter)
    wordsFrequency('Pan Tadeusz/raw')

if __name__ == "__main__":
    main()
