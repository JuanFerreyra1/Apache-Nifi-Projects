import sys
import re




data = sys.stdin.readlines()
lista = data[0].split(",")



#si puede aplicar regex es porque estamos con los archivos de landing, sino de hdfs
try: 
	re.findall("Landing",lista[0])[0]

	lista_goa=["archivos_goa"]
	for x in lista:
		try:
			a = re.findall("[0-9]{8}",x)[0]
	#print(x)
	#print(type(x))
	#print("\n")
	#print(re.findall("[0-9]{8}",x))
			lista_goa.append(a)
		except:
			pass
	print(lista_goa)
except:	
	pass

try:
	re.findall("HDFS",lista[0])[0]
	lista_hdfs=["archivos_hdfs"]
	for x in lista:
		try:
			a = re.findall("[0-9]{8}",x)[0]
			lista_hdfs.append(a)
		except:
			pass
	print(lista_hdfs)
except:
	pass

try:
	re.findall("partition",data[0])[0]
	lista = ["archivos_raw"]
	for x in data:
		try:
			a = re.findall("[0-9]{8}",x)[0]
			lista.append(a)
		except:
			pass	
	print(lista)		
except:
	pass
