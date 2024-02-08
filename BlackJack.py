import random

# Function to create a deck of cards
def create_deck():
    suits = ['Hearts', 'Diamonds', 'Clubs', 'Spades']
    ranks = ['2', '3', '4', '5', '6', '7', '8', '9', '10', 'J', 'Q', 'K', 'A']
    deck = [(rank, suit) for suit in suits for rank in ranks]
    random.shuffle(deck)
    return deck

# Function to calculate the value of a hand
def calculate_hand_value(hand):
    value = 0
    ace_count = 0
    
    for card in hand:
        rank = card[0]
        if rank.isdigit():
            value += int(rank)
        elif rank in ['J', 'Q', 'K']:
            value += 10
        else:  # Ace
            ace_count += 1
            value += 11
    
    while value > 21 and ace_count > 0:
        value -= 10
        ace_count -= 1
    
    return value

# Function for betting
def bet(player_money):
    while True:
        try:
            bet_amount = int(input("Place your bet: $"))
            if bet_amount <= 0:
                print("Please enter a valid bet amount.")
                continue
            elif bet_amount > player_money:
                print("You cannot bet more than your balance.")
                continue
            else:
                return bet_amount
        except ValueError:
            print("Please enter a valid bet amount.")

# Function for player's turn
def player_turn(deck, player_hand, dealer_hand):
    while True:
        player_value = calculate_hand_value(player_hand)
        print("\nPlayer's Hand:", player_hand)
        print("Dealer's Hand:", "[Hidden Card]", dealer_hand[1])

        if player_value == 21:
            print("21! Player wins!")
            return 1
        elif player_value > 21:
            print("Player busts! Dealer wins!")
            return -1
        
        choice = input("Hit or Stand? ").lower()
        
        if choice == 'hit':
            player_hand.append(deck.pop())
        elif choice == 'stand':
            return 0

# Function for dealer's turn
def dealer_turn(deck, player_hand, dealer_hand):
    while True:
        dealer_value = calculate_hand_value(dealer_hand)
        if dealer_value >= 17:
            break
        dealer_hand.append(deck.pop())
    
    player_value = calculate_hand_value(player_hand)
    dealer_value = calculate_hand_value(dealer_hand)
    print("\nPlayer's Hand:", player_hand)
    print("Dealer's Hand:", dealer_hand)
    
    if dealer_value > 21:
        print("Dealer busts! Player wins!")
        return 1
    elif dealer_value == 21:
        print("Dealer gets 21! Dealer wins!")
        return -1
    elif dealer_value > player_value:
        print("Dealer wins!")
        return -1
    elif dealer_value < player_value:
        print("Player wins!")
        return 1
    else:
        print("It's a tie!")
        return 0

# Main game function
def play_blackjack():
    player_money = 100  # Starting amount for the player
    
    while True:
        print("\nYour current balance: ${}".format(player_money))
        if player_money <= 0:
            print("You don't have enough money to bet.")
            break
        
        bet_amount = bet(player_money)
        player_hand = []
        dealer_hand = []
        deck = create_deck()
        
        # Initial deal
        player_hand.append(deck.pop())
        dealer_hand.append(deck.pop())
        player_hand.append(deck.pop())
        dealer_hand.append(deck.pop())

        # Player's turn
        player_result = player_turn(deck, player_hand, dealer_hand)
        if player_result == -1:
            player_money -= bet_amount
        elif player_result == 1:
            player_money += bet_amount
        else:
            # Dealer's turn
            dealer_result = dealer_turn(deck, player_hand, dealer_hand)
            if dealer_result == -1:
                player_money -= bet_amount
            elif dealer_result == 1:
                player_money += bet_amount
        
        play_again = input("Do you want to play again? (y/n): ").lower()
        if play_again != 'y':
            print("Thank you for playing!")
            break

# Start the game
play_blackjack()
