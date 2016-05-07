import qualified Data.Set as Set
import qualified Data.Char as Char
import Control.Monad (replicateM)

main :: IO()
main = do
  let elements = Set.fromList ["h","he","li","be","b","c","n","o","f","ne","na","mg","al","si","p","s","cl","ar","k","ca","sc","ti","v","cr","mn","fe","co","ni","cu","zn","ga","ge","as","se","br","kr","rb","sr","y","zr","nb","mo","tc","ru","rh","pd","ag","cd","in","sn","sb","te","i","xe","cs","ba","hf","ta","w","re","os","ir","pt","au","hg","tl","pb","bi","po","at","rn","fr","ra","rf","db","sg","bh","hs","mt","ds","rg","cn","fl","lv","la","ce","pr","nd","pm","sm","eu","gd","tb","dy","ho","er","tm","yb","lu","ac","th","pa","u","np","pu","am","cm","bk","cf","es","fm","md","no","lr"]
  numWords <- getLine
  strings <- replicateM (read numWords) getLine
  putStrLn $ unlines $ map (canSay elements False) strings

canSay :: Set.Set String -> Bool -> String -> String
canSay _ _  [] = "YES"

canSay elements this [l]
  | this || Set.member [Char.toLower l] elements = "YES"
  | otherwise = "NO"

canSay elements True (first:second:rest)
  | bothInPeriodic = canSay elements True (second:rest)
  | otherwise = canSay elements False (second:rest)
  where
    bothInPeriodic = Set.member [Char.toLower first, Char.toLower second] elements

canSay elements False (first:second:rest)
  | firstInPeriodic && bothInPeriodic = canSay elements True (second:rest)
  | firstInPeriodic = canSay elements False (second:rest)
  | bothInPeriodic = canSay elements False rest
  | otherwise = "NO"
  where
    firstInPeriodic = Set.member [Char.toLower first] elements
    bothInPeriodic = Set.member [Char.toLower first, Char.toLower second] elements
