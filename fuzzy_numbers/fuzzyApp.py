from kivy.app import App
from kivy.uix.button import Button
from kivy.uix.label import Label
from kivy.uix.textinput import TextInput
from kivy.uix.boxlayout import BoxLayout

import re
import numpy as np
import matplotlib.pyplot as plt

class FloatInput(TextInput):
    pat = re.compile('[^0-9]')
    def insert_text(self, substring, from_undo=False):
        pat = self.pat
        if '.' in self.text:
            s = re.sub(pat, '', substring)
        else:
            s = '.'.join([re.sub(pat, '', s) for s in substring.split('.', 1)])
        return super(FloatInput, self).insert_text(s, from_undo=from_undo)
    def update_padding(self, text_input, *args):
        text_width = text_input._get_text_width(text_input.text, text_input.tab_width, text_input._label_cached)
        text_input.padding_y = (text_input.width - text_width)/2

class FuzzyApp(App):
    def callback(self, instance):
        if (self.tiA1 != None and self.tiA2 != None and
            self.tiB1 != None and self.tiB2.text != None and
            self.tiC1 != None and self.tiC2 != None and
            self.tiD1 != None and self.tiD2 != None):

            num1 = FuzzyNumber(float(self.tiA1.text), float(self.tiB1.text), float(self.tiC1.text), float(self.tiD1.text))
            num2 = FuzzyNumber(float(self.tiA2.text), float(self.tiB2.text), float(self.tiC2.text), float(self.tiD2.text))

            if (num1.verify() and num2.verify()):
                if (instance.text == 'add'):
                    plotAdd(num1, num2)
                elif (instance.text == 'subtract'):
                    plotSub(num1, num2)
                elif (instance.text == 'multiply'):
                    plotMul(num1, num2)

    def build(self):
        layoutMain = BoxLayout(orientation='vertical', padding=[64,64], background_color=[0,0,0,0])
        layoutFirstNumber = BoxLayout(padding=[16,32])
        layoutSecondNumber = BoxLayout(padding=[16,32])
        layoutOperations = BoxLayout(padding=[16,16])

        labelA1 = Label(text="A1", size_hint_y=None, height=30)
        self.tiA1 = FloatInput(multiline=False, size_hint_y=None, height=30)
        labelB1 = Label(text="B1", size_hint_y=None, height=30)
        self.tiB1 = FloatInput(multiline=False, size_hint_y=None, height=30)
        labelC1 = Label(text="C1", size_hint_y=None, height=30)
        self.tiC1 = FloatInput(multiline=False, size_hint_y=None, height=30)
        labelD1 = Label(text="D1", size_hint_y=None, height=30)
        self.tiD1 = FloatInput(multiline=False, size_hint_y=None, height=30)

        labelA2 = Label(text="A2", size_hint_y=None, height=30)
        self.tiA2 = FloatInput(multiline=False, size_hint_y=None, height=30)
        labelB2 = Label(text="B2", size_hint_y=None, height=30)
        self.tiB2 = FloatInput(multiline=False, size_hint_y=None, height=30)
        labelC2 = Label(text="C2", size_hint_y=None, height=30)
        self.tiC2 = FloatInput(multiline=False, size_hint_y=None, height=30)
        labelD2 = Label(text="D2", size_hint_y=None, height=30)
        self.tiD2 = FloatInput(multiline=False, size_hint_y=None, height=30)

        buttonAdd = Button(text="add", on_press=self.callback)
        buttonSubtract = Button(text="subtract", on_press=self.callback)
        buttonMultiply = Button(text="multiply", on_press=self.callback)

        layoutFirstNumber.add_widget(labelA1)
        layoutFirstNumber.add_widget(self.tiA1)
        layoutFirstNumber.add_widget(labelB1)
        layoutFirstNumber.add_widget(self.tiB1)
        layoutFirstNumber.add_widget(labelC1)
        layoutFirstNumber.add_widget(self.tiC1)
        layoutFirstNumber.add_widget(labelD1)
        layoutFirstNumber.add_widget(self.tiD1)

        layoutSecondNumber.add_widget(labelA2)
        layoutSecondNumber.add_widget(self.tiA2)
        layoutSecondNumber.add_widget(labelB2)
        layoutSecondNumber.add_widget(self.tiB2)
        layoutSecondNumber.add_widget(labelC2)
        layoutSecondNumber.add_widget(self.tiC2)
        layoutSecondNumber.add_widget(labelD2)
        layoutSecondNumber.add_widget(self.tiD2)

        layoutOperations.add_widget(buttonAdd)
        layoutOperations.add_widget(buttonSubtract)
        layoutOperations.add_widget(buttonMultiply)

        layoutMain.add_widget(layoutFirstNumber)
        layoutMain.add_widget(layoutSecondNumber)
        layoutMain.add_widget(layoutOperations)
        return layoutMain


