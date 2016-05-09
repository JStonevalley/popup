import qualified Data.Set as Set
import Control.Monad (replicateM)

main :: IO()
main = do
  dimentions <- getLine
  strings <- replicateM (read $ head  $ words dimentions) getLine
  let regions = build strings ([], []) 1
  numQueries <- getLine
  queryLines <- replicateM (read numQueries) getLine
  let queries = map (toPoints . words) queryLines
  putStr $ unlines $ map (answer regions) queries

toPoints :: [String] -> (Point, Point)
toPoints [a,b,x,y] = ((read a,read b),(read x,read y))

type Point = (Int, Int)

build :: [String] -> ([Set.Set Point], [Set.Set Point]) -> Int -> ([Set.Set Point], [Set.Set Point])
build [] regions _ = regions
build (line:t) (zeroes, ones) r = build t (row line (zeroes, ones) (r, 1)) (r + 1)

row :: String -> ([Set.Set Point], [Set.Set Point]) -> Point -> ([Set.Set Point], [Set.Set Point])
row [] regions _ = regions
row (h:t) (zeroes, ones) (r, c)
  | h == '1' = row t (zeroes, append (Set.insert (r, c) Set.empty) ones (r, c) []) (r, c + 1)
  | h == '0' = row t (append (Set.insert (r, c) Set.empty) zeroes (r, c) [], ones) (r, c + 1)


append :: Set.Set Point -> [Set.Set Point] -> Point -> [Set.Set Point] -> [Set.Set Point]
append toMerge [] _ res = toMerge:res
append toMerge (h:t) (r, c) res
  | Set.member (r, c - 1) h || Set.member (r - 1, c) h = append (Set.union toMerge h) t (r, c) res
  | otherwise = append toMerge t (r, c) (h:res)

answer :: ([Set.Set Point], [Set.Set Point]) -> (Point, Point) -> String
answer (zeroes, ones) (a, b)
  | inSameSet zeroes a b = "binary"
  | inSameSet ones a b = "decimal"
  | otherwise = "neither"

inSameSet :: [Set.Set Point] -> Point -> Point -> Bool
inSameSet [] _ _ = False
inSameSet (h:t) p1 p2
  | Set.member p1 h && Set.member p2 h = True
  | Set.member p1 h || Set.member p2 h = False
  | otherwise = inSameSet t p1 p2
