# Planlegging av reaksjonstestspill (Del 0)

## Beskrivelse av appen
Reaksjonstestspillet er en app der brukeren tester sin reaksjonstid ved å trykke på mellomromstasten når en rød knapp vises på skjermen. Appen vil måle tiden fra knappen vises til brukeren reagerer. Hovedmålet er å reagere så raskt som mulig. Appen vil lagre tidligere resultater slik at brukeren kan se sin egen utvikling over tid og sammenligne sine beste resultater. Brukeren vil kunne gjennomføre flere tester i en økt og se statistikk som gjennomsnittlig reaksjonstid, beste tid, og utvikling over flere forsøk.

## Grunnklasser
Appen vil ha følgende grunnklasser:

1. **ReactionTest**: Representerer en enkeltstående reaksjonstest med egenskaper som:
   - Tidspunkt for når stimulusen (rød knapp) vises
   - Tidspunkt for når brukeren reagerer
   - Beregnet reaksjonstid
   - Status på testen (venter, viser stimulus, fullført, for tidlig klikk)
   
   Denne klassen vil ha kalkulasjoner for å beregne reaksjonstid og validere gyldige reaksjoner.

2. **ReactionGame**: Administrerer en serie med reaksjonstester og oppretter nye `ReactionTest`-objekter. Den vil:
   - Holde styr på alle gjennomførte tester i en økt
   - Beregne statistikk som gjennomsnitt, median, beste tid
   - Generere tilfeldige intervaller mellom hver test
   - Implementere et grensesnitt, f.eks. `Iterable<ReactionTest>` for å kunne iterere gjennom testene

## Filbehandling
Appen vil lagre følgende informasjon til fil:
- Individuelle testresultater (reaksjonstider) med tidsstempel
- Brukerens personlige rekorder
- Statistikk for hver økt (gjennomsnitt, beste tid, antall tester)

Dataene vil lagres i et tekstformat der hver linje representerer enten en enkelt test eller en økt, med verdier separert av komma eller annet tegn. En egen klasse, `ReactionFileHandler`, vil håndtere lesing fra og skriving til fil.

## Testing
Følgende vil bli testet i appen:
1. Beregning av reaksjonstid (at tiden beregnes riktig)
2. Validering av reaksjoner (at for tidlige klikk registreres korrekt)
3. Statistikkberegning (at gjennomsnitt, median, og beste tid beregnes riktig)
4. Generering av tilfeldige intervaller (at de ligger innenfor definerte grenser)
5. Fillagring og -innlesing (at data lagres og leses korrekt)
6. Tilstandshåndtering i ReactionTest (at tilstandsoverganger skjer korrekt)
