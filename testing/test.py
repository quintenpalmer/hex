import sys
from loc import Loc

debug = 0

def main():
	global debug
	instr = ""
	if len(sys.argv) == 3:
		debug = int(sys.argv[1])
		instr = sys.argv[2]
	else:
		with open("data/instruction.txt",'r') as f:
			debug,instr = [line.strip() for line in f.readline().split(',')]
			debug = int(debug)
	if instr == "all":
		createTest()
		discTest()
	elif instr == "create":
		createTest()
	elif instr == "dist":
		distTest()
	else:
		print "unknown command %s. \nMust be of type 'all', 'create', or 'dist'"%instr

def createTest():
	print "createTest"
	worked = True
	header = ''
	meta_header = ''
	num_wrong = 0
	num_tested = 0
	with open("data/createTest.txt",'r') as input_file, open("data/createTest.log",'w') as output_file:
		for line in input_file.readlines():
			line = line.strip()
			if line[:2] == "#!":
				header = line[3:]
			elif line[:2] == "#@":
				meta_header = line[3:]
			elif line[:1] != "#":
				split = line.split(",")
				x,y,z,xd,yd,zd = [int(num) for num in split[:-1]]
				should_be_same = True if split[-1] == 'true' else False
				loc1 = Loc(x,y,z,True)
				loc2 = Loc(xd,yd,zd,False)
				same = (loc1 == loc2) == should_be_same
				worked = same and worked
				if not same:
					num_wrong += 1
				num_tested += 1
				result = str(same)+'\n'
				full_result = "%s ------------\n%s\n%s\n%r\n%r\n"%(same,meta_header,header,loc1,loc2)
				if debug == 2 or (debug == 1 and not same):
					output_file.write(full_result)
					sys.stdout.write(full_result)
				else:
					output_file.write(result)

	print """Results:
debug: %s
%s
%s/%s succedded
"""%(debug,"Success!" if worked else "Failure!",num_tested - num_wrong,num_tested)

def distTest():
	print "distTest"

if __name__=="__main__":
	main()
