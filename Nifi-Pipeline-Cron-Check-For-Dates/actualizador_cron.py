import sys
import re
import json
import requests
from datetime import date
from datetime import timedelta

entrada = sys.stdin.readlines()

def fecha_ayer():
	fecha_ayer= date.today() - timedelta(days=1)
	anio_ayer = str(fecha_ayer.year)
	mes_ayer = str(fecha_ayer.month)
	if len(mes_ayer)<2:
		mes_ayer = "0"+mes_ayer
	dia_ayer = str(fecha_ayer.day)
	fecha_string = anio_ayer+mes_ayer+dia_ayer

	return fecha_string #ej: '20221217'
def determinar_actualizados(mp):
	lista = mp
	listan = "".join(lista)
	listan2 = listan.split("\n") #me queda una lista correcta
	lista_visible = []
	extra = []
	for x in listan2:
		if x[-8:] == fecha:
			extra = []
			extra.append(x)
			extra.append("  ACTUALIZADO")
			extra = "".join(extra)
			lista_visible.append(extra)
		else:
			lista_visible.append(x)
	return lista_visible
def mejorar_lista(lista_entrada):
	lista_entrada = lista_visible
	listaf  = []
	listatotal = []
	for x in lista_visible:
		listatmp=[]
		longitud = 0
		if len(x)<70:
			listatmp.append(x)
			longitud = 70-len(x)
			listatmp.append("-"*longitud)
			listatmp = "".join(listatmp)
		listaf.append(listatmp)
	listatotal = "".join(listaf)
	return listatotal

fecha = fecha_ayer()
lista_visible = determinar_actualizados(entrada)
listatotal = mejorar_lista(lista_visible)

def peticion(listatotal):
	values = """{"comment_text": "%s","assignee": 183,"notify_all": true}""" %listatotal
	headers = {'Authorization': '3275797_3f8e8c0c587fd88830f0422da833753ce4c188e4','Content-Type': 'application/json'}
	comentar = requests.post('https://api.clickup.com/api/v2/task/1ye3gq0/comment?custom_task_ids=&team_id=', data=values, headers=headers)
	resultado = comentar.json()
	print(resultado)
peticion(listatotal)

