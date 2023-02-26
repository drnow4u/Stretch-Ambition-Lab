# Stretch Ambition
## Make a spiral

One day ago I heard about software development Kata. I found https://www.codewars.com/ and start to do Kata.

[Make a spiral](https://www.codewars.com/kata/534e01fbbb17187c7e0000c6/java)

The goal is to draw spiral:

```asciidoc
0000000000
.........0
00000000.0
0......0.0
0.0000.0.0
0.0..0.0.0
0.0....0.0
0.000000.0
0........0
0000000000
```



The first time I use [Logo](https://en.wikipedia.org/wiki/Logo) was on [ZX Spectrum+](https://en.wikipedia.org/wiki/ZX_Spectrum)
or [Atari 800XL](https://en.wikipedia.org/wiki/Atari_8-bit_family)

[More Logo command](https://pclogo.fandom.com/wiki/Special:AllPages?from=%22name%22+is+already+in+use.+Try+a+different+name.)
[Logo in the browser](https://www.calormen.com/jslogo/#)
[Logo Tutorial](https://www.tutorialspoint.com/logo/index.htm)


Spiral solution in Logo:

```shell
clearscreen
make "a 200
rt 90
fd :a 
rt 90
make "r :a / 4
repeat :r [
  repeat 2 [
    fd :a 
    rt 90  
  ]
  make "a :a - 4
]
```

[Domain-specific language](https://en.wikipedia.org/wiki/Domain-specific_language)