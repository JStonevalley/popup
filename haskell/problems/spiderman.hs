import qualified Data.Map.Strict as Map
main :: IO()
main = do
  numCases <- getLine
  testCase (read numCases)

testCase :: Integer -> IO()
testCase 0 = putStr ""
testCase num = do
  _ <- getLine
  distancesString <- getLine
  putStrLn(exercise (map read (words distancesString)))
  testCase (num - 1)

k1 :: String
k1 = exercise [20,20,20,20]

k2 :: String
k2 = exercise [3,2,5,3,1,2]

empty :: String
empty = exercise []

height :: [Integer] -> Integer
height moves
  | sum moves == 0 = 0
  | sum moves `mod` 2 == 1 = 0
  | otherwise = sum moves `quot` 2

exercise :: [Integer] -> String
exercise [] = "IMPOSSIBLE"
exercise [_] = "IMPOSSIBLE"
exercise distances =
  if wall > 0
  then backtrack (compute distances [] wall)
  else "IMPOSSIBLE"
  where
    wall = height distances

compute :: [Integer] -> [Map.Map Integer Break] -> Integer -> [Map.Map Integer Break]
compute [] positions _ = positions
compute distances [] wall = compute distances [Map.insert 0 (Break U 0 0) Map.empty] wall
compute (distance:t) (positions:t2) wall = compute t (computeStep (Map.keys positions) positions Map.empty wall distance:(positions:t2)) wall

computeStep :: [Integer] -> Map.Map Integer Break -> Map.Map Integer Break -> Integer -> Integer -> Map.Map Integer Break
computeStep [] _ newPositions _ _ = newPositions
computeStep (prevPosition:t) prevPositions newPositions wall distance =
    let maybePrevBreak = Map.lookup prevPosition prevPositions
    in case maybePrevBreak of
      Nothing -> newPositions
      Just prevBreak -> computeStep t prevPositions (moveDown prevPosition prevBreak (moveUp prevPosition prevBreak newPositions distance wall) distance 0) wall distance

move :: Maybe Break -> Integer -> Break -> Map.Map Integer Break -> Map.Map Integer Break
move Nothing newPosition newBreak newPositions =
  Map.insert newPosition newBreak newPositions
move (Just prevBreak) newPosition newBreak newPositions
  | maxHeight prevBreak > maxHeight newBreak =
    Map.insert newPosition newBreak newPositions
  | otherwise = newPositions

moveUp :: Integer -> Break -> Map.Map Integer Break -> Integer -> Integer -> Map.Map Integer Break
moveUp prevPosition prevBreak newPositions distance limit
  | newPosition <= limit =
    move (Map.lookup newPosition newPositions) newPosition newBreak newPositions
  | otherwise = newPositions
  where newBreak = Break U distance (max (maxHeight prevBreak) newPosition)
        newPosition = prevPosition + distance

moveDown :: Integer -> Break -> Map.Map Integer Break -> Integer -> Integer -> Map.Map Integer Break
moveDown prevPosition prevBreak newPositions distance limit
  | newPosition >= limit =
    move (Map.lookup newPosition newPositions) newPosition newBreak newPositions
  |otherwise = newPositions
  where newBreak = Break D distance (max (maxHeight prevBreak) newPosition)
        newPosition = prevPosition - distance

backtrack :: [Map.Map Integer Break] -> String
backtrack positions = backtrackStep positions 0 ""

backtrackStep ::[Map.Map Integer Break]-> Integer -> String -> String
backtrackStep [] _ _ = "IMPOSSIBLE"
backtrackStep [_] _ path = path
backtrackStep (positions:t) nextPosition path =
  let maybeBreak = Map.lookup nextPosition positions
  in case maybeBreak of
    Nothing -> "IMPOSSIBLE"
    Just prevBreak ->
      if direction prevBreak == U
      then backtrackStep t (nextPosition - distance prevBreak) "U" ++ path
      else backtrackStep t (nextPosition + distance prevBreak) "D" ++ path

data Break = Break Direction Integer Integer deriving (Show)

direction :: Break -> Direction
direction (Break d _ _) = d

distance :: Break -> Integer
distance (Break _ d _) = d

maxHeight :: Break -> Integer
maxHeight (Break _ _ mh) = mh

data Direction = U | D
  deriving (Show, Eq)
