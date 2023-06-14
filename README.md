# Shoe shop

Sa bussiness strane, **Shoe shop** je aplikacjia koja služi za kupovinu obuće. Sa tehničke strane, **Shoe shop** se sastoji od tri mikroservisa (6 ukoliko uzimamo u obzir i pomoćne servise). Primarna tri mikroservisa su: *Users Service*, *Product Service* i *Order Service*.
*User Service* obavlja sve funkcionalnosti vezane za usere, uključujući autentifikaciju i autorizaciju uz pomoć *API gateway*-a (koji je jedan od servisa) te bijleženje sistemskih događaja. *Product Service* i *Order Service* se bave proizvodima i narudžbama, respektivno. Također, *Order Service* služi kao mailing service. Pored već spomenutog *API gateway*-a, pomoćni servisi su *Eureka Service* (omogućava *Service Discovery*) i *Central Config Service* (služi za omogućavanje centralizovane konfiguracije servisa).

## Članovi tima:

 - Azra Ahmić
 - Nedina Muratović
 - Muharem Kapo
 
 Link na repozitorij na frontend: https://github.com/nmuratovic3/nwt_frontend

## Pokretanje projekta

Da bi se projekat pokrenuo kroz docker, prvo je potrebno za svaki od servisa očistiti i instalirati sve dependecy-e:
```
mvn clean install -DskipTests
```
Nakon što se to uradi, potrebno je kreirati network u dockeru sljedećom komandom:
```
docker create network nwt_network
```
Nakon ovoga, projekat se može pokrenuti sljedećom komandom:
```
docker-compose up --build
```

### Pokretanje individualnih servisa

Ukoliko je potrebno dockerom pokrenuti samo baze podataka i rmq, a svaki od servisa pokretati ručno kroz IDE, onda se može koristiti sljedeća komanda:

```
docker-compose -f docker-compose-local.yml up --build
```
