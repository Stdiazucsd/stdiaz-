def print_1(data_file):
    with open(data_file, "r", encoding="utf-8") as f:
        read_line = f.readline()
        return read_line

result1 = print_1("C:\\Users\\Steve\\Desktop\\VsCODE PROJECT\\bundesliga_player.csv")
print(result1)

def avg_nationality_club(datafile):
    
    # Create an empty dictionary to store the count of nationalities for each club
    club_nationality = {}
    # Open the datafile and read it line by line
    with open(datafile, "r", encoding="utf-8") as f:
        # Skip the first line as it contains headers
        next(f)
        # Loop through each line in the file
        for line in f:
            # Split the line into a list of values
            club_data = line.strip().split(",")
            # Get the club and nationality from the list
            club = club_data[12]
            nationality = club_data[5]
            # If the club is not in the dictionary, add it with the nationality count as 1
            if club not in club_nationality:
                club_nationality[club] = {nationality: 1}
            else:
                # If the club is already in the dictionary, check if the nationality is in the inner dictionary
                if nationality not in club_nationality[club]:
                    # If the nationality is not in the inner dictionary, add it with the count as 1
                    club_nationality[club][nationality] = 1
                else:
                    # If the nationality is already in the inner dictionary, increment the count by 1
                    club_nationality[club][nationality] += 1
    results = {}
    # Loop through each club and its nationalities in the club_nationality dictionary
    for club, nationalities in club_nationality.items():
        # Create an empty dictionary to store the average count of nationalities for the club
        club_results = {}
        # Loop through each nationality and its count in the nationalities dictionary
        for nationality, count in nationalities.items():
            # Calculate the average count of the nationality for the club
            avg_nationality = count / sum(nationalities.values())
            # Add the nationality and its average count to the club_results dictionary
            club_results[nationality] = avg_nationality
        # Add the club and its nationalities with average counts to the results dictionary
        results[club] = club_results
    return results

result2 = avg_nationality_club("C:\\Users\\Steve\\Desktop\\VsCODE PROJECT\\bundesliga_player.csv")
for club, nationalities in result2.items():
    print(f"Club: {club}")
    for nationality, avg_nationality in nationalities.items():
        print(f" {nationality}'s portortion average on this team is {avg_nationality}")
    print()

def avg_height_nationality(datafile):
    
    nationality_height = {}
    
    # open the datafile and read the contents
    with open(datafile, "r", encoding="utf-8") as f:
        # skip the header row
        next(f)
        # iterate over each line in the file
        for line in f:
            # split the line into a list of values
            player_data = line.strip().split(",")
            # extract the height and nationality of the player
            height = float(player_data[4])
            nationality = player_data[5]
            # if the nationality is not already in the dictionary, add it with the current height
            if nationality not in nationality_height:
                nationality_height[nationality] = [height]
            # if the nationality is already in the dictionary, append the current height to the list of heights
            else:
                nationality_height[nationality].append(height)
    
    results = {}
    # iterate over each nationality and its corresponding list of heights
    for nationality, heights in nationality_height.items():
        # calculate the average height for the nationality
        avg_height = sum(heights) / len(heights)
        # add the nationality and its average height to the results dictionary
        results[nationality] = avg_height
    
    return results

result3 = avg_height_nationality("C:\\Users\\Steve\\Desktop\\VsCODE PROJECT\\bundesliga_player.csv")
for nationality, avg_height in result3.items():
    print(f"Average height for {nationality}: {avg_height}")

def avg_foot(datafile):
    
    left_footed_players = 0
    right_footed_players = 0
    with open(datafile, "r", encoding="utf-8") as f: # open the datafile in read mode
        next(f) # skip the header row
        for line in f: 
            player_data = line.strip().split(",") # split the line into a list of values
            foot = player_data[11] # get the value of the 12th column which represents the player's preferred foot
                               
            if foot == "left": # if the player is left-footed
                left_footed_players += 1 # increment the count of left-footed players
            elif foot == "right": # if the player is right-footed
                right_footed_players += 1 # increment the count of right-footed players
    if left_footed_players > right_footed_players: # if there are more left-footed players
        return "There are more left-footed players"
    elif right_footed_players > left_footed_players: # if there are more right-footed players
        return "There are more right-footed players"
    else: # if there are equal number of left-footed and right-footed players
        return "There are equal number of left-footed and right-footed players"
def avg_foot(datafile):
    
    left_footed_players = 0
    right_footed_players = 0

    # open the datafile and iterate through each line
    with open(datafile, "r", encoding="utf-8") as f:
        # skip the header line
        next(f)
        for line in f:
            # split the line into a list of player data
            player_data = line.strip().split(",")
            # get the player's preferred foot
            foot = player_data[11]
                               
            # increment the appropriate counter based on the player's preferred foot
            if foot == "left":
                left_footed_players += 1
            elif foot == "right":
                right_footed_players += 1

    # compare the number of left-footed and right-footed players and return the appropriate string
    if left_footed_players > right_footed_players:
        return "There are more left-footed players"
    elif right_footed_players > left_footed_players:
        return "There are more right-footed players"
    else:
        return "There are equal number of left-footed and right-footed players"

result4 = avg_foot("C:\\Users\\Steve\\Desktop\\VsCODE PROJECT\\bundesliga_player.csv")
print(result4)







