package model;

import java.lang.Math;

public class Loc{
	public int x;
	public int y;
	public int z;

	public Loc(int nx, int ny, int nz, boolean reduce){
		x = nx;
		y = ny;
		z = nz;
		if(reduce){
			int[] origin = {0,0,0};
			int[] temp = normal(origin);
			x = temp[0];
			y = temp[1];
			z = temp[2];
		}
	}

	public int distance(Loc loc){
		int[] lcord = {loc.x,loc.y,loc.z};
		int[] dists = normal(lcord);
		int dist = Math.abs(dists[0]) + Math.abs(dists[1]) + Math.abs(dists[2]);
		return dist;
	}

	public boolean same(Loc loc){
		if(x==loc.x && y== loc.y && z==loc.z){ return true; }
		else{ return false; }
	}

	public int[] normal(int[] origin){
		// The 'r' x,y,z are relative to the 'origin'
		int rx = x - origin[0];
		int ry = y - origin[1];
		int rz = z - origin[2];
		// The 'a' x,y,z are  the absolute values of the relative x, y, and z
		int ax = Math.abs(rx);
		int ay = Math.abs(ry);
		int az = Math.abs(rz);
		int amount = 0;
		// I and II
		// +x +y +z
		if(rx>=0 && ry>=0 && rz>=0){
			amount = near0(rx,rz);
		}
		// II and III
		// -x +y +z
		else if(rx<=0 && ry>=0 && rz>=0){
			amount = near0(rx,ry);
			if(ax > ay){
				amount = -amount;
			}
			else if(ax == ay){
				amount = -Math.abs(amount);
			}
		}
		// III and IV
		// -x -y +z
		else if(rx<=0 && ry<=0 && rz>=0){
			amount = near0(ry,rz);
			if(ay < az){
				amount = -amount;
			}
			else if(ay == az){
				amount = Math.abs(amount);
			}
		}
		// IV and V
		// -x -y -z
		else if(rx<=0 && ry<=0 && rz<=0){
			amount = near0(rx,rz);
		}
		// V and VI
		// +x -y -z
		else if(rx>=0 && ry<=0 && rz<=0){
			amount = near0(rx,ry);
			if(ax > ay){
				amount = -amount;
			}
			else if(ax == ay){
				amount = Math.abs(amount);
			}
		}
		// VI and I
		// +x +y -z
		else if(rx>=0 && ry>=0 && rz<=0){
			amount = near0(ry,rz);
			if(ay < az){
				amount = -amount;
			}
			else if(ay == az){
				amount = -Math.abs(amount);
			}
		}
		// +x -y +z
		else if(rx>=0 && ry<=0 && rz>=0){
			// +x axis
			if(ax> az && az==ay){
				amount = Math.abs(near0(ry,rz));
			}
			// I
			else if(ax > az && az > ay){
				amount = az;
			}
			// +y axis
			else if(az == ax && ax > ay){
				amount = Math.abs(near0(rx,rz));
			}
			// II
			else if(az > ax && ax > ay){
				amount = ax;
			}
			// +z axis
			else if(az > ay && ay == ax){
				amount = Math.abs(near0(rx,ry));
			}
			// III
			else if(az > ay && ay > ax){
				amount = ay;
			}
			// -x axis
			else if(ay == az && az > ax){
				amount = Math.abs(near0(ry,rz));
			}
			// IV
			else if(ay > az && az > ax){
				amount = az;
			}
			// -y axis
			else if(ay > az && az == ax){
				amount = Math.abs(near0(rx,rz));
			}
			// V
			else if(ay > ax && ax > az){
				amount = ax;
			}
			// -z axis
			else if(ax == ay && ay > az){
				amount = Math.abs(near0(rx,ry));
			}
			// VI
			else if(ax > ay && ay > az){
				amount = ay;
			}
			// Origin
			else if(ax == ay && ay == az){
				amount = ax;
			}
		}
		// -x +y -z
		else if(rx<=0 && ry>=0 && rz<=0){
			// +x axis
			if(ax< az && az==ay){
				amount = -Math.abs(near0(ry,rz));
			}
			// I
			else if(ax < az && az < ay){
				amount = -az;
			}
			// +y axis
			else if(az == ax && ax < ay){
				amount = -Math.abs(near0(rx,rz));
			}
			// II
			else if(az < ax && ax < ay){
				amount = -ax;
			}
			// +z axis
			else if(az < ay && ay == ax){
				amount = -Math.abs(near0(rx,ry));
			}
			// III
			else if(az < ay && ay < ax){
				amount = -ay;
			}
			// -x axis
			else if(ay == az && az < ax){
				amount = -Math.abs(near0(ry,rz));
			}
			// IV
			else if(ay < az && az < ax){
				amount = -az;
			}
			// -y axis
			else if(ay < az && az == ax){
				amount = -Math.abs(near0(rx,rz));
			}
			// V
			else if(ay < ax && ax < az){
				amount = -ax;
			}
			// -z axis
			else if(ax == ay && ay < az){
				amount = -Math.abs(near0(rx,ry));
			}
			// VI
			else if(ax < ay && ay < az){
				amount = -ay;
			}
			// Origin
			else if(ax == ay && ay == az){
				amount = -ax;
			}
		}
		int[] result = new int[3];
		result[0] = rx - amount;
		result[1] = ry + amount;
		result[2] = rz - amount;
		return result;
	}

	private static int near0(int n1, int n2){
		if(Math.abs(n1)<Math.abs(n2)){ return n1; }
		else{ return n2; }
	}

	public String toString(){
		return "x : " + x + " y : " + y + " z : " + z;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getZ(){
		return z;
	}
}
