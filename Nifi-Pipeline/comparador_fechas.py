import sys
import re

data = sys.stdin.readlines()

pclaveuno = data[0].split(",")[0]

if "raw" in pclaveuno:
	raw = data[0].split(",")[1:]
	rawfinallist = []
	for x in raw:
		try:
			rawfinal = re.findall("[0-9]{8}",x)[0]
			rawfinallist.append(rawfinal)
		except:
			pass

if "hdfs" in pclaveuno:
	hdfs = data[0].split(",")[1:]
	hdfsfinallist = []
	for x in hdfs:
		try:
			hdfsfinal = re.findall("[0-9]{8}",x)[0]
			hdfsfinallist.append(hdfsfinal)
		except:
			pass
if "goa" in pclaveuno:
	goa = data[0].split(",")[1:]
	goafinallist = []
	for x in goa:
		try:
			goafinal = re.findall("[0-9]{8}",x)[0]
			goafinallist.append(goafinal)
		except:
			pass



pclavedos = data[2].split(",")[0]

if "raw" in pclavedos:
	raw = data[2].split(",")[1:]
	rawfinallist = []
	for x in raw:
		try:
			rawfinal = re.findall("[0-9]{8}",x)[0]
			rawfinallist.append(rawfinal)
		except:
			pass
if "hdfs" in pclavedos:
	hdfs = data[2].split(",")[1:]
	hdfsfinallist = []
	for x in hdfs:
		try:
			hdfsfinal = re.findall("[0-9]{8}",x)[0]
			hdfsfinallist.append(hdfsfinal)
		except:
			pass

if "goa" in pclavedos:
	goa = data[2].split(",")[1:]
	goafinallist = []
	for x in goa:
		try:
			goafinal = re.findall("[0-9]{8}",x)[0]
			goafinallist.append(goafinal)
		except:
			pass




pclavetres = data[4].split(",")[0]
if "raw" in pclavetres:
	raw = data[4].split(",")[1:]
	rawfinallist = []
	for x in raw:
		try:
			rawfinal = re.findall("[0-9]{8}",x)[0]
			rawfinallist.append(rawfinal)
		except:
			pass
if "hdfs" in pclavetres:
	hdfs = data[4].split(",")[1:]
	hdfsfinallist = []
	for x in hdfs:
		try:
			hdfsfinal = re.findall("[0-9]{8}",x)[0]
			hdfsfinallist.append(hdfsfinal)
		except:
			pass
if "goa" in pclavetres:
	goa = data[4].split(",")[1:]
	goafinallist = []
	for x in goa:
		try:
			goafinal = re.findall("[0-9]{8}",x)[0]
			goafinallist.append(goafinal)
		except:
			pass




def comparador(goa,raw,hdfs):
	malgoa = False
	malraw = False
	print(f"Fechas goa: {goa}")
	print("\n")
	print("HDFS")
	for fechas_goa in goa:
		if fechas_goa not in hdfs:
			print(f"Fecha:{fechas_goa} no esta en hdfs ")
			malgoa = True
	if malgoa == False:
		print("Cargado exitosamente")
	print("\n")
	print("\n")
	print("RAW")
	for fechas_goa in goa:
		if fechas_goa not in raw:
			print(f"Fecha:{fechas_goa} no esta en raw ")
			malraw = True
	if malraw == False:
		print("Cargado exitosamente")

comparador(goafinallist,rawfinallist,hdfsfinallist)