
from piece import Piece

class Board:
	def __init__(self,size):
		self.locs = {}
		self.size = size
		def add_piece(x,y,z):
			tmp = Piece(x,y,z)
			self.locs[(tmp.x(),tmp.y(),tmp.z())] = tmp
		x,y,z = 0
		add_piece(x,y,z)
		for i in range(1,size+1):
			x += i
			add_piece(x,y,z)
			for j in range(1,i+1):
				z += 1
				add_piece(x,y,z)
			for j in range(1,i+1):
				x -= 1
				add_piece(x,y,z)
			for j in range(1,i+1):
				y -= 1
				add_piece(x,y,z)
			for j in range(1,i+1):
				z -= 1
				add_piece(x,y,z)
			for j in range(1,i+1):
				x += 1
				add_piece(x,y,z)
			for j in range(1,i):
				y += 1
				add_piece(x,y,z)
			y += 1
			x -= i
	def __repr__(self):
		return self.locs[(0,0,0)].__repr__()
	def draw(self,slant=True):
		array = []
		zero = self.size+1
		size = (self.size+2)*2-1
		for i in range(size):
			array.append([])
			for j in range(size):
				x = str(i-zero)
				if i-zero >= 0:
					x = ' '+x
				y = str(j-zero)
				if j-zero >= 0:
					y = ' '+y
				array[i].append('('+x+','+y+')')
		for loc in self.locs:
			print loc
			x = zero
			y = zero
			x += loc[0]
			if loc[1] % 2 == 1:
				x += int(round(loc[1]/2.0))-1
			else:
				x += loc[1]
			if loc[2] % 2 == 1:
				x -= int(round(loc[2]/2.0))-1
			else:
				x -= loc[2]
			'''
			yx = int(round(loc[1]/2.0))
			zx = int(round(loc[2]/2.0))
			if yx > 0:
				yx -= 1
			if zx > 0:
				zx -= 1
			print 'y change to x',yx
			print 'z change to x',zx
			x += yx
			x -= zx
			'''
			y += loc[2]
			y += loc[1]
			'''
			array[x][y] += 'X'
			print x,y
			print x-zero,y-zero
			print '-'*40
		for a in array:
			for b in a:
				if b[-1] != 'X':
					b += ' '
			'''
		#array[zero][zero] = 'o'
		ret_string = ''
		for i in range(size-1,-1,-1):
			if i % 2 == 1 and slant:
				ret_string += '       '
			for j in range(size):
				ret_string += array[j][i]
				ret_string += '       '
			ret_string += '\n\n'
		return ret_string
