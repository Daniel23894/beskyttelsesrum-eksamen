# Beskyttelsesrum-Manager

**Udvikler:** Daniel Søgaard  
**Kontakt:** daso0005@stud.ek.dk  
**Repository:** [https://github.com/Daniel23894/beskyttelsesrum-eksamen.git](https://github.com/Daniel23894/beskyttelsesrum-eksamen.git)

## Projektbeskrivelse
Dette projekt er en full-stack applikation udviklet til administration og overblik over danske beskyttelsesrum. Applikationen opfylder de fire delopgaver i eksamenssættet for 3. semester på datamatikeruddannelsen.

## Systemets opbygning

### Datadesign og JPA (Delopgave 1)
Jeg har implementeret et database-design med JPA, der består af entiteterne Beskyttelsesrum og Kommune.
- Relationen er opbygget som en One-to-Many relation.
- Systemet initialiseres automatisk via en InitData-klasse, der populere databasen med 10 kommuner og 13 beskyttelsesrum fordelt over hele landet ved opstart.

### REST API og CRUD (Delopgave 2)
Backenden tilbyder et REST API med fuld CRUD-funktionalitet.
- Der er implementeret endpoints til GET, POST, PUT og DELETE.
- Bemærk venligst: Mens backenden understøtter fuld CRUD, er det i frontenden på nuværende tidspunkt kun slette-funktionen, der er implementeret via brugerfladen. Oprettelse og redigering understøttes udelukkende i backenden i denne version.

### Frontend (Delopgave 3)
Frontenden er opbygget som en OnePager, der styres dynamisk med JavaScript uden genindlæsning af siden.
- Brugerfladen er opdelt i tre logiske visninger: en liste over kommuner/rum, en kapacitetsoversigt og en afstandsberegner.
- Kapacitetsoversigten summerer den samlede kapacitet per kommune via JavaScript-logik i frontenden.
- Slette-funktionen interagerer direkte med backenden via Fetch API og opdaterer brugerfladen øjeblikkeligt ved fjernelse af et rum.

### Afstandsberegning (Delopgave 4)
Jeg har implementeret Haversine-formlen i backenden til beregning af afstande mellem geografiske koordinater.
- Systemet kan modtage brugerens position (Latitude/Longitude) og returnere en liste over alle beskyttelsesrum sorteret efter afstand, så de nærmeste rum vises først.

## Test og validering
Jeg har implementeret 4 JUnit-tests, der verificerer applikationens kernefunktionalitet:
- Matematisk validering af afstandsberegningen (Haversine-algoritmen).
- Integrationstest af JPA-relationer mellem Kommune og Beskyttelsesrum.
- Web-layer test af API-endpoints ved brug af MockMvc.
- Validering af korrekt slette-funktionalitet i databasen.
