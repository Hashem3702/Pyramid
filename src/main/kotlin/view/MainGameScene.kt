package view

import entity.Card
import service.RootService
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label

import tools.aqua.bgw.util.BidirectionalMap
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import service.CardImageLoader
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.uicomponents.Orientation
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.DEFAULT_CARD_HEIGHT
import tools.aqua.bgw.util.Font

import java.awt.Color

//
/**
 * This is the main thing for the Pyramid game. The scene shows the complete table at once.
 */
class MainGameScene(private val rootService: RootService): BoardGameScene(1920, 1080), Refreshable {
    private val pairs = mutableListOf<Card>()
    private val pairViews = mutableListOf<CardView>()

    private val passCounter = Label( width = 200, height = 50,
        posX = 1600, posY =1020, text = "Passcounter: 0",
        font = Font(24.0, Color.BLACK,"Open Sans",Font.FontWeight.BOLD),
        visual = ColorVisual.WHITE)

    private val player1Name =  Label( width = 150, height = 50,
        posX = 30, posY =40,
        font = Font(24.0, Color.BLACK,"Open Sans", Font.FontWeight.BOLD),
        visual = ColorVisual.WHITE
    )

    private val player2Name =  Label( width = 150, height = 50,
        posX = 1750, posY =40,
        font = Font(24.0, Color.BLACK,"Open Sans",Font.FontWeight.BOLD),
        visual = ColorVisual.WHITE)

    private val player1Points = Label( width = 150, height = 50,
        posX = 30, posY =90, text = "Points: 0",
        font = Font(24.0, Color.BLACK,"Open Sans",Font.FontWeight.BOLD),
        visual = ColorVisual.WHITE)

    private val player2Points = Label( width = 150, height = 50,
        posX = 1750, posY =90, text = "Points: 0",
        font = Font(24.0, Color.BLACK,"Open Sans",Font.FontWeight.BOLD),
        visual = ColorVisual.WHITE)

