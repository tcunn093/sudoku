# Sudoku Generator and Solver
A REST-based web service for 
- generating and persisting solvable Sudoku puzzles
-  sending moves to persisted Sudoku puzzles
## Sudoku Generation Algorithm
Given a sector length (where sector length is the square root of the size of a Sudoku board),
generate a valid Sudoku board, namely:
1. All values in a given row are unique
2. All values in a given column are unique
3. All values in a given sector are unique

First, generate any values that are mutually exclusive. 
- Diagonal sectors can be populated at random since those values do not yet interact with any row or column values.

Second, recursively populate the rest of the board, checking on each recursion whether the board is completely filled. 
- If the recursion reaches a state where it is not possible to complete the board, reset the recursed value to null and continue.

Once the board is full, we have generated a completed Sudoku puzzle. Now, we can consider removing some values from the board. To create a solvable puzzle in a 9x9 grid, we need at least 17 tiles to remain populated. We can randomly remove tiles until the threshold of 17 is reached. 

TODO: We can enable different levels of difficulty by making the number of removed tiles configurable.

## Sudoku Solving Algorithm

Since we are capable of generating solved Sudoku, we can verify that a Sudoku is solvable using the same algorithm that can fill an empty Sudoku board. Given a set of rows and columns, recursively populate any missing values until a solution is found. The single difference is that some of the values are already populated for you.

# Sudoku REST Service cURL examples
Create a new game:

`curl -X POST localhost:8000 `

Get an existing game:

`curl -X GET localhost:8000/game/1634fc0b-aa05-4231-b226-2d4a76c80fb4`

Set a value at a position on the Sudoku board:

`curl -X PATCH localhost:8000/game/1634fc0b-aa05-4231-b226-2d4a76c80fb4/x/0/y/0 -H 'Content-Type: application/json' -d '{"value": 7}'`

Update a game board:

`curl -X PUT localhost:8000/game/1634fc0b-aa05-4231-b226-2d4a76c80fb4 -H 'Content-Type: application/json' -d '{"board": [...[...]]}'`

Delete a game:

`curl -X GET localhost:8000/game/1634fc0b-aa05-4231-b226-2d4a76c80fb4`

## Development

To launch the service locally, clone this repository and invoke:
1. Make sure Docker is installed. Download here: `https://docs.docker.com/get-docker/`
2. Clone this repository
3. Navigate to the root directory of the cloned repository
4. Invoke `docker-compose build; docker-compose up`

TODO: Deploy to Heroku once TTL for games is implemented

