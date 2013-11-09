
from numpy import average
import matplotlib.pyplot as plt

class MeasurementSet:
    
    def __init__(self, measurements):
        self.measurements = measurements

    def getMeasurements(self):
        return self.measurements

class Measurement:
    
    def __init__(self, quantity, values=None, units=""):
        self.quantity = quantity
        if values is None:
            values = []
        self.values = values
        self.units = units

    def add(self, value):
        self.values.append(value)

    def avg(self):
        return average(self.values, axis=0)

    def plot(self, color='b'):
        plt.plot(self.values, '%s.-'%color)
        plt.title(self.quantity)
        plt.ylabel(self.quantity)
        return plt

        
