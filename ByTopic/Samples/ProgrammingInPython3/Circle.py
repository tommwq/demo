class Point:
    def __init__(self, x=0, y=0):
        self.x = x
        self.y =y
        
class Circle(Point):
    def __init__(self, radius, x, y):
        super().__init__(x, y)
        self.radius = radius

        