    private val confirmButton =Button(
            width = 300, height = 85,
    posX = 1600, posY = 800,
    text = "Confirm Selection",
        font = Font(24.0, Color.WHITE,"Open Sans",Font.FontWeight.BOLD),
        visual = ColorVisual(Color(0x81BA29)) ).apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->
                if(pairs.size==2) {
                    rootService.spielerService.checkPair(pairs[0], pairs[1])
                    rootService.spielerService.removePair(pairs[0],pairs[1])
                }
            // hier muss eine Meldung ausgegeben werden , wenn die List keine zwei Cards hat

            }
        }
    }
    private val passButton = Button( width = 300, height = 85,
        posX = 1600, posY = 900,
        text = "Pass",
        font = Font(24.0, Color.WHITE,"Open Sans",Font.FontWeight.BOLD),
        visual = ColorVisual(Color(0x0500FF)))
        .apply {
            onMouseClicked = {
                rootService.currentGame?.let { game ->
                    rootService.spielerService.pass()
                }
            }
        }
    val mainMenuButton = Button( width = 300, height = 85,
        posX = 20, posY = 960,
        text = "Main Menu",
        font = Font(24.0, Color.WHITE,"Open Sans",Font.FontWeight.BOLD),
        visual = ColorVisual(Color(0xFDB55B))).apply {

    }

    private val drawStack = LabeledStackView(posX = 1770, posY = 350, "Draw Stack").apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->
                rootService.spielerService.drawCard()
            }
        }
    }
    private val reserveStack = LabeledStackView(posX = 10, posY = 350, "Reserve Stack").apply {

        onMouseClicked = {
            val game = rootService.currentGame
            checkNotNull(game)
            /**      pairs.add(0,game.reserveStack.first())
            this.scale(1.15)
            pairViews.add(0,this)
            if(pairs.size >2 ){
            pairs.removeLast()
            pairViews.last().scale(1)
            pairViews.removeLast()
            }
             */
                    pairs.add(0,game.reserveStack.first())
                    this.peek().scale(1.18)
                    pairViews.add(0,this.peek())
                    if(pairs.size > 2) {
                        pairs.removeLast()
                        pairViews.last().scale(1)
                        pairViews.removeLast()
                }
            }
        }


    private val firstLinearLayout = LinearLayout<CardView>(
        posX = 461,
        posY = 0,
        orientation = Orientation.HORIZONTAL,
        alignment = Alignment.CENTER,
        spacing = 15,
        width = 1000,
        height = DEFAULT_CARD_HEIGHT,
    ).apply { this.scale(0.65) }
        // LinearLayout<CardView>(posX = 900, posY = 350,width=0,height=200,)

    private val secondLinearLayout =  LinearLayout<CardView>(
        posX = 461,
        posY = 150,
        orientation = Orientation.HORIZONTAL,
        alignment = Alignment.CENTER,
        spacing = 15,
        width = 1000,
        height = DEFAULT_CARD_HEIGHT,
    ).apply { this.scale(0.65) }
        // LinearLayout<CardView>(posX = 875, posY = 450,width=100,height=200,)

    private val thirdLinearLayout =LinearLayout<CardView>(
        posX = 461,
        posY = 300,
        orientation = Orientation.HORIZONTAL,
        alignment = Alignment.CENTER,
        spacing = 15,
        width = 1000,
        height = DEFAULT_CARD_HEIGHT,
    ).apply { this.scale(0.65) }
        // LinearLayout<CardView>(posX = 850, posY = 550,width=200,height=200,)

    private val fourthLinearLayout = LinearLayout<CardView>(
        posX = 461,
        posY = 450,
        orientation = Orientation.HORIZONTAL,
        alignment = Alignment.CENTER,
        spacing = 15,
        width = 1000,
        height = DEFAULT_CARD_HEIGHT,
    ).apply { this.scale(0.65) }
        //LinearLayout<CardView>(posX = 825, posY = 650,width=200,height=200,)

    private val fifthLinearLayout = LinearLayout<CardView>(
        posX = 461,
        posY = 600,
        orientation = Orientation.HORIZONTAL,
        alignment = Alignment.CENTER,
        spacing = 15,
        width = 1000,
        height = DEFAULT_CARD_HEIGHT,
    ).apply { this.scale(0.65) }
        // LinearLayout<CardView>(posX = 800, posY = 750,width=300,height=200,)

    private val sixthLinearLayout =LinearLayout<CardView>(
        posX = 461,
        posY = 750,
        orientation = Orientation.HORIZONTAL,
        alignment = Alignment.CENTER,
        spacing = 15,
        width = 1000,
        height = DEFAULT_CARD_HEIGHT,
    ).apply { this.scale(0.65) }
        //LinearLayout<CardView>(posX = 775, posY = 850,width=400,height=200,)

    private val seventhLinearLayout =  LinearLayout<CardView>(
        posX = 461,
        posY = 900,
        orientation = Orientation.HORIZONTAL,
        alignment = Alignment.CENTER,
        spacing = 15,
        width = 1000,
        height = DEFAULT_CARD_HEIGHT,
    ).apply { this.scale(0.65) }
        //LinearLayout<CardView>(posX = 750,950,width=500,height=200,)


    private val cardMap : BidirectionalMap<Card,CardView> = BidirectionalMap()

    init {
        background = ColorVisual(Color(0xFFE9D9))
        // dark green for "Casino table" flair

        addComponents(
            passCounter,passButton,
            confirmButton,mainMenuButton,
            player1Name,player2Name,
            player1Points,player2Points,
            drawStack,reserveStack,
            firstLinearLayout,secondLinearLayout,
            thirdLinearLayout,fourthLinearLayout,
            fifthLinearLayout,sixthLinearLayout,
            seventhLinearLayout
        )

    }

    /**
     * Initializes the complete GUI, i.e. the four stack views (left, right, played,
     * collected) of each player.
     */
    override fun refreshAfterNewGame() {
        val game = rootService.currentGame
        checkNotNull(game) { "No started game found." }
        cardMap.clear()

        val cardImageLoader = CardImageLoader()
        drawStack.clear()
        fifthLinearLayout.clear()
        secondLinearLayout.clear()
        thirdLinearLayout.clear()
        fourthLinearLayout.clear()
        fifthLinearLayout.clear()
        sixthLinearLayout.clear()
        seventhLinearLayout.clear()

        initializeStackView(game.drawStack, drawStack, cardImageLoader)
        initializePyramidView(game.pyramid[0],firstLinearLayout,cardImageLoader)
        initializePyramidView(game.pyramid[1],secondLinearLayout,cardImageLoader)
        initializePyramidView(game.pyramid[2],thirdLinearLayout,cardImageLoader)
        initializePyramidView(game.pyramid[3],fourthLinearLayout,cardImageLoader)
        initializePyramidView(game.pyramid[4],fifthLinearLayout,cardImageLoader)
        initializePyramidView(game.pyramid[5],sixthLinearLayout,cardImageLoader)
        initializePyramidView(game.pyramid[6],seventhLinearLayout,cardImageLoader)

        reserveStack.clear()
        passCounter.text = "Passcounter: ${game.passCounter}"
        player1Points.text = "Points: ${game.player1.point}"
        player2Points.text = "Points: ${game.player2.point}"
        player1Name.text = game.player1.name
        player2Name.text = game.player2.name
        if(game.currentPlayer.name == player1Name.text){
            player1Name.visual = ColorVisual.GREEN
            player2Name.visual = ColorVisual.WHITE
        }
        else{
            player2Name.visual = ColorVisual.GREEN
            player1Name.visual = ColorVisual.WHITE
        }

    }

    /**
     * clears [stackView], adds a new [CardView] for each
     * element of [stack] onto it, and adds the newly created view/card pair
     * to the global [cardMap].
     */
    private fun initializeStackView(stack: MutableList<Card>,
                                    stackView: LabeledStackView,
                                    cardImageLoader: CardImageLoader) {
        stackView.clear()
        stack.reversed().forEach { card ->
            val cardView = CardView(
                height = 200,
                        //150,
                width = 130,
                        //80,
                front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
                back = ImageVisual(cardImageLoader.backImage)
            )
            stackView.add(cardView)
            cardMap.add(card to cardView)
        }
    }
    private fun initializePyramidView(row: MutableList<Card>,
                                      rowLinearLayout: LinearLayout<CardView>,
                                      cardImageLoader: CardImageLoader) {
        rowLinearLayout.clear()
        row.forEach { card ->
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
                back = ImageVisual(cardImageLoader.backImage)
            ).apply { onMouseClicked = {
                val game = rootService.currentGame
                checkNotNull(game)
                pairs.add(0,card)
                this.scale(1.15)
                pairViews.add(0,this)
                if(pairs.size > 2 ){
                    pairs.removeLast()
                    pairViews.last().scale(1)
                    pairViews.removeLast()
                }
            } }
           if(card.isFaceUp){
                cardView.showFront()
            }
            else{
                cardView.showBack()
            }

            rowLinearLayout.add(cardView)
            cardMap.add(card to cardView)

        }

    }
    private fun moveCardView(cardView: CardView, toStack: LabeledStackView) {

        cardView.showFront()
        cardView.removeFromParent()
        toStack.add(cardView)
    }

    override fun refreshAfterPass() {
        val game = rootService.currentGame
        checkNotNull(game)
        passCounter.text = "Passcounter: ${game.passCounter}"
        if(game.passCounter == 2) {
            refreshAfterGameEnd(mutableListOf(game.player1, game.player2))
        }
        pairs.clear()
        if(pairViews.isNotEmpty()) {
            pairViews.first().scale(1)
            if(pairViews.size == 2){
                pairViews.last().scale(1)
            }
            pairViews.clear()
        }
    }

    override fun refreshAfterDrawCard() {
        val game = rootService.currentGame
        checkNotNull(game) { "No game found." }
        moveCardView(cardMap.forward(game.reserveStack.first()),reserveStack)
        passCounter.text = "Passcounter : ${game.passCounter}"
        pairs.clear()
        if(pairViews.isNotEmpty()) {
            pairViews.first().scale(1)
            if(pairViews.size == 2){
                pairViews.last().scale(1)
            }
            pairViews.clear()
        }

    }
    override fun refreshAfterRemovePair(card1:Card,card2:Card){
        val game = rootService.currentGame
        checkNotNull(game)
        val cardView1 = cardMap.forward(card1)
        val cardView2 = cardMap.forward(card2)

        cardView1.removeFromParent()
        cardView2.removeFromParent()

        player1Points.text = "Points : ${game.player1.point}"
        player2Points.text = "Points : ${game.player2.point}"


    }
   override fun refreshAfterTurnCards(cards: List<Card>) {
       if(cardMap.isNotEmpty()) {
           cards.forEach { card ->
               val cardView = cardMap.forward(card)
               if (secondLinearLayout.contains(cardView)) {
                   turnCards(secondLinearLayout, cardView)
               } else if (thirdLinearLayout.contains(cardView)) {
                   turnCards(thirdLinearLayout, cardView)
               } else if (fourthLinearLayout.contains(cardView)) {
                   turnCards(fourthLinearLayout, cardView)
               } else if (fifthLinearLayout.contains(cardView)) {
                   turnCards(fifthLinearLayout, cardView)
               } else if (sixthLinearLayout.contains(cardView)) {
                   turnCards(sixthLinearLayout, cardView)
               } else {
                   turnCards(seventhLinearLayout, cardView)
               }
           }
       }

    }
    private fun turnCards(linearLayout: LinearLayout<CardView>,cardView:CardView){
    linearLayout.forEach { card->
        if(card == cardView){
            card.showFront()
        }
    }

    }

    override fun refreshAfterSwitchPlayer() {
        val game = rootService.currentGame
        checkNotNull(game)
        if(game.currentPlayer.name == player1Name.text){
            player1Name.visual = ColorVisual.GREEN
            player2Name.visual = ColorVisual.WHITE
        }
        else{
            player2Name.visual = ColorVisual.GREEN
            player1Name.visual = ColorVisual.WHITE
        }
    }
}