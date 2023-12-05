<h1 align="center"><strong>Simulador partido de Tenis Dux</strong></h1>

<p align="center">
  <img src="https://www.channelpartner.es/wp-content/uploads/2021/09/10085_21.jpg.webp" alt="Java Logo">
</p>

<h2 align="center"><strong>Parte 1: SQL</strong></h2>

<h3>Todos los productos del rubro "librería", creados hoy:</strong></h3>
SELECT *
FROM productos p
LEFT JOIN rubro r on r.id_rubro = p.id_rubro
WHERE DATE(fecha_creacion) = CURRENT_DATE and r.rubro = 'libreria';

<h3>Monto total vendido por cliente (mostrar nombre del cliente y monto):</h3>
SELECT CONCAT(c.nombre, ' ' ,c.apellido) as Nombre, SUM(precio_unitario * cantidad)
FROM venta v
LEFT JOIN cliente c on c.id_cliente = v.id_cliente
GROUP BY v.id_cliente, C.apellido, c.nombre

<h3>Cantidad de ventas por producto:</h3>
<h4>Opción por cantidad de ventas (En cuantas ventas se haya el producto sin importar la cantidad del mismo):</h4>
SELECT p.nombre as producto, count(v.id_venta) AS "cantidad de ventas"
FROM venta v
LEFT JOIN productos p on p.codigo = codigo_producto
group by p.nombre

<h4>Opción por cantidad de productos vendidos (La cantidad de cada producto que se vendio):</h4>
SELECT p.nombre as producto, SUM(v.cantidad) AS "cantidad vendida"
FROM venta v
LEFT JOIN productos p on p.codigo = codigo_producto
group by p.nombre

<h3>• Cantidad de productos diferentes comprados por cliente en el mes actual:</h3>
SELECT
c.id_cliente,
CONCAT(c.nombre, ' ', C.apellido) AS "Nombre",
COUNT(DISTINCT v.codigo_producto) AS "Cantidad de productos"
FROM
cliente c
LEFT JOIN venta v ON c.id_cliente = v.id_cliente
LEFT JOIN productos p ON v.codigo_producto = p.codigo
WHERE EXTRACT(MONTH FROM v.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)
GROUP BY c.id_cliente, c.nombre, c.apellido;

<h3>• Ventas que tienen al menos un producto del rubro "bazar".</h3>
SELECT *
FROM venta v
LEFT JOIN productos p ON v.codigo_producto = p.codigo
LEFT JOIN rubro r ON p.id_rubro = r.id_rubro
WHERE r.rubro = 'bazar';

<h3>• Rubros que no tienen ventas en los últimos 2 meses.</h3>
SELECT
r.id_rubro, r.rubro
FROM rubro r
WHERE
NOT EXISTS (
SELECT 1
FROM venta v
JOIN productos p ON v.codigo_producto = p.codigo
WHERE p.id_rubro = r.id_rubro
AND v.fecha >= CURRENT_DATE - INTERVAL '2 months'
);

<h2 align="center"><strong>Parte 2: Java </strong></h2>
Para resolver este desafío realize una api rest utilizando el
modelo vista controlador, es un proyecto en Spring Boot, con
Maven como gestor de dependecias (Lombok, Junit, devtools, etc).
Básicamente la Api mediante un metodo POST puede recibir
un JSON con la información del partido (nombre de los jugadores,
probabilidades, nombre del torneo, y demas datos requeridos)
Ejemplo de Json esperado por el post
{
"jugador1":"Federer",
"jugador2":"Nadal",
"sets":"5",
"probabilidadJugador1":50,
"probabilidadJugador2":50,
"nombreTorneo":"Wimbledon"
}
Luego en el servicio se procesa la información y se simula el partido.
El mismo metodo Post devuelve un List<Map<String, String>> con toda
la simulación e información del partido.
No he llegado a realizar el front end, por lo cual la forma de probar
el funcionamiento de la app es mediante Postman.
El end point que se útiliza en la app es: 
http://localhost:8080/apiDux/tenis/simular
Las pruebas unitarias con Junit se encuentran 
en el package test -> java -> com.dux.simulador_tenis -> PartidoServiceTests

Muchas gracias por leer!
