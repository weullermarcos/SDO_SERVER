SDO_SERVER
==========

Para melhor visualização deste arquivo: 

https://raw.githubusercontent.com/weullermarcos/SDO_SERVER/master/README.md



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

RN-03: serão subtraídas 3 horas da data/hora enviada pelo Arduino quando esta chegar no servidor.



-- Heroku Deploy REF.:

https://devcenter.heroku.com/articles/getting-started-with-java#set-up
https://www.playframework.com/documentation/1.2.7/deployment



OBS.: Os detalhes de erros causados por má formatação de REQUESTs(HTTP status 400 - badRequest) podem ser encontrados no HEADER de nome "Errors" presente no HTTP RESPONSE



// ------------------------------------------------------------------------------------------------------------------
0 - Para recuperar itinerários existentes no servidor para lista-los no display do Hardware:
// ------------------------------------------------------------------------------------------------------------------

HTTP GET:  http://sdo-server.herokuapp.com/find/bus/itinerary

Ex.: JSON RESPONSE:
[
  {
    "routeNumber": "560",
    "startPoint": "Riacho Fundo II",
    "endPoint": "W3 Sul"
  },
  {
    "routeNumber": "813.1",
    "startPoint": "W3 Sul",
    "endPoint": "Pistão Sul"
  },
  {
    "routeNumber": "921",
    "startPoint": "Setor Q",
    "endPoint": "W3 Sul"
  },
  {
    "routeNumber": "330",
    "startPoint": "Setor Q",
    "endPoint": "W3 Norte"
  },
  {
    "routeNumber": "343.1",
    "startPoint": "P Norte",
    "endPoint": "Rod. P. Piloto"
  }
]


// ------------------------------------------------------------------------------------------------------------------
1 - Para enviar posições de um determinado veículo ao servidor via HTTP POST:
// ------------------------------------------------------------------------------------------------------------------

HTTP POST:  http://sdo-server.herokuapp.com/save/bus/position

Ex.: JSON REQUEST BODY:
{
    "latitude":-16.694177,
    "longitude":-48.790577,
    "date":"07/10/2014 03:51:00",
    "speed":55,
    "positionSense":"TO_END_POINT | TO_START_POINT",
    "bus": {
        "licensePlate": "OVR-5555",
        "busNumber":55577777,
        "capacity":55,
        "itinerary": {
            "routeNumber": "921",
            "startPoint": "Setor Q",
            "endPoint": "W3 Sul"
        }
    }
}


HTTP RESPONSE: statusCode: 200


// ------------------------------------------------------------------------------------------------------------------
2 - Para enviar posições de um determinado veículo ao servidor via HTTP GET:
// ------------------------------------------------------------------------------------------------------------------

Ex.: HTTP GET:  
http://sdo-server.herokuapp.com/save/bus/position?latitude=-16.694177&longitude=-48.790577&date=07/10/2014 04:46:00&speed=77&positionSense=TO_START_POINT&licensePlate=OVR-3431&busNumber=34313431&capacity=70&routeNumber=341.1&startPoint=P Norte&endPoint=Rod. P. Piloto

OBS.: %20 significa espaço em branco na URL ( http://en.wikipedia.org/wiki/Percent-encoding )


HTTP RESPONSE: statusCode: 200


// ------------------------------------------------------------------------------------------------------------------
3 - Para recuperar posições a partir de uma placa:
// ------------------------------------------------------------------------------------------------------------------

HTTP GET:  http://sdo-server.herokuapp.com/find/bus/position/byLicensePlate/JFJ-1593
    
JSON RESPONSE:
[
  {
    "id": 103,
    "latitude": -16.994133,
    "longitude": -49.090502,
    "date": "07/10/2014 04:24:06",
    "speed": 72,
    "positionSense": "TO_END_POINT",
    "bus": {
      "licensePlate": "JFJ-1593",
      "busNumber": 77775555,
      "capacity": 57,
      "itinerary": {
        "routeNumber": "560",
        "startPoint": "Riacho Fundo II",
        "endPoint": "W3 Sul"
      }
    }
  },
  {
    "id": 104,
    "latitude": -17.094133,
    "longitude": -49.190502,
    "date": "07/10/2014 04:24:06",
    "speed": 49,
    "positionSense": "TO_END_POINT",
    "bus": {
      "licensePlate": "JFJ-1593",
      "busNumber": 77775555,
      "capacity": 57,
      "itinerary": {
        "routeNumber": "560",
        "startPoint": "Riacho Fundo II",
        "endPoint": "W3 Sul"
      }
    }
  },
  {
    "id": 105,
    "latitude": -17.194133,
    "longitude": -49.290502,
    "date": "07/10/2014 04:24:06",
    "speed": 83,
    "positionSense": "TO_END_POINT",
    "bus": {
      "licensePlate": "JFJ-1593",
      "busNumber": 77775555,
      "capacity": 57,
      "itinerary": {
        "routeNumber": "560",
        "startPoint": "Riacho Fundo II",
        "endPoint": "W3 Sul"
      }
    }
  }
]


// ------------------------------------------------------------------------------------------------------------------
4 - Para recuperar posições a partir de uma linha:
// ------------------------------------------------------------------------------------------------------------------

HTTP GET:  http://sdo-server.herokuapp.com/find/bus/position/byLineItinerary/813.1
    
JSON RESPONSE:
[
  {
    "id": 103,
    "latitude": -16.994133,
    "longitude": -49.090502,
    "date": "07/10/2014 04:24:06",
    "speed": 72,
    "positionSense": "TO_END_POINT",
    "bus": {
      "licensePlate": "JFJ-1593",
      "busNumber": 77775555,
      "capacity": 57,
      "itinerary": {
        "routeNumber": "560",
        "startPoint": "Riacho Fundo II",
        "endPoint": "W3 Sul"
      }
    }
  },
  {
    "id": 111,
    "latitude": -16.294133,
    "longitude": -48.390502,
    "date": "07/10/2014 04:30:07",
    "speed": 71,
    "positionSense": "TO_START_POINT",
    "bus": {
      "licensePlate": "OVP-5577",
      "busNumber": 75757575,
      "capacity": 75,
      "itinerary": {
        "routeNumber": "560",
        "startPoint": "Riacho Fundo II",
        "endPoint": "W3 Sul"
      }
    }
  }
]
