// URL'er til dit API
const BASE_URL = "http://localhost:8080";

// Globale lister til at gemme data fra backenden
let kommuner = [];
let rum = [];

// Kører hver gang man trykker på en knap i menuen
function visSektion(id) {
    // Skjul alle sektioner
    document.getElementById('sektion-rum').classList.add('hidden');
    document.getElementById('sektion-kapacitet').classList.add('hidden');
    document.getElementById('sektion-afstand').classList.add('hidden');
    
    // Vis den valgte sektion
    document.getElementById(id).classList.remove('hidden');
}

// Hovedfunktion der henter alt data fra databasen
async function hentAltData() {
    try {
        // henter både requests (kommuner og rum) på samme tid 
        const [resK, resR] = await Promise.all([
            fetch(`${BASE_URL}/komunner`),
            fetch(`${BASE_URL}/rooms/all`) 
        ]);

        kommuner = await resK.json();
        rum = await resR.json();

        // vis data
        bygVisning3a();
        bygVisning3b();
        fyldAfstandDropdown();
    } catch (fejl) {
        console.error("Kunne ikke hente data fra API:", fejl);
    }
}

// DEL 3a: Liste over kommuner og deres rum med slet-knap
function bygVisning3a() {
    const container = document.getElementById('rum-liste-indhold');
    container.innerHTML = ""; 

    kommuner.forEach(k => {
        const rumIKommune = rum.filter(r => r.kommune.id === k.id);

        let html = `<div class="kommune-kort">
                        <h3>${k.navn}</h3>`;
        
        if (rumIKommune.length > 0) {
            html += "<ul>";
            rumIKommune.forEach(r => {
                // Her kombinerer vi din tekst med den nye slet-knap
                html += `<li>
                            ${r.adresse} - Plads til: ${r.kapacitet}
                            <button class="delete-btn" onclick="sletRum(${r.id})">Slet</button>
                         </li>`;
            });
            html += "</ul>";
        } else {
            html += "<p><em>Ingen rum registreret i denne kommune.</em></p>";
        }

        html += "</div><hr>";
        container.innerHTML += html;
    });
}

// udfører sletningen i backenden
async function sletRum(id) {
    if (!confirm("Er du sikker på, at du vil slette dette rum?")) return;

    // standard er GET-anmodning, derfor skal specificere at vi vil slette og ikke hente
    try {
        const response = await fetch(`${BASE_URL}/rooms/${id}`, {
            method: 'DELETE' 
        });

        if (response.ok) {
            // Opdaterer data og visning med det samme uden refresh
            await hentAltData(); 
        } else {
            alert("Fejl: Kunne ikke slette rummet.");
        }
    } catch (fejl) {
        console.error("Netværksfejl ved sletning:", fejl);
    }
}

// DEL 3b: Samlet kapacitet per kommune
function bygVisning3b() {
    const tbody = document.querySelector("#kapacitet-tabel tbody");
    tbody.innerHTML = ""; // Tøm tabellen

    kommuner.forEach(k => {
        // Find alle rum, der tilhører én bestemt kommune
        const rumIKommune = rum.filter(r => r.kommune.id === k.id);
        
        // Læg alle kapaciteter sammen (reduce-metoden)
        const totalKapacitet = rumIKommune.reduce((sum, r) => sum + r.kapacitet, 0);

        const row = `<tr>
                        <td>${k.navn}</td>
                        <td>${rumIKommune.length}</td>
                        <td>${totalKapacitet}</td>
                    </tr>`;
        tbody.innerHTML += row;
    });
}

// Fylder dropdown-menuen med alle tilgængelige rum
function fyldAfstandDropdown() {
    const select = document.getElementById('afstand-rum-select');
    select.innerHTML = '<option value="">Vælg et rum...</option>';

    rum.forEach(r => {
        const opt = document.createElement('option');
        opt.value = r.id;
        opt.textContent = `${r.adresse} (${r.kommune.navn})`;
        select.appendChild(opt); // tilføjer det nye option (rum) til dropdown menu
    });
}

// Kalder Java API for at beregne afstanden
async function beregnAfstand() {
    const lat = document.getElementById('bruger-lat').value;
    const lon = document.getElementById('bruger-lon').value;
    const roomId = document.getElementById('afstand-rum-select').value;
    const resultatDiv = document.getElementById('afstand-resultat');

    if (!lat || !lon || !roomId) {
        alert("Udfyld venligst alle felter!");
        return;
    }

    try {
        // kalder @GetMapping("/distance") i BeskyttelsesrumController
        const response = await fetch(`${BASE_URL}/rooms/distance?userLat=${lat}&userLon=${lon}&roomId=${roomId}`);
        const afstand = await response.json();

        //afrunder resultatet til 2 decimaler for læsbarhed
        resultatDiv.innerHTML = `Afstand til rummet: ${afstand.toFixed(2)} km`;
    } catch (fejl) {
        resultatDiv.innerHTML = "Fejl: Kunne ikke beregne afstand.";
        console.error(fejl);
    }
}

// 
async function findNærmesteRum() {
    const lat = document.getElementById('bruger-lat').value;
    const lon = document.getElementById('bruger-lon').value;
    const resultatDiv = document.getElementById('afstand-resultat');

    if (!lat || !lon) {
        alert("Indtast venligst dine koordinater først!");
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/rooms/sorted?userLat=${lat}&userLon=${lon}`);
        const data = await response.json(); // liste af RoomDistanceDTO

        let html = "<h4>Rum sorteret efter afstand:</h4><ul>";
        data.forEach(item => {
            html += `<li><strong>${item.distance.toFixed(2)} km</strong> - ${item.room.adresse} (${item.room.kommune.navn})</li>`;
        });
        html += "</ul>";

        resultatDiv.innerHTML = html;
    } catch (fejl) {
        console.error("Fejl ved hentning af sorteret liste:", fejl);
    }
}

// Start-kommando: Hent data når siden åbner
hentAltData();