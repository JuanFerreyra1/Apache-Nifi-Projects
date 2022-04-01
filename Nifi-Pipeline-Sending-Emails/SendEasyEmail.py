from sys import stdin
import json
import smtplib
from smtplib import SMTP as smtp
from email.mime.text import MIMEText as text
import sys
import json

data = sys.stdin.read()

class SendEasyEmail():
    def __init__(self,data_dict_mp):  
            self.data_dict= json.loads(data_dict_mp)
            self.result = str(self.data_dict["result"])
            self.fecha = str(self.data_dict["fecha_proceso"])
            self.usuario_emisor = str(self.data_dict["usuario_del_emisor"])
            self.contrasena_emisor = str(self.data_dict["contrasena_del_emisor"])
            self.text_body = '''Para la corrida efectuada en la fecha {}, se encontraron los siguientes datos nuevos:\n{}'''.format(self.fecha,self.result)
            self.receptores = str(self.data_dict["destinatarios"]).split(',')
            
    def action(self):
        for x in range(len(self.receptores)):
            try:
                self.s = smtplib.SMTP_SSL('smtp.gmail.com', 465)
                self.s.login(self.usuario_emisor,self.contrasena_emisor)
                self.m = text(self.text_body)
                self.m['Subject']= 'Reporte segmentos de prestamos no identificados | fecha {}'.format(self.fecha)
                self.s.sendmail(self.usuario_emisor,self.receptores[x], self.m.as_string())
                self.s.close()
                print("Se han reportado novedades de la fecha {} al mail {}".format(self.fecha,self.receptores[x]))
            except:
                pass
envio = SendEasyEmail(data)
envio.action()