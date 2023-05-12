# Blackjack CLI and GUI

**run `make` from command-line and you should be good to go**

*   to play CLI run `java -cp bin Driver2` (in `.`)
    or `java Driver2` (in `bin/`)
*   to play GUI run `java -cp bin -jar bin/bjgame.jar` (in `.`)
    or `java -jar bjgame.jar` (in `bin/`)

main program is in `bin/blackjack.jar` so just run
`java -jar bin/bjgame.jar`

for time: i started writing this on Dec 1, 2021 and hastily
finished GUI before 2022? (CLI took no time lol the only confusion
i had was for the game)

oh yeah right... [here][bjrules] are the game rules that i utterly
disregarded with my program (if anything dealer and player are level?
i thought like it should have been asymmetrical so that dealer has a
higher chance of winning...? well i'm a CS student, not a... card game
casino person i guess haha)

[bjrules]: https://www.blackjackinfo.com/blackjack-rules/

about how i got the cards, well... i got them from
[here](https://en.m.wikipedia.org/wiki/File:Atlasnye_playing_cards_deck.svg),
rasterized it, and ran Python to crop these out automatically
(not because i'm lazy but because my perfectionism would KILL me
if the cards have unmatching dimensions UGGGH)

that among us card was a joke btw, wanted to make it funny to
my E-TOEFL classmates (AND THEY DIDNT EVEN BUDGE BJGFHGDABGJ-)
