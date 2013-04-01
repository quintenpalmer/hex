
from loc import Loc

class Piece:
	def __init__(self,x,y,z):
		self.loc = Loc(x,y,z)
		self.neighbors = []
	def x(self):
		return self.loc.x
	def y(self):
		return self.loc.y
	def z(self):
		return self.loc.z
	def friend(self,piece):
		if self.legal_neighbor(piece):
			self.neighbors.append(piece)
			if not piece.is_friend(self):
				piece.friend(self)
	def legal_neighbor(self,piece):
		return self.loc.distance(piece.loc) == 1
	def is_friend(self,piece):
		return piece.loc in [x.loc for x in self.neighbors]
	def defriend(self,piece):
		self.neighbors = [x for x in self.neighbors if x.loc != piece.loc]
		if piece.is_friend(self):
			piece.defriend(self)
	def __repr__(self):
		return "Loc(" + str(self.loc) + ') Neighbors(' + str([x.loc for x in self.neighbors]) + ')'
