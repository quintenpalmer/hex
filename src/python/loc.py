
class Loc:
	def __init__(self,x,y,z,do_reduce=True):
		self.x = x
		self.y = y
		self.z = z
		if do_reduce:
			self.x, self.y, self. z= self.normal(0,0,0)

	def distance(self,loc):
		dists = self.normal(loc.x,loc.y,loc.z)
		return abs(dists[0])+abs(dists[1])+abs(dists[2])

	def __eq__(self,loc):
		return loc.x == self.x and loc.y == self.y and loc.z == self.z

	def normal(self,ox,oy,oz):
		# The 'r' coords are the relative to the origin coordinates
		rx = self.x - ox
		ry = self.y - oy
		rz = self.z - oz
		# the 'a' coords are the absolute values of the coordinates
		ax = abs(self.x)
		ay = abs(self.y)
		az = abs(self.z)
		amount = 0
		# I and II
		# +x +y +z
		if rx >= 0 and ry >= 0 and rz >= 0:
			amount = self.near_zero(rx,rz)
		# II and III
		# -x +y +z
		elif rx <= 0 and ry >= 0 and rz >= 0:
			amount  = self.near_zero(rx,ry)
			if ax > ay:
				amount = - amount
			elif ax == ay:
				amount = -abs(amount)
		# III and IV
		# -x -y +z
		elif rx <= 0 and ry <= 0 and rz >= 0:
			amount = self.near_zero(ry,rz)
			if ay < az:
				amount = -amount
			elif ay == az:
				amount = abs(amount)
		# IV and V
		# -x -y -z
		elif rx <= 0 and ry <= 0 and rz <= 0:
			amount = self.near_zero(rx,rz)
		# V and VI
		# +x -y -z
		elif rx >= 0 and ry <= 0 and rz <= 0:
			amount = self.near_zero(rx,ry)
			if ax > ay:
				amount = -amount
			elif ax == ay:
				amount = abs(amount)
		# VI and I
		# +x +y -z
		elif rx >= 0 and ry >= 0 and rz <= 0:
			amount = self.near_zero(ry,rz)
			if ay < az:
				amount = -amount
			elif ay == az:
				amount = -abs(amount)
		# +x -y +z
		elif rx >= 0 and ry <= 0 and rz >= 0:
			# +x axis
			if ax > az and az == ay:
				amount = abs(self.near_zero(ry,rz))
			# I
			elif ax > az and az > ay:
				amount = az
			# +y axis
			elif az == ax and ax > ay:
				amount = abs(self.near_zero(rx,rz))
			# II
			elif az > ax and ax > ay:
				amount = ax
			# +z axis
			elif az > ay and ay == ax:
				amount = abs(self.near_zero(rx,ry))
			# III
			elif az > ay and ay > ax:
				amount = ay
			# -x axis
			elif ay == az and az > ax:
				amount = abs(self.near_zero(ry,rz))
			# IV
			elif ay > az and az > ax:
				amount = az
			# -y axis
			elif ay > az and az == ax:
				amount = abs(self.near_zero(rx,rz))
			# V
			elif ay > ax and ax > az:
				amount = ax
			# -z axis
			elif ax == ay and ay > az:
				amount = abs(self.near_zero(rx,ry))
			# VI
			elif ax > ay and ay > az:
				amount = ay
			# Origin
			elif ax == ay and ay == az:
				amount = ax
		# -x +y -z
		elif rx <= 0 and ry >= 0 and rz <= 0:
			# +x axis
			if ax < az and az == ay:
				amount = -abs(self.near_zero(ry,rz))
			# I
			elif ax < az and az < ay:
				amount = -az
			# +y axis
			elif az == ax and ax < ay:
				amount = -abs(self.near_zero(rx,rz))
			# II
			elif az < ax and ax < ay:
				amount = -ax
			# +z axis
			elif az < ay and ay == ax:
				amount = -abs(self.near_zero(rx,ry))
			# III
			elif az < ay and ay < ax:
				amount = -ay
			# -x axis
			elif ay == az and az < ax:
				amount = -abs(self.near_zero(ry,rz))
			# IV
			elif ay < az and az < ax:
				amount = -az
			# -y axis
			elif ay < az and az == ax:
				amount = -abs(self.near_zero(rx,rz))
			# V
			elif ay < ax and ax < az:
				amount = -ax
			# -z axis
			elif ax == ay and ay < az:
				amount = -abs(self.near_zero(rx,ry))
			# VI
			elif ax < ay and ay < az:
				amount = -ay
			# Origin
			elif ax == ay and ay == az:
				amount = -ax
		return (rx - amount, ry + amount, rz - amount)

	def near_zero(self,n1,n2):
		if abs(n1) < abs(n2):
			return n1
		else:
			return n2

	def __repr__(self):
		return "x: %s y: %s z: %s" % (str(self.x),str(self.y),str(self.z))