class FuzzyNumber:
    def __init__(self, a, b, c ,d):
        self.a = a
        self.b = b
        self.c = c
        self.d = d

    def __add__(num1, num2):
        a = [num1.a + num2.a, 1][num1.a + num2.a > 1]
        b = [num1.b + num2.b, 1][num1.b + num2.b > 1]
        c = [num1.c + num2.c, 1][num1.c + num2.c > 1]
        d = [num1.d + num2.d, 1][num1.d + num2.d > 1]

        return FuzzyNumber(a, b, c, d)

    def __mul__(num1, num2):
        a = [num1.a * num2.a , 1][num1.a * num2.a > 1]
        b = [num1.b * num2.b, 1][num1.b * num2.b > 1]
        c = [num1.c * num2.c, 1][num1.c * num2.c > 1]
        d = [num1.d * num2.d, 1][num1.d * num2.d > 1]
        return FuzzyNumber(a, b, c, d)

    def __sub__(num1, num2):
        a = [num1.a - num2.d, 0][num1.a - num2.d < 0]
        b = [num1.b - num2.c, 0][num1.b - num2.c < 0]
        c = [num1.c - num2.b, 0][num1.c - num2.b < 0]
        d = [num1.d - num2.a, 0][num1.d - num2.a < 0]
        return FuzzyNumber(a, b, c, d)

    def __str__(self):
        return '<{0}, {1}, {2}, {3}>'.format(self.a, self.b, self.c, self.d)

    def __repr__(self):
        return '<{0}, {1}, {2}, {3}>'.format(self.a, self.b, self.c, self.d)

    def verify(self):
        return self.a <= self.b and self.b <= self.c and self.c <= self.d

    def exactMulCoeffs(num1, num2):
        [[a1, b1], [c1, d1]] = [[num1.b - num1.a, num1.a], [-(num1.d - num1.c), num1.d]]
        [[a2, b2], [c2, d2]] = [[num2.b - num2.a, num2.a], [-(num2.d - num2.c), num2.d]]
        return [[a1 * a2, a1 * b2 + a2 * b1, b1 * b2], [c1 * c2, c1 * d2 + c2 * d1, d1 * d2]]

def getValueFromCoefs(coeffs, x):
    return coeffs[0] * x * x + coeffs[1] * x + coeffs[2]

def plotAdd(num1, num2):
    x1 = [num1.a, num1.b, num1.c, num1.d]
    x2 = [num2.a, num2.b, num2.c, num2.d]
    num3 = num1 + num2
    x3 = [num3.a, num3.b, num3.c, num3.d]
    y = [0.0, 1.0, 1.0, 0.0]

    fig = plt.figure()
    ax = fig.add_subplot(221)
    ax.set_ylim(0, 1.05)
    plt.plot(x1, y)
    bx = fig.add_subplot(222)
    bx.set_ylim(0, 1.05)
    plt.plot(x2, y)
    cx = fig.add_subplot(212)
    cx.set_ylim(0., 1.05)
    plt.plot(x3, y, 'r', antialiased=True)
    plt.show()

