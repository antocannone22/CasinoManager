package com.tuonome.casinomanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CasinoManagerApp()
        }
    }
}

@Composable
fun CasinoManagerApp() {
    var soldi by remember { mutableStateOf(20000) }
    var giorno by remember { mutableStateOf(1) }
    
    // Infrastrutture e Attrezzature
    var slotMachines by remember { mutableStateOf(5) }
    var tavoliBlackjack by remember { mutableStateOf(2) }
    
    // Sicurezza e Server Room
    var livelloTelecamere by remember { mutableStateOf(1) }
    var guardie by remember { mutableStateOf(2) }
    
    // Gestione Animazioni ed Eventi
    var mostraAnimazione by remember { mutableStateOf(false) }
    var testoAnimazione by remember { mutableStateOf("") }
    var logMessaggi by remember { mutableStateOf("Benvenuto Direttore! Controlla la sala e gestisci i rischi.") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Giorno $giorno", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Cassa: $soldi €", fontSize = 28.sp, color = Color(0xFF007A33), fontWeight = FontWeight.ExtraBold)
            
            Spacer(modifier = Modifier.height(16.dp))

            // SEZIONE GESTIONE SALE
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("🎰 Sale da Gioco", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Slot Machines: $slotMachines | Tavoli Blackjack: $tavoliBlackjack")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(onClick = { if (soldi >= 2500) { soldi -= 2500; slotMachines++ } }) {
                            Text("+ Slot (2.5k€)")
                        }
                        Button(onClick = { if (soldi >= 8000) { soldi -= 8000; tavoliBlackjack++ } }) {
                            Text("+ Tavolo (8k€)")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // SEZIONE SICUREZZA & SALA MONITOR
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("🎥 Sala Sicurezza & Computer", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Liv. Telecamere AI: $livelloTelecamere | Staff Guardie: $guardie")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(
                            onClick = { if (soldi >= 5000) { soldi -= 5000; livelloTelecamere++ } },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                        ) {
                            Text("Upgrade AI Cam (5k€)")
                        }
                        Button(
                            onClick = { if (soldi >= 2000) { soldi -= 2000; guardie++ } },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                        ) {
                            Text("+1 Guardia (2k€)")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // BOTTONE SIMULAZIONE GIORNATA
            Button(
                onClick = {
                    val incassoSlot = slotMachines * Random.nextInt(200, 450)
                    val incassoTavoli = tavoliBlackjack * Random.nextInt(600, 1800)
                    val spese = (slotMachines * 50) + (tavoliBlackjack * 200) + (guardie * 120)
                    var profitto = incassoSlot + incassoTavoli - spese

                    // Controllo Contatori di Carte / Bari
                    val probabilitaBari = Random.nextInt(100)
                    if (tavoliBlackjack > 0 && probabilitaBari < 40) {
                        val rilevato = (livelloTelecamere * 25) + (guardie * 10) > Random.nextInt(100)
                        
                        val scena = if (rilevato) {
                            profitto += 2000
                            when (Random.nextInt(1, 11)) {
                                1 -> "🚨 ANIMAZIONE: I buttafuori scortano il baro all'uscita pacificamente."
                                2 -> "🚨 ANIMAZIONE: Il baro viene portato nella stanza sul retro e minacciato di non tornare mai più."
                                3 -> "🚨 ANIMAZIONE: Espulsione violenta! Le guardie lo lanciano fisicamente fuori dalle porte."
                                4 -> "🚨 ANIMAZIONE: Il truffatore tenta la fuga, scivola sulle fiches e viene ammanettato."
                                5 -> "🚨 ANIMAZIONE: Interrogatorio seminterrato con telecamere spente. Soldi confiscati!"
                                6 -> "🚨 ANIMAZIONE: Il baro viene seguito nel vicolo per un 'confronto fisico' lontano da sguardi."
                                7 -> "🚨 ANIMAZIONE: Arriva la Polizia chiamata in segreto dal Pit Boss per arrestare il truffatore."
                                8 -> "🚨 ANIMAZIONE: Costretto a restituire tutti i contanti direttamente dalla valigetta."
                                9 -> "🚨 ANIMAZIONE: Rissa sfiorata! La sicurezza lo immobilizza a terra davanti ai clienti."
                                10 -> "🚨 ANIMAZIONE: Il baro viene messo su una lista nera nazionale e scortato a una limousine."
                            }
                        } else {
                            profitto -= 4000
                            "⚠️ FRODE: Un contatore di carte ha sbancato i tavoli da Blackjack (-4000€)! Migliora le telecamere."
                        }

                        testoAnimazione = scena
                        mostraAnimazione = true
                    }

                    soldi += profitto
                    giorno++
                    logMessaggi = "Giorno ${giorno - 1} Concluso:\nIncassi: +${incassoSlot + incassoTavoli}€ | Spese: -$spese€\nRisultato Netto: $profitto€"
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003366)),
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Text("Chiudi & Incassa Giornata", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(color = Color.LightGray.copy(alpha = 0.2f), modifier = Modifier.fillMaxWidth()) {
                Text(logMessaggi, modifier = Modifier.padding(12.dp), color = Color.Black)
            }
        }

        // SCENA ANIMATA FULLSCREEN OVERLAY
        AnimatedVisibility(
            visible = mostraAnimazione,
            enter = fadeIn(animationSpec = tween(400)),
            exit = fadeOut(animationSpec = tween(400))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(colors = CardDefaults.cardColors(containerColor = Color.DarkGray)) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(testoAnimazione, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator(color = Color.Red)
                    }
                }
            }
        }
    }

    // Auto-chiusura dell'evento visivo dopo 4 secondi
    LaunchedEffect(mostraAnimazione) {
        if (mostraAnimazione) {
            delay(4000)
            mostraAnimazione = false
        }
    }
}
