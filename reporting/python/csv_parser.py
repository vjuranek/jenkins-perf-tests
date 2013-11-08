
import csv

class CsvParser:

    def __init__(self, file_name, x_name, y_name, delimiter=";"):
        print "Parsing %s"%file_name
        self.x_name = x_name
        self.y_name = y_name
        self.x_pos = 0
        self.y_pos = 0

        f = open(file_name, "rb")
        self.reader = csv.reader(f, delimiter=delimiter)
        self.header = self.reader.next()
        self.__parse_header()

    def __parse_header(self):
        header_len = len(self.header)
        for col_num in range(0,header_len):
            if(self.header[col_num] == self.x_name):
                self.x_pos = col_num
            if(self.header[col_num] == self.y_name):
                self.y_pos = col_num
        

    def getMeasurement(self):
        for row in self.reader:
            print '%s: %s' % (row[self.x_pos], row[self.y_pos])

    def close(self):
        self.reader.close()


        

