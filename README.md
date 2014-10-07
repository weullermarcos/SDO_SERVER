SDO_SERVER
==========

Para melhor visualização deste arquivo: https://raw.githubusercontent.com/weullermarcos/SDO_SERVER/master/README.md

-- Navegadores compatíveis:

Chrome (recomendado)
Firefox


-- Dados de acesso:

user: master
pass: sdoserver

-- Regras de negócio:

RNF-01: usaremos base de dados H2 que simplesmente grava os dados em um arquivo no diretório 
        DB da aplicação. Ref: http://www.h2database.com/html/features.html.
        A motivação para isto é facilitar a implantação(instalar a APP na NET) e torna-la 
        independente de configurações adicionais necessárias aos bancos de dados reais.

RN-01: caso o servidor não identifique a placa enviada no JSON, um pré-cadastro do veículo 
       será feito e a posição será armazenada normalmente, desta forma, o veículo estará 
       com seu cadastro incompleto, ou seja, aguardando informações de itinerário, capacidade e etc. 

RN-02: apenas as últimas 10 posições de cada veículo serão armazenadas a fim de poupar 
       recursos de disco. 


-- Heroku Deploy REF.:

https://devcenter.heroku.com/articles/getting-started-with-java#set-up
https://www.playframework.com/documentation/1.2.7/deployment


// ------------------------------------------------------------------------------------------------------------------
1 - Para enviar posições de um determinado veículo ao servidor via HTTP POST:
// ------------------------------------------------------------------------------------------------------------------

HTTP POST:  http://sdo-server.herokuapp.com/save/bus/position

JSON REQUEST BODY:
{
"latitude":-16.694133,
"longitude":-48.790502,
"date":"02/10/2014 23:53:00",
"speed":55,
"busLicensePlate":"JFJ-1593"
}

HTTP RESPONSE: statusCode: 200


// ------------------------------------------------------------------------------------------------------------------
2 - Para enviar posições de um determinado veículo ao servidor via HTTP GET:
// ------------------------------------------------------------------------------------------------------------------

HTTP GET:  http://sdo-server.herokuapp.com/save/bus/position?latitude=-16.694177&longitude=-48.790577&date=07/10/2014 23:53:00&speed=77&busLicensePlate=JFJ-1593

HTTP RESPONSE: statusCode: 200


// ------------------------------------------------------------------------------------------------------------------
3 - Para recuperar posições a partir de uma placa:
// ------------------------------------------------------------------------------------------------------------------

HTTP GET:  http://sdo-server.herokuapp.com/find/bus/position/byLicensePlate/JFJ-1593
    
JSON RESPONSE:
[
  {
    "id": 272,
    "latitude": -16.494133,
    "longitude": -48.590502,
    "date": "02/10/2014 15:30:50",
    "speed": 83
  },
  {
    "id": 273,
    "latitude": -16.594133,
    "longitude": -48.690502,
    "date": "02/10/2014 15:31:50",
    "speed": 55
  },
  {
    "id": 274,
    "latitude": -16.694133,
    "longitude": -48.790502,
    "date": "02/10/2014 15:32:50",
    "speed": 31
  }
]


// ------------------------------------------------------------------------------------------------------------------
4 - Para recuperar posições a partir de uma linha:
// ------------------------------------------------------------------------------------------------------------------

HTTP GET:  http://sdo-server.herokuapp.com/find/bus/position/byLineItinerary/813.1
    
JSON RESPONSE:
[
  {
    "licensePlate": "OVP-5577",
    "busNumber": 75757575,
    "capacity": 57,
    "itinerary": {
      "routeNumber": "813.1",
      "origin": "W3 Sul",
      "arrival": "Pistão Sul"
    },
    "lstPositions": [
      {
        "id": 264,
        "latitude": -16.694133,
        "longitude": -48.790502,
        "date": "02/10/2014 15:26:21",
        "speed": 70
      },
      {
        "id": 275,
        "latitude": -15.794133,
        "longitude": -47.890501,
        "date": "02/10/2014 15:30:50",
        "speed": 94
      },
      {
        "id": 276,
        "latitude": -15.894133,
        "longitude": -47.990501,
        "date": "02/10/2014 15:30:50",
        "speed": 31
      }
    ]
  },
  {
    "licensePlate": "JFJ-1593",
    "busNumber": 77775555,
    "capacity": 57,
    "itinerary": {
      "routeNumber": "813.1",
      "origin": "W3 Sul",
      "arrival": "Pistão Sul"
    },
    "lstPositions": [
      {
        "id": 254,
        "latitude": -16.694133,
        "longitude": -48.790502,
        "date": "02/10/2014 15:26:21",
        "speed": 70
      },
      {
        "id": 265,
        "latitude": -15.794133,
        "longitude": -47.890501,
        "date": "02/10/2014 15:30:50",
        "speed": 92
      },
      {
        "id": 266,
        "latitude": -15.894133,
        "longitude": -47.990501,
        "date": "02/10/2014 15:30:50",
        "speed": 40
      }
    ]
  }
]