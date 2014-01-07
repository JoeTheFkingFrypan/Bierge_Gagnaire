package utt.fr.rglb.main.java.view.graphics.fxml;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.game.controller.GameControllerGraphicsOriented;
import utt.fr.rglb.main.java.game.model.GameFlag;
import utt.fr.rglb.main.java.main.Server;
import utt.fr.rglb.main.java.view.graphics.CustomImageView;
import utt.fr.rglb.main.java.view.graphics.GraphicsPlayers;
import utt.fr.rglb.main.java.view.graphics.GraphicsReferences;

public class FXMLControllerGameScreen extends AbstractFXMLController {
	private static final Logger log = LoggerFactory.getLogger(FXMLControllerGameScreen.class);
	private GameControllerGraphicsOriented gameController;
	private CustomImageView referenceImageView;
	private GraphicsPlayers graphicsPlayers;
	private Queue<String> textToDisplay;
	private Text eventsAnnouncer;

	@FXML private GridPane mainGrid;
	@FXML private GridPane playerTwoCardsGrid;
	@FXML private Label playerTwoName;
	@FXML private GridPane eventsGrid;
	@FXML private Label playerOneName;
	@FXML private GridPane playerOneCardsGrid;

	/* ========================================= CONSTRUCTOR========================================= */

	public FXMLControllerGameScreen() {
		this.gameController = Server.getGameController();
		this.gameController.setCurrentFXMLController(this);
		this.textToDisplay = new LinkedList<String>();
	}

	/* ========================================= INITIALIZING ========================================= */

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		this.eventsAnnouncer = new Text("");
		this.eventsAnnouncer.getStyleClass().add("fancyText");
		this.eventsAnnouncer.setEffect(createSwaggifiedAnnoucementEffect());
		this.mainGrid.add(this.eventsAnnouncer, 0, 2);

		GameFlag gameFlag = this.gameController.triggerEffectFromFirstCard();
		this.graphicsPlayers = new GraphicsPlayers(this.gameController.getAllPlayers(),this);

		CardsModelBean references = this.gameController.getReferences();
		this.referenceImageView = new CustomImageView(references.getLastCardPlayed(),this);
		mainGrid.add(this.referenceImageView, 0, 1);

		handlePlayerOne(references);
		handlePlayerTwo(references);
		
