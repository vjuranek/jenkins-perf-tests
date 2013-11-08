
from numpy import average

class Measurement:
    
    def __init__(self, quantity, values=[], units=""):
        self.quantity = quantity
        self.values = values
        self.units = units

    def add(self, value):
        self.values.append(value)

    def avg(self):
        return average(self.values, axis=0)


class Point:

    def __init__(self, x, y):
        self.x = x
        self.y = y

        
