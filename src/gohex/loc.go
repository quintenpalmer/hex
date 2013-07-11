package gohex

type Loc struct {
	x int
	y int
	z int
	neighbors []*Loc
}

func NewRawLoc(x,y,z int) *Loc {
	loc := new(Loc)
	loc.x = x
	loc.y = y
	loc.z = z
	loc.neighbors = make([]*Loc,0)
	return loc
}

func NewLoc(x,y,z int) *Loc {
	loc := NewRawLoc(x,y,z)
	loc.x,loc.y,loc.z = loc.Normalize(0,0,0)
	return loc
}

func (loc *Loc) Eq(otherLoc *Loc) bool {
	return loc.x == otherLoc.x && loc.y == otherLoc.y && loc.z == otherLoc.z
}

func (loc *Loc) Distance(otherLoc *Loc) int {
	x,y,z := loc.Normalize(otherLoc.x,otherLoc.y,otherLoc.z)
	return abs(x) + abs(y) + abs(z)
}

func (loc *Loc) Friend(otherLoc *Loc) {
	if loc.IsLegalNeighbor(otherLoc) {
		loc.neighbors = append(loc.neighbors,otherLoc)
		if !otherLoc.IsFriend(loc) {
			otherLoc.Friend(loc)
		}
	}
}

func (loc *Loc) IsLegalNeighbor(otherLoc *Loc) bool {
	return loc.Distance(otherLoc) == 1
}

func (loc *Loc) IsFriend(otherLoc *Loc) bool {
	for _,l := range loc.neighbors {
		if loc.Eq(l) {
			return true
		}
	}
	return false
}

func (loc *Loc) Normalize(ox, oy, oz int) (int, int, int) {
	// 'o' coords are the origin coords
	// 'r' coords are relative to the origin coords
	rx := loc.x - ox
	ry := loc.y - oy
	rz := loc.z - oz
	// 'a' coords are absolute values
	ax := abs(loc.x)
	ay := abs(loc.y)
	az := abs(loc.z)
	amount := 0
	// I && II
	// +x +y +z
	if rx >= 0 && ry >= 0 && rz >= 0 {
		amount = nearZero(rx,rz)
	// II && III
	// -x +y +z
	} else if rx <= 0 && ry >= 0 && rz >= 0 {
		amount  = nearZero(rx,ry)
		if ax > ay {
			amount = - amount
		} else if ax == ay {
			amount = -abs(amount)
		}
	// III && IV
	// -x -y +z
	} else if rx <= 0 && ry <= 0 && rz >= 0 {
		amount = nearZero(ry,rz)
		if ay < az {
			amount = -amount
		} else if ay == az {
			amount = abs(amount)
		}
	// IV && V
	// -x -y -z
	} else if rx <= 0 && ry <= 0 && rz <= 0 {
		amount = nearZero(rx,rz)
	// V && VI
	// +x -y -z
	} else if rx >= 0 && ry <= 0 && rz <= 0 {
		amount = nearZero(rx,ry)
		if ax > ay {
			amount = -amount
		} else if ax == ay {
			amount = abs(amount)
		}
	// VI && I
	// +x +y -z
	} else if rx >= 0 && ry >= 0 && rz <= 0 {
		amount = nearZero(ry,rz)
		if ay < az {
			amount = -amount
		} else if ay == az {
			amount = -abs(amount)
		}
	// +x -y +z
	} else if rx >= 0 && ry <= 0 && rz >= 0 {
		// +x axis
		if ax > az && az == ay {
			amount = abs(nearZero(ry,rz))
		// I
		} else if ax > az && az > ay {
			amount = az
		// +y axis
		} else if az == ax && ax > ay {
			amount = abs(nearZero(rx,rz))
		// II
		} else if az > ax && ax > ay {
			amount = ax
		// +z axis
		} else if az > ay && ay == ax {
			amount = abs(nearZero(rx,ry))
		// III
		} else if az > ay && ay > ax {
			amount = ay
		// -x axis
		} else if ay == az && az > ax {
			amount = abs(nearZero(ry,rz))
		// IV
		} else if ay > az && az > ax {
			amount = az
		// -y axis
		} else if ay > az && az == ax {
			amount = abs(nearZero(rx,rz))
		// V
		} else if ay > ax && ax > az {
			amount = ax
		// -z axis
		} else if ax == ay && ay > az {
			amount = abs(nearZero(rx,ry))
		// VI
		} else if ax > ay && ay > az {
			amount = ay
		// Origin
		} else if ax == ay && ay == az {
			amount = ax
		}
	// -x +y -z
	} else if rx <= 0 && ry >= 0 && rz <= 0 {
		// +x axis
		if ax < az && az == ay {
			amount = -abs(nearZero(ry,rz))
		// I
		} else if ax < az && az < ay {
			amount = -az
		// +y axis
		} else if az == ax && ax < ay {
			amount = -abs(nearZero(rx,rz))
		// II
		} else if az < ax && ax < ay {
			amount = -ax
		// +z axis
		} else if az < ay && ay == ax {
			amount = -abs(nearZero(rx,ry))
		// III
		} else if az < ay && ay < ax {
			amount = -ay
		// -x axis
		} else if ay == az && az < ax {
			amount = -abs(nearZero(ry,rz))
		// IV
		} else if ay < az && az < ax {
			amount = -az
		// -y axis
		} else if ay < az && az == ax {
			amount = -abs(nearZero(rx,rz))
		// V
		} else if ay < ax && ax < az {
			amount = -ax
		// -z axis
		} else if ax == ay && ay < az {
			amount = -abs(nearZero(rx,ry))
		// VI
		} else if ax < ay && ay < az {
			amount = -ay
		// Origin
		} else if ax == ay && ay == az {
			amount = -ax
		}
	}
	return rx - amount, ry + amount, rz - amount
}

func nearZero(n1,n2 int) int {
	if abs(n1) < abs(n2) {
		return n1
	}
	return n2
}

func abs(i int) int {
	if i < 0 {
		return -i
	}
	return i
}
