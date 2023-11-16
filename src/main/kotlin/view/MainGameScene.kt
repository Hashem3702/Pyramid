package view

import entity.Card
import service.RootService
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.BidirectionalMap
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import service.CardImageLoader
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.core.Alignment

//
class MainGameScene(private val rootService: RootService): BoardGameScene(1920, 1080), Refreshable {
    private val pairs = mutableListOf<Card>()

    private val passCounter = Label( width = 100, height = 35,
        posX = 1750, posY =1020, text = "Passcounter: 0")

    private val player1Name =  Label( width = 100, height = 35,
        posX = 30, posY =40)

    private val player2Name =  Label( width = 100, height = 35,
        posX = 1800, posY =40)

    private val player1Points = Label( width = 100, height = 35,
        posX = 30, posY =70, text = "Points: 0")

    private val player2Points = Label( width = 100, height = 35,
        posX = 1800, posY =70, text = "Points: 0")

    private val confirmButton =Button(
            width = 140, height = 35,
    posX = 1750, posY = 900,
    text = "Confirm Selection")
    .apply {
        visual = ColorVisual(221, 136, 136)
        onMouseClicked = {
            rootService.currentGame?.let { game ->
                if(pairs.size==2) {
                    rootService.spielerService.checkPair(pairs[0], pairs[1])
                }
            // hier muss eine Meldung ausgegeben werden , wenn die List keine zwei Cards hat
            }
        }
    }
    private val passButton = Button( width = 140, height = 35,
        posX = 1750, posY = 960,
        text = "Pass")
        .apply {
            visual = ColorVisual(221, 136, 136)
            onMouseClicked = {
                rootService.currentGame?.let { game ->
                    rootService.spielerService.pass()
                }
            }
        }
    private val mainMenuButton = Button( width = 140, height = 35,
        posX = 20, posY = 960,
        text = "Main Menu")
        .apply {
            visual = ColorVisual(221, 136, 136)
        }
    private val drawStack = LabeledStackView(posX = 1780, posY = 350, "Draw Stack").apply {
        onMouseClicked = {
            rootService.currentGame?.let { game ->
                rootService.spielerService.drawCard()
            }
        }
    }
    private val reserveStack = LabeledStackView(posX = 10, posY = 350, "Reserve Stack").apply {
        val game = rootService.currentGame

        game?.reserveStack?.first()?.let { pairs.add(0, it) }
        if(pairs.size == 2){
            pairs.removeLast()
        }
    }

    private val firstLinearLayout = LinearLayout<CardView>(posX = 900, posY = 350,width=0,height=200)
    private val secondLinearLayout = LinearLayout<CardView>(posX = 875, posY = 450,width=100,height=200)
    private val thirdLinearLayout = LinearLayout<CardView>(posX = 850, posY = 550,width=200,height=200)
    private val fourthLinearLayout = LinearLayout<CardView>(posX = 825, posY = 650,width=200,height=200)
    private val fifthLinearLayout = LinearLayout<CardView>(posX = 800, posY = 750,width=300,height=200)
    private val sixthLinearLayout = LinearLayout<CardView>(posX = 775, posY = 850,width=400,height=200)
    private val seventhLinearLayout = LinearLayout<CardView>(posX = 750,950,width=500,height=200)



    private val cardMap : BidirectionalMap<Card,CardView> = BidirectionalMap()

    init {
        background = ColorVisual(108, 168, 59)
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
        player1Name.text = game.player1.name
        player2Name.text = game.player2.name
        cardMap.clear()

        val cardImageLoader = CardImageLoader()

        initializeStackView(game.drawStack, drawStack, cardImageLoader)
        initializePyramidView(game.pyramid[0],firstLinearLayout,cardImageLoader)
        initializePyramidView(game.pyramid[1],secondLinearLayout,cardImageLoader)
        initializePyramidView(game.pyramid[2],thirdLinearLayout,cardImageLoader)
        initializePyramidView(game.pyramid[3],fourthLinearLayout,cardImageLoader)
        initializePyramidView(game.pyramid[4],fifthLinearLayout,cardImageLoader)
        initializePyramidView(game.pyramid[5],sixthLinearLayout,cardImageLoader)
        initializePyramidView(game.pyramid[6],seventhLinearLayout,cardImageLoader)

        reserveStack.clear()

    }

    /**
     * clears [stackView], adds a new [CardView] for each
     * element of [stack] onto it, and adds the newly created view/card pair
     * to the global [cardMap].
     */
    private fun initializeStackView(stack: MutableList<Card>, stackView: LabeledStackView, cardImageLoader: CardImageLoader) {
        stackView.clear()
        stack.reversed().forEach { card ->
            val cardView = CardView(
                height = 150,
                width = 80,
                front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
                back = ImageVisual(cardImageLoader.backImage)
            )
            stackView.add(cardView)
            cardMap.add(card to cardView)
        }
    }
    private fun initializePyramidView(row: MutableList<Card>, rowLinearLayout: LinearLayout<CardView>, cardImageLoader: CardImageLoader) {
        rowLinearLayout.clear()
        row.forEach { card ->
            val cardView = CardView(
                height = 100,
                width = 50,
                front = ImageVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
                back = ImageVisual(cardImageLoader.backImage)
            ).apply { onMouseClicked = {
                val game = rootService.currentGame
                checkNotNull(game)
                pairs.add(0,card)
                if(pairs.size == 2){
                    pairs.removeLast()
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

}