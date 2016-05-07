import qualified Data.Set as Set

main :: IO()
main = do
  dimentions <- getLine
  putStrLn "hej"
--  readLines (head (map read (words dimentions))) []

readLines :: Integer -> [[Char]] -> [[Char]]
readLines 0 lines = lines
readLines rows lines = do
    line <- getLine
    readLines rows - 1 line:lines

type Point = (Integer, Integer)
build :: [[Char]] -> ([Set.Set Point], [Set.Set Point]) -> ([Set.Set Point], [Set.Set Point])
build [] regions = regions
build (line:lines) (zeroes, ones) = 
