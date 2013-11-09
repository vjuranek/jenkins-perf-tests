
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


    def plot(self, xvar=None, color='b'):
        if xvar is not None:
            plt.plot(xvar.values, self.values, '%s.-'%color)
            plt.xlabel(xvar.quantity)
        else:
            plt.plot(self.values, '%s.-'%color)

        plt.ylabel(self.quantity)
        plt.title(self.quantity)
        return plt


    def plot_with(self, xvar, yvar, color1='b', color2='r'):
        plt.plot(xvar.values, self.values, color1, xvar.values, yvar.values, color2)
        return plt


    def save_plot(self, filname):
        plt = self.plot()
        plt.savefig(filename, filename[-3:])
        
