import re;
from base_tokenizer import BaseTokenizer
from utils import load_n_grams

text = 'tôi đang làm việc chăm chỉ'


biGrams= load_n_grams('bi-grams.txt');
triGrams = load_n_grams('tri-grams.txt')


def tokenize(text):

    syllables = BaseTokenizer.syllablize(text)
    lenofSen = len(syllables)
    currId = 0
    result = []
    done = False
    while (currId < lenofSen) and (not done):

        currWord = syllables[currId]
        print(currWord)
        if currId >= (lenofSen - 1): # nêu là từ cuối cùng của câu
            result.append(currWord)
            done = True
        else:
            nextWord = syllables[currId + 1]
            twoWord = ' '.join([currWord.lower(), nextWord.lower()])
            if currId >= (lenofSen - 2):
                if twoWord in biGrams:
                    result.append('_'.join([currWord, nextWord]))
                    currId += 2
                else:
                    result.append(currWord)
                    currId += 1
            else:
                nextNextWord = syllables[currId + 2]
                threeWord = ' '.join([twoWord, nextNextWord.lower()])
                if threeWord in triGrams:
                    result.append('_'.join([currWord, nextWord, next_next_word]))
                    currId += 3
                elif twoWord in biGrams:
                    result.append('_'.join([currWord, nextWord]))
                    currId += 2
                else:
                    result.append(currWord)
                    currId += 1
    return result
    # return 0

# print(re.findall(regex, text));
# BaseTokenizer.syllablize(text)
# biGrams= load_n_grams('bi-grams.txt');
# triGrams = load_n_grams('tri-grams.txt')
# print(biGrams);


print(tokenize(text));