def plotSub(num1, num2):
    x1 = [num1.a, num1.b, num1.c, num1.d]
    x2 = [num2.a, num2.b, num2.c, num2.d]
    num3 = num1 - num2
    x3 = [num3.a, num3.b, num3.c, num3.d]
    y = [0.0, 1.0, 1.0, 0.0]

    fig = plt.figure()
    ax = fig.add_subplot(221)
    ax.set_ylim(0, 1.05)
    plt.plot(x1, y)
    bx = fig.add_subplot(222)
    bx.set_ylim(0, 1.05)
    plt.plot(x2, y)
    cx = fig.add_subplot(212)
    cx.set_ylim(0., 1.05)
    #cx.set_xlim(0., 0.01)
    plt.plot(x3, y, 'r', antialiased=True)
    plt.show()

def plotMul(num1, num2):
    x1 = [num1.a, num1.b, num1.c, num1.d]
    x2 = [num2.a, num2.b, num2.c, num2.d]
    num3 = num1 * num2
    # trapeziodal approximation
    x3 = [num3.a, num3.b, num3.c, num3.d]
    y = [0.0, 1.0, 1.0, 0.0]

    coeffs = FuzzyNumber.exactMulCoeffs(num1, num2)

    y1 = np.arange(0., 1.1, 0.1)
    l, r = [], []
    for element in y1:
        l.append(getValueFromCoefs(coeffs[0], element))
        r.append(getValueFromCoefs(coeffs[1], element))

    # lambda = 0
    a = getValueFromCoefs(coeffs[0], 0)
    d = getValueFromCoefs(coeffs[1], 0)
    # lamda = 1
    b = getValueFromCoefs(coeffs[0], 1)
    c = getValueFromCoefs(coeffs[1], 1)

    x4 = [a, b, c, d]

    fig = plt.figure()
    ax = fig.add_subplot(221)
    ax.set_ylim(0, 1.05)
    plt.plot(x1, y)
    bx = fig.add_subplot(222)
    bx.set_ylim(0, 1.05)
    plt.plot(x2, y)
    cx = fig.add_subplot(212)
    cx.set_ylim(0., 1.05)
    #cx.set_xlim(0., 0.01)
    print x3
    plt.plot(x3, y, r, y1, 'r', [x4[1], x4[2]], [y[1], y[2]], 'r', l, y1, 'r', antialiased=True)
    #plt.plot(x3, y, r, y1, 'r', l, y1, 'r', antialiased=True)

    plt.show()



if __name__ == '__main__':
    FuzzyApp().run()

# example of using FuzzyNumber class:

# sm = FuzzyNumber(0.0, 0.0, 0.0005, 0.001)
# av = FuzzyNumber(0.0005, 0.001, 0.01, 0.03)
# bg = FuzzyNumber(0.01, 0.03, 1.0, 1.0)
#
# events = {'e1':bg, 'e2':bg, 'e3':av, 'e4':av, 'e5':sm, 'e6':av, 'e7':av}
# n1 = {'e1':bg, 'e2':bg, 'e3':av, 'e4':av, 'e5':av, 'e6':av, 'e7':av}
# n2 = {'e1':bg, 'e2':bg, 'e3':sm, 'e4':sm, 'e5':sm, 'e6':sm, 'e7':sm}
# n3 = {'e1':bg, 'e2':bg, 'e3':bg, 'e4':bg, 'e5':sm, 'e6':bg, 'e7':bg}
# n4 = {'e1':av, 'e2':av, 'e3':av, 'e4':av, 'e5':sm, 'e6':av, 'e7':av}
#
# rules = {}
#
# events_and_neighbours = [events]#, n1, n2, n3, n4]
#
# num1 = FuzzyNumber(0.1, 0.2, 0.5, 0.7)
# num2 = FuzzyNumber(0.3, 0.7, 0.8, 0.9)
# plotMul(None, num1, num2)