		this.gameController.applyEffectFromFirstCard(gameFlag);
		uncoverInitialCards(references);
	}
	
	private void handlePlayerOne(CardsModelBean references) {
		playerOneName.setText(this.graphicsPlayers.getAliasFromPlayerNumber(0));
		for(CustomImageView imageFromCard : this.graphicsPlayers.getDisplayableCardsFromPlayer(0, references)) {
			int currentCardIndex = playerOneCardsGrid.getChildren().size();
			playerOneCardsGrid.add(imageFromCard, currentCardIndex, 0);
		}
	}

	private void handlePlayerTwo(CardsModelBean references) {
		playerTwoName.setText(this.graphicsPlayers.getAliasFromPlayerNumber(1));
		for(CustomImageView imageFromCard : this.graphicsPlayers.getDisplayableCardsFromPlayer(1, references)) {
			int currentCardIndex = playerTwoCardsGrid.getChildren().size();
			playerTwoCardsGrid.add(imageFromCard, currentCardIndex, 0);
		}
	}

	private void uncoverInitialCards(CardsModelBean references) {
		ParallelTransition startingAnimation = new ParallelTransition();
		SequentialTransition mainAnimation = new SequentialTransition();
		mainAnimation.getChildren().add(annouceMessage("New round starting !"));
		mainAnimation.getChildren().add(this.referenceImageView.generateEffect());
		mainAnimation.getChildren().add(startTurnForInitialPlayer(references));
		startingAnimation.getChildren().add(mainAnimation);
		startingAnimation.play();
	}
	
	/* ========================================= TURN HANDLING ========================================= */

	private void playOneMoreTurn(final boolean activePlayerNeedsCardsFlipping, final boolean nextPlayerNeedsCardFlipping) {
		log.info("--Starting turn of " + this.graphicsPlayers.getAliasFromActivePlayer());
		if(activePlayerNeedsCardsFlipping) {
			SequentialTransition flip = this.graphicsPlayers.generateEffectFromActivePlayer();
			flip.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					gameController.playOneTurn(nextPlayerNeedsCardFlipping);
				}
			});
			flip.play();
		} else {
			this.gameController.playOneTurn(nextPlayerNeedsCardFlipping);
		}
	}
	
	/* ========================================= CARD PLAY ========================================= */
	
	public void chooseThisCardAndPlayIt(int cardIndex, CustomImageView thisImageView) {
		Card chosenCard = this.graphicsPlayers.chooseCardFromActivePlayerAt(cardIndex,thisImageView);
		log.info("    --Played : " + chosenCard);
		boolean stillHasCards = this.graphicsPlayers.findIfActivePlayerStillHasCards();
		//FIXME cheated
		int indexFromPlayer = this.graphicsPlayers.getIndexFromActivePlayer();
		setNewReference(indexFromPlayer,thisImageView);
		GameFlag event = this.gameController.activePlayerChose(chosenCard,stillHasCards,this.graphicsPlayers.deservesTheRightToAnnounceUno());
		if(event.equals(GameFlag.NORMAL)) {
			playOneMoreTurn(true,true);
		}
	}
	
	

	/* ========================================= CARD DRAW ========================================= */
	
	private void setNewReference(int indexFromPlayer, CustomImageView thisImageView) {
		log.debug("setNewReference");
		thisImageView.setAsReference();
		if(indexFromPlayer == 0) {
			this.playerOneCardsGrid.getChildren().remove(thisImageView);
			List<Node> nodesToShift = new ArrayList<Node>(playerOneCardsGrid.getChildren());
			playerOneCardsGrid.getChildren().clear();
			for(Node node : nodesToShift) {
				int currentCardIndex = playerOneCardsGrid.getChildren().size();
				playerOneCardsGrid.add(node, currentCardIndex, 0);
			}
		} else {
			this.playerTwoCardsGrid.getChildren().remove(thisImageView);
			List<Node> nodesToShift = new ArrayList<Node>(playerTwoCardsGrid.getChildren());
			playerTwoCardsGrid.getChildren().clear();
			for(Node node : nodesToShift) {
				int currentCardIndex = playerTwoCardsGrid.getChildren().size();
				playerTwoCardsGrid.add(node, currentCardIndex, 0);
			}
		}
		this.mainGrid.getChildren().remove(this.referenceImageView);
		this.referenceImageView = thisImageView;
		this.mainGrid.add(this.referenceImageView, 0, 1);
		
	}

	private SequentialTransition addCardToInitialPlayer(Card card, CardsModelBean references) {
		int cardIndex = this.graphicsPlayers.findNextCardIndex(0);
		CustomImageView imageView = new CustomImageView(card, cardIndex, references.isCompatibleWith(card),this);
		this.graphicsPlayers.addCardToPlayer(0, card,imageView);
		this.playerOneCardsGrid.add(imageView,this.playerOneCardsGrid.getChildren().size(),0);
		SequentialTransition test = displayCardDrawing(imageView);
		return test;
	}

	public SequentialTransition addCardToPlayer(int playerIndex, Collection<Card> cardsDrawn) {
		Preconditions.checkState(this.graphicsPlayers != null);
		Preconditions.checkNotNull(cardsDrawn);
		log.debug("addCardToPlayer --collection");
		List<CustomImageView> imageViewsFromCards = this.graphicsPlayers.addCardToPlayer(playerIndex, cardsDrawn,this.gameController.getReferences());
		if(playerIndex == 0) {
			this.playerOneCardsGrid.getChildren().clear();
			for(CustomImageView view : imageViewsFromCards) {
				this.playerOneCardsGrid.add(view,this.playerOneCardsGrid.getChildren().size(),0);
			}
		} else {
			this.playerTwoCardsGrid.getChildren().clear();
			for(CustomImageView view : imageViewsFromCards) {
				this.playerTwoCardsGrid.add(view,this.playerTwoCardsGrid.getChildren().size(),0);
			}
		}
		return this.graphicsPlayers.generateEffectFromPlayer(playerIndex);
	}		
	
	private void addCardToPlayer(int playerIndex, Card cardDrawn) {
		Preconditions.checkState(this.graphicsPlayers != null);
		Preconditions.checkNotNull(cardDrawn);
		log.debug("addCardToPlayer");
		List<CustomImageView> imageViewsFromCards = this.graphicsPlayers.addCardToPlayer(playerIndex, cardDrawn,this.gameController.getReferences());
		if(playerIndex == 0) {
			this.playerOneCardsGrid.getChildren().clear();
			for(CustomImageView view : imageViewsFromCards) {
				this.playerOneCardsGrid.add(view,this.playerOneCardsGrid.getChildren().size(),0);
			}
		} else {
			this.playerTwoCardsGrid.getChildren().clear();
			for(CustomImageView view : imageViewsFromCards) {
				this.playerTwoCardsGrid.add(view,this.playerTwoCardsGrid.getChildren().size(),0);
			}
		}
	}
	
	private SequentialTransition displayCardDrawing(CustomImageView imageView) {
		SequentialTransition flipSide = imageView.generateEffect();
		PauseTransition pause = new PauseTransition(Duration.millis(1000));
		return new SequentialTransition(flipSide,pause);
	}

	/* ========================================= EFFECTS ========================================= */
	
	public void triggerReverseCurrentOrder(String message) {
		log.debug("triggerReverseCurrentOrder");
		SequentialTransition intermediateAnnoucement = annouceMessage(message);
		intermediateAnnoucement.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				playOneMoreTurn(true,true);
			}		
		});
		intermediateAnnoucement.play();
		
	}
	
	public void triggerColorPicking(final boolean isRelatedToPlus4, final boolean continueToNextTurn) {
		log.debug("triggerColorPicking");
		
		Button redButton = new Button("Choose RED");
		redButton.getStyleClass().add("shiny-red");
		redButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				Color chosenColor = Color.RED;
				setGlobalColor(chosenColor);
				gameController.activePlayerChoseColor(chosenColor);
				
				mainGrid.getChildren().remove(referenceImageView);
				Image chosenImage = referenceImageView.retrieveAssociatedCardColor(chosenColor,isRelatedToPlus4);	
				referenceImageView.setImage(chosenImage);
				mainGrid.add(referenceImageView, 0, 1);
				
				eventsGrid.getChildren().clear();
				if(continueToNextTurn) {
					playOneMoreTurn(true,true);
				}
			}
		});
		
		Button blueButton = new Button("Choose BLUE");
		blueButton.getStyleClass().add("shiny-blue");
		blueButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				Color chosenColor = Color.BLUE;
				setGlobalColor(chosenColor);
				gameController.activePlayerChoseColor(chosenColor);

				mainGrid.getChildren().remove(referenceImageView);
				Image chosenImage = referenceImageView.retrieveAssociatedCardColor(chosenColor,isRelatedToPlus4);	
				referenceImageView.setImage(chosenImage);
				mainGrid.add(referenceImageView, 0, 1);
				
				eventsGrid.getChildren().clear();
				if(continueToNextTurn) {
					playOneMoreTurn(true,true);
				}
			}
		});
		
		Button greenButton = new Button("Choose GREEN");
		greenButton.getStyleClass().add("shiny-green");
		greenButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				Color chosenColor = Color.GREEN;
				setGlobalColor(chosenColor);
				gameController.activePlayerChoseColor(chosenColor);
				
				mainGrid.getChildren().remove(referenceImageView);
				Image chosenImage = referenceImageView.retrieveAssociatedCardColor(chosenColor,isRelatedToPlus4);	
				referenceImageView.setImage(chosenImage);
				mainGrid.add(referenceImageView, 0, 1);
				
				eventsGrid.getChildren().clear();
				if(continueToNextTurn) {
					playOneMoreTurn(true,true);
				}
			}
		});
		
		Button yellowButton = new Button("Choose YELLOW");
		yellowButton.getStyleClass().add("shiny-yellow");
		yellowButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				Color chosenColor = Color.YELLOW;
				setGlobalColor(chosenColor);
				gameController.activePlayerChoseColor(chosenColor);
			
				mainGrid.getChildren().remove(referenceImageView);
				Image chosenImage = referenceImageView.retrieveAssociatedCardColor(chosenColor,isRelatedToPlus4);	
				referenceImageView.setImage(chosenImage);
				mainGrid.add(referenceImageView, 0, 1);
				
				eventsGrid.getChildren().clear();
				if(continueToNextTurn) {
					playOneMoreTurn(true,true);
				}
			}
		});
		
		eventsGrid.add(redButton, 0, 3);
		eventsGrid.add(blueButton, 1, 3);
		eventsGrid.add(greenButton, 2, 3);
		eventsGrid.add(yellowButton, 4, 3);	
	}
	
	protected void setGlobalColor(Color chosenColor) {
		ObservableList<Node> imageViews = this.playerOneCardsGrid.getChildren();
		for(Node node : imageViews) {
			CustomImageView currentImageView = (CustomImageView)node;
			currentImageView.setGlobalColor(chosenColor);
		}
		imageViews = this.playerTwoCardsGrid.getChildren();
		for(Node node : imageViews) {
			CustomImageView currentImageView = (CustomImageView)node;
			currentImageView.setGlobalColor(chosenColor);
		}
	}

	public void triggerPlusX(final Collection<Card> cardsDrawn) {
		log.debug("triggerPlusX");
		SequentialTransition intermediateAnnoucement = annouceMessage("Forced to draw 2 cards");
		addCardToPlayer(gameController.getIndexFromPreviousPlayer(),cardsDrawn);
		intermediateAnnoucement.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addCardToPlayer(gameController.getIndexFromPreviousPlayer(),cardsDrawn);
			}
		});
		intermediateAnnoucement.play();
		playOneMoreTurn(true,true);
	}

	public void triggerBluffing(final Collection<Card> cardsDrawn, boolean isBluffing, boolean applyPenaltyToNextPlayer) {
		log.debug("triggerBluffing");
		SequentialTransition intermediateAnnoucement = annouceMessage("Forced to draw 4 cards");
		addCardToPlayer(gameController.getIndexFromPreviousPlayer(),cardsDrawn);
		//intermediateAnnoucement.getChildren().add(flip);
		intermediateAnnoucement.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final boolean isRelatedToPlus4 = true;
				triggerColorPicking(isRelatedToPlus4,true);
			}		
		});
		intermediateAnnoucement.play();
	}

	public void triggerSkipNextPlayer(String message) {
		log.debug("triggerSkipNextPlayer");
		SequentialTransition intermediateAnnoucement = annouceMessage(message);
		intermediateAnnoucement.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				playOneMoreTurn(false,false);
			}		
		});
		intermediateAnnoucement.play();
	}

	/* ========================================= GAME LOGIC ========================================= */

	private SequentialTransition startTurnForInitialPlayer(final CardsModelBean references) {
		SequentialTransition mainAnimation = annouceMessage("Your turn, " + this.graphicsPlayers.getAliasFromActivePlayer());
		PauseTransition part02 = new PauseTransition(Duration.millis(1));
		mainAnimation.getChildren().addAll(this.graphicsPlayers.generateEffectFromActivePlayer());
		mainAnimation.getChildren().add(part02);

		part02.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(graphicsPlayers.initialPlayerNeedsToDraw(references)) {
					final Card firstCardDrawn = gameController.drawOneCard();
					SequentialTransition intermediateAnnoucement = annouceMessage("You've drawn 1 card");
					SequentialTransition intermediateCardDraw = addCardToInitialPlayer(firstCardDrawn,references);
					intermediateAnnoucement.getChildren().add(intermediateCardDraw);
					intermediateCardDraw.setOnFinished(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							if(!references.isCompatibleWith(firstCardDrawn)) {
								final Card secondCardDrawn = gameController.drawOneCard();
								SequentialTransition innerAnnoucement = annouceMessage("You've drawn 1 card");
								SequentialTransition innerCardDraw = addCardToInitialPlayer(secondCardDrawn,references);
								innerAnnoucement.getChildren().add(innerCardDraw);
								innerCardDraw.setOnFinished(new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent event) {
										if(!references.isCompatibleWith(secondCardDrawn)) {
											SequentialTransition badLuckAnnoucement = annouceMessage("No cards playable");
											SequentialTransition flipSide = graphicsPlayers.generateEffectFromActivePlayer();
											badLuckAnnoucement.getChildren().add(flipSide);
											flipSide.setOnFinished(new EventHandler<ActionEvent>() {
												@Override
												public void handle(ActionEvent event) {
													gameController.activePlayerCannotPlay();
												}		
											});
											badLuckAnnoucement.play();
										}
									}		
								});
								innerAnnoucement.play();
							}
						}
					});
					intermediateAnnoucement.play();
				}
			}
		});
		return mainAnimation;
	}

	public void playOneTurn(final GraphicsReferences graphicsReferences, boolean cardsFlippingRequired) {
		log.debug("playOneTurn " + cardsFlippingRequired);
		final int playerIndex = graphicsReferences.getIndexFromActivePlayer();
		this.graphicsPlayers.setActivePlayer(playerIndex);

		SequentialTransition mainAnimation = new SequentialTransition();
		SequentialTransition annoucement = annouceMessage("Your turn, " + this.graphicsPlayers.getAliasFromActivePlayer());
		mainAnimation.getChildren().addAll(annoucement);
		if(cardsFlippingRequired) {
			mainAnimation.getChildren().addAll(this.graphicsPlayers.generateEffectFromActivePlayer());
		}
		
		annoucement.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(graphicsReferences.hasDrawnOneTime()) {
					log.debug("Drawn 1 card");
					SequentialTransition intermediateAnnoucement = annouceMessage("You've drawn 1 card");
					addCardToPlayer(playerIndex,graphicsReferences.getFirstCardDrawn());
					intermediateAnnoucement.setOnFinished(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							if(graphicsReferences.hasDrawnTwoTimes()) {
								log.debug("Drawn 2 cards");
								SequentialTransition innerAnnoucement = annouceMessage("You've drawn 1 card");
								addCardToPlayer(playerIndex,graphicsReferences.getSecondCardDrawn());
								innerAnnoucement.setOnFinished(new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent event) {
										if(graphicsReferences.hasNoPlayableCards()) {
											log.debug("No playable cards");
											SequentialTransition badLuckAnnoucement = annouceMessage("No playable cards");
											SequentialTransition flipSide = graphicsPlayers.generateEffectFromActivePlayer();
											badLuckAnnoucement.getChildren().add(flipSide);
											flipSide.setOnFinished(new EventHandler<ActionEvent>() {
												@Override
												public void handle(ActionEvent event) {
													gameController.activePlayerCannotPlay();
												}		
											});
											badLuckAnnoucement.play();
										}
									}		
								});
								innerAnnoucement.play();
							}
						}
					});
					intermediateAnnoucement.play();
				}
			}	
		});
		mainAnimation.play();
	}
	
	/* ========================================= UTILS ========================================= */

	private SequentialTransition annouceMessage(final String message) {
		if(this.textToDisplay.size() == 0) {
			this.eventsAnnouncer.setText(message);
		}
		this.textToDisplay.add(message);
		log.debug(this.textToDisplay.toString());
		
		FadeTransition displayAnimation = new FadeTransition(Duration.millis(750), this.eventsAnnouncer);
		displayAnimation.setFromValue(0.0);
		displayAnimation.setToValue(1.0);
		displayAnimation.setCycleCount(1);
		displayAnimation.setAutoReverse(false);

		PauseTransition pause = new PauseTransition(Duration.millis(1000));

		FadeTransition hideAnimation = new FadeTransition(Duration.millis(750), this.eventsAnnouncer);
		hideAnimation.setFromValue(1.0);
		hideAnimation.setToValue(0.0);
		hideAnimation.setCycleCount(1);
		hideAnimation.setAutoReverse(false);

		SequentialTransition annoucement = new SequentialTransition(displayAnimation,pause,hideAnimation);
		annoucement.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				textToDisplay.poll();
				eventsAnnouncer.setText(textToDisplay.peek());
				log.debug(textToDisplay.toString());
			}
		});
		return annoucement;
	}

	public void displayMessage(String message) {
		SequentialTransition coolMessage = annouceMessage(message);
		coolMessage.play();
	}
	
	public void setActivePlayer(int playerIndex) {
		this.graphicsPlayers.setActivePlayer(playerIndex);
	}

	public void displayScores() {
		// TODO Auto-generated method stub
		
	}
}