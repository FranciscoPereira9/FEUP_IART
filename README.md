# FEUP_IART
[Projeto 1 - Folding Blocks](https://github.com/BernardoCMoreira/FEUP_IART#projeto-1-folding-blocks)
## Autores:
*****
* Bernardo Moreira - up201604014
* Francisco Pereira - up201605306
* Filipe Nogueira - up201604129

## PROJETO 1: FOLDING BLOCKS
*****
* Neste projeto é pretendido implementar um algoritmo de  inteligência artificial capaz de vencer uma série de niveis do jogo **Folding Blocks**. Este jogo inicialmente composto por um tabuleiro com uma ou mais peças, tem como objetivo, ocupar todos os espaços dos tabuleiros utilizando essas peças. 
Esta utilização é feita através de uma seleção da peça a jogar, seguida de uma transformação simétrica, isto é, cada movimento vai **duplicar** o tamanho da peça na direção escolhida.

* Representação do estado : O tabuleiro do jogo vai ser representado por uma matriz com tamanho variável (varia conforme o nivel).
Esta matriz é um `int[][]` cujo valor inicial para todas as posições é 0 representando assim os espaços vazios. Seguidamente, e consoante o nivel, são predefinidas peças para se jogar, por exemplo, no nivel 1 só é colocada uma peça na primeira posição, portanto ` board[0][0] = 1 `. Caso existam mais peças o id desta vai incrementando.

* Teste objetivo : O jogo acaba quando o tabuleiro não tiver espaços vazios. Ou seja, quando em todas as posições do tabuleiro x e y, `board[x][y]` seja diferente de 0.


### Implementação do jogo

* Linguagem escolhida para desenvolvimento do código : **Java**

* Trabalho realizado em **Eclipse** & **VSCode**
* Código encontra-se dentro de uma pasta **src** contendo os seguintes ficheiros : 
    * Main.java
    * Game.java 
    * Level.java
    * Logic.java

* A classe Level trata de definir, e desenhar os niveis. Trata da atribuição de cores consoante os valores da matriz e trata de atualizar a mesma.
   * ![alt text](https://github.com/BernardoCMoreira/FEUP_IART/blob/master/FoldingBlocks/images/level.PNG "Level")

* A classe Logic verifica a logica de jogo. Trata de fazer as jogadas, bem como as verificações de jogada e de fim de jogo. 
   * ![alt text](https://github.com/BernardoCMoreira/FEUP_IART/blob/master/FoldingBlocks/images/logic.PNG "Level")

* Finalmente, tanto a classe Main como Game servem para funcionalidades da interface. Permite a criação de uma janela para o jogo, bem como permite à classe Level que desenhe nesta mesma interface. Inicialmente implementamos também deteção de pressão de teclas, para testarmos as funcionalidades do jogo antes de implementar os algoritmos.
   * ![alt text](https://github.com/BernardoCMoreira/FEUP_IART/blob/master/FoldingBlocks/images/game.PNG "Level")
   * ![alt text](https://github.com/BernardoCMoreira/FEUP_IART/blob/master/FoldingBlocks/images/main.PNG "Level")

### Representação do estado inicial do nivel 6
![alt text](https://github.com/BernardoCMoreira/FEUP_IART/blob/master/FoldingBlocks/images/level6_init.PNG "Level 6 init")

### Representação do estado final do nivel 6
![alt text](https://github.com/BernardoCMoreira/FEUP_IART/blob/master/FoldingBlocks/images/level6_won.PNG "Level 6 won")