# for e in events_and_neighbours:
#     events = e
#
#     # every event
#
#     rules['a12'] = events.get('e7') + events.get('e6')
#     rules['a11'] = events.get('e5') + events.get('e4')
#     rules['a10'] = events.get('e3') + rules.get('a12')
#     rules['a9'] = rules.get('a10') * rules.get('a11')
#     rules['a8'] = events.get('e1') + events.get('e2')
#     rules['a13'] = rules.get('a8') + rules.get('a9')
#     rules['a'] = rules['a13']
#
#     print rules['a']
#
#     # without e1
#
#     rules['a12'] = events.get('e7') + events.get('e6')
#     rules['a11'] = events.get('e5') + events.get('e4')
#     rules['a10'] = events.get('e3') + rules.get('a12')
#     rules['a9'] = rules.get('a10') * rules.get('a11')
#     print rules['a10']
#     print rules['a11']
#     plotMul(rules['a10'], rules['a11'])
#     rules['a8'] =  events.get('e2')
#     rules['awoe1'] = rules.get('a8') + rules.get('a9')
#
#     #print rules['a'] - rules['awoe1']
#
#     # without e2
#
#     rules['a12'] = events.get('e7') + events.get('e6')
#     rules['a11'] = events.get('e5') + events.get('e4')
#     rules['a10'] = events.get('e3') + rules.get('a12')
#     rules['a9'] = rules.get('a10') * rules.get('a11')
#     rules['a8'] = events.get('e1')
#     rules['awoe2'] = rules.get('a8') + rules.get('a9')
#
#     #print rules['a'] - rules['awoe2']
#
#     # without e3
#
#     rules['a12'] = events.get('e7') + events.get('e6')
#     rules['a11'] = events.get('e5') + events.get('e4')
#     rules['a10'] = rules.get('a12')
#     rules['a9'] = rules.get('a10') * rules.get('a11')
#     rules['a8'] = events.get('e1') + events.get('e2')
#     rules['awoe3'] = rules.get('a8') + rules.get('a9')
#
#     #print  rules['a'] - rules['awoe3']
#
#     # without e4
#
#     rules['a12'] = events.get('e7') + events.get('e6')
#     rules['a11'] = events.get('e5')
#     rules['a10'] = events.get('e3') + rules.get('a12')
#     rules['a9'] = rules.get('a10') * rules.get('a11')
#     rules['a8'] = events.get('e1') + events.get('e2')
#     rules['awoe4'] = rules.get('a8') + rules.get('a9')
#
#     #print rules['a'] - rules['awoe4']
#
#     # without e5
#
#     rules['a12'] = events.get('e7') + events.get('e6')
#     rules['a11'] = events.get('e4')
#     rules['a10'] = events.get('e3') + rules.get('a12')
#     rules['a9'] = rules.get('a10') * rules.get('a11')
#     rules['a8'] = events.get('e1') + events.get('e2')
#     rules['awoe5'] = rules.get('a8') + rules.get('a9')
#
#     #print rules['a'] - rules['awoe5']
#
#     # without e6
#
#     rules['a12'] = events.get('e7')
#     rules['a11'] = events.get('e5') + events.get('e4')
#     rules['a10'] = events.get('e3') + rules.get('a12')
#     rules['a9'] = rules.get('a10') * rules.get('a11')
#     rules['a8'] = events.get('e1') + events.get('e2')
#     rules['awoe6'] = rules.get('a8') + rules.get('a9')
#
#     #print rules['a'] - rules['awoe6']
#
#     # without e7
#
#     rules['a12'] = events.get('e6')
#     rules['a11'] = events.get('e5') + events.get('e4')
#     rules['a10'] = events.get('e3') + rules.get('a12')
#     rules['a9'] = rules.get('a10') * rules.get('a11')
#     rules['a8'] = events.get('e1') + events.get('e2')
#     rules['awoe7'] = rules.get('a8') + rules.get('a9')
#
#     #print rules['a'] - rules['awoe7'], '\n'
