package utt.fr.rglb.main.java.view.graphics.fxml;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

	/* ========================================= FXML ========================================= */
	
	@FXML private GridPane mainGrid;
	@FXML private GridPane playerTwoCardsGrid;
	@FXML private Label playerTwoName;
	@FXML private GridPane eventsGrid;
	@FXML private Label playerOneName;
	@FXML private GridPane playerOneCardsGrid;

	/* ========================================= CONSTRUCTOR ========================================= */

	public FXMLControllerGameScreen() {
		this.gameController = Server.getGameController();
		this.gameController.setCurrentFXMLController(this);
		this.textToDisplay = new LinkedList<String>();
	}

	/* ========================================= INITIALIZING ========================================= */

	/**
	 * Méthode appelée par le FXMLLoader quand l'initialisation de tous les éléments est terminée
	 * Permet d'ajouter/retirer dynamiquement des élements dans la fenêtre en réponse à la selection du nombre de joueurs par l'utilisateur
	 */
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		this.eventsAnnouncer = new Text("");
		this.eventsAnnouncer.getStyleClass().add("fancyText");
		this.eventsAnnouncer.setEffect(createSwaggifiedAnnoucementEffect());
		this.mainGrid.add(this.eventsAnnouncer, 0, 2);

		GameFlag gameFlag = this.gameController.retrieveEffectFromFirstCard();
		this.graphicsPlayers = new GraphicsPlayers(this.gameController.getAllPlayers(),this);

		CardsModelBean references = this.gameController.getReferences();
		this.referenceImageView = new CustomImageView(references.getLastCardPlayed(),this);
		mainGrid.add(this.referenceImageView, 0, 1);

		handlePlayerOne(references);
		handlePlayerTwo(references);

		this.gameController.applyEffectFromFirstCard(gameFlag);
		uncoverInitialCards(references);
	}

	/**
	 * Méthode permettant d'initiliaser le 1er joueur
	 * @param references Références de jeu
	 */
	private void handlePlayerOne(CardsModelBean references) {
		playerOneName.setText(this.graphicsPlayers.getAliasFromPlayerNumber(0));
		for(CustomImageView imageFromCard : this.graphicsPlayers.getDisplayableCardsFromPlayer(0, references)) {
			int currentCardIndex = playerOneCardsGrid.getChildren().size();
			playerOneCardsGrid.add(imageFromCard, currentCardIndex, 0);
		}
	}

	/**
	 * Méthode permettant d'initiliaser 2ème joueur
	 * @param references Références de jeu
	 */
	private void handlePlayerTwo(CardsModelBean references) {
		playerTwoName.setText(this.graphicsPlayers.getAliasFromPlayerNumber(1));
		for(CustomImageView imageFromCard : this.graphicsPlayers.getDisplayableCardsFromPlayer(1, references)) {
			int currentCardIndex = playerTwoCardsGrid.getChildren().size();
			playerTwoCardsGrid.add(imageFromCard, currentCardIndex, 0);
		}
	}

	/**
	 * Méthode permettant de retourner les cartes initiales
	 * @param references Références de jeu
	 */
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

	/**
	 * Méthode permettant de lancer un nouveau tour
	 * @param activePlayerNeedsCardsFlipping Booleén valant <code>TRUE</code> si les cartes du joueur actuel doivent être retournées, <code>FALSE</code> sinon
	 * @param nextPlayerNeedsCardFlipping Booleén valant <code>TRUE</code> si les cartes du joueur suivant doivent être retournées, <code>FALSE</code> sinon
	 */
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

	/**
	 * Méthode permettant de choisir une carte et de la jouer
	 * @param cardIndex Numéro de la carte
	 * @param thisImageView Image correspondant à la carte
	 */
	public void chooseThisCardAndPlayIt(int cardIndex, CustomImageView thisImageView) {
		Card chosenCard = this.graphicsPlayers.chooseCardFromActivePlayerAt(cardIndex,thisImageView);
		log.info("    --Played : " + chosenCard);
		boolean stillHasCards = this.graphicsPlayers.findIfActivePlayerStillHasCards();
		int indexFromPlayer = this.graphicsPlayers.getIndexFromActivePlayer();
		setNewReference(indexFromPlayer,thisImageView);
		GameFlag event = this.gameController.activePlayerChose(chosenCard,stillHasCards,this.graphicsPlayers.deservesTheRightToAnnounceUno());
		if(event.equals(GameFlag.NORMAL)) {
			playOneMoreTurn(true,true);
		}
	}

	/* ========================================= CARD DRAW ========================================= */

	/**
	 * Méthode permettant de définir la nouvelle référence
	 * @param indexFromPlayer Index du joueur
	 * @param thisImageView Image correspondante
	 */
	private void setNewReference(int indexFromPlayer, CustomImageView thisImageView) {
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

	/**
	 * Méthode permettant d'ajouter une carte au joueur initial
	 * @param card Carte à ajouter
	 * @param references Références de jeu
	 * @return Animation séquentielle associée
	 */
	private SequentialTransition addCardToInitialPlayer(Card card, CardsModelBean references) {
		int cardIndex = this.graphicsPlayers.findNextCardIndex(0);
		CustomImageView imageView = new CustomImageView(card, cardIndex, references.isCompatibleWith(card),this);
		this.graphicsPlayers.addCardToPlayer(0, card,imageView);
		this.playerOneCardsGrid.add(imageView,this.playerOneCardsGrid.getChildren().size(),0);
		SequentialTransition test = displayCardDrawing(imageView);
		return test;
	}

	/**
	 * Méthode permettant d'ajouter une collection de cartes à un joueur
	 * @param playerIndex Index du joueur
	 * @param cardsDrawn Cartes à ajouter
	 * @return Animation séquentielle associée
	 */
	public SequentialTransition addCardToPlayer(int playerIndex, Collection<Card> cardsDrawn) {
		Preconditions.checkState(this.graphicsPlayers != null);
		Preconditions.checkNotNull(cardsDrawn);
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

	/**
	 * Méthode permettant d'ajouter une carte à un joueur
	 * @param playerIndex Numéro du joueur
	 * @param cardDrawn Carte à ajouter
	 */
	private void addCardToPlayer(int playerIndex, Card cardDrawn) {
		Preconditions.checkState(this.graphicsPlayers != null);
		Preconditions.checkNotNull(cardDrawn);
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

	/**
	 * Méthode permettant d'animer l'action de pioche d'une carte
	 * @param imageView Image associée
	 * @return Animation séquentielle associée
	 */
	private SequentialTransition displayCardDrawing(CustomImageView imageView) {
		SequentialTransition flipSide = imageView.generateEffect();
		PauseTransition pause = new PauseTransition(Duration.millis(1000));
		return new SequentialTransition(flipSide,pause);
	}

	/* ========================================= EFFECTS ========================================= */

	/**
	 * Méthhode permettant d'avoir un rendu visuel sur l'inversion de tour
	 * @param message Message à afficher
	 */
	public void triggerReverseCurrentOrder(String message) {
		SequentialTransition intermediateAnnoucement = annouceMessage(message);
		intermediateAnnoucement.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				playOneMoreTurn(true,true);
			}		
		});
		intermediateAnnoucement.play();

	}

	/**
	 * Méthode permettant d'avoir un rendu visuel sur le choix de couleur
	 * @param isRelatedToPlus4 Vaut <code>TRUE</code> s'il est lié à un +4,<code>FALSE</code> sinon 
	 * @param continueToNextTurn Vaut <code>TRUE</code> si cela implique de changer de tour,<code>FALSE</code> sinon 
	 */
	public void triggerColorPicking(final boolean isRelatedToPlus4, final boolean continueToNextTurn) {
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

	/**
	 * Méthode permettant de définir la couleur globale
	 * @param chosenColor Couleur choisie
	 */
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

	/**
	 * Méthode permettant d'avoir un rendu visuel sur le jeu d'un +2/+4
	 * @param cardsDrawn
	 */
	public void triggerPlusX(final Collection<Card> cardsDrawn) {
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

	/**
	 * Méthode permettant d'avoir un rendu visuel sur lors d'un jeu d'un +4 (blff)
	 * @param cardsDrawn
	 * @param isBluffing
	 * @param applyPenaltyToNextPlayer
	 */
	public void triggerBluffing(final Collection<Card> cardsDrawn, boolean isBluffing, boolean applyPenaltyToNextPlayer) {
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

	/**
	 * Méthode permettant d'avoir un rendu visuel dans le cas du blocage du joueur suivant
	 * @param message
	 */
	public void triggerSkipNextPlayer(String message) {
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

	/**
	 * Méthode permettant de démarrer le tour du joueur initial
	 * @param references
	 * @return Animation séquentielle
	 */
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

	/**
	 * Méthode permettant de démarrer le tour d'un joueur (hors joueur initial)
	 * @param graphicsReferences
	 * @param cardsFlippingRequired
	 */
	public void playOneTurn(final GraphicsReferences graphicsReferences, boolean cardsFlippingRequired) {
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
					SequentialTransition intermediateAnnoucement = annouceMessage("You've drawn 1 card");
					addCardToPlayer(playerIndex,graphicsReferences.getFirstCardDrawn());
					intermediateAnnoucement.setOnFinished(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							if(graphicsReferences.hasDrawnTwoTimes()) {
								SequentialTransition innerAnnoucement = annouceMessage("You've drawn 1 card");
								addCardToPlayer(playerIndex,graphicsReferences.getSecondCardDrawn());
								innerAnnoucement.setOnFinished(new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent event) {
										if(graphicsReferences.hasNoPlayableCards()) {
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

	/**
	 * Méthode permettant d'annoncer un message animé
	 * @param message
	 * @return Animation séquentielle
	 */
	private SequentialTransition annouceMessage(final String message) {
		if(this.textToDisplay.size() == 0) {
			this.eventsAnnouncer.setText(message);
		}
		this.textToDisplay.add(message);

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
			}
		});
		return annoucement;
	}

	/**
	 * Méthode permettant d'afficher un message
	 * @param message
	 */
	public void displayMessage(String message) {
		SequentialTransition coolMessage = annouceMessage(message);
		coolMessage.play();
	}

	/**
	 * Méthode permettant d'indiquer quel est le joueur actif
	 * @param playerIndex
	 */
	public void setActivePlayer(int playerIndex) {
		this.graphicsPlayers.setActivePlayer(playerIndex);
	}

	/**
	 * Méthode permettant d'afficher les scores
	 * @return GameFlag
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GameFlag displayScores() {
		log.info("One player used all his cards, displaying scores");
		
		this.mainGrid.getChildren().clear();
		this.mainGrid.setAlignment(Pos.CENTER);

		Map<String, Integer> scores = this.gameController.displayIndividualTotalScore();
		ObservableList<ScoreModel> dataFromPlayers = FXCollections.observableArrayList();
		for(Entry<String, Integer> currentScoreEntry : scores.entrySet()) {
			dataFromPlayers.add(new ScoreModel(currentScoreEntry));
		}
		
		TableView table = new TableView();
		table.setEditable(false);
		
		TableColumn playerNameColumn = new TableColumn("Player");
		playerNameColumn.setCellValueFactory(new PropertyValueFactory<ScoreModel,String>("playerName"));
		playerNameColumn.setMinWidth(200);
		
		TableColumn totalScoreColumn = new TableColumn("Score");
		totalScoreColumn.setCellValueFactory(new PropertyValueFactory<ScoreModel,String>("totalScore"));
		totalScoreColumn.setMinWidth(200);
		
		TableColumn pointsToVictoryColumn = new TableColumn("Points to Victory");
		pointsToVictoryColumn.setCellValueFactory(new PropertyValueFactory<ScoreModel,String>("pointsToVictory"));
		pointsToVictoryColumn.setMinWidth(200);
		
		table.getColumns().addAll(playerNameColumn,totalScoreColumn,pointsToVictoryColumn);
		table.setItems(dataFromPlayers);
		
		Button goBackToGame = createValidationButton();
		
		this.mainGrid.add(table,0,0);
		this.mainGrid.add(goBackToGame,0,0);
		return GameFlag.GRAPHICS_GAME_WON;
	}

	/**
	 * Classe statique permettant de remplir la tableView associée aux scores individuels
	 */
	public static class ScoreModel {
		private final SimpleStringProperty playerName;
		private final SimpleStringProperty totalScore;
		private final SimpleStringProperty pointsToVictory;

		public ScoreModel(Entry<String, Integer> scoreEntry) {
			Integer pointsNeededToWin = 500 - scoreEntry.getValue();
			this.playerName = new SimpleStringProperty(scoreEntry.getKey());
			this.totalScore = new SimpleStringProperty(scoreEntry.getValue().toString());
			this.pointsToVictory = new SimpleStringProperty(pointsNeededToWin.toString());
		}

		public String getPlayerName() {
			return playerName.get();
		}

		public String getTotalScore() {
			return totalScore.get();
		}

		public String getPointsToVictory() {
			return pointsToVictory.get();
		}
	}
	
	/**
	 * Méthode permettant de créer le bouton qui sert à passer à l'écran suivant<br/>
	 * Quand l'utilisateur clique sur ce bouton, des vérifications sont faites pour s'assurer que les données entrées sont valides<br/>
	 * Tant que les données ne sont pas valides, l'utilisateur ne quitte pas cet écran
	 * @return Un bouton (JavaFX)
	 */
	private Button createValidationButton() {
		Button goForIt = new Button("Cool, but let's fight again");
		goForIt.getStyleClass().add("acceptButton");
		goForIt.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				log.info("Enough of score display, time for real business");
				mainGrid.getChildren().clear();
				gameController.resetAllCardsAndStartNewRound(mainGrid.getScene());
			}
		});
		return goForIt;
	}
}